<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@page import="com.asiamiles.partnerportal.domain.UserSession"%>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_verify_redemption_acknowledgement"/>
</jsp:include>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="redemption"/>
            <jsp:param name="hideLangSwitch" value="true"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <jsp:include page="/WEB-INF/jsp/redemption/progressBar.jsp">
                        <jsp:param name="selected" value="acknowledgement"/>
                    </jsp:include>

                    <a href="javascript:window.print()" class="print">
                        <img src="images/btn_print.gif" alt="Print"/>
                        <str:label strId="AMPARTNERPORTAL" key="label_print"/>
                    </a>

                    <c:if test="${!empty redemptionForm.completedNARDetails}">
                        <strong><str:label strId="AMPARTNERPORTAL" key="info_completed_collections"/></strong><br>
                        <table class="sortable" id="claimtable" width="100%">
                            <thead>
                            <tr>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
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
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${redemptionForm.completedNARDetails}" var="nar" varStatus="claimCount">
                                <tr>
                                    <td><c:out value="${redemptionForm.memberId}"/></td>
                                    <td><c:out value="${nar.holderFirstName}"/> <c:out value="${nar.holderName}"/></td>
                                    <td><c:out value="${nar.claimNo}"/></td>
                                    <td><c:out value="${nar.packageDescription}"/></td>
                                    <td><c:out value="${nar.receiptNo}"/></td>
                                    <td><c:out value="${nar.remarks}"/></td>
                                    <td><c:out value="${nar.remarks2}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        <br>
                    </c:if>

                    <c:if test="${!empty redemptionForm.cancelledNARDetails}">
                        <strong><str:label strId="AMPARTNERPORTAL" key="info_cancelled_collections"/></strong><br>
                        <table class="sortable" id="claimtable" width="100%">
                            <thead>
                            <tr>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_cancel_reason"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${redemptionForm.cancelledNARDetails}" var="nar" varStatus="claimCount">
                                <tr>
                                    <td><c:out value="${redemptionForm.memberId}"/></td>
                                    <td><c:out value="${nar.holderFirstName}"/> <c:out value="${nar.holderName}"/></td>
                                    <td><c:out value="${nar.claimNo}"/></td>
                                    <td><c:out value="${nar.packageDescription}"/></td>
                                    <td><c:out value="${nar.cancelReason}"/></td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        <br>
                    </c:if>

                    <span class="bulletLink"><a href="<c:url value="/redemption.do"/>?_cancel"><str:label strId="AMPARTNERPORTAL" key="info_verify_another_redemption"/></a></span>
                </div>
            </div>
        </div>

    <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
