package com.cw.auction.util.db;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cw.auction.util.db.util.DBUtils;
import com.cw.auction.util.db.util.SpringUtils;




public class UpgradeDatabase {

	private static final Logger logger = LoggerFactory.getLogger(UpgradeDatabase.class);

	private SqlSessionTemplate jdbcTemplateTool;

	public UpgradeDatabase() {
		this.jdbcTemplateTool = (SqlSessionTemplate) SpringUtils.getBean("sqlSession");
	}

	private Properties props;

	private String sqlFileStoreDir;
	private String verTableName = "product_version";
	private String[] coreTables = new String[]{""};
	private String sqlFileNamePrefix = "product_";
	private String[] sqlFileNames = {"product_1.0"};

	private Connection conn;

	public void init() throws SQLException {
		//
		//conn = jdbcTemplateTool.getJdbcTemplate().getDataSource().getConnection();
		conn = jdbcTemplateTool.getSqlSessionFactory().openSession().getConnection();
		
		//
		File dbDir = new File(Thread.currentThread().getContextClassLoader().getResource("/db").getPath());
		File propsFile = new File(dbDir.getAbsolutePath() + File.separator + "upgrade.properties");
		props = loadProperties(propsFile);

		sqlFileStoreDir = dbDir.getAbsolutePath() + File.separator + DBUtils.dbType(conn);

		//
		verTableName = this.props.getProperty("verTableName", "product_version");
		coreTables = split(this.props.getProperty("coreTables"));
		sqlFileNamePrefix = this.props.getProperty("sqlFileNamePrefix", "product_");
		sqlFileNames = split(this.props.getProperty("sqlFileNames"));
	}

	public void release() {
		if (conn != null)
			try {
				conn.close();
			} catch (SQLException e) {
			}
	}

	public void doUpgrade() throws Exception {
		if (sqlFileNames == null || sqlFileNames.length == 0
				|| coreTables == null || coreTables.length == 0
				|| verTableName == null || verTableName.length() == 0
				|| sqlFileNamePrefix == null || sqlFileNamePrefix.length() == 0) {
			throw new Exception("检测upgrade.properties配置参数失败，请手动检查和执行升级");
		}

		// 1. 获取脚本范围
		int endSqlFileIndex = sqlFileNames.length - 1;
		String ver = getVerFromSqlFileNames(endSqlFileIndex);

		int startSqlFileIndex = -1;
		String oldVer;

		boolean processCacheFile1 = false;
		if (!DBUtils.hasAllTables(conn, coreTables)) { // 基本表不存在
			// 空库，所有脚本都执行，缓存记录不用处理
			startSqlFileIndex = 0;
			oldVer = "0.1";
		} else if (!DBUtils.hasAllTables(conn, new String[]{verTableName})) { // 基本表存在，版本表不存在
			// 第一个脚本不执行，缓存记录需要处理
			startSqlFileIndex = 1;
			oldVer = getVerFromSqlFileNames(startSqlFileIndex - 1);
			processCacheFile1 = true;
		} else {
			oldVer = getDBVer(conn, coreTables, verTableName);
			String sqlFileName = sqlFileNamePrefix + oldVer;
			for (int i = 0; i < sqlFileNames.length; i++) {
				if (sqlFileName.equals(sqlFileNames[i])) {
					startSqlFileIndex = i + 1;
					break;
				}
			}
			// 已经是最后的版本了，不再处理
			if (startSqlFileIndex == -1) {
				logger.warn("没有从数据库获取到当前表结构版本，请手动检查和执行升级");
			}
			if (startSqlFileIndex > endSqlFileIndex) {
				logger.info("不需要执行升级数据库");
				return;
			}
		}

		logger.info(String.format("从版本[%s]升级到版本[%s]", oldVer, ver));

		// 2. 开始执行脚本
		conn.setAutoCommit(false);
		try {
			// 执行sql文件
			logger.info("-------- 开始执行SQL脚本升级 --------");
			this.execSqlFile(startSqlFileIndex, endSqlFileIndex);
			conn.commit();
			logger.info("-------- 执行SQL脚本升级完成 --------");
		} catch(Exception e) {
			conn.rollback();
			logger.error("-------- 执行SQL脚本升级失败 --------");
			throw new Exception("执行SQL脚本文件失败，回滚所有SQL脚本，请手动检查和执行升级", e);
		}

		// 3. 保存版本号
		try {
			// 写入版本号
			updateVer(conn, verTableName, ver, oldVer);
		} catch(Exception e) {
			throw new Exception("保存升级版本号失败", e);
		}

		// 4. 其他，处理缓存文件
		if (processCacheFile1) {
			this.processCacheFile();
		}

		logger.info("升级数据库完成");
	}

