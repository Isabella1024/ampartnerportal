<%@ page import="com.asiamiles.partnerportal.SystemException" %>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_error"/>
</jsp:include>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/header.jsp">
            <jsp:param name="headerMenu" value="nav_error"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <span class="error"><strong><str:label strId="AMPARTNERPORTAL" key="info_error"/></strong><br/>
                    <c:choose>
	                    <c:when test="${!empty message}">
	                        <strong><c:out value="${message}"/></strong>
	                    </c:when>
	                    <c:otherwise><strong>(error_unclassified)</strong></c:otherwise>
                    </c:choose>
					</span>
                    <%
                        Exception ex = (Exception)pageContext.findAttribute(SystemException.SESSION_ATTRIBUTE_NAME);
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        Date now = new Date();
                        if (ex != null) {
                            out.println("<!--");
                            out.println("Time: " + format.format(now));
                            out.println("Message: " + ex.getMessage());
                            ex.printStackTrace(new PrintWriter(out));
                            out.println("-->");
                        }
                     %>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
