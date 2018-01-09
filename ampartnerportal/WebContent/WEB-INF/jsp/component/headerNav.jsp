<%@ page import="com.asiamiles.partnerportal.domain.UserSession" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%-- STR include --%>
<%@ taglib uri="/WEB-INF/str.tld" prefix="str" %>
<%-- Init STR Common --%>
<% 
	String isReport = "false";
	if(request.getRequestURI().indexOf("report") >= 0)
	{ isReport = "true";
	}
	pageContext.setAttribute("isReport",isReport);
	pageContext.setAttribute("lang",lang);
%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<% UserSession userSession = (UserSession) request.getSession().getAttribute("userSession"); %>

<%
//cppjox add For AML38938 20160606 start 
String displayAccrulHyperlink = "style='display:display;'";
String displayRedemptionHyperlink= "style='display:display;'";
Agent loggedInAgent = userSession.getAgent();
if("A".equals(loggedInAgent.getPartnerGroup())){
	displayAccrulHyperlink = "style='display:none;'";
}
if("R".equals(loggedInAgent.getPartnerGroup())){
	displayRedemptionHyperlink = "style='display:none;'";
}

//cppjox add For AML38938 20160606 end 
%>

<!-- length of param query string: ${fn:length(param.queryString)}-->                    

<%@page import="com.asiamiles.partnerportal.domain.Agent"%>
<%@page import="org.springframework.web.util.WebUtils"%><c:if test="${fn:length(param.queryString) > 0}">
	<c:set var="queryString" value="${param.queryString}&"/>
