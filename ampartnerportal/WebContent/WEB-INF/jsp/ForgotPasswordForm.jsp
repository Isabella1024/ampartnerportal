<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_forgot_password"/>
</jsp:include>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/header.jsp">
            <jsp:param name="headerMenu" value="nav_user_login"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <str:label strId="AMPARTNERPORTAL" key="instruction_forgot_password"/><br>

                    <form:form commandName="forgotPasswordForm">
                        <table id="partnerLogin" class="tbl_transparent" width="400px">
                            <tbody>
                            <tr>
                                <td colspan="3"  align="center">
                                    <c:if test="${!empty messageCode}">
                                        <fmt:message key="${messageCode}"/>
                                    </c:if>

                                    <!-- Support for Spring errors holder -->
                                    <spring:bind path="forgotPasswordForm.*">
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
                                </td>
                            </tr>
                            <tr>
                                <td width="30%" align="right">
                                    <form:label path="agentID" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_agent_id"/></form:label>
                                </td>
                                <td width="70%" colspan="2">
                                    <form:input path="agentID" maxlength="15"/>
                                    <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_submit"/>" class="btnGeneric"/>
                                </td>
                            </tr>
                            </tbody>
                        </table>
                    </form:form>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
