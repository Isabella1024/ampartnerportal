<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_agent_admin"/>
</jsp:include>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="admin"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <strong><str:label strId="AMPARTNERPORTAL" key="info_list_user"/></strong><br><br>
					<c:if test="${!empty messageCode}">
                        <div class="box_acknowledgement"><fmt:message key="${messageCode}"/></div><br>
					</c:if>
                    <c:if test="${!empty message}">
                        <p><c:out value="${message}"/></p>
                    </c:if>
                    <c:if test="${!empty clsexception}">
                    	<span class="error"><fmt:message key="${clsexception.errorMessageCode}"/></span><br/>
                    </c:if>
                    

                    <form action="<c:url value='/newAgent.do'/>">
                    	<input class="btnGenericRight" type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_add_user"/>">
                    </form>
                    <br>
                    <br>
                    <table class="sortable" id="usersTable" width="100%">
                        <thead>
                            <tr>
                                <th width="25%"><str:label strId="AMPARTNERPORTAL" key="label_agent_id"/></th>
                                <th width="25%"><str:label strId="AMPARTNERPORTAL" key="label_name"/></th>
                                <th width="25%"><str:label strId="AMPARTNERPORTAL" key="label_agent_type"/></th>
                                <th class="unsortable" width="25%">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${agents}" var="agent" varStatus="index">
                            	<c:url value="/updateAgent.do" var="updateURL">
                            		<c:param name="agentID" value="${agent.agentID}"/>
                            	</c:url>
                            	<c:url value="/agentResetPassword.do" var="resetPasswordURL">
                            		<c:param name="agentID" value="${agent.agentID}"/>
                            	</c:url>
                            <tr>
                                <td><c:out value="${agent.agentID}"/></td>
                                <td><c:out value="${agent.firstName} ${agent.familyName}"/></td>
                                <c:choose>
                                    <c:when test="${agent.administratorIndicator == 'A'}">
                                        <td><str:label strId="AMPARTNERPORTAL" key="label_agent_type_admin"/></td>
                                    </c:when>
                                    <c:when test="${agent.administratorIndicator == 'S'}">
                                        <td><str:label strId="AMPARTNERPORTAL" key="label_agent_type_supervisor"/></td>
                                    </c:when>                                     
                                    <c:when test="${agent.administratorIndicator == 'F'}">
                                        <td><str:label strId="AMPARTNERPORTAL" key="label_agent_type_finance"/></td>
                                    </c:when>
                                    <c:when test="${agent.administratorIndicator == 'N'}">
                                        <td><str:label strId="AMPARTNERPORTAL" key="label_agent_type_normal"/></td>
                                    </c:when>
                                    <c:otherwise><td></td></c:otherwise>
                                </c:choose>
                                <td><a href="<c:out value='${updateURL}'/>"><str:label strId="AMPARTNERPORTAL" key="label_view_edit"/></a>
                                    | <a href="<c:out value='${resetPasswordURL}'/>"><str:label strId="AMPARTNERPORTAL" key="label_unlock_reset"/></a>
                                </td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
