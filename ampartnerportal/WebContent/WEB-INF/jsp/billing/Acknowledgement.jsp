<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_billing_acknowledgement"/>
</jsp:include>

<script language="javascript">
    function submitForm() {
        var MyForm = document.forms["export"];
        MyForm.submit();
    }
</script>

<body>
<div id="main_container">
    <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
        <jsp:param name="nav" value="billing"/>
        <jsp:param name="hideLangSwitch" value="true"/>
    </jsp:include>

    <div id="content">
        <div id="mod_form" class="mod">
            <div class="bd">
                <a href="javascript:submitForm()" class="downloadIcon">
                	<img alt="Download" src="images/btn_download.gif" height="26px" width="26px"/>
                	<str:label strId="AMPARTNERPORTAL" key="label_download"/>
                </a>             
                <a href="javascript:window.print()" class="printIcon">
                    <img src="images/btn_print.gif" alt="Print"/>
                    <str:label strId="AMPARTNERPORTAL" key="label_print"/>
                </a>
                <c:set var="currentLocale" value="${pageContext.response.locale}"/>
                <%-- CPPYOW DELETE for iRedeem 20120314 start
                <strong><c:out value="${billingForm.selectedUnbilledEstablishment.establishmentName}"/></strong>
                <br>
                <c:set var="currentLocale" value="${pageContext.response.locale}"/>
                <strong><str:label strId="AMPARTNERPORTAL" key="info_bill_completed_and_recorded"/></strong><br><br>
                <strong>
           		<fmt:message key="info_bill_for_completed_claims">
            		<fmt:param>
                		<fmt:setLocale value="en" scope="session"/> 
                        <fmt:formatDate value="${billingForm.selectedUnbilledEstablishment.oldestCollectionTimestamp}" pattern="dd MMM yyyy"/> 
                        <fmt:setLocale value="${currentLocale}" scope="session"/>
                    </fmt:param>
            		<fmt:param>
                		<fmt:setLocale value="en" scope="session"/> 
                        <fmt:formatDate value="${billingForm.cutOffDate}" pattern="dd MMM yyyy"/> 
                        <fmt:setLocale value="${currentLocale}" scope="session"/>
                    </fmt:param>
        	    </fmt:message>
                </strong><br><br>
				<form:form id="export" name="export" commandName="billingForm" target="_blank">
				<input type="hidden" name="_target5" value="">
				</form:form>
				<br>
				<br>                
                <table class="sortable" id="billTable" width="100%">
                    <thead>
                    <tr>
                        <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                        <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
                        <th><str:label strId="AMPARTNERPORTAL" key="label_collection_date"/></th>
                        <th><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></th>
                        <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                        <th><str:label strId="AMPARTNERPORTAL" key="label_receipt_ref"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:set var="count" value="${0}"/>
	                <fmt:setLocale value="en" scope="session"/>                       
                    <c:forEach items="${billingForm.claims}" var="claim" varStatus="claimCount">
                        <tr>
                            <td><c:out value="${claim.claimNo}"/></td>
                            <td><c:out value="${claim.packageDescription}"/></td>
                            <td><fmt:formatDate value="${claim.collectionTime}" pattern="dd MMM yyyy"/></td>
                            <td><c:out value="${claim.memberID}"/></td>
                            <td><c:out value="${claim.holderName}"/> <c:out value="${claim.holderFirstName}"/></td>
                            <td><c:out value="${claim.receiptNo}"/></td>
                        </tr>
                        <c:set var="count" value="${count + 1}"/>
                    </c:forEach>
                    <fmt:setLocale value="${currentLocale}" scope="session"/>
                    </tbody>
                </table>
                <br><str:label strId="AMPARTNERPORTAL" key="info_total_completed_claims"/> <c:out value="${count}"/>
                <br><str:label strId="AMPARTNERPORTAL" key="info_invoice_no"/> <c:out value="${billingForm.invoiceNo}"/>
                --%>
                <%-- CPPYOW add for iRedeem 20120314 start --%>
                <div>
				<c:set var="currentLocale" value="${pageContext.response.locale}"/>
                <str:label strId="AMPARTNERPORTAL" key="info_bill_processing"/>
                </div>
                <%-- CPPYOW add for iRedeem 20120314 end --%>
                <br>
                <br>
                <span class="bulletLink"><a href="<c:url value="/billing.do"/>?_cancel"><str:label strId="AMPARTNERPORTAL" key="info_other_outstanding_billings"/></a></span>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
