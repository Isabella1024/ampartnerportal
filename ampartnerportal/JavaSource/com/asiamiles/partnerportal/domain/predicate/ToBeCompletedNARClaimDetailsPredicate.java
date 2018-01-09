/*
 * Created on Jul 30, 2009
 *
 */
package com.asiamiles.partnerportal.domain.predicate;

import org.apache.commons.collections.Predicate;

import com.asiamiles.partnerportal.domain.NARClaimDetails;

/**
 * Predicate for identifying NAR Claims Details with was to be completed.
 * It is to be used in conjunction with CollectionUtils' methods
 * @author CPPBENL
 * @see org.apache.commons.collections.CollectionUtils
 */
public class ToBeCompletedNARClaimDetailsPredicate implements Predicate {

	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(Object obj) {
		if (!(obj instanceof NARClaimDetails)) {
			return false;
		}
		NARClaimDetails claim = (NARClaimDetails)obj;
		return (claim.isToBeProcessed() && NARClaimDetails.ACTION_COMPLETE.equals(claim.getAction()));
	}

}
