/**
 * 
 */
package com.asiamiles.partnerportal.domain;

/**
 * @author fuzhen.he
 *
 */
public class InstantRedeemPartnerGroup {
	
	private String partnerCode;
	
	private String groupId;
	
	private String type;
	
	private String[] agentId;
	
	private String[] packageCode;

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getAgentId() {
		return agentId;
	}

	public void setAgentId(String[] agentId) {
		this.agentId = agentId;
	}

	public String[] getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String[] packageCode) {
		this.packageCode = packageCode;
	}
	
	
}
