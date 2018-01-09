<%@ page import="com.asiamiles.partnerportal.domain.UserSession" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_pending_claims_complete"/>
</jsp:include>

<body>
<script language="javascript">
    function displayComplete() {
        document.getElementById("complete").style.display = 'inline';
        document.getElementById("cancel").style.display = 'none';
    }
    function displayCancel() {
        document.getElementById("complete").style.display = 'none';
        document.getElementById("cancel").style.display = 'inline';
    }
    function reset(){
    	if (document.forms['actionForm'].elements['completeOption'].checked)
    	{ 	document.getElementById("complete").style.display = 'inline';
        	document.getElementById("cancel").style.display = 'none';    	
    	}
    	else
    	{	document.getElementById("complete").style.display = 'none';
	        document.getElementById("cancel").style.display = 'inline';    	
    	}
    }
    function submitForm() {
        var MyForm = document.forms["return"];
        MyForm.submit();
    }      
</script>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="pending"/>
            <jsp:param name="hideLangSwitch" value="true"/>
        </jsp:include>
        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <jsp:include page="/WEB-INF/jsp/pendingClaims/progressBar.jsp">
                        <jsp:param name="selected" value="completeClaims"/>
                    </jsp:include>

                    <strong><str:label strId="AMPARTNERPORTAL" key="instruction_complete_collection"/></strong><br><br>
                    <!-- Support for Spring errors holder -->
                    <spring:bind path="pendingClaimsForm.*">
                        <c:forEach var="error" items="${status.errors.globalErrors}">
                            <span class="error">
                            	<spring:message message="${error}" arguments="${error.arguments}" htmlEscape="true"/>
                            </span><br/>
                        </c:forEach>
                        <c:forEach var="error" items="${status.errors.fieldErrors}">
                            <span class="error">
                            	<spring:message message="${error}" arguments="${error.arguments}" htmlEscape="true"/>
                            </span><br/>
                        </c:forEach>
                        <br>
                    </spring:bind>

                    <table class="tbl_content" width="100%">
                        <thead>
                        <tr>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_status"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><c:out value="${pendingClaimsForm.activeClaim.memberID}"/></td>
                            <td><c:out value="${pendingClaimsForm.activeClaim.holderName}"/> <c:out value="${pendingClaimsForm.activeClaim.holderFirstName}"/></td>
                            <td><c:out value="${pendingClaimsForm.activeClaim.claimNo}"/></td>
                            <td><c:out value="${pendingClaimsForm.activeClaim.packageDescription}"/></td>
                            <td>In Progress</td>
                        </tr>
                        </tbody>
                    </table>
                    <br>
                    <form id="return" name="return" action="<c:url value="/pendingClaims.do"/>" method="post">
                    	<input type="hidden" value="" name="_target0"/>
                    </form>
                    <form:form commandName="pendingClaimsForm" id="actionForm">
						<form:radiobutton path="activeClaim.action" id="completeOption" value="COMPLETE" onclick="displayComplete()"/><str:label strId="AMPARTNERPORTAL" key="info_complete_collection"/>&nbsp;
						<c:if test="${agentType ne 'N'}">
							<form:radiobutton path="activeClaim.action" id="cancelOption" value="CANCEL" onclick="displayCancel()"/><str:label strId="AMPARTNERPORTAL" key="info_cancel_collection"/>
						</c:if>
                        <br>
                        <br>
                        <div id="complete">
                            <table>
                                <tr>
                                    <td><form:label path="activeClaim.receiptNo" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_partner_receipt_ref_no"/></form:label></td>
                                    <td><form:input path="activeClaim.receiptNo" maxlength="25"/></td>
                                </tr>
                                <tr>
                                    <% UserSession userSession = (UserSession) request.getSession().getAttribute("userSession"); %>
                                    <c:set var="key1">label_partner_remark_1_<%=userSession.getAgent().getPartnerCode()%></c:set>
                                    <c:set var="key2">label_partner_remark_2_<%=userSession.getAgent().getPartnerCode()%></c:set>
                                    <c:set var="rem_1"><str:label strId="AMPARTNERPORTAL" key="${key1}"/></c:set>
                                    <c:set var="rem_2"><str:label strId="AMPARTNERPORTAL" key="${key2}"/></c:set>
                                    <c:if test="${key1 ne rem_1}">
                                    <td><form:label path="activeClaim.remarks" cssClass="label" cssErrorClass="error">${rem_1}</form:label></td>
                                    </c:if>
                                    <c:if test="${key1 eq rem_1}">
                                    <td><form:label path="activeClaim.remarks" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_partner_remark_1"/></form:label></td>
                                    </c:if>
                                    <td><form:input path="activeClaim.remarks" maxlength="250"/></td>
                                </tr>
                                <tr>
                                    <c:if test="${key2 ne rem_2}">
                                    <td><form:label path="activeClaim.remarks2" cssClass="label" cssErrorClass="error">${rem_2}</form:label></td>
                                    </c:if>
                                    <c:if test="${key2 eq rem_2}">
                                    <td><form:label path="activeClaim.remarks2" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_partner_remark_2"/></form:label></td>
                                    </c:if>
                                    <td><form:input path="activeClaim.remarks2" maxlength="250"/></td>
                                </tr>
                            </table>
                        </div>
                        <div id="cancel" style="display:none">
                            <form:label path="activeClaim.cancelReason" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_cancel_reason_must"/></form:label>
                            <form:input path="activeClaim.cancelReason" maxlength="250"/>
                            <br>
                        </div>
                        <script>reset();</script>
                        <br>
                        <span class="bulletLink"><a href="javascript:submitForm()"><str:label strId="AMPARTNERPORTAL" key="info_back_to_previous"/></a></span>
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_submit"/>" name="_target2" class="btnGenericRight" />
                    </form:form>

                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
