<%@ page import="com.asiamiles.partnerportal.domain.UserSession" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_verify_redemption_complete_collection"/>
</jsp:include>

<body>
<script language="javascript">
    function displayComplete(number) {
        document.getElementById("complete" + number).style.display = 'inline';
        document.getElementById("cancel" + number).style.display = 'none';
    }
    function displayCancel(number) {
        document.getElementById("complete" + number).style.display = 'none';
        document.getElementById("cancel" + number).style.display = 'inline';
    }
	function applyToAll(index) {
        for (var i = 0; i < <c:out value="${fn:length(redemptionForm.NARDetails)}"/>; i++) {
            if(document.getElementById("receiptNum"+i) != null)
		    {	document.getElementById("receiptNum"+i).value=document.getElementById("receiptNum"+index).value
		    }
            if(document.getElementById("remark"+i) != null)
		    {	document.getElementById("remark"+i).value=document.getElementById("remark"+index).value
		    }
            if(document.getElementById("anotherRemark"+i) != null)
		    {	document.getElementById("anotherRemark"+i).value=document.getElementById("anotherRemark"+index).value
		    }
		}
	}    
    function reset(){
    	for (var i = 0; i < <c:out value="${fn:length(redemptionForm.NARDetails)}"/>; i++) {
	    	if(document.forms['actionForm'].elements['completeOption'+i] != null)
		    {	if (document.forms['actionForm'].elements['completeOption'+i].checked)
		    	{ 	document.getElementById("complete"+i).style.display = 'inline';
		        	document.getElementById("cancel"+i).style.display = 'none';    	
		    	}
		    	else
		    	{	document.getElementById("complete"+i).style.display = 'none';
			        document.getElementById("cancel"+i).style.display = 'inline';    	
		    	}
		    }
    	}
    }		
</script>

    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="redemption"/>
            <jsp:param name="hideLangSwitch" value="true"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <jsp:include page="/WEB-INF/jsp/redemption/progressBar.jsp">
                        <jsp:param name="selected" value="completeClaims"/>
                    </jsp:include>

                    <strong><str:label strId="AMPARTNERPORTAL" key="instruction_complete_collection"/></strong><br>
                    
					<!-- Support for Spring errors holder -->
                    <spring:bind path="redemptionForm.*">
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


                   
                    <form:form commandName="redemptionForm" id="actionForm">
                        <c:forEach items="${redemptionForm.NARDetails}" var="nar" varStatus="claimCount">
                        <c:if test="${nar.toBeProcessed}">
                        <table class="tbl_content" width="100%">
                            <thead>
                            <tr>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_member_name"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_status"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td><c:out value="${redemptionForm.memberId}"/></td>
                                <td><c:out value="${redemptionForm.memberEmbossedName}"/></td>
                                <td><c:out value="${nar.holderFirstName}"/> <c:out value="${nar.holderName}"/></td>
                                <td><c:out value="${nar.claimNo}"/></td>
                                <td><c:out value="${nar.packageDescription}"/></td>
                                <td><strong><str:label strId="AMPARTNERPORTAL" key="label_in_progress"/></strong></td>
                            </tr>
                            </tbody>
                        </table>
                        <br>
                            <form:radiobutton path="NARDetails[${claimCount.index}].action" id="completeOption${claimCount.index}" value="COMPLETE" onclick="displayComplete(${claimCount.index})"/>
                            <str:label strId="AMPARTNERPORTAL" key="info_complete_collection"/>&nbsp;
							<c:if test="${agentType ne 'N'}">
                        		<form:radiobutton path="NARDetails[${claimCount.index}].action" id="cancelOption${claimCount.index}" value="CANCEL" onclick="displayCancel(${claimCount.index})"/>
                        		<str:label strId="AMPARTNERPORTAL" key="info_cancel_collection"/>
                        	</c:if>                           		
                        <br>

                        <div id="complete<c:out value="${claimCount.index}"/>">
                            <form:label path="NARDetails[${claimCount.index}].receiptNo" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_partner_receipt_ref_no"/></form:label>
                            <form:input path="NARDetails[${claimCount.index}].receiptNo" id="receiptNum${claimCount.index}" maxlength="25"/> <a href="javascript:applyToAll('${claimCount.index}')"><str:label strId="AMPARTNERPORTAL" key="info_apply_to_all"/></a>
                            <br>
                            <% UserSession userSession = (UserSession) request.getSession().getAttribute("userSession"); %>
                            <c:set var="key1">label_partner_remark_1_<%=userSession.getAgent().getPartnerCode()%></c:set>
                            <c:set var="key2">label_partner_remark_2_<%=userSession.getAgent().getPartnerCode()%></c:set>
                            <c:set var="rem_1"><str:label strId="AMPARTNERPORTAL" key="${key1}"/></c:set>
                            <c:set var="rem_2"><str:label strId="AMPARTNERPORTAL" key="${key2}"/></c:set>
                            <c:if test="${rem_1 ne key1}">
                            <form:label path="NARDetails[${claimCount.index}].remarks" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="${rem_1}"/></form:label>
                            </c:if>
                            <c:if test="${rem_1 eq key1}">
                            <form:label path="NARDetails[${claimCount.index}].remarks" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_partner_remark_1"/></form:label>
                            </c:if>
                            <form:input path="NARDetails[${claimCount.index}].remarks" id="remark${claimCount.index}" maxlength="250"/>
                            <br>
                            <c:if test="${rem_2 ne key2}">
                            <form:label path="NARDetails[${claimCount.index}].remarks2" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="${rem_2}"/></form:label>
                            </c:if>
                            <c:if test="${rem_2 eq key2}">
                            <form:label path="NARDetails[${claimCount.index}].remarks2" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_partner_remark_2"/></form:label>
                            </c:if>
                            <form:input path="NARDetails[${claimCount.index}].remarks2" id="anotherRemark${claimCount.index}" maxlength="250"/>
                        </div>
                        <div id="cancel<c:out value="${claimCount.index}"/>" style="display:none">
                        	<form:label path="NARDetails[${claimCount.index}].cancelReason" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_cancel_reason_must"/></form:label>
                            <form:input path="NARDetails[${claimCount.index}].cancelReason" maxlength="250"/>
                        </div>
                        <br>
                        <br>
                        </c:if>
                        </c:forEach>
                        <script>reset()</script>
                        <span class="bulletLink"><a href="<c:url value="/redemption.do"/>?_target0"><str:label strId="AMPARTNERPORTAL" key="info_verify_another_redemption"/></a></span>
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_submit"/>" name="_finish" class="btnGenericRight"/>
                    </form:form>
                </div>
            </div>
        </div>

    <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
