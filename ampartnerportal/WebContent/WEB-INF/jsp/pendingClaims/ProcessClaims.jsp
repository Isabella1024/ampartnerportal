<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>

<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_pending_claims_process"/>
</jsp:include>

<script language="javascript">
    function submitForm(claimIndex) {
        MyForm = document.forms["claim"];
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "claimIndex");
        hiddenField.setAttribute("value", claimIndex);
        MyForm.appendChild(hiddenField);
        MyForm.submit();
    }
</script>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="pending"/>
        </jsp:include>
		

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <jsp:include page="/WEB-INF/jsp/pendingClaims/progressBar.jsp">
                        <jsp:param name="selected" value="processClaims"/>
                    </jsp:include>

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

                    <strong><str:label strId="AMPARTNERPORTAL" key="instruction_pending_claim"/></strong><br><br>

                    <form:form commandName="pendingClaimsForm">
                        <strong>
                        <form:label path="claimNo" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></form:label></strong>
                        <form:input path="claimNo" maxlength="8"/>
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_search"/>" name="_target0" class="btnGeneric">
                    </form:form>
                    <br>
                    <form id="claim" name="claim" action="<c:url value="/pendingClaims.do"/>" method="post">
                        <input type="hidden" name="_target1" value="">
                    </form>
                    <table class="sortable" id="claimtable" width="100%">
                        <thead>
                        <tr>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_last_updated_time"/> (GMT <c:out value="${timeZone}"/>)</th>
                            <th><str:label strId="AMPARTNERPORTAL" key="label_last_updated_agent_id"/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:set var="currentLocale" value="${pageContext.response.locale}"/>
                        <fmt:setLocale value="en" scope="session"/>
                        <c:forEach items="${pendingClaimsForm.claims}" var="claim" varStatus="claimCount">
                            <tr>
                                <td><a href="javascript:submitForm('${claimCount.index}')"><c:out value="${claim.claimNo}"/></a></td>
                                <td><c:out value="${claim.packageDescription}"/></td>
                                <td><fmt:formatDate value="${claim.collectionTime}" pattern="dd MMM yyyy HH:mm"/></td>
                                <td><c:out value="${claim.collectionHandledBy}"/></td>
                            </tr>
                        </c:forEach>
                        <fmt:setLocale value="${currentLocale}" scope="session"/>
                        </tbody>
                    </table>
                    
                	<c:if test="${empty pendingClaimsForm.claims}">
                        <br><br><str:label strId="AMPARTNERPORTAL" key="info_no_pending_claim"/><br><br>
                    </c:if>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
        
    </div>
</body>
</html>
