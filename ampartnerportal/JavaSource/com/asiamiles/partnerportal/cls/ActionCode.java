package com.asiamiles.partnerportal.cls;

/**
 * Typesafe enum for action codes in the CLS Interface.
 * 
 * @author CPPBENL
 *
 *
 */
public class ActionCode {
	
	private String code;
	
	private ActionCode(String code){
		this.code = code;
	}
	
	/*
	 *  (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof ActionCode)) {
			return false;
		}
		ActionCode other = (ActionCode)obj; 
		return code.equals(other.code);
	}
	
	/*
	 *  (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return code.hashCode();
	}
	
	/*
	 *  (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return code;
	}
	
	/**
	 * Action Code for creating an agent (used in AgentRetrievalServlet)
	 */
	public static final ActionCode AGENT_CREATE = new ActionCode("C");

	/**
	 * Action Code for updating an agent (used in AgentRetrievalServlet)
	 */
	public static final ActionCode AGENT_UPDATE = new ActionCode("U");
	
	/**
	 * Action Code for deleting an agent. (used in AgentRetrievalServlet)
	 */
	public static final ActionCode AGENT_DELETE = new ActionCode("D");
	
	/**
	 * Action Code for resetting an agent's password. (used in AgentRetrievalServlet)
	 */
	public static final ActionCode AGENT_RESET_PASSWORD = new ActionCode("P");
	
	
	
	/**
	 * Action Code for confirming a NAR collection. (used in PaperlessCollectionServlet)
	 */
	public static final ActionCode NAR_CONFIRM = new ActionCode("C");

	/**
	 * Action Code for voiding a NAR collection. (used in PaperlessCollectionServlet)
	 */
	public static final ActionCode NAR_VOID = new ActionCode("V");
	
	/**
	 * Action Code for completing a NAR collection. (used in PaperlessCollectionServlet)
	 */
	public static final ActionCode NAR_COMPLETE = new ActionCode("P");
	
	
	
	/**
	 * Action Code for retrieving all outstanding NAR. 
	 * (used in PaperlessCollectionRetrievalServlet)
	 */
	public static final ActionCode NAR_RETRIEVAL_OUTSTANDING = new ActionCode("O");

	/**
	 * Action Code for retrieving all unbilled but collection completed NAR. 
	 * (used in PaperlessCollectionRetrievalServlet)
	 */
	public static final ActionCode NAR_RETRIEVAL_UNBILLED = new ActionCode("U");
	
	/**
	 * Action Code for retrieving all collection completed and billed NAR . 
	 * (used in PaperlessCollectionRetrievalServlet)
	 */
	public static final ActionCode NAR_RETRIEVAL_BILLED = new ActionCode("B");

	/**
	 * Action Code for retrieving all outstanding, unbilled and billed NAR (collection confirmed and complete collection NAR). 
	 * (used in PaperlessCollectionRetrievalServlet)
	 */
	public static final ActionCode NAR_RETRIEVAL_ALL = new ActionCode("A");
	
}
