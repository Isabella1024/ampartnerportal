/*
 * Created on Jul 13, 2009
 *
 */
package com.asiamiles.partnerportal.domain.predicate;

import org.apache.commons.collections.Predicate;

import com.asiamiles.partnerportal.domain.NARClaimDetails;

/**
 * Predicate for identifying completed but unbilled NAR Claims.
 * It is to be used in conjunction with CollectionUtils' methods
 * @author CPPBENL
 * @see org.apache.commons.collections.CollectionUtils
 */
public class CompletedNARClaimDetailsPredicate implements Predicate {

	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(Object obj) {
		if (!(obj instanceof NARClaimDetails)) {
			return false;
		}
		NARClaimDetails nar = (NARClaimDetails)obj;
		return (nar.getCompletionTime() != null && nar.getBilledDate() == null);
	}

}
