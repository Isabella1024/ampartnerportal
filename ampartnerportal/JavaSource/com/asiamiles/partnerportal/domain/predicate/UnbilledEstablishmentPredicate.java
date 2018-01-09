/*
 * Created on Jul 13, 2009
 *
 */
package com.asiamiles.partnerportal.domain.predicate;

import java.util.Date;

import org.apache.commons.collections.Predicate;

import com.asiamiles.partnerportal.domain.Unbilled;

/**
 * Predicate for identifying unbilled establishments
 * It is to be used in conjunction with CollectionUtils' methods
 * @author CPPBENL
 * @see org.apache.commons.collections.CollectionUtils
 */
public class UnbilledEstablishmentPredicate implements Predicate {

	private Date cutOffDate;
	
	public UnbilledEstablishmentPredicate(Date cutOffDate) {
		this.cutOffDate = cutOffDate;
	}
	
	/* Establishments have outstanding completed claims if the oldest collection date <= monthly cutoff date
	 * and last billing date < monthly cutoff date in the case that last billing date exists
	 */
	public boolean evaluate(Object obj) {
		if (!(obj instanceof Unbilled.Establishment)) {
			return false;
		}
		Unbilled.Establishment establishment = (Unbilled.Establishment)obj;
		return establishment.getOldestCollectionTimestamp() != null 
				&& establishment.getOldestCollectionTimestamp().compareTo(cutOffDate) < 0
				&& ( establishment.getLastBillingDate() == null || establishment.getLastBillingDate().compareTo(cutOffDate)<0)
				;
	}

}
