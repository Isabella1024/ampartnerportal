<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%-- STR include --%>
<%@ taglib uri="/WEB-INF/str.tld" prefix="str" %>


<div id="header">
    <div class="logo_bar">
        <h1 class="AM_logo">
            <a href="<%=request.getContextPath()%>/homepage.do">
                <img alt="Asia Miles" src="https://www.asiamiles.com/am/System/application_images/bg/amlogo.gif"/>
            </a>
            <span class="title">
                <str:label strId="AMPARTNERPORTAL" key="label_site_title"/>
            </span>
        </h1>
        <c:if test="${param.hideLangSwitch != 'true'}">
            <ul class="lang_switch">
                <li>
                    <% if (lang.equals("en")) { %>
                        <a class="selected">ENG</a>
                    <% } else { %>
                        <a href="?lang=en">ENG</a>
                    <% } %>
                </li>
                <li>
                    <% if (lang.equals("zh")) { %>
                        <a class="selected">繁體中文</a>
                    <% } else { %>
                        <a href="?lang=zh">繁體中文</a>
                    <% } %>
                </li>
                <li>
                    <% if (lang.equals("sc")) { %>
                        <a class="selected">简体中文</a>
                    <% } else { %>
                        <a href="?lang=sc">简体中文</a>
                    <% } %>
                </li>
            </ul>
        </c:if>
    </div>

    <div id="main_nav">
        <ul class="menu">
            <li class="double-line"><a><str:label strId="AMPARTNERPORTAL" key="${param.headerMenu}"/></a></li>
        </ul>
    </div>

    <div id="breadcrumb">
        <ul>
            <li>&nbsp;</li>
        </ul>
    </div>
</div>
