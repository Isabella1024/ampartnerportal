<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="org.apache.commons.httpclient.HttpClient" %>
<%@ page import="org.apache.commons.httpclient.methods.GetMethod" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.IOException" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="com.asiamiles.partnerportal.util.WebUtil" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_user_login"/>
</jsp:include>

<body>
    <div id="main_container">
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
                    <li style="text-align:left; padding:0 4px; font-weight:bold; color:#999; line-height:3.0em; width:200px"><a><str:label strId="AMPARTNERPORTAL" key="footer_terms"/></a></li>
                </ul>
            </div>

            <div id="breadcrumb">
                <ul>
                    <li>&nbsp;</li>
                </ul>
            </div>
        </div>
        
        <c:set var="targetURL"><str:label strId="AMPARTNERPORTAL" key="footer_link_terms"/></c:set>
        <jsp:useBean id="targetURL" class="java.lang.String"/>
        <%
            int timeout = 30000;
            String responseBodyAsString = "";
            HttpClient client = new HttpClient();
            GetMethod methodCall = null;
            try {
                //Socket
                client.getHttpConnectionManager().getParams().setSoTimeout(new Integer(timeout).intValue());
                //Connection
                client.getHttpConnectionManager().getParams().setConnectionTimeout(new Integer(timeout).intValue());
                client.getParams().setParameter("http.protocol.content-charset", "UTF-8");

                methodCall = new GetMethod(targetURL);
                client.executeMethod(methodCall);
                //Get the response body.
                byte[] responseBody = methodCall.getResponseBodyAsString().getBytes("UTF8");
                if (responseBody != null) {
                    responseBodyAsString = new String(responseBody, "UTF-8"); // ALL HTML content in AMSite is UTF-8 hardcoded!!!
                    responseBodyAsString = WebUtil.getDivContent("content", responseBodyAsString);
                }
            } catch (Exception e) {

            } finally {
                if (methodCall != null) {
                    // release any connection resources used by the method
                    methodCall.releaseConnection();
                }
            }
        %>
        <%=responseBodyAsString%>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>