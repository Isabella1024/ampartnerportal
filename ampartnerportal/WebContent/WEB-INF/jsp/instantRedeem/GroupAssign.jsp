<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="instant_redeem_group_assign_title"/>
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
<form id="group" action="/amPartner/instantRedeem/groupAssign.do" method="post">

                        <table class="tbl_form" width="100%">
                            <thead>
                                <tr>
                                    <th colspan="2"><strong><str:label strId="AMPARTNERPORTAL" key="instant_redeem_group_assign"/></strong></th>
                                </tr>
                            </thead>
                            <tbody>
                            <tr>
                                <td width="20%"><label for="pargentCode"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_partner_code"/></label></td>
                                <td width="80%"><label>${sessionScope['userSession'].agent.partnerCode }</label><input type="hidden" id="type" name="type" value="agentId"></td>
                            </tr>
                            <tr>
                                <td width="20%"><label for="groupId"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_group_id"/> *</label></td>
                                <td width="80%"><label>${groupId}</label><input type="hidden" id="groupId" name="groupId" value="${groupId }"/></td>
                            </tr>
                            <tr id="agentIDRow">
                                <td width="20%"><label for="agentId"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_agent_id"/> *</label></td>
                                <td width="80%">
                                	<c:forEach items="${agents}" var="agent" varStatus="index">
                                		<input type="checkbox" name="agentId" id="agentId" value="${agent.agentID }" 
                                			<c:forEach items="${existingMember }" var="member" varStatus="index">
	                              				<c:if test="${member.AGENT_ID eq agent.agentID}">
	                              					<c:out value="checked"/>
												</c:if>
                                			</c:forEach>
                                		> &nbsp;&nbsp;${agent.agentID }&nbsp;&nbsp;<br/>
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