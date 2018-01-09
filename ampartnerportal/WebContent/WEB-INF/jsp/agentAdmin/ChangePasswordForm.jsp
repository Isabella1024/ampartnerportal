<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_change_password"/>
</jsp:include>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="change"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <strong><str:label strId="AMPARTNERPORTAL" key="info_password_help"/></strong><br><br>
                    <strong><str:label strId="AMPARTNERPORTAL" key="info_input_help"/></strong><br>

					<c:if test="${!empty messageCode}">
						<div class="box_acknowledgement"><fmt:message key="${messageCode}"/></div><br>
					</c:if>

                    <c:if test="${!empty clsexception}">
                    	<span class="error"><fmt:message key="${clsexception.errorMessageCode}"/></span><br/>
                    </c:if>

                    <!-- Support for Spring errors holder -->
                    <spring:bind path="changePasswordForm.*">
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

                    <form:form commandName="changePasswordForm">
                        <table class="tbl_form" width="100%">
                            <thead>
                                <tr>
                                    <th colspan="2"><str:label strId="AMPARTNERPORTAL" key="label_enter_passwords"/></th>
                                </tr>
                            </thead>
                            <tbody>
                                <tr>
                                    <td width="20%"><form:label path="oldPassword" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_old_password"/></form:label></td>
                                    <td width="80%"><form:password path="oldPassword" maxlength="8"/></td>
                                </tr>
                                <tr>
                                    <td><form:label path="newPassword" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_new_password"/></form:label></td>
                                    <td><form:password path="newPassword" maxlength="8"/>&nbsp;<str:label strId="AMPARTNERPORTAL" key="info_password_length"/></td>
                                </tr>
                                <tr>
                                    <td><form:label path="confirmPassword" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_confirm_password"/></form:label></td>
                                    <td><form:password path="confirmPassword" maxlength="8"/></td>
                                </tr>
                            </tbody>
                        </table>
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_submit"/>" class="btnGenericRight"/>
                    </form:form>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
