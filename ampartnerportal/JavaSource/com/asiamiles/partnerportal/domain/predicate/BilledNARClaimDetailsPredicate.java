/*
 * Created on Jul 13, 2009
 *
 */
package com.asiamiles.partnerportal.domain.predicate;

import org.apache.commons.collections.Predicate;

import com.asiamiles.partnerportal.domain.NARClaimDetails;

/**
 * Predicate for identifying Billed NAR Claims.
 * It is to be used in conjunction with CollectionUtils' methods
 * @author CPPBENL
 * @see org.apache.commons.collections.CollectionUtils
 */
public class BilledNARClaimDetailsPredicate implements Predicate {

	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(Object obj) {
		if (!(obj instanceof NARClaimDetails)) {
			return false;
		}
		NARClaimDetails claim = (NARClaimDetails)obj;
		return claim.getBilledDate() != null;
	}

}
