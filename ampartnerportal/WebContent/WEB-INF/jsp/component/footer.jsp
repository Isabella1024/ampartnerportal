<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%-- STR include --%>
<%@ taglib uri="/WEB-INF/str.tld" prefix="str" %>


<div id="footer">
    <div class="links">
        <a href="<%=request.getContextPath()%>/terms.do"><str:label strId="AMPARTNERPORTAL" key="footer_terms"/></a> | <a href="<%=request.getContextPath()%>/privacy.do"><str:label strId="AMPARTNERPORTAL" key="footer_privacy"/></a>
    </div>
    <div class="copyright">
        <a href="<%=request.getContextPath()%>/copyright.do"><str:label strId="AMPARTNERPORTAL" key="footer_copyright"/></a> <str:label strId="AMPARTNERPORTAL" key="footer_copyright_2"/>
    </div>
</div>