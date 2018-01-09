<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>

<%@ page import="java.util.Date"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="com.asiamiles.partnerportal.util.CryptoHelper" %>

<META http-equiv="Content-Type" content="text/html; charset=UTF-8">
<TITLE>WebCLS Agent Admin Test Client</TITLE>
</HEAD>
<BODY>
	<h2>WebCLS Agent Admin Test Client</h2>
	
<%
	WebApplicationContext appContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
	CryptoHelper cryptoHelper = (CryptoHelper)appContext.getBean("CryptoHelper");
	String hash = null;
%>	

<c:choose>
	<c:when test="${!empty param.timestamp}">
		<c:set var="timestamp" value="${param.timestamp}"/>
	</c:when>
	<c:otherwise>
		<% 
			Date currentDate = new Date();
			pageContext.setAttribute("currentDate", currentDate);
		%>
		<fmt:formatDate var="timestamp" value="${currentDate}" pattern="yyyyMMddHH:mm:ss"/>
	</c:otherwise>
</c:choose>
<c:if test="${!empty param.agentID}">
	<c:set var="agentID" value="${param.agentID}"/>
</c:if>
<c:if test="${!empty param.hash}">
	<c:set var="hash" value="${param.hash}"/>
	<% hash = (String)pageContext.getAttribute("hash"); %>
</c:if>
<c:if test="${!empty param.inputHash}">
	<c:set var="inputHash" value="${param.inputHash}"/>
</c:if>

<c:if test="${!empty inputHash}">
	<%
		String inputHash = (String)pageContext.getAttribute("inputHash");
		if (inputHash != null) {
			String decryptedHash = cryptoHelper.decrypt(inputHash);
			pageContext.setAttribute("decryptedhash", decryptedHash);
		} 
	%>
</c:if>

<c:choose>
	<c:when test="${!empty param.refreshTimestamp}">
		<% 
			Date currentTime = new Date();
			pageContext.setAttribute("currentDate", currentTime);
		%>
		<fmt:formatDate var="timestamp" value="${currentDate}" pattern="yyyyMMddHH:mm:ss"/>
	</c:when>
	<c:when test="${!empty param.refreshTimestamp || !empty param.refreshHash}">
		<c:set var="data" value="AgentID=${agentID}&timestamp=${timestamp}"/>
		<%
			String data = (String)pageContext.getAttribute("data");
			hash = cryptoHelper.encrypt(data);
			pageContext.setAttribute("hash", hash);
		%>
 	</c:when>
 	<c:when test="${!empty param.refreshTimestamp || !empty param.refreshHash || !empty param.genTestLink}">
 		<c:set var="testLink" value="/amPartner/clsAgentAdmin.do?md=${hash}"/>
 	</c:when>
</c:choose>

<form method="post">
<table border="0">
    <thead>
        <td colspan="2"><strong>Partner Login Test</strong></td>
    </thead>
	<tr>
		<td>AgentID:</td>
		<td><input type="text" id="agentID" name="agentID" size="15" value="<c:out value='${agentID}'/>"/></td>
	</tr>
	<tr>
		<td>Timestamp:</td>
		<td><input type="text" id="timestamp" name="timestamp" size="12"  value="<c:out value='${timestamp}'/>"/></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td><fmt:parseDate var="date" value="${timestamp}" pattern="yyyyMMddHH:mm:ss"/><fmt:formatDate type="both" value="${date}" dateStyle="FULL" timeStyle="FULL"/>&nbsp;<input type="submit" id="refreshTimestamp" name="refreshTimestamp" value="Current Time"/></td>
	</tr>
	<tr>
		<td>md:</td>
		<td><input type="text" size="100" id="hash" name="hash" value="<c:out value='${hash}'/>"/>&nbsp;<input type="submit" id="refreshHash" name="refreshHash" value="Recompute HASH"/></td>
	</tr>
	<tr>
		<td>&nbsp;&nbsp;&nbsp;&nbsp;(decrypted)</td>
		<td><%=StringUtils.isEmpty(hash)? "" : cryptoHelper.decrypt(hash)%></td>
	</tr>
	<tr>
		<td>Test Link:</td>
		<td><input type="text" size="50" id="testLink" name="testLink" value="<c:out value='${testLink}'/>"/>&nbsp;<input type="submit" id="genTestLink" name="genTestLink"value="Generate"/></td>
	</tr>
	<tr>
		<td>&nbsp;</td>
		<td>
		<c:if test="${!empty testLink}">
			<a href="<c:out value='${testLink}'/>" target="_blank">Open Link in New Window</a>
		</c:if>
		</td>
	</tr>
</table>
<br/>
<table>
    <thead>
        <td colspan="2"><strong>Hash Decryption</strong></td>
    </thead>
    <tr>
        <td>Hash Value:</td>
        <td><input type="text" size="50" id="inputHash" name="inputHash" value="<c:out value='${inputHash}'/>"/>&nbsp;<input type="submit" id="decryptHash" name="decyptHash" value="Decypt Hash"/></td>
    </tr>
	<tr>
        <td>&nbsp;&nbsp;&nbsp;&nbsp;(decrypted)</td>
        <td><c:out value="${decryptedhash}"/></td>
    </tr>
</table>
</form>
</BODY>
</HTML>
