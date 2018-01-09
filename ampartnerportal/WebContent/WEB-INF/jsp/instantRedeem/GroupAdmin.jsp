<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="instant_redeem_group_admin_title"/>
</jsp:include>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="groupAdmin"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <strong><str:label strId="AMPARTNERPORTAL" key="instant_redeem_title_list_group"/></strong><br><br>
					<c:if test="${!empty messageCode}">
                        <div class="box_acknowledgement"><fmt:message key="${messageCode}"/></div><br>
					</c:if>
                    <c:if test="${!empty message}">
                        <p><c:out value="${message}"/></p>
                    </c:if>
                    <c:if test="${!empty clsexception}">
                    	<span class="error"><fmt:message key="${clsexception.errorMessageCode}"/></span><br/>
                    </c:if>
                    

                    <form action="<c:url value='/instantRedeem/loadGroupAdd.do'/>">
                    	<input class="btnGenericRight" type="submit" value="<str:label strId="AMPARTNERPORTAL" key="instant_redeem_btn_add_group"/>">
                    </form>
                    <br>
                    <br>
                    <table class="sortable" id="groupsTable" width="100%">
                        <thead>
                            <tr>
                                <th width="50%"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_group_id"/></th>
                                <th class="unsortable" width="50%">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${groups}" var="group" varStatus="index">
                            	<c:url value="/instantRedeem/listGroup.do" var="agentAssignUrl">
                            		<c:param name="groupId" value="${group.GROUP_ID}"/>
                            	</c:url>
                            	<c:url value="/instantRedeem/listPackageGroup.do" var="packageAssignUrl">
                            		<c:param name="groupId" value="${group.GROUP_ID}"/>
                            	</c:url>
                            	<c:url value="/instantRedeem/deleteGroup.do" var="deleteGroupUrl">
                            		<c:param name="groupId" value="${group.GROUP_ID}"/>
                            	</c:url>
                           		<tr>
                                <td><c:out value="${group.GROUP_ID}"/></td>
                                <td><a href="<c:out value='${agentAssignUrl}'/>"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_assign_agent"/></a>
                                    | <a href="<c:out value='${packageAssignUrl}'/>"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_package_code_assign"/></a>
                                    | <a href="<c:out value='${deleteGroupUrl}'/>"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_group_delete"/></a>
                                </td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
                
                <div class="bd">
                    <strong><str:label strId="AMPARTNERPORTAL" key="instant_redeem_title_list_package"/></strong><br><br>
                    <br>
                    <br>
                    <table class="sortable" id="packagesTable" width="100%">
                        <thead>
                            <tr>
                                <th width="50%"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_package_code"/></th>
                                <th class="unsortable" width="50%">&nbsp;</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${packages}" var="package" varStatus="index">
                            	<c:url value="/instantRedeem/listGroupForPackage.do" var="groupAssignUrl">
                            		<c:param name="packageCode" value="${package.PACKAGE_CODE}"/>
                            	</c:url>
                           		<tr>
                                <td><c:out value="${package.PACKAGE_CODE}"/></td>
                                <td><a href="<c:out value='${groupAssignUrl}'/>"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_assign_group"/></a>
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
