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
public class HandledByAgentNARClaimDetailsPredicate implements Predicate {

	private String agentID;
	
	public HandledByAgentNARClaimDetailsPredicate(String agentID) {
		this.agentID = agentID;
	}
	
	/* (non-Javadoc)
	 * @see org.apache.commons.collections.Predicate#evaluate(java.lang.Object)
	 */
	public boolean evaluate(Object obj) {
		if (!(obj instanceof NARClaimDetails)) {
			return false;
		}
		NARClaimDetails claim = (NARClaimDetails)obj;
		if (claim.getCompletionHandledBy()!=null && !claim.getCompletionHandledBy().equals(""))
		{	return claim.getCompletionHandledBy().toLowerCase().indexOf(agentID.toLowerCase()) >= 0;
		}
		return claim.getCollectionHandledBy().toLowerCase().indexOf(agentID.toLowerCase()) >= 0;
	}

}
