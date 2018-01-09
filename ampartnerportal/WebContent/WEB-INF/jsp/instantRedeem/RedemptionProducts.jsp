<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="instant_redeem_group_admin_title"/>
</jsp:include>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="listPackage"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <strong><str:label strId="AMPARTNERPORTAL" key="instant_redeem_title_list_redemption_products"/></strong><br><br>
                    <table class="sortable" id="redemptionProductsTable" width="100%">
                        <thead>
                            <tr>
                                <th><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_redemption_package_code"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_description"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_miles_required"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_qr_code"/></th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${packageList}" var="package" varStatus="index">
                            	<c:url value="/instantRedeem/generateQrCode.do" var="generateQrCodeUrl">
                            		<c:param name="packageCode" value="${package.itemSku}"/>
                            	</c:url>
                           		<tr>
                                <td><c:out value="${package.itemSku}"/></td>
                                <td><c:out value="${package.itemName}"/></td>
                                <td><c:out value="${package.itemPoints}"/></td>
                                <td>
                                <a href="javascript:void(0)" onclick="generateQrCode('${package.itemSku}')"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_generate_qr_code"/></a>
                                </td>
                            </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
        
        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
        <jsp:include page="/WEB-INF/jsp/component/instantRedeem/js/js_include_common.jsp"/>
    </div>
</body>
</html>
<script type="text/javascript">

function generateQrCode(packageCode){
    var lang = '<%= lang %>';
    window.open('<%=request.getContextPath()%>/instantRedeem/generateQrCode.do?packageCode='+packageCode+'&lang='+lang,'qrCodeImage','height=400, width=400, top=200, left=200, toolbar=yes, menubar=yes, scrollbars=yes, resizable=yes,location=yes, status=yes');
}

</script>

