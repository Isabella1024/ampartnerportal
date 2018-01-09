/**
 * 
 */
package com.asiamiles.partnerportal.domain;

/**
 * @author rocky.y.wan
 *
 */
public class InstantRedeemAssignGroupToPackageCommand {
	
	private String partnerCode;
	
	private String packageCode;
	
	private String agentId;
	
	private String[] groupId;

	public String getPartnerCode() {
		return partnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	public String[] getGroupId() {
		return groupId;
	}

	public void setGroupId(String[] groupId) {
		this.groupId = groupId;
	}

	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getPackageCode() {
		return packageCode;
	}

	public void setPackageCode(String packageCode) {
		this.packageCode = packageCode;
	}
}
