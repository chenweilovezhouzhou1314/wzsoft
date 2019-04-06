package com.cw.auction.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.cw.auction.util.db.UpgradeDatabase;



/**
 * 自动升级脚本
 * @author Administrator
 *
 */
@Component
public class InitParameterListener implements ApplicationListener<ContextRefreshedEvent> {
    
	private Logger logger = LoggerFactory.getLogger(InitParameterListener.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		if(event.getApplicationContext().getParent()!=null){
			//checkAndUpgradeDatabase();
		}
		
		
	}
	/**
	 * @Comments ：自动升级数据库
	 * @Author ：陈伟
	 * @Group : A组
	 * @Worker: 1001
	 * @Date ：2017年7月24日 下午5:12:04
	 */
	private void checkAndUpgradeDatabase() {
		logger.info("检测和执行升级数据库。。。");
		UpgradeDatabase upgrade = new UpgradeDatabase();
		try {
			upgrade.init();
			upgrade.doUpgrade();
		} catch (Exception e) {
			logger.error("检测和执行升级数据库失败，需要手动升级：" + e.getMessage(), e);
			throw new RuntimeException("检测和执行升级数据库失败，需要手动升级：" + e.getMessage(), e);
		} finally {
			upgrade.release();
		}
	}

}
