<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_user_login"/>
</jsp:include>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/header.jsp">
            <jsp:param name="headerMenu" value="nav_user_login"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <form action="<c:url value="/login.do"/>" method="POST">
                        <table id="partnerLogin" class="tbl_transparent" width="400px">
                            <thead>
                            </thead>
                            <tbody>
                            <tr>
                                <td colspan="3" align="center">
                                    <c:if test="${!empty messageCode}">
                                        <span class="error">
                                        	<fmt:message key="${messageCode}">
                                        		<fmt:param value="${agentId}"/>
                                        	</fmt:message>
                                        </span>
                                    </c:if>
                                    <c:if test="${!empty clsexception}">
                                        <span class="error"><fmt:message key="${clsexception.errorMessageCode}"/></span>
                                    </c:if>
                                    <c:if test="${!empty loginForwardAction}">
                                        <input type="hidden" name="forwardAction" value="<c:url value="${loginForwardAction}"/>"/>
                                    </c:if>
                                </td>
                            </tr>
                            <tr>
                                <td width="30%" align="right"><strong><label for="agentId"><str:label strId="AMPARTNERPORTAL" key="label_agent_id"/></label></strong></td>
                                <td width="70%" colspan="2"><input type="text" name="agentId" id="agentId" maxlength="15" value="<c:out value='${agentId}'/>"/></td>
                            </tr>
                            <tr>
                                <td align="right"><strong><label for="password"><str:label strId="AMPARTNERPORTAL" key="label_password"/></label></strong></td>
                                <td colspan="3"><input type="password" name="password" id="password" maxlength="8"/>&nbsp;<input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_login"/>" class="btnGeneric"/></td>
                            </tr>
                            <tr>
                                <td>&nbsp;</td>
                                <td><a href="<c:url value='/forgotPassword.do'/>"><str:label strId="AMPARTNERPORTAL" key="label_forgot_password"/></a></td>
                            </tr>
                            </tbody>
                        </table>
                    </form>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>