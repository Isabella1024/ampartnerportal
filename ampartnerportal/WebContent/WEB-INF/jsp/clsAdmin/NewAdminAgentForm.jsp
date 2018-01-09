<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_agent_admin_new"/>
</jsp:include>

<body>
    <div id="main_container">

		<div id="header"></header>
        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <strong><str:label strId="AMPARTNERPORTAL" key="info_input_help"/></strong><br>

                    <c:if test="${!empty messageCode}">
                        <p><fmt:message key="${messageCode}"/></p>
                    </c:if>
                    <c:if test="${!empty clsexception}">
                    	<span class="error"><fmt:message key="${clsexception.errorMessageCode}"/></span><br/>
                    </c:if>

                    <!-- Support for Spring errors holder -->
                    <spring:bind path="agent.*">
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

                    <form:form commandName="agent">
                        <table class="tbl_form" width="100%">
                            <thead>
                                <tr>
                                    <th colspan="2"><strong><str:label strId="AMPARTNERPORTAL" key="label_agent_id"/></strong></th>
                                </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td width="20%"><form:label path="partnerCode" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_partner_code_must"/></form:label></td>
                                <td width="80%"><form:input path="partnerCode" size="30%" maxlength="3"/></td>
                            </tr>
                            <tr>
                                <td width="20%"><form:label path="agentID" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_agent_id_must"/></form:label></td>
                                <td width="80%"><form:input path="agentID" size="30%" maxlength="15"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="familyName" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_family_name_must"/></form:label></td>
                                <td><form:input path="familyName" size="30%" maxlength="40"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="firstName" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_given_name_must"/></form:label></td>
                                <td><form:input path="firstName" size="30%" maxlength="25"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="emailAddress" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_agent_email_must"/></form:label></td>
                                <td><form:input path="emailAddress" size="30%" maxlength="50"/></td>
                            </tr>
                            <tr>
                                <td><form:label path="administratorIndicator" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_access_level"/></form:label></td>
                                <td>
                                	<spring:bind path="agent.administratorIndicator">
                                		<input type="hidden" name="${status.expression}" value="A"/>
                                		<str:label strId="AMPARTNERPORTAL" key="label_agent_type_admin"/>
                                	</spring:bind>
                                </td>
                            </tr>
<!--AML38938 Start-->
                            <tr>
                                <td><form:label path="partnerGroup" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_access_category"/></form:label></td>
                                <td>
                                    <form:select path="partnerGroup">
                                        <form:option value="R"><str:label strId="AMPARTNERPORTAL" key="label_agent_category_redemption"/></form:option>
                                        <form:option value="A"><str:label strId="AMPARTNERPORTAL" key="label_agent_category_accrual"/></form:option>
                                    </form:select>
                                </td>
                            </tr>
<!--AML38938 End-->
                            <tr>
                                <td><form:label path="remarks" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_agent_remarks"/></form:label></td>
                                <td><form:input path="remarks" size="100%" maxlength="250"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_add"/>" class="btnGenericRight"/>
                    </form:form>
                </div>
            </div>
        </div>    

		<div id="footer">
			<div class="links">&nbsp;</div>
		</div>
    </div>
</body>
</html>
