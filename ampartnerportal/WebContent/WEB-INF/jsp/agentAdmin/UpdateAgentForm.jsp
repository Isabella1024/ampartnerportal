<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@ page import="java.net.URLEncoder" %>
<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_agent_admin_update"/>
</jsp:include>

<script type="text/javascript">
    function formSubmit(command) {
        document.getElementById('updateCommand').value = command;
        document.agentForm.submit();
    }
    function deleteUser() {
        if (!confirm('<str:label strId="AMPARTNERPORTAL" key="info_confirmation"/>')) {
            return;
        }
        formSubmit('Delete');
    }
</script>
<% 
	try {
		// We need to URLEncode the agent ID in case it contains URL-reserved characters ('&', '=' etc)
		String agentID = URLEncoder.encode(request.getParameter("agentID"), "UTF-8");
		pageContext.setAttribute("agentID", agentID);
	} catch (Exception e) {
		//Swallowed
	}
%>
<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="admin"/>
            <jsp:param name="hideLangSwitch" value="true"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <strong><str:label strId="AMPARTNERPORTAL" key="info_input_help"/></strong><br>

                    <c:if test="${!empty message}">
                        <p><c:out value="${message}"/></p>
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

                    <form:form commandName="agent" name="agentForm">
                        <table class="tbl_form" width="100%">
                            <thead>
                            <tr>
                                <th colspan="2"><str:label strId="AMPARTNERPORTAL" key="label_user_details"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td width="20%"><strong><str:label strId="AMPARTNERPORTAL" key="label_agent_id"/></strong></td>
                                <td width="80%">
                                    <spring:bind path="agent.agentID">
                                        <c:out value="${status.value}"/>
                                    </spring:bind>
                                </td>
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
                                    <c:choose>
                                    	<c:when test="${sessionScope['userSession'].agent.agentID eq agent.agentID}">
                                    		<spring:bind path="administratorIndicator">
                                    			<input type="hidden" name="<c:out value='${status.expression}'/>" value="<c:out value='${status.value}'/>"/>
                                    			<c:choose>
                                    				<c:when test="${status.value eq 'A'}"><str:label strId="AMPARTNERPORTAL" key="label_agent_type_admin"/></c:when>
                                    				<c:when test="${status.value eq 'F'}"><str:label strId="AMPARTNERPORTAL" key="label_agent_type_finance"/></c:when>
                                    				<c:when test="${status.value eq 'N'}"><str:label strId="AMPARTNERPORTAL" key="label_agent_type_normal"/></c:when>                                    				
                                    				<c:when test="${status.value eq 'S'}"><str:label strId="AMPARTNERPORTAL" key="label_agent_type_supervisor"/></c:when> 
                                    			</c:choose>
                                    		</spring:bind>
                                    	</c:when>
                                    	<c:otherwise>
		                                    <form:select path="administratorIndicator">
		                                        <form:option value="A"><str:label strId="AMPARTNERPORTAL" key="label_agent_type_admin"/></form:option>
												<form:option value="S"><str:label strId="AMPARTNERPORTAL" key="label_agent_type_supervisor"/></form:option>		                                        
		                                        <form:option value="F"><str:label strId="AMPARTNERPORTAL" key="label_agent_type_finance"/></form:option>
		                                        <form:option value="N"><str:label strId="AMPARTNERPORTAL" key="label_agent_type_normal"/></form:option>
		                                    </form:select>
	                                    </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>
                            <tr>
                                <td><form:label path="remarks" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_agent_remarks"/></form:label></td>
                                <td><form:input path="remarks" size="100%" maxlength="250"/></td>
                            </tr>
                            </tbody>
                        </table>
                        <span class="bulletLink"><a href="<c:url value="/agentAdmin.do"/>"><str:label strId="AMPARTNERPORTAL" key="info_back_to_users_list"/></a></span>
                        <input type="hidden" id="updateCommand" name="updateCommand" value=""/>
                        <input name="updateButton" type="button" value="<str:label strId="AMPARTNERPORTAL" key="btn_save"/>" class="btnGenericRight" onclick="formSubmit('Save');"/>
                        <input name="deleteButton" type="button" value="<str:label strId="AMPARTNERPORTAL" key="btn_delete"/>" class="btnGenericRight2" onclick="return deleteUser();"/>
                    </form:form>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
