<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@page import="com.asiamiles.partnerportal.domain.UserSession"%>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_pending_claims_acknowledgement"/>
</jsp:include>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="pending"/>
            <jsp:param name="hideLangSwitch" value="true"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <jsp:include page="/WEB-INF/jsp/pendingClaims/progressBar.jsp">
                        <jsp:param name="selected" value="acknowledgement"/>
                    </jsp:include>

                    <a href="javascript:window.print()" class="print">
                        <img src="images/btn_print.gif" alt="Print"/>
                        <str:label strId="AMPARTNERPORTAL" key="label_print"/>
                    </a>
                    <strong>
                    <c:choose>
                    <c:when test="${pendingClaimsForm.activeClaim.action eq 'COMPLETE'}">
                    <str:label strId="AMPARTNERPORTAL" key="info_completed_collections"/>
                    </c:when>
                    <c:otherwise>
                    <str:label strId="AMPARTNERPORTAL" key="info_cancelled_collections"/>
                    </c:otherwise>
                    </c:choose>
                    </strong><br><br>

                    <table class="tbl_content" width="100%">
                        <thead>
                        <tr>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
                            <c:choose>
                                <c:when test="${pendingClaimsForm.activeClaim.action eq 'COMPLETE'}">
                                    <th><str:label strId="AMPARTNERPORTAL" key="label_receipt_ref"/></th>
		                            <%	UserSession userSession = (UserSession) request.getSession().getAttribute("userSession"); %>
		                        	<c:set var="key1">label_partner_remark_1_<%=userSession.getAgent().getPartnerCode()%></c:set>
		                            <c:set var="key2">label_partner_remark_2_<%=userSession.getAgent().getPartnerCode()%></c:set>
		                            <c:set var="rem_1"><str:label strId="AMPARTNERPORTAL" key="${key1}"/></c:set>
		                            <c:set var="rem_2"><str:label strId="AMPARTNERPORTAL" key="${key2}"/></c:set>
		                            <c:if test="${key1 ne rem_1}">
		                            <th width="6%">${rem_1}</th>
		                            </c:if>
		                            <c:if test="${key1 eq rem_1}">
		                            <th width="6%"><str:label strId="AMPARTNERPORTAL" key="label_partner_remark_1"/></th>
		                            </c:if>
		                            
		                            <c:if test="${key2 ne rem_2}">
		                            <th width="6%">${rem_2}</th>
		                            </c:if>
		                            <c:if test="${key2 eq rem_2}">
		                            <th width="6%"><str:label strId="AMPARTNERPORTAL" key="label_partner_remark_2"/></th>
		                            </c:if>
                                </c:when>
                                <c:otherwise>
                                    <th><str:label strId="AMPARTNERPORTAL" key="label_cancel_reason"/></th>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td><c:out value="${pendingClaimsForm.activeClaim.memberID}"/></td>
                            <td><c:out value="${pendingClaimsForm.activeClaim.holderName}"/> <c:out value="${pendingClaimsForm.activeClaim.holderFirstName}"/></td>
                            <td><c:out value="${pendingClaimsForm.activeClaim.claimNo}"/></td>
                            <td><c:out value="${pendingClaimsForm.activeClaim.packageDescription}"/></td>
                            <c:choose>
                                <c:when test="${pendingClaimsForm.activeClaim.action eq 'COMPLETE'}">
                                    <td><c:out value="${pendingClaimsForm.activeClaim.receiptNo}"/></td>
                                    <td><c:out value="${pendingClaimsForm.activeClaim.remarks}"/></td>
                                    <td><c:out value="${pendingClaimsForm.activeClaim.remarks2}"/></td>
                                </c:when>
                                <c:otherwise>
                                    <td><c:out value="${pendingClaimsForm.activeClaim.cancelReason}"/></td>
                                </c:otherwise>
                            </c:choose>
                        </tr>
                        </tbody>
                    </table>
                    <br>
                    <br>
                    <span class="bulletLink"><a href="<c:url value="/pendingClaims.do"/>?_cancel"><str:label strId="AMPARTNERPORTAL" key="info_back_to_pending_claim"/></a></span>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
