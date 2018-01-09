/*
 * Created on Jun 22, 2009
 *
 */
package com.asiamiles.partnerportal.domain;

import java.util.Date;
import java.util.List;


/**
 * Data Object for a Unbilled NAR Partner and its list of establishments (i.e. billing departments)
 * @author CPPKENW
 *
 */
public class Unbilled {

	private String partnerCode;
	private String partnerName;
	private String monthlyCutoffDay;
	private String timeZone;

	private List establishments; 

	public Unbilled() {}	
	
	public class Establishment {

		private String establishmentCode;
		private String establishmentName;
		private Date oldestCollectionTimestamp;
		private Date latestCollectionTimestamp;
		private Date lastBillingDate;

		/**
		 * @return Returns the establishmentCode.
		 */
		public String getEstablishmentCode() {
			return establishmentCode;
		}
		/**
		 * @param establishmentCode The establishmentCode to set.
		 */
		public void setEstablishmentCode(String establishmentCode) {
			this.establishmentCode = establishmentCode;
		}
		/**
		 * @return Returns the establishmentName.
		 */
		public String getEstablishmentName() {
			return establishmentName;
		}
		/**
		 * @param establishmentName The establishmentName to set.
		 */
		public void setEstablishmentName(String establishmentName) {
			this.establishmentName = establishmentName;
		}
		/**
		 * @return Returns the latestCollectionTimestamp.
		 */
		public Date getLatestCollectionTimestamp() {
			return latestCollectionTimestamp;
		}
		/**
		 * @param latestCollectionTimestamp The latestCollectionTimestamp to set.
		 */
		public void setLatestCollectionTimestamp(Date latestCollectionTimestamp) {
			this.latestCollectionTimestamp = latestCollectionTimestamp;
		}
		/**
		 * @return Returns the oldCollectionTimestamp.
		 */
		public Date getOldestCollectionTimestamp() {
			return oldestCollectionTimestamp;
		}
		/**
		 * @param oldCollectionTimestamp The oldCollectionTimestamp to set.
		 */
		public void setOldestCollectionTimestamp(Date oldestCollectionTimestamp) {
			this.oldestCollectionTimestamp = oldestCollectionTimestamp;
		}
		/**
		 * @return Returns the lastBillingDate.
		 */
		public Date getLastBillingDate() {
			return lastBillingDate;
		}
		/**
		 * @param lastBillingDate The lastBillingDate to set.
		 */
		public void setLastBillingDate(Date lastBillingDate) {
			this.lastBillingDate = lastBillingDate;
		}
	}
	
	/**
	 * @return Returns the establishment.
	 */
	public List getEstablishments() {
		return establishments;
	}
	/**
	 * @param establishment The establishment to set.
	 */
	public void setEstablishments(List establishments) {
		this.establishments = establishments;
	}
	/**
	 * @return Returns the monthlyCutoffDay.
	 */
	public String getMonthlyCutoffDay() {
		return monthlyCutoffDay;
	}
	/**
	 * @param monthlyCutoffDay The monthlyCutoffDay to set.
	 */
	public void setMonthlyCutoffDay(String monthlyCutoffDay) {
		this.monthlyCutoffDay = monthlyCutoffDay;
	}
	/**
	 * @return Returns the partnerCode.
	 */
	public String getPartnerCode() {
		return partnerCode;
	}
	/**
	 * @param partnerCode The partnerCode to set.
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
	 * @param partnerName The partnerName to set.
	 */
	public void setPartnerName(String partnerName) {
		this.partnerName = partnerName;
	}
	/**
	 * @return Returns the timeZone.
	 */
	public String getTimeZone() {
		return timeZone;
	}
	/**
	 * @param timeZone The timeZone to set.
	 */
	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}
}