</c:if>

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
                <c:choose>
            	<c:when test="${isReport == 'true'}">
                	<li>
                	<c:choose>
                	<c:when test="${lang == 'en'}">
                        <a class="selected">ENG</a>
                    </c:when>
                    <c:otherwise>
        				<a href="javascript:switchLang('en');">ENG</a>
                 	</c:otherwise>
                 	</c:choose>
                	</li>
                	<li>
                	<c:choose>
                	<c:when test="${lang == 'zh'}">
                        <a class="selected">繁體中文</a>
                    </c:when>
                    <c:otherwise>
        				<a href="javascript:switchLang('zh');">繁體中文</a>
                 	</c:otherwise>
                 	</c:choose>
                 	</li>
                 	<li>
 		            <c:choose>
                	<c:when test="${lang == 'sc'}">
                        <a class="selected">简体中文</a>
                    </c:when>
                    <c:otherwise>
        				<a href="javascript:switchLang('sc');">简体中文</a>
                 	</c:otherwise>
                 	</c:choose>
                 	</li>	                 	
            	</c:when>
            	<c:otherwise>
            		<li>
                	<c:choose>
                	<c:when test="${lang == 'en'}">
                        <a class="selected">ENG</a>
                    </c:when>
                    <c:otherwise>
        				<a href="?<c:out value='${queryString}'/>lang=en">ENG</a>
                 	</c:otherwise>
                 	</c:choose>
                 	</li>
                 	<li>
                	<c:choose>
                	<c:when test="${lang == 'zh'}">
                        <a class="selected">繁體中文</a>
                    </c:when>
                    <c:otherwise>
        				<a href="?<c:out value='${queryString}'/>lang=zh">繁體中文</a>
                 	</c:otherwise>
                 	</c:choose>	                 	
                 	</li>
                 	<li>
                	<c:choose>
                	<c:when test="${lang == 'sc'}">
                        <a class="selected">简体中文</a>
                    </c:when>
                    <c:otherwise>
        				<a href="?<c:out value='${queryString}'/>lang=sc">简体中文</a>
                 	</c:otherwise>
                 	</c:choose>	                 	
                 	</li>	                 	
             	</c:otherwise>
             	</c:choose>
            </ul>
        </c:if>
    </div>

                    
    <div id="main_nav">
        <ul class="menu">
            <% if (lang.equals("en")) { %>
                <li>
            <% } else { %>
                <li class="double-line">
            <% } %>
            <%
                if (request.getSession().getAttribute("allowURLs") != null) {
                    ArrayList allowURLs = (ArrayList) request.getSession().getAttribute("allowURLs");
            %>
                <% if (allowURLs.contains("/redemption.do")) { %>
                <a id="hdrNav1"
                        <c:if test="${param.nav == 'redemption'}">
                            class="nav_current"
                        </c:if>
                        onmouseover="fnMenuShowHolder('navChnl1',this);" onmouseout="fnMenuHide();" href="<c:url value='/redemption.do'/>"><str:label strId="AMPARTNERPORTAL" key="nav_verify_redemption"/></a>
                <% } else { %>
                   <div <%=displayAccrulHyperlink%>> <str:label strId="AMPARTNERPORTAL" key="nav_verify_redemption"/></div>
                <% } %>
            </li>
            <li class="seperator" <%=displayAccrulHyperlink%>>|</li>
            <% if (lang.equals("en")) { %>
                <li>
            <% } else { %>
                <li class="double-line">
            <% } %>
                <% if (allowURLs.contains("/pendingClaims.do")) { %>
                <a id="hdrNav2"
                        <c:if test="${param.nav == 'pending'}">
                            class="nav_current"
                        </c:if>
                        onmouseover="fnMenuShowHolder('navChnl2',this);" onmouseout="fnMenuHide();" href="<c:url value='/pendingClaims.do'/>"><str:label strId="AMPARTNERPORTAL" key="nav_pending_claims"/></a>
                <% } else { %>
                   <div <%=displayAccrulHyperlink%>> <str:label strId="AMPARTNERPORTAL" key="nav_pending_claims"/></div>
                <% } %>
            </li>
            <li class="seperator" <%=displayAccrulHyperlink%>>|</li>
            <% if (lang.equals("en")) { %>
                <li>
            <% } else { %>
                <li class="double-line">
            <% } %>
                <% if (allowURLs.contains("/membership.do")) { %>
                <a id="hdrNav9"
                        <c:if test="${param.nav == 'membership'}">
                            class="nav_current"
                        </c:if>
                        onmouseover="fnMenuShowHolder('navChn19',this);" onmouseout="fnMenuHide();" href="<c:url value='/membership.do'/>"><str:label strId="AMPARTNERPORTAL" key="nav_verify_membership"/></a>
                <% } else { %>
                    <div <%=displayRedemptionHyperlink%>><str:label strId="AMPARTNERPORTAL" key="nav_verify_membership"/></div>
                <% } %>
            </li>
            <li class="seperator" <%=displayRedemptionHyperlink%>>|</li>
            <li class="double-line">
                <% if (allowURLs.contains("/billing.do")) { %>
                <a id="hdrNav3"
                        <c:if test="${param.nav == 'billing'}">
                            class="nav_current"
                        </c:if>
                        onmouseover="fnMenuShowHolder('navChnl3',this);" onmouseout="fnMenuHide();" href="<c:url value='/billing.do'/>"><str:label strId="AMPARTNERPORTAL" key="nav_billing"/></a>
                <% } else { %>
                     <div <%=displayAccrulHyperlink%>><str:label strId="AMPARTNERPORTAL" key="nav_billing"/></div>
                <% } %>
            </li>
            <li class="seperator" <%=displayAccrulHyperlink%>>|</li>
            <li class="double-line">
                <% if (allowURLs.contains("/report.do")) { %>
                <a id="hdrNav4"
                        <c:if test="${param.nav == 'report'}">
                            class="nav_current"
                        </c:if>
                        onmouseover="fnMenuShowHolder('navChnl4',this);" onmouseout="fnMenuHide();" href="<c:url value='/report.do'/>"><str:label strId="AMPARTNERPORTAL" key="nav_report"/></a>
                <% } else { %>
                    <div <%=displayAccrulHyperlink%>><str:label strId="AMPARTNERPORTAL" key="nav_report"/></div>
                <% } %>
            </li>
            <li class="seperator" <%=displayAccrulHyperlink%>>|</li>
            <% if (lang.equals("en")) { %>
                <li>
            <% } else { %>
                <li class="double-line">
            <% } %>
                <% if (allowURLs.contains("/changePassword.do")) { %>
                <a id="hdrNav5"
                        <c:if test="${param.nav == 'change'}">
                            class="nav_current"
                        </c:if>
                        onmouseover="fnMenuShowHolder('navChnl5',this);" onmouseout="fnMenuHide();" href="<c:url value='/changePassword.do'/>"><str:label strId="AMPARTNERPORTAL" key="nav_change_password"/></a>
                <% } else { %>
                    <str:label strId="AMPARTNERPORTAL" key="nav_change_password"/>
                <% } %>
            </li>
            <li class="seperator">|</li>
            <li class="double-line">
                <% if (allowURLs.contains("/agentAdmin.do")) { %>
                <a id="hdrNav6"
                        <c:if test="${param.nav == 'admin'}">
                            class="nav_current"
                        </c:if>
                        onmouseover="fnMenuShowHolder('navChnl6',this);" onmouseout="fnMenuHide();" href="<c:url value='/agentAdmin.do'/>"><str:label strId="AMPARTNERPORTAL" key="nav_user_admin"/></a>
                <% } else { %>
                    <str:label strId="AMPARTNERPORTAL" key="nav_user_admin"/>
                <% } %>
            </li>
            <% if (allowURLs.contains("/instantRedeem/groupAdmin.do")) { %>
            <li class="seperator">|</li>
            <li class="double-line">
                <a id="hdrNav7"
                        <c:if test="${param.nav == 'groupAdmin'}">
                            class="nav_current"
                        </c:if>
                        onmouseover="fnMenuShowHolder('navChnl7',this);" onmouseout="fnMenuHide();" href="<c:url value='/instantRedeem/groupAdmin.do'/>"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_nav"/></a>
            </li>
            <% } %>  
             <% if (allowURLs.contains("/instantRedeem/listPackage.do")) { %>
            <li class="seperator">|</li>
            <% if (lang.equals("en")) { %>
                <li>
            <% } else { %>
                <li class="double-line">
            <% } %>
                <a id="hdrNav8"
                        <c:if test="${param.nav == 'listPackage'}">
                            class="nav_current"
                        </c:if>
                        onmouseover="fnMenuShowHolder('navChnl8',this);" onmouseout="fnMenuHide();" href="<c:url value='/instantRedeem/listPackage.do?lang=${lang}'/>"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_nav_redemption_products"/></a>
            </li>
            <% } %>           
            <% } %>
            <li class="logout"><a href="<c:url value='/logout.do'/>"><str:label strId="AMPARTNERPORTAL" key="nav_logout"/></a></li>
            <li class="seperatorRight">&nbsp;|</li>
            <li class="message">
                <str:label strId="AMPARTNERPORTAL" key="nav_welcome"/> <%=userSession.getAgent().getFamilyName()%> <%=userSession.getAgent().getFirstName()%><br>
                <%=userSession.getAgent().getPartnerName()%>
            </li>
        </ul>
    </div>

    <div id="breadcrumb">
        <ul>
            <li>&nbsp;</li>
        </ul>
    </div>
</div>
