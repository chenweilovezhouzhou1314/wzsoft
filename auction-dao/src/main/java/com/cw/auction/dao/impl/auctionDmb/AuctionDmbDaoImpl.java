package com.cw.auction.dao.impl.auctionDmb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cw.auction.dao.auctionDmb.IAuctionDmbDao;
import com.cw.auction.framework.basedao.impl.BaseDaoImpl;
import com.cw.auction.framework.baseentity.IBaseMapper;
import com.cw.auction.mapper.auctionDmb.IAuctionDmbMapper;

/**
 * 
 * 系统代码表配置DaoImpl
 * 
 * @version 
 * <pre>
 * Author  Administrator	
 * Version 1.0 				
 * Date	 2019年04月05日
 * </pre>
 * @since 1.
 */

@Repository 
public class AuctionDmbDaoImpl extends BaseDaoImpl implements IAuctionDmbDao {
	
	@Autowired
	private IAuctionDmbMapper  iAuctionDmbMapper;

	@Override
	public IBaseMapper getBaseMapper() {
		// TODO Auto-generated method stub
		return iAuctionDmbMapper;
	}

}
