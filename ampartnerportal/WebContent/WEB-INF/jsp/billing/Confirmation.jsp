<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@page import="com.asiamiles.partnerportal.domain.UserSession"%>
<%@page import="com.asiamiles.partnerportal.domain.BillingForm"%>
<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_billing_confirmation"/>
</jsp:include>

<script language="javascript">
    function submitForm() {
        MyForm = document.forms["returnForm"];
        MyForm.submit();
    }
</script>

<!-- added by Steven 20140730 for session cache -->
<%	UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
	BillingForm reportForm = (BillingForm) request.getAttribute("billingForm");
	userSession.setAgent(reportForm.getAgent());
	userSession.setUnbillList(reportForm.getUnbillList());
	userSession.setClaims(reportForm.getClaims());
	userSession.setNextClaimNum(reportForm.getNextClaimNum());
	userSession.setNextPackageCode(reportForm.getNextPageCode());
	userSession.setPreClaimNum(reportForm.getPreClaimNum());
	userSession.setPrePackageCode(reportForm.getPrePageCode());
	//userSession.setPageCode(reportForm.getPageCode());
	//userSession.setClaimNum(reportForm.getClaimNum());
	userSession.setEstablishmentCode(reportForm.getEstablishmentCode());
	userSession.setCurrentPage(reportForm.getIndex());
%> 

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="billing"/>
            <jsp:param name="hideLangSwitch" value="true"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                     <!-- Support for Spring errors holder -->
                    <spring:bind path="billingForm.*">
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
                    
                    <form id="returnForm" action="<c:url value="/billing.do"/>" method="post">
                        <input type="hidden" name="_target1" value="">
                    </form>
                    <form action="<c:url value="/billing.do"/>" method="post">
                        <strong><str:label strId="AMPARTNERPORTAL" key="info_confirm_bill"/></strong><br><br>
                        <span class="bulletLink"><a href="javascript:submitForm()"><str:label strId="AMPARTNERPORTAL" key="info_return"/></a></span>
                        <%--CPPJULI UPDATE FOR AML.34188 20140923 START --%>
                        <%-- <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_confirm"/>" name="_finish" class="btnGenericRight"/> --%>
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_confirm"/>" name="_target3" class="btnGenericRight"/>
                        <%--CPPJULI UPDATE FOR AML.34188 20140923 END --%>
                        <br>
                        <br>
                        <br>
                        <strong><c:out value="${billingForm.selectedUnbilledEstablishment.establishmentName}"/></strong>
                    	<br>
                        <c:set var="currentLocale" value="${pageContext.response.locale}"/>
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

                        <table class="sortable" id="billTable" width="100%">
                            <thead>
                                <tr>
                                    <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                                    <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
                                    <th><str:label strId="AMPARTNERPORTAL" key="label_collection_date"/></th>
                                    <th><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></th>
                                    <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                                    <th><str:label strId="AMPARTNERPORTAL" key="label_receipt_ref"/></th>
                                    <th class="unsortable"><str:label strId="AMPARTNERPORTAL" key="label_select"/></th>
                                </tr>
                            </thead>
                            <tbody>
                            <c:set var="count" value="${0}"/>
	                        <fmt:setLocale value="en" scope="session"/>                             
                            <c:forEach items="${billingForm.claims}" var="claim" varStatus="claimCount">                               
                                    
                                    <c:if test="${claim.toBeBilled}">
                                    <tr>
                                        <td><c:out value="${claim.claimNo}"/></td>
                                        <td><c:out value="${claim.packageDescription}"/></td>
                                        <td><fmt:formatDate value="${claim.collectionTime}" pattern="dd MMM yyyy"/></td>
                                        <td><c:out value="${claim.memberID}"/></td>
                                        <td><c:out value="${claim.holderName}"/> <c:out value="${claim.holderFirstName}"/></td>
                                        <td><c:out value="${claim.receiptNo}"/></td>
                                        <td class="center">
                                          <input type="checkbox" name="id" checked="checked" disabled="true"/>
                                        </td> 
                                    </tr>
                                    </c:if>
                                    <c:if test="${!claim.toBeBilled}">
                                    <tr>
                                    <div isDisabled="true">
                                        <td style="color: gray"><c:out value="${claim.claimNo}"/></td>
                                        <td style="color: gray"><c:out value="${claim.packageDescription}"/></td>
                                        <td style="color: gray"><fmt:formatDate value="${claim.collectionTime}" pattern="dd MMM yyyy"/></td>
                                        <td style="color: gray"><c:out value="${claim.memberID}"/></td>
                                        <td style="color: gray"><c:out value="${claim.holderName}"/> <c:out value="${claim.holderFirstName}"/></td>
                                        <td style="color: gray"><c:out value="${claim.receiptNo}"/></td>
                                        <td class="center">
                                          <input type="checkbox" name="id" disabled="true"/>
                                        </td> 
                                    </div>
                                    </tr>
                                    </c:if>
                                        
                                    
                                    <c:set var="count" value="${count + 1}"/>                              
                            </c:forEach>
                            <fmt:setLocale value="${currentLocale}" scope="session"/>
                            </tbody>
                        </table>
                        <br>
                        <!-- added by Steven for pagenation 20140730 -->
                        <input name="prePageCode" type="hidden" value="${billingForm.prePageCode}">
			            <input name="nextPageCode" type="hidden" value="${billingForm.nextPageCode}">
				        <div style="float:right">
				        <c:if test="${!empty billingForm.prePageCode}">
				         <a href="<c:url value="/billing.do?prePageCode=${billingForm.prePageCode}&com=1"/>" style="font-size: 12px"><str:label strId="AMPARTNERPORTAL" key="label_pre"/></a>&nbsp;
				        </c:if>
	                    <c:if test="${!empty billingForm.nextPageCode}">
	                    <a href="<c:url value="/billing.do?nextPageCode=${billingForm.nextPageCode}&com=1"/>" style="font-size: 12px"><str:label strId="AMPARTNERPORTAL" key="label_next"/></a>&nbsp;
	                     </c:if>
                        </div>
                        <br/>
                        <br/>
                        <div style="float:right">
                        <c:if test="${fn:length(billingForm.claims)>0}">
                        <str:label strId="AMPARTNERPORTAL" key="label_page"/>&nbsp;<c:out value="${billingForm.index}"/>&nbsp;<str:label strId="AMPARTNERPORTAL" key="label_of"/>&nbsp;<c:out value="${billingForm.totalPage}"/>&nbsp;
                        </c:if>
                        </div>
                        <br/>
                        <br/>
                        <div style="float:right">
                        <str:label strId="AMPARTNERPORTAL" key="label_total"/>&nbsp;<c:out value="${billingForm.totalNum}"/>&nbsp;
                        </div>
                        <c:set var="num" value="${billingForm.totalNum}"/>
                        <c:set var="unbilled" value="${fn:length(billingForm.unbillList)}"/>
                        <input name="billedCount" type="hidden" value="${num - unbilled}">
                        <br/>
                        <strong><str:label strId="AMPARTNERPORTAL" key="info_total_completed_claims"/> <c:out value="${num - unbilled}"/></strong><br><br>
                        <strong><str:label strId="AMPARTNERPORTAL" key="info_invoice_no"/> <c:out value="${billingForm.invoiceNo}"/></strong><br><br>
                        <span class="bulletLink"><a href="javascript:submitForm()"><str:label strId="AMPARTNERPORTAL" key="info_return"/></a></span>
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_confirm"/>" name="_target3" class="btnGenericRight"/>
                    </form>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
