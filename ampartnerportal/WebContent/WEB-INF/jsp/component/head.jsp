<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- STR include --%>
<%@ taglib uri="/WEB-INF/str.tld" prefix="str" %>


<head>
	<%-- Make sure that browsers do not cache the content --%>
	<meta http-equiv="Pragma" content="no-cache">
	<meta http-equiv="expires" content="0">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/theme/portal.css"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/sortable.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/script/init.js"></script>

    <title><str:label strId="AMPARTNERPORTAL" key="${param.title}"/></title>
</head>