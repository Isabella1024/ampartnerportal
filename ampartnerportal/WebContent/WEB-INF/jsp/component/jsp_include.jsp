<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="com.asiamiles.partnerportal.util.WebUtil" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%-- STR include --%>
<%@ taglib uri="/WEB-INF/str.tld" prefix="str" %>

<%-- Init STR Common --%>
<% 
   String lang = "";
   if (null != request.getParameter("lang")) {
       lang = request.getParameter("lang");
   } else {
       if (null != WebUtil.getCookieByName(request.getCookies(), "clientlanguage")) {
           lang = WebUtil.getCookieByName(request.getCookies(), "clientlanguage").getValue();
       } else  {
	       lang = response.getLocale().getLanguage(); 
	       String Country = response.getLocale().getCountry();
	       if (Country != null && Country.equalsIgnoreCase("CN")) {
	            lang = "sc";
	       } else if (!lang.equalsIgnoreCase("zh")) {
	            lang = "en";
	       }
       }
   }

%>
<str:map appCode="AMPARTNERPORTAL" lang="<%=lang%>" cache="true" strId='AMPARTNERPORTAL'/>

