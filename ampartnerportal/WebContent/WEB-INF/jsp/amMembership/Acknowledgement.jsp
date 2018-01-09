<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_verify_membership_acknowledgement"/>
</jsp:include>


<body>
<div id="main_container">
    <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
        <jsp:param name="nav" value="membership"/>
        <jsp:param name="hideLangSwitch" value="true"/>
    </jsp:include>

    <div id="content">
        <div id="mod_form" class="mod">
            <div class="bd">
            	Success
                <br>
                <table width="100%" height="67%"></table>
                <!-- 
                <span class="bulletLink"><a href="<c:url value="/membership.do"/>"><str:label strId="AMPARTNERPORTAL" key="info_other_outstanding_membership"/></a></span>
            	 -->
            </div>
        </div>
	 </div>
     <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
