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
 * @author CPPKENW
 *
 */
public class RetrievalServletResponse extends CLSResponse {

	private String memberID;
	//CPPNXU start
	private String memberName;
	//CPPNXU end
	private List NARDetails; 

	public RetrievalServletResponse() {}	
	
	public class NARDetails {

		private String claimStatusCode;
		private String claimNo;
		private String securityCode;
		private String holderName;
		private String holderFirstName;
		private String consumptionStartDate;
		private String consumptionEndDate;
		private String collectionTime;
		private String collectionHandledBy;
		private String completionTime;
		private String completionHandledBy;
		private String packageCode;
		private String packageDescription;
		
		
		/**
		 * @return Returns the claimNo.
		 */
		public String getClaimNo() {
			return claimNo;
		}
		/**
		 * @param claimNo The claimNo to set.
		 */
		public void setClaimNo(String claimNo) {
			this.claimNo = claimNo;
		}
		/**
		 * @return Returns the claimStatusCode.
		 */
		public String getClaimStatusCode() {
			return claimStatusCode;
		}
		/**
		 * @param claimStatusCode The claimStatusCode to set.
		 */
		public void setClaimStatusCode(String claimStatusCode) {
			this.claimStatusCode = claimStatusCode;
		}
		/**
		 * @return Returns the collectionHandledBy.
		 */
		public String getCollectionHandledBy() {
			return collectionHandledBy;
		}
		/**
		 * @param collectionHandledBy The collectionHandledBy to set.
		 */
		public void setCollectionHandledBy(String collectionHandledBy) {
			this.collectionHandledBy = collectionHandledBy;
		}
		/**
		 * @return Returns the collectionTime.
		 */
		public String getCollectionTime() {
			return collectionTime;
		}
		/**
		 * @param collectionTime The collectionTime to set.
		 */
		public void setCollectionTime(String collectionTime) {
			this.collectionTime = collectionTime;
		}
		/**
		 * @return Returns the completionHandledBy.
		 */
		public String getCompletionHandledBy() {
			return completionHandledBy;
		}
		/**
		 * @param completionHandledBy The completionHandledBy to set.
		 */
		public void setCompletionHandledBy(String completionHandledBy) {
			this.completionHandledBy = completionHandledBy;
		}
		/**
		 * @return Returns the completionTime.
		 */
		public String getCompletionTime() {
			return completionTime;
		}
		/**
		 * @param completionTime The completionTime to set.
		 */
		public void setCompletionTime(String completionTime) {
			this.completionTime = completionTime;
		}
		/**
		 * @return Returns the consumptionEndDate.
		 */
		public String getConsumptionEndDate() {
			return consumptionEndDate;
		}
		/**
		 * @param consumptionEndDate The consumptionEndDate to set.
		 */
		public void setConsumptionEndDate(String consumptionEndDate) {
			this.consumptionEndDate = consumptionEndDate;
		}
		/**
		 * @return Returns the consumptionStartDate.
		 */
		public String getConsumptionStartDate() {
			return consumptionStartDate;
		}
		/**
		 * @param consumptionStartDate The consumptionStartDate to set.
		 */
		public void setConsumptionStartDate(String consumptionStartDate) {
			this.consumptionStartDate = consumptionStartDate;
		}
		/**
		 * @return Returns the holderFirstName.
		 */
		public String getHolderFirstName() {
			return holderFirstName;
		}
		/**
		 * @param holderFirstName The holderFirstName to set.
		 */
		public void setHolderFirstName(String holderFirstName) {
			this.holderFirstName = holderFirstName;
		}
		/**
		 * @return Returns the holderName.
		 */
		public String getHolderName() {
			return holderName;
		}
		/**
		 * @param holderName The holderName to set.
		 */
		public void setHolderName(String holderName) {
			this.holderName = holderName;
		}
		/**
		 * @return Returns the packageCode.
		 */
		public String getPackageCode() {
			return packageCode;
		}
		/**
		 * @param packageCode The packageCode to set.
		 */
		public void setPackageCode(String packageCode) {
			this.packageCode = packageCode;
		}
		/**
		 * @return Returns the packageDescription.
		 */
		public String getPackageDescription() {
			return packageDescription;
		}
		/**
		 * @param packageDescription The packageDescription to set.
		 */
		public void setPackageDescription(String packageDescription) {
			this.packageDescription = packageDescription;
		}
		/**
		 * @return Returns the securityCode.
		 */
		public String getSecurityCode() {
			return securityCode;
		}
		/**
		 * @param securityCode The securityCode to set.
		 */
		public void setSecurityCode(String securityCode) {
			this.securityCode = securityCode;
		}
	}
	
	public static RetrievalServletResponse decodeFromXML(String xml) {
		XStream xstream = new XStream(new XppDomDriver());
		xstream.alias("PaperlessRetrievalServlet", RetrievalServletResponse.class);
		xstream.alias("Entry", RetrievalServletResponse.NARDetails.class);
		xstream.addImplicitCollection(RetrievalServletResponse.class, "NARDetails");
		Object obj = xstream.fromXML(xml);
		return (RetrievalServletResponse)obj;
	}


	/**
	 * @return Returns the memberID.
	 */
	public String getMemberID() {
		return memberID;
	}
	/**
	 * @param memberID The memberID to set.
	 */
	public void setMemberID(String memberID) {
		this.memberID = memberID;
	}
	//CPPNXU start
	/**
	 * @return Returns the memberName.
	 */
	public String getMemberName() {
		return memberName;
	}

	/**
	 * @param memberName The memberName to set.
	 */
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	//CPPNXU end
	/**
	 * @return Returns the nARDetails.
	 */
	public List getNARDetails() {
		return NARDetails;
	}
	/**
	 * @param details The nARDetails to set.
	 */
	public void setNARDetails(List details) {
		NARDetails = details;
	}
}
