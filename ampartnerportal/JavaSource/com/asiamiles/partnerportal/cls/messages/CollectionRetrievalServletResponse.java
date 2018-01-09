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
public class CollectionRetrievalServletResponse extends CLSResponse {

	private String actionCode;
	private String partnerCode;
	private String partnerName;

	// Steven ADD for AML34188 20140715 START
	private String pageNextValue;
	private String pageNextPkgValue;
	// Steven ADD for AML34188 20140715 END

	// Steven ADD for AML34188 20140829 START
	private String pagePreValue;
	private String pagePrePkgValue;
	private String totalNum;
	private String totalPage;
	// Steven ADD for AML34188 20140829 END

	private List NARDetails;

	public CollectionRetrievalServletResponse() {
	}

	/**
	 * @return Returns the actionCode.
	 */
	public String getActionCode() {
		return actionCode;
	}

	/**
	 * @param actionCode
	 *            The actionCode to set.
	 */
	public void setActionCode(String actionCode) {
		this.actionCode = actionCode;
	}

	/**
	 * @return Returns the nARDetails.
	 */
	public List getNARDetails() {
		return NARDetails;
	}

	/**
	 * @param details
	 *            The nARDetails to set.
	 */
	public void setNARDetails(List details) {
		NARDetails = details;
	}

	/**
	 * @return Returns the partnerCode.
	 */
	public String getPartnerCode() {
		return partnerCode;
	}

	/**
	 * @param partnerCode
	 *            The partnerCode to set.
	 */
	public void setPartnerCode(String partnerCode) {
		this.partnerCode = partnerCode;
	}

	/**
	 * @return Returns the partnerName.
	 */
	public String getPartnerName() {
		return partnerName;
	}

	/**
	 * @param partnerName
	 *            The partnerName to set.
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}

	public String getPageNextValue() {
		return pageNextValue;
	}

	public void setPageNextValue(String pageNextValue) {
		this.pageNextValue = pageNextValue;
	}

	public String getPageNextPkgValue() {
		return pageNextPkgValue;
	}

	public void setPageNextPkgValue(String pageNextPkgValue) {
		this.pageNextPkgValue = pageNextPkgValue;
	}

	public String getPagePreValue() {
		return pagePreValue;
	}

	public void setPagePreValue(String pagePreValue) {
		this.pagePreValue = pagePreValue;
	}

	public String getPagePrePkgValue() {
		return pagePrePkgValue;
	}

	public void setPagePrePkgValue(String pagePrePkgValue) {
		this.pagePrePkgValue = pagePrePkgValue;
	}
	
	

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}



	public class NARDetails {

		private String memberID;
		private String claimNo;
		private String collectionTime;
		private String collectionHandledBy;
		private String completionTime;
		private String completionHandledBy;
		private String billedDate;
		private String holderName;
		private String holderFirstName;
		private String receiptNo;
		private String remarks;
		private String remarks2;
		private String packageCode;
		private String establishmentCode;
		private String packageDescription;

		/**
		 * @return Returns the billedDate.
		 */
		public String getBilledDate() {
			return billedDate;
		}

		/**
		 * @param billedDate
		 *            The billedDate to set.
		 */
		public void setBilledDate(String billedDate) {
			this.billedDate = billedDate;
		}

		/**
		 * @return Returns the claimNo.
		 */
		public String getClaimNo() {
			return claimNo;
		}

		/**
		 * @param claimNo
		 *            The claimNo to set.
		 */
		public void setClaimNo(String claimNo) {
			this.claimNo = claimNo;
		}

		/**
		 * @return Returns the collectionHandledBy.
		 */
		public String getCollectionHandledBy() {
			return collectionHandledBy;
		}

		/**
		 * @param collectionHandledBy
		 *            The collectionHandledBy to set.
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
		 * @param collectionTime
		 *            The collectionTime to set.
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
		 * @param completionHandledBy
		 *            The completionHandledBy to set.
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
		 * @param completionTime
		 *            The completionTime to set.
		 */
		public void setCompletionTime(String completionTime) {
			this.completionTime = completionTime;
		}

		/**
		 * @return Returns the establishmentCode.
		 */
		public String getEstablishmentCode() {
			return establishmentCode;
		}

		/**
		 * @param establishmentCode
		 *            The establishmentCode to set.
		 */
		public void setEstablishmentCode(String establishmentCode) {
			this.establishmentCode = establishmentCode;
		}

		/**
		 * @return Returns the holderFirstName.
		 */
		public String getHolderFirstName() {
			return holderFirstName;
		}

		/**
		 * @param holderFirstName
		 *            The holderFirstName to set.
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
		 * @param holderName
		 *            The holderName to set.
		 */
		public void setHolderName(String holderName) {
			this.holderName = holderName;
		}

		/**
		 * @return Returns the memberID.
		 */
		public String getMemberID() {
			return memberID;
		}

		/**
		 * @param memberID
		 *            The memberID to set.
		 */
		public void setMemberID(String memberID) {
			this.memberID = memberID;
		}

		/**
		 * @return Returns the packageCode.
		 */
		public String getPackageCode() {
			return packageCode;
		}

		/**
		 * @param packageCode
		 *            The packageCode to set.
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
		 * @param packageDescription
		 *            The packageDescription to set.
		 */
		public void setPackageDescription(String packageDescription) {
			this.packageDescription = packageDescription;
		}

		/**
		 * @return Returns the receiptNo.
		 */
		public String getReceiptNo() {
			return receiptNo;
		}

		/**
		 * @param receiptNo
		 *            The receiptNo to set.
		 */
		public void setReceiptNo(String receiptNo) {
			this.receiptNo = receiptNo;
		}

		/**
		 * @return Returns the remarks.
		 */
		public String getRemarks() {
			return remarks;
		}

		/**
		 * @param remarks
		 *            The remarks to set.
		 */
		public void setRemarks(String remarks) {
			this.remarks = remarks;
		}

		/**
		 * @return Returns the remarks2.
		 */
		public String getRemarks2() {
			return remarks2;
		}

		/**
		 * @param remarks2
		 *            The remarks2 to set.
		 */
		public void setRemarks2(String remarks2) {
			this.remarks2 = remarks2;
		}

	}

	public static CollectionRetrievalServletResponse decodeFromXML(String xml) {
		XStream xstream = new XStream(new XppDomDriver());
		xstream.alias("PaperlessCollectionRetrievalServlet",
				CollectionRetrievalServletResponse.class);
		xstream.alias("claim",
				CollectionRetrievalServletResponse.NARDetails.class);
		xstream.addImplicitCollection(CollectionRetrievalServletResponse.class,
				"NARDetails");
		Object obj = xstream.fromXML(xml);
		return (CollectionRetrievalServletResponse) obj;
	}

}
