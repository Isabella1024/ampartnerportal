package com.asiamiles.partnerportal.dao;

import java.util.List;

import com.asiamiles.partnerportal.domain.InstantRedeemPartnerGroup;

public interface InstantRedeemGroupDao {
	
	public int storeGroup(final InstantRedeemPartnerGroup instantRedeempartnerGroupModel, final String user);
	
	public int clearAgentGroup(final String partnerCode, String groupId);
	
	public int[] storeAgentGroup(final InstantRedeemPartnerGroup instantRedeempartnerGroupModel, final String user);
	
	public int clearPackageCodeGroup(final String partnerCode, String groupId);
	
	public int[] storePackageCodeGroup(final InstantRedeemPartnerGroup instantRedeempartnerGroupModel, final String user);
	
	public int clearGroupForPackageCode(final String partnerCode, String packageCode);
	
	public int[] storeGroupForPackageCode(final String[] groupId, final String packageCode, final String partnerCode, final String user);
	
	public List listInstantPackageCode(final String partnerCode);
	
	public List listGroup(final String partnerCode);
	
	public List queryPackageCodeByAgentId(String partnerCode, String agentId, String packageCode);
	
	public List queryAgentByGroupName(String partnerCode, String groupId);
	
	public List queryPackageByGroupName(String partnerCode, String groupId);
	
	public List queryGroupByPackageCode(final String partnerCode, final String packagecode);
	
	public List queryPackageByPartnerCode(final String partnerCode, final String packageCode);
	
	public int deleteGroup(String partnerCode, String groupId);
	
	public int deleteAgentGroupByAgentId(String agentId);
}
