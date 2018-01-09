<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@page import="com.asiamiles.partnerportal.domain.UserSession"%>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_report_void_claim"/>
</jsp:include>
<script language="javascript">
    function submitForm() {
        var MyForm = document.forms["return"];
        MyForm.submit();
    }
</script>    
<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="report"/>
            <jsp:param name="hideLangSwitch" value="true"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <str:label strId="AMPARTNERPORTAL" key="instruction_void_collection"/><br>
  
                    <c:if test="${!empty clsexception}">
                    	<span class="error"><fmt:message key="${clsexception.errorMessageCode}"/></span><br/>
                    </c:if>
                    
                    <!-- Support for Spring errors holder -->
                    <spring:bind path="reportForm.*">
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
                            <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item_package_code"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_status"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_date"/> (GMT <c:out value="${timeZone}"/>)</th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_agent_id"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
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
                        <c:set var="currentLocale" value="${pageContext.response.locale}"/>
                        <fmt:setLocale value="en" scope="session"/>                         
                        <tr>
                            <td><c:out value="${reportForm.activeClaim.claimNo}"/></td>
                            <td><c:out value="${reportForm.activeClaim.packageDescription}"/> (<c:out value="${reportForm.activeClaim.packageCode}"/>)</td>
                            <td>
                                <c:choose>
                                    <c:when test="${empty reportForm.activeClaim.completionTime}"><str:label strId="AMPARTNERPORTAL" key="label_in_progress"/></c:when>
                                    <c:otherwise>
                                        <c:choose>
                                            <c:when test="${empty reportForm.activeClaim.billedDate}"><str:label strId="AMPARTNERPORTAL" key="label_completed"/></c:when>
                                            <c:otherwise><str:label strId="AMPARTNERPORTAL" key="label_billed"/></c:otherwise>
                                        </c:choose>
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <c:choose>
                                <c:when test="${!empty reportForm.activeClaim.billedDate}">
                                <td><fmt:formatDate value="${reportForm.activeClaim.billedDate}" pattern="dd MMM yyyy"/></td>
                                </c:when>
                                <c:otherwise>
                                    <c:choose>
                                        <c:when test="${!empty reportForm.activeClaim.completionTime}">
                                        <td><fmt:formatDate value="${reportForm.activeClaim.completionTime}" pattern="dd MMM yyyy HH:mm"/></td>
                                        </c:when>
                                        <c:otherwise>
                                        <td><fmt:formatDate value="${reportForm.activeClaim.collectionTime}" pattern="dd MMM yyyy HH:mm"/></td>
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>                            
                            <c:choose>
                                <c:when test="${!empty reportForm.activeClaim.completionHandledBy}">
                                <td><c:out value="${reportForm.activeClaim.completionHandledBy}"/></td>
                                </c:when>
                                <c:otherwise>
                                <td><c:out value="${reportForm.activeClaim.collectionHandledBy}"/></td>
                                </c:otherwise>
                            </c:choose>
                            <td><c:out value="${reportForm.activeClaim.holderName}"/> <c:out value="${reportForm.activeClaim.holderFirstName}"/></td>
                            <td><c:out value="${reportForm.activeClaim.receiptNo}"/></td>
                            <td><c:out value="${reportForm.activeClaim.remarks}"/></td>
                            <td><c:out value="${reportForm.activeClaim.remarks2}"/></td>
                        </tr>
                        <fmt:setLocale value="${currentLocale}" scope="session"/>
                        </tbody>
                    </table>
                    <br>                 
                    <form:form id="voidClaim" name="voidClaim" commandName="reportForm">    
                        <form:label path="reason" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_void_reason"/></form:label><br>
                        <form:input path="reason" size="100%" maxlength="250"/>
                        <br>
                        <br>
						<input type="hidden" value="<c:out value="${reportForm.activeClaim.claimNo}"/>" name="voidedClaim"/>
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_void_collection"/>" name="_target0" class="btnGenericRight">
                        <span class="bulletLink"><a href="<c:url value="/report.do"/>"><str:label strId="AMPARTNERPORTAL" key="info_back_to_previous"/></a></span>					                    
                    </form:form>
                    <form id="return" name="return" action="<c:url value="/report.do"/>" method="post">
                   		<input type="hidden" name="voidedClaim" value="" />
                    </form>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>

</body>
</html>
