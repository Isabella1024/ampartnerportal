<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="instant_redeem_list_group_for_package_title"/>
</jsp:include>
<body>
<div id="main_container">
	<jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
	            <jsp:param name="nav" value="groupAdmin"/>
	            <jsp:param name="hideLangSwitch" value="true"/>
	</jsp:include>
	<div id="content">
		<div id="mod_form" class="mod">
        	<div class="bd">
				<form id="assignGroupToPackage" action="/amPartner/instantRedeem/assignGroupToPackage.do" method="post">
            		<table class="tbl_form" width="100%">
                    	<thead>
                        	<tr>
                            	<th colspan="2"><strong><str:label strId="AMPARTNERPORTAL" key="instant_redeem_package_code_assign"/></strong></th>
                            </tr>
                        </thead>
                        <tbody>
                        	<tr>
                            	<td width="20%"><label for="pargentCode"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_partner_code"/></label></td>
                            	<td width="80%">
                            		<label>${sessionScope['userSession'].agent.partnerCode }</label>
                            		<input type="hidden" name="partnerCode" id="partnerCode" value="${sessionScope['userSession'].agent.partnerCode}"/>
                            	</td>
                        	</tr>
                        	<tr>
                            	<td width="20%"><label for="packageCode"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_package_code"/> *</label></td>
                            	<td width="80%"><label>${packageCode}</label><input type="hidden" id="packageCode" name="packageCode" value="${packageCode }"/></td>
                        	</tr> 
                        	<tr id="packageCodeRow">
                            	<td width="20%"><label for="type"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_group_id"/> *</label></td>
                            	<td width="80%">
                             	<c:forEach items="${groups}" var="group" varStatus="index">
                             		<input type="checkbox" name="groupId" id="groupId" value="${group.GROUP_ID }"
                             			<c:forEach items="${assignedGroups }" var="assignedGroup" varStatus="index">
                            				<c:if test="${group.GROUP_ID eq assignedGroup.GROUP_ID}">
                            					<c:out value="checked"/>
											</c:if>
                             			</c:forEach>
                             		> &nbsp;&nbsp;${group.GROUP_ID }&nbsp;&nbsp;<br/>
                            	</c:forEach>
                            	</td>
                        	</tr>
                    	</tbody>
                    </table>
                    <span class="bulletLink"><a href="<c:url value="/instantRedeem/groupAdmin.do"/>"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_back_to_admin"/></a></span>
                    <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_submit"/>" class="btnGenericRight"/>
   				</form>
   			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
</body>
</html>