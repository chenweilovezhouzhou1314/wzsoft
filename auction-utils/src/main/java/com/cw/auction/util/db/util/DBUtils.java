package com.cw.auction.util.db.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DBUtils {
	private static final Logger logger = LoggerFactory.getLogger(DBUtils.class);

	public static String dbType(Connection conn){
		DatabaseMetaData meta;
		try {
			meta = conn.getMetaData();
			String s1 = meta.getDatabaseProductName();
			//DB2返回的形式不一样, 会返回DB2/NT4之类的, 所以特殊处理
			if (s1.indexOf("DB2") == 0) {
				s1 = "DB2";
			} else if (s1.indexOf("Microsoft SQL Server") == 0){
				s1 = "MSSQL";
			} else if (s1.indexOf("DM DBMS") == 0){
			s1 = "DM";
			}
			return s1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "unknown";
	}
	public static boolean hasUserTable(Connection conn, String tableName) throws SQLException{
		Map<String, String> allTables = getAllUserTableNames(conn);
		tableName = tableName.toUpperCase();
		return allTables.containsKey(tableName);
	}
	public static boolean hasUserTable(Map<String, String> allTableNames, String tableName){
		return allTableNames.containsKey(tableName);
	}
	public static boolean hasUserTableAndColumn(Connection conn, String tableName, String columnName) throws SQLException{
		PreparedStatement tableInfoStatement = getTableMetaDataStatement(conn, tableName);
		if (tableInfoStatement == null){
			return false;
		}
		try {
			ResultSet tableInfoResultSet = tableInfoStatement.executeQuery();
			try{
				ResultSetMetaData tableInfo = tableInfoResultSet.getMetaData();
				for (int i = 1; i <= tableInfo.getColumnCount(); i++) {
					String colName = tableInfo.getColumnName(i);
					if (colName.equalsIgnoreCase(columnName)){
						return true;
					}
				}
				return false;
			}finally{
				tableInfoResultSet.close();
			}
		}finally{
			tableInfoStatement.close();
		}
	}
	public static boolean hasUserTableAndColumns(Connection conn, String tableName, String[] columnNames) throws SQLException{
		PreparedStatement tableInfoStatement = getTableMetaDataStatement(conn, tableName);
		if (tableInfoStatement == null){
			return false;
		}
		try {
			ResultSet tableInfoResultSet = tableInfoStatement.executeQuery();
			try{
				ResultSetMetaData tableInfo = tableInfoResultSet.getMetaData();
				for(int j=0; j< columnNames.length; j++){
					String columnName = columnNames[j];
					boolean founded = false;
					for (int i = 1; i <= tableInfo.getColumnCount(); i++) {
						String colName = tableInfo.getColumnName(i);
						if (colName.equalsIgnoreCase(columnName)){
							founded = true;
							break;
						}
					}
					if (!founded){
						return false;
					}
				}
				return true;
			}finally{
				tableInfoResultSet.close();
			}
		}finally{
			tableInfoStatement.close();
		}
	}
	public static Map<String, String> getTableIndexes(Connection conn, String tableName) throws SQLException{
		DatabaseMetaData dbmd=conn.getMetaData();
		ResultSet rs = dbmd.getIndexInfo(null, null, tableName, false, false);
		try{
			Map<String, String> indexes = new HashMap<String, String>();
			while(rs.next()){
				String iName = rs.getString("INDEX_NAME");
				int iType = rs.getInt("TYPE");
				if (iType != 0){
					indexes.put(iName, iName);
				}

			}
			return indexes;
		}finally{
			rs.close();
		}

	}
	public static boolean hasTableIndex(Connection conn, String tableName, String indexName) throws SQLException{
		if ("".equalsIgnoreCase(indexName)){
			return false;
		}
		Map<String, String> indexes = getTableIndexes(conn, tableName);

		return indexes.containsKey(indexName);
	}
	public static boolean hasTableIndexes(Connection conn, String tableName, String[] indexNames) throws SQLException{
		Map<String, String> tableIndexes = getTableIndexes(conn, tableName);

		for(int i=0; i<indexNames.length; i++){
			String indexName = indexNames[i];
			if (!tableIndexes.containsKey(indexName)){
				return false;
			}
		}
		return true;
	}

	public static String getUserTableColumnTypeName(Connection conn, String tableName, String colName) throws SQLException{
		PreparedStatement tableInfoStatement = getTableMetaDataStatement(conn, tableName);
		if (tableInfoStatement == null){
			return "";
		}
		try {
			ResultSet tableInfoResultSet = tableInfoStatement.executeQuery();
			try{
				ResultSetMetaData tableInfo = tableInfoResultSet.getMetaData();
				for (int i = 1; i <= tableInfo.getColumnCount(); i++) {
					String columnName = tableInfo.getColumnName(i);
					if (columnName.equalsIgnoreCase(columnName)){
						tableInfo.getColumnTypeName(i);
					}
				}
				return "";
			}finally{
				tableInfoResultSet.close();
			}
		}finally{
			tableInfoStatement.close();
		}
	}
	public static String getUserTableColumnSize(Connection conn, String tableName, String colName) throws SQLException{
		PreparedStatement tableInfoStatement = getTableMetaDataStatement(conn, tableName);
		if (tableInfoStatement == null){
			return "";
		}
		try {
			ResultSet tableInfoResultSet = tableInfoStatement.executeQuery();
			try{
				ResultSetMetaData tableInfo = tableInfoResultSet.getMetaData();
				for (int i = 1; i <= tableInfo.getColumnCount(); i++) {
					String columnName = tableInfo.getColumnName(i);
					if (columnName.equalsIgnoreCase(colName)){
						return Integer.toString(tableInfo.getPrecision(i));
					}
				}
				return "";
			}finally{
				tableInfoResultSet.close();
			}
		}finally{
			tableInfoStatement.close();
		}
	}
	public static boolean hasAllTables(Connection conn, String[] tables) throws SQLException{
		if ("KingbaseES".equalsIgnoreCase(dbType(conn))){
			for(int i=0; i<tables.length; i++){
				String tableName = tables[i];
				tableName = tableName.toUpperCase();
				if (!tableExists(conn, tableName)){
					return false;
				}
			}
		}else{
			Map<String, String> allTables = getAllUserTableNames(conn);
			for(int i=0; i<tables.length; i++){
				String tableName = tables[i];
				tableName = tableName.toUpperCase();
				if (!allTables.containsKey(tableName)){
					return false;
				}
			}
		}
		return true;
	}
	private static boolean tableExists(Connection conn, String tableName){
		String sql = "select * from " + tableName + " where 1<>1";
		Statement stat;
		try {
			stat = conn.createStatement();
			try{
				ResultSet rs = stat.executeQuery(sql);
				try{
					return true;
				}finally{
					rs.close();
				}
			}finally{
				stat.close();
			}
		} catch (SQLException e) {
			//需要吃掉这个异常;
			//e.printStackTrace();
		}
		return false;
	}


	public static Map<String, String> getAllUserTableNames(Connection conn) throws SQLException{
		Map<String, String> allTables = new HashMap<String, String>();
		DatabaseMetaData metaData = conn.getMetaData();
		String userName = metaData.getUserName();

		if ("DB2".equalsIgnoreCase(DBUtils.dbType(conn))){
			userName = "%";
		}
		ResultSet rs = null;
		if ("MSSQL".equalsIgnoreCase(dbType(conn))){
			rs = metaData.getTables(null, null, null, new String[]{"TABLE"});
		} else {
			rs = metaData.getTables(null, userName, "%", new String[]{"TABLE"});
		}
		try{
			while (rs.next()){
				String tableName = rs.getString("TABLE_NAME");
				//allTables.add(tableName);
				//oracle返回的是大写的, 此处统一处理;
				allTables.put(tableName.toUpperCase(), tableName.toUpperCase());
			}
		} finally{
			rs.close();
		}
		return allTables;
	}
	public static Map<String, PreparedStatement>  getAllUserTableInfo(Connection conn) throws SQLException{
		Map<String, PreparedStatement> ret = new HashMap<String, PreparedStatement>();
		Map<String, String> allTablesMap = getAllUserTableNames(conn);
		String[] allTables = (String[])allTablesMap.keySet().toArray();
		for(int i=0; i<allTables.length; i++){
			String tableName = allTables[i];
			PreparedStatement tableInfo = getTableMetaDataStatement(conn, tableName);
			ret.put(tableName, tableInfo);
		}
		return ret;
	}

	/*
	 * oracle数据库关闭掉stmt, ResultSetMetaData调用getColumnCount会跳错: java.sql.SQLRecoverableException: 关闭的语句
	 *
	public static ResultSetMetaData getTableMetaData(Connection conn, String tableName) throws SQLException{
		if (!DBUtils.hasUserTable(conn, tableName)){
			return null;
		}
		String sql = "select * from " + tableName + " where 1<>1";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		try {
			ResultSet rs = stmt.executeQuery(sql);
			try{
				ResultSetMetaData ret = rs.getMetaData();
				return ret;
			}finally{
				rs.close();
			}
		}finally{
			stmt.close();
		}
	}
	*/
	public static PreparedStatement getTableMetaDataStatement(Connection conn, String tableName) throws SQLException{
		if (!DBUtils.hasUserTable(conn, tableName)){
			return null;
		}
		String sql = "select * from " + tableName + " where 1<>1";
		PreparedStatement stmt;
		stmt = conn.prepareStatement(sql);
		return stmt;
	}

	public static boolean isDB2Cmd(String sql){
		if (sql == null || "".equalsIgnoreCase(sql.trim())){
			return false;
		}
		String s1 = sql.trim();
		s1 = s1.toLowerCase();
		//目前仅支持reorg命令判断
		if (s1.indexOf("reorg ") == 0) return true;
		return false;
	}

	public static String appendSqls(String filePath, List<String> sqls) throws IOException{
		String line; // 用来保存每行读取的内容
		StringBuffer buffer = new StringBuffer();
		//InputStream is = new FileInputStream(context.getRealPath(filePath));
		//InputStream is = new FileInputStream(context.getRealPath(filePath));

	    //String fileName = servletSqlPath + "/upgrade.properties";
		// InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(filePath);

		{
			InputStream is = null;
	        BufferedReader reader = null;
			try {
				is = new FileInputStream(filePath);
				reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				line = reader.readLine(); // 读取第一行
				while (line != null) {
					line = line.trim();
					if (!"".equalsIgnoreCase(line)){
						String s1 = line.trim();
						if (s1 != null && s1.indexOf("--") == 0){
							//忽略掉注释语句, 以支持注释语法;
							System.out.println("需要忽略的sql: " + s1);
						} else{
							if (line != null &&( line.lastIndexOf(";") == line.length() - 1 || line.toLowerCase().trim().endsWith("go")) ) {
								//oracle语句里, 分号";", 无法执行;
							    if(line.lastIndexOf(";") == line.length() - 1 ){
							        line = line.substring(0, line.lastIndexOf(";"));
							    }else{
							         if(line.toLowerCase().equals("go")){
							             line = " ";
							         }else{
							             line = line.substring(0, line.lastIndexOf("go")) + " ";
							         }
							    }
							    buffer.append(line);
								sqls.add(buffer.toString());
								//System.out.println("升级sql: " + buffer.toString()  );
								buffer  = null;
								buffer = new StringBuffer();

							} else {
								//加一个空格避免sql脚本里多行被弄成一行后出错
								buffer.append(line + " ");
							}
						}
					}
					line = reader.readLine(); // 读取下一行
				}
			} finally {
				reader.close();
				is.close();
			}
			return "";
		}
	}

	public static void execSqls(Connection conn, List<String> sqls, boolean commit) throws Exception{

		Statement sm= conn.createStatement();
		try{
			int startRunSqlLine = 0;
			for(int i=startRunSqlLine; i<sqls.size(); i++){
				String sql = sqls.get(i);
				if (!"".equalsIgnoreCase(sql.trim())){
					try {
						//sm.addBatch(sql);
						if ("DB2".equalsIgnoreCase(DBUtils.dbType(conn)) && DBUtils.isDB2Cmd(sql)){
							//注意sql命令里不能有单引号"'"
							sql = "CALL SYSPROC.ADMIN_CMD('" + sql + "')";
						}
						sm.execute(sql);
//						if (!conn.getAutoCommit()){
//							conn.commit();
//						}
						logger.debug("自动升级数据库, 执行成功sql:" + sql);
					} catch (SQLException e) {
						String s1 = "执行sql出错: " + sql;
						//SQLException ex =  new SQLException(s1);
						//e.getErrorCode()
						logger.error(s1, e);
						if (isIgnore(conn, String.valueOf(e.getErrorCode()))){
							logger.warn("表, 列, 索引重复错误, 忽略!");
						} else{
							// ucontext.logErrorDetail(logger);
							throw e;
						}
					}
				}
			}
			if (commit){
				conn.commit();
			}
		}finally{
			sm.close();
		}
	}

	private static boolean isIgnore(Connection conn, String errorCode){
		//return false;//测试用
		String dbType = DBUtils.dbType(conn);
		String ignoreCodes = "";
		if ("MySql".equalsIgnoreCase(dbType)){
			ignoreCodes = "1050," + //数据表已存在; 表'%s'已存在。
					"1060," + //列重名; 重复列名'%s'。
					"1061," + //索引重名;重复键名称'%s'。
					"1005,";//外键重名; 无法创建表'%s' (errno: %d)
		} else if ("Oracle".equalsIgnoreCase(dbType)){
			ignoreCodes = "955," + //数据表, 列已存在是同一个错误码; ORA-00955: 名称已由现有对象使用
					"1430," + //列重名;ORA-01430: 表中已经存在要添加的列
					"2275,";//外键重名; 找不到对应的ORA错误码说明
		} else if ("DB2".equalsIgnoreCase(dbType)){
			//-601 42710 试图创建（或重命名）已经存在的对象; 对于db2, -601代表对象重名, 现在已知的是表, 外键重名会跳这个错误码
			ignoreCodes = "-601," + //数据表已存在; -601 42710 试图创建（或重命名）已经存在的对象
					"-612,"+//列重名; -612 42711 在同一个表、索引或试图中不允许有重复列名
					//索引重名不报错
					"-601,";//外键重名
		}
		if (errorCode != null && !"".equalsIgnoreCase(errorCode)){
			errorCode = errorCode + ",";
			if (ignoreCodes.indexOf(errorCode) != -1){
				return true;
			}
		}
		return false;
	}
}
