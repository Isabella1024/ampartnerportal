<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_billing_establishment"/>
</jsp:include>

<script language="javascript">
    function submitForm(establishmentIndex) {
        MyForm = document.forms["selection"];
        var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "establishmentIndex");
        hiddenField.setAttribute("value", establishmentIndex);
        MyForm.appendChild(hiddenField);
        MyForm.submit();
    }
</script>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="billing"/>
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
 					<!-- number of unbilled establishments: ${fn:length(billingForm.unbilledEstablishments)}-->
                    <form id="selection" action="<c:url value="/billing.do"/>" method="post">
                        <input type="hidden" name="_target1" value="">
                    </form>
                    <c:choose>
                        <c:when test="${fn:length(billingForm.unbilledEstablishments) == 0}">
                            <strong><str:label strId="AMPARTNERPORTAL" key="info_no_outstanding_billing"/></strong><br><br>
                        </c:when>
                        <c:otherwise>
                            <strong><str:label strId="AMPARTNERPORTAL" key="info_select_outstanding_billing"/></strong><br><br>
	                        <c:set var="currentLocale" value="${pageContext.response.locale}"/>
	                        <fmt:setLocale value="en" scope="session"/>    
                            <c:forEach items="${billingForm.unbilledEstablishments}" var="establishment" varStatus="loopStatus">
                                <span class="bulletLink">
                                    <a href="javascript:submitForm('<c:out value="${loopStatus.index}"/>')">
                                    <fmt:formatDate value="${establishment.oldestCollectionTimestamp}" pattern="dd MMM yyyy"/> - <fmt:formatDate value="${billingForm.cutOffDate}" pattern="dd MMM yyyy"/> <c:out value="${establishment.establishmentName}"/>
                                    </a>
                                </span><br>
                            </c:forEach>
                            <fmt:setLocale value="${currentLocale}" scope="session"/>
                            <br>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>