<%@ page import="com.asiamiles.partnerportal.domain.UserSession" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%-- STR include --%>
<%@ taglib uri="/WEB-INF/str.tld" prefix="str" %>

<% UserSession userSession = (UserSession) request.getSession().getAttribute("userSession"); %>


<header class="app-bar promote-layer app-bar-aml">
	<div class="topbar-aml-member topbar-aml-merchant-inside">
		<div class="app-bar-container">
			<h1 class="logo logo-aml"><a href="#" class="logo-img"><img src="<%=request.getContextPath()%>/images/instantRedeem/merchant/asiamiles-logo-2.png"/></a><span class="merchantname"><%=userSession.getAgent().getPartnerName()%></span></h1>
		</div>
	</div>
</header> 
