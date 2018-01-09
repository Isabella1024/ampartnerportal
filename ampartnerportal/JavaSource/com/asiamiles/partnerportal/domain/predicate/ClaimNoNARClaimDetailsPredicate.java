/*
 * Created on Jul 13, 2009
 *
 */
package com.asiamiles.partnerportal.domain.predicate;

import org.apache.commons.collections.Predicate;

import com.asiamiles.partnerportal.domain.NARClaimDetails;

/**
 * Predicate for identifying NAR Claims with was handled by a specific agent.
 * It is to be used in conjunction with CollectionUtils' methods
 * @author CPPBENL
 * @see org.apache.commons.collections.CollectionUtils
 */
public class ClaimNoNARClaimDetailsPredicate implements Predicate {

	private String keyword;
	
	public ClaimNoNARClaimDetailsPredicate(String keyword) {
		this.keyword = keyword;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(Object obj) {
		if (!(obj instanceof NARClaimDetails)) {
			return false;
		}
		NARClaimDetails claim = (NARClaimDetails)obj;
		String claimNoStr = claim.getClaimNo().toString();
//		System.out.println("\""+claimNoStr+"\".indexOf(\""+keyword+"\")=" + claimNoStr.indexOf(keyword));
		return claimNoStr.indexOf(keyword) >= 0;
	}

}
