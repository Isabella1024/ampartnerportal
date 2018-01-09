/*
 * Created on Sep 26, 2014
 *
 */
package com.asiamiles.partnerportal.util;

import java.util.List;

import com.asiamiles.partnerportal.dao.InstantRedeemGroupDao;

/**
 * TODO add Javadoc
 * @author CPPXUEC
 *
 */
public class InstantRedeemGroup {
	
	private InstantRedeemGroupDao instantRedeemGroupDao;

	public boolean verifyPackageCodeAllowanceByAgent (String partnerCode, String agentId, String packageCode) {
		
		List listGroups = instantRedeemGroupDao.queryGroupByPackageCode(partnerCode, packageCode);
		List listPackageBelongsGroup = instantRedeemGroupDao.queryPackageByPartnerCode(partnerCode, packageCode);
		if (listPackageBelongsGroup.size() > 0 && listGroups.size() == 0)	{
			return true;
		}
		else {
			List listPackageCode = instantRedeemGroupDao.queryPackageCodeByAgentId(partnerCode, agentId, packageCode);
			return (listPackageCode.size() >0 ) ? true : false;
		}
	}
	
	public InstantRedeemGroupDao getInstantRedeemGroupDao() {
		return instantRedeemGroupDao;
	}

	public void setInstantRedeemGroupDao(InstantRedeemGroupDao instantRedeemGroupDao) {
		this.instantRedeemGroupDao = instantRedeemGroupDao;
	}
}
