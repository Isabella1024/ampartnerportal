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
public class InvoiceRetrievalServletResponse extends CLSResponse {

	private String partnerCode;
	private String partnerName;
	private String monthlyCutoffDay;
	private String timeZone;

	private List Establishment; 

	public InvoiceRetrievalServletResponse() {}	
	
	public class Establishment {

		private String establishmentCode;
		private String establishmentName;
		private String oldestCollectionTimestamp;
		private String latestCollectionTimestamp;
		private String lastBillingDate;

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
		public String getLatestCollectionTimestamp() {
			return latestCollectionTimestamp;
		}
		/**
		 * @param latestCollectionTimestamp The latestCollectionTimestamp to set.
		 */
		public void setLatestCollectionTimestamp(
				String latestCollectionTimestamp) {
			this.latestCollectionTimestamp = latestCollectionTimestamp;
		}
		/**
		 * @return Returns the oldCollectionTimestamp.
		 */
		public String getOldestCollectionTimestamp() {
			return oldestCollectionTimestamp;
		}
		/**
		 * @param oldCollectionTimestamp The oldCollectionTimestamp to set.
		 */
		public void setOldestCollectionTimestamp(String oldCollectionTimestamp) {
			this.oldestCollectionTimestamp = oldCollectionTimestamp;
		}
		/**
		 * @return Returns the lastBillingDate.
		 */
		public String getLastBillingDate() {
			return lastBillingDate;
		}
		/**
		 * @param lastBillingDate The lastBillingDate to set.
		 */
		public void setLastBillingDate(String lastBillingDate) {
			this.lastBillingDate = lastBillingDate;
		}
	}
	
	public static InvoiceRetrievalServletResponse decodeFromXML(String xml) {
		XStream xstream = new XStream(new XppDomDriver());
		xstream.alias("PaperlessInvoiceRetrievalServlet", InvoiceRetrievalServletResponse.class);
		xstream.alias("entry", InvoiceRetrievalServletResponse.Establishment.class);
		xstream.addImplicitCollection(InvoiceRetrievalServletResponse.class, "Establishment");
		Object obj = xstream.fromXML(xml);
		return (InvoiceRetrievalServletResponse)obj;
	}


	/**
	 * @return Returns the establishment.
	 */
	public List getEstablishment() {
		return Establishment;
	}
	/**
	 * @param establishment The establishment to set.
	 */
	public void setEstablishment(List establishment) {
		Establishment = establishment;
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