	private void execSqlFile(int startSqlFileIndex, int endSqlFileIndex) throws Exception {
		for (int i = startSqlFileIndex; i <= endSqlFileIndex; i++) {
			String sqlFileName = sqlFileNames[i];
			String path = sqlFileStoreDir + File.separator + sqlFileName;
			logger.info("执行数据库脚本文件：" + path);
			List<String> sqls  = new ArrayList<String>();
			DBUtils.appendSqls(path, sqls);
			DBUtils.execSqls(conn, sqls, false);
		}
	}

	private String getVerFromSqlFileNames(int endSqlFileIndex) {
		if (endSqlFileIndex >= sqlFileNames.length)
			return "unknown";
		String sqlFileName = sqlFileNames[endSqlFileIndex];
		String ver = sqlFileName.replace(sqlFileNamePrefix, "");
		return ver;
	}

	//

	public static void updateVer(Connection conn, String verTableName, String version, String oldVer) throws SQLException{
		Date d1 = new Date();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String curDateTimeStr = df.format(d1);

		String vComment = curDateTimeStr + " " + oldVer + "->" + version + "\n";
		int l = vComment.length();
		if (l>200) l = 200;
		vComment = vComment.substring(0, l-1);

		String sql = "update " + verTableName + " set fVersion=?, fVersion2=?, fVersion3=?, fTimeStr=?, fComment=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		try{
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, version);
			stmt.setString(2, version);
			stmt.setString(3, oldVer);
			stmt.setString(4, curDateTimeStr);
			stmt.setString(5, vComment);
			stmt.execute();
			if (!conn.getAutoCommit()){
				conn.commit();
			}
		}finally{
			stmt.close();
		}
	}

	public static String getDBVer(Connection conn, String[] coreTables, String verTableName){
		try {
			if (!DBUtils.hasAllTables(conn, coreTables)){
				return "0.1";
			} else {
				//版本表不存在, 无法判断数据库的版本
				if (!DBUtils.hasAllTables(conn, new String[]{verTableName})){
					return "1.0";
				} else {
					String sql = "select * from " + verTableName;
					Statement stmt = conn.createStatement();
					try{
						ResultSet rs = stmt.executeQuery(sql);
						try{
							if (rs.next()){
								String v1 = rs.getString("fVersion");
								String v2 = rs.getString("fVersion2");
								//版本表的两个字段不一致时返回错误标示，但是按update语句，不会不一致
								if (v1.equalsIgnoreCase(v2)){
									return v1;
								}else {
									return "error";
								}
							} else {
								return "exception";
							}
						}finally{
							rs.close();
						}
					}finally{
						stmt.close();
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return "exception";
	}

	private static Properties loadProperties(File propsFile) {
		Properties props = new Properties();
		InputStream is = null;
		try {
			is = new FileInputStream(propsFile);
			props.load(is);
		} catch (IOException e) {
			logger.error("加载配置文件出错" + e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
				}
			}
		}
		return props;
	}

	private static String[] split(String str) {
		if (str == null || str.length() == 0)
			return new String[0];
		String[] arr = str.split(",");
		String[] arr1 = new String[arr.length];
		int idx = 0;
		for (String s : arr) {
			if (s == null || s.length() == 0)
				continue;
			s = s.trim();
			if (s.length() == 0)
				continue;
			arr1[idx++] = s;
		}
		if (idx == arr1.length) {
			return arr1;
		} else {
			String[] arr2 = new String[idx];
			System.arraycopy(arr1, 0, arr2, 0, idx);
			return arr2;
		}
	}

	//
	private void processCacheFile() throws Exception {
	}

}
