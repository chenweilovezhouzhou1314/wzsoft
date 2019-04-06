package com.cw.auction.service.impl.auctionDmb;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cw.auction.dao.auctionDmb.IAuctionDmbDao;
import com.cw.auction.framework.basedao.IBaseDao;
import com.cw.auction.framework.baseservice.impl.BaseServiceImpl;
import com.cw.auction.service.auctionDmb.IAuctionDmbService;
import com.cw.auction.util.common.SystemUtils;


/**
 * 
 * 系统代码表配置ServiceImpl类
 * 
 * @version 
 * <pre>
 * Author	Version		Date		Changes
 * Administrator 	1.0  		2019年04月05日 	Created
 *
 * </pre>
 * @since 1.0
 */

@Service
public class AuctionDmbServiceImpl extends BaseServiceImpl implements IAuctionDmbService {
	
	@Autowired
	private IAuctionDmbDao iAuctionDmbDao;

	@Override
	public IBaseDao getBaseDao() {
		// TODO Auto-generated method stub
		return iAuctionDmbDao;
	}
    
	
	@Override
	public void beforeInsert(Map<String, Object> map) {
		// TODO Auto-generated method stub
		map.put("id", SystemUtils.buidLongId());
	}

	@Override
	public void beforeUpdate(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void beforeDelete(Map<String, Object> map) {
		// TODO Auto-generated method stub
		
	}


}
