/*
 * Created on Jun 11, 2009
 *
 */
package com.asiamiles.partnerportal.domain;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

import org.apache.commons.collections.CollectionUtils;

import com.asiamiles.partnerportal.domain.predicate.UnbilledEstablishmentPredicate;

/**
 * @author CPPKENW
 *
 */
public class BillingForm implements Serializable{
	
	private List claims=new ArrayList(); //List<NARClaimDetails>
	private String invoiceNo;
	private List establishments; //List<Unbilled.Establishment>
	private int establishmentIndex;
	private Unbilled.Establishment selectedUnbilledEstablishment;
	private String cutOffDay;
	private Date today;
	private int timeZone;
	
	//Steven ADD for AML34188 20140806 START
	private String establishmentCode;
	
	private String nextPageCode;
	private String nextClaimNum;
	private String prePageCode;
	private String preClaimNum;
	private String totalNum;
	private String totalPage;
	private int index;
	private Agent agent;
	private List unbillList=new ArrayList();
	//Steven ADD for AML34188 20140806 END
	
	public BillingForm(){
		today = new Date();
	}	

	/*
	 * 	To retrieve the billing period end date :
	 * 
	 * 	if (cutoffday in this month < today)
	 *		billingEndDate = cutoffday in this month
	 *	else
	 *		billingEndDate = min(cutoffday in last month, last day in last month)
	 *
	 *	For example:
	 *	Cut-off date: 31, Current date: Oct 31 =>  Billing period end date: Sep 30
	 *	Cut-off date: 31, Current date: Nov 30 =>  Billing period end date: Oct 31
	 *	Cut-off date: 31, Current date: Dec 1  =>  Billing period end date: Nov 30
	 */
	public Date getCutOffDate() {
		Calendar cToday = Calendar.getInstance();
		cToday.setTime(this.today);
		Calendar c = Calendar.getInstance();
		c.setTime(this.today);
		
		int iCutOffDay = Integer.parseInt(cutOffDay);
		
		if (iCutOffDay >= cToday.get(Calendar.DATE)){	
			c.add(Calendar.MONTH, -1);
			if (c.getActualMaximum(Calendar.DAY_OF_MONTH) < iCutOffDay) {
				iCutOffDay = c.getActualMaximum(Calendar.DAY_OF_MONTH);
			}	
		}
		c.set(Calendar.DAY_OF_MONTH, iCutOffDay);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 0);	
		
		return c.getTime();
	}
	
	


	public List getUnbilledEstablishments() {
		List result = new ArrayList();
		CollectionUtils.select(establishments, new UnbilledEstablishmentPredicate(getCutOffDate()), result);
		return result;
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
	/**
	 * @return Returns the claims.
	 */
	public List getClaims() {
		return claims;
	}
	/**
	 * @param claims The claims to set.
	 */
	public void setClaims(List claims) {
		this.claims = claims;
	}
	/**
	 * @return Returns the cutOffDay.
	 */
	public String getCutOffDay() {
		return cutOffDay;
	}
	/**
	 * @param cutOffDay The cutOffDay to set.
	 */
	public void setCutOffDay(String cutOffDay) {
		this.cutOffDay = cutOffDay;
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
	 * @return Returns the establishmentIndex.
	 */
	public int getEstablishmentIndex() {
		return establishmentIndex;
	}
	/**
	 * @param establishmentIndex The establishmentIndex to set.
	 */
	public void setEstablishmentIndex(int establishmentIndex) {
		this.establishmentIndex = establishmentIndex;
	}

	/**
	 * @return Returns the selectedUnbilledEstablishment.
	 */
	public Unbilled.Establishment getSelectedUnbilledEstablishment() {
		return selectedUnbilledEstablishment;
	}
	/**
	 * @param selectedUnbilledEstablishment The selectedUnbilledEstablishment to set.
	 */
	public void setSelectedUnbilledEstablishment(Unbilled.Establishment selectedUnbilledEstablishment) {
		this.selectedUnbilledEstablishment = selectedUnbilledEstablishment;
	}
	/**
	 * @param invoiceNo The invoiceNo to set.
	 */
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	/**
	 * @return Returns the invoiceNo.
	 */
	public String getInvoiceNo() {
		return invoiceNo;
	}
	/**
	 * @return Returns the today.
	 */
	public Date getToday() {
		return today;
	}
	/**
	 * @param today The today to set.
	 */
	public void setToday(Date today) {
		this.today = today;
	}
	/**
	 * @return Returns the timeZone.
	 */
	public int getTimeZone() {
		return timeZone;
	}
	/**
	 * @param timeZone The timeZone to set.
	 */
	public void setTimeZone(int timeZone) {
		this.timeZone = timeZone;
	}

	//Steven ADD for AML34188 20140806 START
	public Agent getAgent() {
		return agent;
	}

	public void setAgent(Agent agent) {
		this.agent = agent;
	}

	public String getNextPageCode() {
		return nextPageCode;
	}

	public void setNextPageCode(String nextPageCode) {
		this.nextPageCode = nextPageCode;
	}

	public String getNextClaimNum() {
		return nextClaimNum;
	}

	public void setNextClaimNum(String nextClaimNum) {
		this.nextClaimNum = nextClaimNum;
	}

	public String getEstablishmentCode() {
		return establishmentCode;
	}

	public void setEstablishmentCode(String establishmentCode) {
		this.establishmentCode = establishmentCode;
	}

	public String getPrePageCode() {
		return prePageCode;
	}

	public void setPrePageCode(String prePageCode) {
		this.prePageCode = prePageCode;
	}

	public String getPreClaimNum() {
		return preClaimNum;
	}

	public void setPreClaimNum(String preClaimNum) {
		this.preClaimNum = preClaimNum;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public List getUnbillList() {
		return unbillList;
	}

	public void setUnbillList(List unbillList) {
		this.unbillList = unbillList;
	}
	
	//Steven ADD for AML34188 20140806 END
	
}
