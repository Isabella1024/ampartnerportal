/*
 * Created on Jun 22, 2009
 *
 */
package com.asiamiles.partnerportal.cls.messages;

import java.util.List;

import com.asiamiles.partnerportal.cls.CLSResponse;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.XppDomDriver;

/**
 * @author CPPBENL
 *
 */
public class AgentRetrievalServletResponse extends CLSResponse {

	private String AdministratorAgentID;
	private String PartnerCode;
	private Integer ExportCount;
	private List agents; // List<AgentRetrievalServletResponse.AgentDetails>
	
	public AgentRetrievalServletResponse() {}
	
	/**
	 * @return Returns the AdministratorAgentID.
	 */
	public String getAdministratorAgentID() {
		return AdministratorAgentID;
	}
	/**
	 * @param AdministratorAgentID The AdministratorAgentID to set.
	 */
	public void setAdministratorAgentID(String adminID) {
		this.AdministratorAgentID = adminID;
	}
	/**
	 * @return Returns the agents.
	 */
	public List getAgents() {
		return agents;
	}
	/**
	 * @param agents The agents to set.
	 */
	public void setAgents(List agents) {
		this.agents = agents;
	}
	/**
	 * @return Returns the PartnerCode.
	 */
	public String getPartnerCode() {
		return PartnerCode;
	}
	/**
	 * @param PartnerCode The PartnerCode to set.
	 */
	public void setPartnerCode(String partnerCode) {
		this.PartnerCode = partnerCode;
	}
	/**
	 * @return Returns the exportCount.
	 */
	public Integer getExportCount() {
		return ExportCount;
	}
	/**
	 * @param exportCount The exportCount to set.
	 */
	public void setExportCount(Integer exportCount) {
		ExportCount = exportCount;
	}
	public class AgentDetails {
		private String agentID;
		private String email;
		private String familyName;
		private String firstName;
		private String agentType;
		private String remarks;
		private String updatedBy;
		//AML38938 Start
		private String partnerGroup;
		//AML38938 End
		/**
		 * @return Returns the agentID.
		 */
		public String getAgentID() {
			return agentID;
		}
		/**
		 * @param agentID The agentID to set.
		 */
		public void setAgentID(String agentID) {
			this.agentID = agentID;
		}
		/**
		 * @return Returns the agentType.
		 */
		public String getAgentType() {
			return agentType;
		}
		/**
		 * @param agentType The agentType to set.
		 */
		public void setAgentType(String agentType) {
			this.agentType = agentType;
		}
		/**
		 * @return Returns the email.
		 */
		public String getEmail() {
			return email;
		}
		/**
		 * @param email The email to set.
		 */
		public void setEmail(String email) {
			this.email = email;
		}
		/**
		 * @return Returns the familyName.
		 */
		public String getFamilyName() {
			return familyName;
		}
		/**
		 * @param familyName The familyName to set.
		 */
		public void setFamilyName(String familyName) {
			this.familyName = familyName;
		}
		/**
		 * @return Returns the firstName.
		 */
		public String getFirstName() {
			return firstName;
		}
		/**
		 * @param firstName The firstName to set.
		 */
		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}
		/**
		 * @return Returns the remarks.
		 */
		public String getRemarks() {
			return remarks;
		}
		/**
		 * @param remarks The remarks to set.
		 */
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}
		/**
		 * @return Returns the updatedBy.
		 */
		public String getUpdatedBy() {
			return updatedBy;
		}
		/**
		 * @param updatedBy The updatedBy to set.
		 */
		public void setUpdatedBy(String updateUser) {
			this.updatedBy = updateUser;
		}
		//AML38938 Start
		/**
		 * @return Returns the partnerGroup.
		 */
		public String getPartnerGroup() {
			return partnerGroup;
		}
		/**
		 * @param partnerGroup The partnerGroup to set.
		 */
		public void setPartnerGroup(String partnerGroup) {
			this.partnerGroup = partnerGroup;
		}
		//AML38938 End
	}
	
	public static AgentRetrievalServletResponse decodeFromXML(String xml) {
		XStream xstream = new XStream(new XppDomDriver());
		xstream.alias("AgentRetrievalServlet", AgentRetrievalServletResponse.class);
		xstream.alias("agent", AgentRetrievalServletResponse.AgentDetails.class);
		xstream.addImplicitCollection(AgentRetrievalServletResponse.class, "agents");
		Object obj = xstream.fromXML(xml);
		return (AgentRetrievalServletResponse)obj;
	}
}
