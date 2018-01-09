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
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0, user-scalable=no">
    <title><str:label strId="AMPARTNERPORTAL" key="instant_redeem_meta_title"/></title>
    
    <!-- Add to homescreen for Chrome on Android -->
    <meta name="mobile-web-app-capable" content="yes">
    <link rel="icon" sizes="196x196" href="<%=request.getContextPath()%>/images/instantRedeem/touch/chrome-touch-icon-196x196.png">

    <!-- Add to homescreen for Safari on iOS -->
    <meta name="apple-mobile-web-app-capable" content="yes">
    <meta name="apple-mobile-web-app-status-bar-style" content="black">
    <meta name="apple-mobile-web-app-title" content="<str:label strId='AMPARTNERPORTAL' key='instant_redeem_meta_title'/>">
    <!-- Disable phone number linking in Mobile Safari -->
    <meta name="format-detection" content="telephone=no">

    <!-- Tile icon for Win8 (144x144 + tile color) -->
    <meta name="msapplication-TileImage" content="<%=request.getContextPath()%>/images/instantRedeem/touch/ms-touch-icon-144x144-precomposed.png">
    <meta name="msapplication-TileColor" content="#3372DF">

    <!-- build:css styles/components/main.min.css -->
    <link rel="stylesheet" href="<%=request.getContextPath()%>/theme/instantRedeem/components/components.css">
    <link rel="stylesheet" href="<%=request.getContextPath()%>/theme/instantRedeem/main.css">
	<link rel="stylesheet" href="<%=request.getContextPath()%>/theme/instantRedeem/merchant.css">
</head>