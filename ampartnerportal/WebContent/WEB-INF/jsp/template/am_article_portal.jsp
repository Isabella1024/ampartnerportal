<%@include file="/component/am_jsp_include.jsp"%>
<%@page import = "org.apache.commons.lang.StringUtils"%>

<%  try {
        String friendlyURL = (String) request.getAttribute("friendlyURL");
        String contextPath = request.getContextPath();
        String section = StringUtils.substringAfterLast(friendlyURL,"/");
        pageContext.setAttribute("section",section);
        pageContext.setAttribute("lang",lang);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

<%-- include this html header in channel/content template --%>
<%@include file="/component/am_html_header.jsp"%>

<dsm:styleSheet/>
<link href="<%=SiteURLUtil.createSystemStaticFileSiteURL("/css/am_portal.css") %>" rel="stylesheet" type="text/css"/>
</head>
<body>
    <div id="main_container">
        <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
        <%-- STR include --%>
        <%@ taglib uri="/WEB-INF/str.tld" prefix="str" %>
        <%-- Init STR Common --%>
        <str:map appCode="AMPARTNERPORTAL" lang="<%=lang%>" cache="true" strId='AMPARTNERPORTAL'/>


        <div id="header">
            <div class="logo_bar">
                <h1 class="AM_logo">
                    <a href="<str:label strId="AMPARTNERPORTAL" key="link_partner_portal"/>">
                        <img alt="Asia Miles" src="https://www.asiamiles.com/am/System/application_images/bg/amlogo.gif"/>
                    </a>
                    <span class="title">
                        <str:label strId="AMPARTNERPORTAL" key="label_site_title"/>
                    </span>
                </h1>
                <ul class="lang_switch">
                    <li>
                        <c:choose>
                            <c:when test="${lang == 'en'}">
                                <a class="selected">ENG</a>
                            </c:when>
                            <c:otherwise>
                                <a href="<%=contextPath%>/en<%=friendlyURL%>">ENG</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${lang == 'zh'}">
                                <a class="selected">繁體中文</a>
                            </c:when>
                            <c:otherwise>
                                <a href="<%=contextPath%>/zh<%=friendlyURL%>">繁體中文</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                    <li>
                        <c:choose>
                            <c:when test="${lang == 'sc'}">
                                <a class="selected">简体中文</a>
                            </c:when>
                            <c:otherwise>
                                <a href="<%=contextPath%>/sc<%=friendlyURL%>">简体中文</a>
                            </c:otherwise>
                        </c:choose>
                    </li>
                </ul>
            </div>

            <div id="main_nav">
                <ul class="menu">
                    <li><div><a>
                     <c:if test="${section == 'terms'}"><str:label strId="AMPARTNERPORTAL" key="footer_terms"/></c:if>
                     <c:if test="${section == 'privacy'}"><str:label strId="AMPARTNERPORTAL" key="footer_privacy"/></c:if>
                     <c:if test="${section == 'disclaimer'}"><str:label strId="AMPARTNERPORTAL" key="footer_disclaimer"/></c:if>
                    </a></div></li>
                </ul>
            </div>

            <div id="breadcrumb">
                <ul>
                    <li>&nbsp;</li>
                </ul>
            </div>
        </div>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <dsm:contentRegion name="am_article_portal" scope="template" jsp="/am/jsp/am_simple_article.jsp" />
                </div>
            </div>
        </div>

        <div id="footer">
            <div class="links">
                <str:label strId="AMPARTNERPORTAL" key="footer_Terms_And_Conditions"/> | <str:label strId="AMPARTNERPORTAL" key="footer_Privacy_Policy"/>
            </div>
            <div class="copyright">
                <str:label strId="AMPARTNERPORTAL" key="footer_Copyright"/>
            </div>
        </div>
    </div>

    <%@include file="/component/am_vgn_edit.jsp" %>
</body>
</html>
<%
	} catch (Throwable t) {
		ExceptionHandler.handleJspException(request, response, t, "/templates/am_article_portal.jsp");
	}
%>