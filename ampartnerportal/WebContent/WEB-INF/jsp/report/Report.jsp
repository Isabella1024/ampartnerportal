<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@page import="com.asiamiles.partnerportal.domain.UserSession"%>
<%@page import="com.asiamiles.partnerportal.domain.ReportForm"%>
<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_report"/>
</jsp:include>
<script language="javascript">
	function download() {
        MyForm = document.forms["export"];
        MyForm.submit();
    }
    
    function checkFromDate(objForm) {
        var dayIdx = objForm.fromDay.selectedIndex;
        var monthIdx = objForm.fromMonth.selectedIndex;
        var yearIdx = objForm.fromYear.selectedIndex;

        if (dayIdx > 0 && monthIdx > 0 && yearIdx > 0) {
            var dd = parseInt(objForm.fromDay.options[dayIdx].value);
            var mm = objForm.fromMonth.options[monthIdx].value;
            var yyyy = parseInt(objForm.fromYear.options[yearIdx].value);

            if (checkInvalidDate(dd, mm, yyyy))
                objForm.fromDay.options[0].selected = true;
        }
    }

    function checkToDate(objForm) {
        var dayIdx = objForm.toDay.selectedIndex;
        var monthIdx = objForm.toMonth.selectedIndex;
        var yearIdx = objForm.toYear.selectedIndex;

        if (dayIdx > 0 && monthIdx > 0 && yearIdx > 0) {
            var dd = parseInt(objForm.toDay.options[dayIdx].value);
            var mm = objForm.toMonth.options[monthIdx].value;
            var yyyy = parseInt(objForm.toYear.options[yearIdx].value);

            if (checkInvalidDate(dd, mm, yyyy))
                objForm.toDay.options[0].selected = true;
        }
    }

    function checkInvalidDate(dd, mm, yyyy) {
        var falseDay = false;
        // check the months with 30 days
        if (mm == "4" || mm == "6" || mm == "9" || mm == "11") {
            if (dd == 31)
                falseDay = true;
        }

        // check February and leap years
        if (mm == "2") {
            if (dd > 29) falseDay = true;
            if (dd == 29 && !((yyyy % 4 == 0 && yyyy % 100 != 0) || yyyy % 400 == 0))
                falseDay = true;
        }
        return falseDay;
    }

    function submitForm(claimToVoid) {
        var MyForm = document.forms["voidForm"];
        var hiddenField = document.createElement("input");
 
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "claimToVoid");
        hiddenField.setAttribute("value", claimToVoid);
        MyForm.appendChild(hiddenField);
              
        MyForm.submit();
    }
    
    function switchLang(lang) {
        var MyForm = document.forms["search"];        
        var hiddenField = document.createElement("input");
		var hiddenField2 = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "lang");
        hiddenField.setAttribute("value", lang);
        MyForm.appendChild(hiddenField);  
        hiddenField2.setAttribute("type", "hidden");
        hiddenField2.setAttribute("name", "_target0");
        MyForm.appendChild(hiddenField2);
        MyForm.submit();
    }
</script>
<%	UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
	ReportForm reportForm = (ReportForm) request.getAttribute("reportForm");
	userSession.setFromDay(reportForm.getFromDay());
	userSession.setFromMonth(reportForm.getFromMonth());
	userSession.setFromYear(reportForm.getFromYear());
	userSession.setToDay(reportForm.getToDay());
	userSession.setToMonth(reportForm.getToMonth());
	userSession.setToYear(reportForm.getToYear());
	//Steven DEL FOR AML.34188 20140730 START
	//userSession.setBillOption(reportForm.isBillOption());
	//userSession.setProgressOption(reportForm.isProgressOption());
	//userSession.setCompleteOption(reportForm.isCompleteOption());
	//Steven DEL FOR AML.34188 20140730 END
	userSession.setKeyword(reportForm.getKeyword());
    //Steven ADD FOR AML.34188 20140730 START
    userSession.setChoose(reportForm.getChoose());
    userSession.setNextClaimNum(reportForm.getNextClaimNum());
    userSession.setNextPackageCode(reportForm.getNextPackageCode());
    userSession.setPreClaimNum(reportForm.getPreClaimNum());
    userSession.setPrePackageCode(reportForm.getPrePackageCode());
    userSession.setCurrentPage(reportForm.getCurrentPage());
    //Steven ADD FOR AML.34188 20140730 END
%>  
<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="report"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <a href="javascript:download()" class="downloadIcon">
	                    <img alt="Download" src="images/btn_download.gif" height="26px" width="26px"/>
	                    <str:label strId="AMPARTNERPORTAL" key="label_download"/>
                    </a>                
                    <a href="javascript:window.print()" class="printIcon">
                        <img src="images/btn_print.gif" alt="Print"/>
                        <str:label strId="AMPARTNERPORTAL" key="label_print"/>
                    </a>
                    <strong><str:label strId="AMPARTNERPORTAL" key="info_report_search"/></strong><br>
                    <!-- Support for Spring errors holder -->
                    <spring:bind path="reportForm.*">
                        <c:forEach var="error" items="${status.errors.globalErrors}">
                            <span class="error">
                            	<spring:message message="${error}" arguments="${error.arguments}" htmlEscape="true"/>
                            </span><br/>
                        </c:forEach>
                        <c:forEach var="error" items="${status.errors.fieldErrors}">
                            <span class="error">
                            	<spring:message message="${error}" arguments="${error.arguments}" htmlEscape="true"/>
                            </span><br/>
                        </c:forEach>
                        <br>
                    </spring:bind>
					<form:form id="export" name="export" commandName="reportForm" target="_blank">
						<input type="hidden" name="_target1" value="">
					</form:form>
					<br>
                    <c:if test="${!empty reportForm.voidedClaim}">
                    	<div class="box_acknowledgement">
                            <fmt:message key="info_report_claim_void">
                    		    <fmt:param value="${reportForm.voidedClaim}"/>
                    	    </fmt:message>
                        </div><br><br>
                    </c:if>
                    <form:form id="search" name="search" commandName="reportForm">
                        <table width="100%">
                            <tr>
                                <td width="20%"><form:label path="" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_from"/></form:label></td>
                                <td width="20%"><form:label path="toDay" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_to"/></form:label></td>
                                <td width="30%"><strong><str:label strId="AMPARTNERPORTAL" key="label_status"/></strong></td>
                                <td width="30%"><form:label path="keyword" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_agent_id_filter"/></form:label></td>
                            </tr>
                            <tr>
                                <td>
                                    <form:select path="fromDay" onchange="checkFromDate(this.form);" id="startDay">
                                    	<form:option value="">(dd)</form:option>
                                    	<form:options items="${days}"/>
                                    </form:select>
                                    <form:select path="fromMonth" onchange="checkFromDate(this.form);" id="startMonth">
                                    	<form:option value="">(mmm)</form:option>
                                    	<form:options items="${months}"/>
                                    </form:select>
                                    <form:select path="fromYear" onchange="checkFromDate(this.form);" id="startYear">
                                    	<form:option value="">(yyyy)</form:option>
                                    	<form:options items="${years}"/>
                                    </form:select>
                                </td>
                                <td>
                                     <form:select path="toDay" onchange="checkToDate(this.form);" id="endDay">
                                    	<form:option value="">(dd)</form:option>
                                    	<form:options items="${days}"/>
                                    </form:select>
                                    <form:select path="toMonth" onchange="checkToDate(this.form);" id="endMonth">
                                    	<form:option value="">(mmm)</form:option>
                                    	<form:options items="${months}"/>
                                    </form:select>
                                    <form:select path="toYear" onchange="checkToDate(this.form);" id="endYear">
                                    	<form:option value="">(yyyy)</form:option>
                                    	<form:options items="${years}"/>
                                    </form:select>
                                </td>
                                <td>              
									<%-- Steven UPDATE FOR AML.34188 20140730 START --%>
									<%-- 
									<form:checkbox path="progressOption"/> <str:label strId="AMPARTNERPORTAL" key="label_in_progress"/>
                                    <form:checkbox path="completeOption"/> <str:label strId="AMPARTNERPORTAL" key="label_completed"/>
									<form:checkbox path="billOption"/> <str:label strId="AMPARTNERPORTAL" key="label_billed"/>
									--%>
									
									<%--<form:radiobutton path="choose" value="progressOption"/> <str:label strId="AMPARTNERPORTAL" key="label_in_progress"/>
                                    <form:radiobutton path="choose" value="completeOption"/> <str:label strId="AMPARTNERPORTAL" key="label_completed"/>
									<form:radiobutton path="choose" value="billOption"/> <str:label strId="AMPARTNERPORTAL" key="label_billed"/>
									<form:radiobutton path="choose" value="ALL"/> <str:label strId="AMPARTNERPORTAL" key="label_all"/>--%>
									
									<form:radiobutton path="choose" value="O"/><str:label strId="AMPARTNERPORTAL" key="label_in_progress"/>
                                    <form:radiobutton path="choose" value="U"/><str:label strId="AMPARTNERPORTAL" key="label_completed"/>
									<form:radiobutton path="choose" value="B"/><str:label strId="AMPARTNERPORTAL" key="label_billed"/>
									<form:radiobutton path="choose" value="A"/><str:label strId="AMPARTNERPORTAL" key="label_all"/>
								
								    <input name="prePageCode" type="hidden" value="${reportForm.prePackageCode}">
			                        <input name="nextPageCode" type="hidden" value="${reportForm.nextPackageCode}">
				        			<%-- Steven UPDATE FOR AML.34188 20140730 END --%>
                                </td>
                                <td>
                                	<form:input path="keyword" maxlength="15"/>
                                	<%-- Steven UPDATE FOR AML.34188 20140730 START --%>
									<%-- <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_search"/>" name="_target0" class="btnGeneric"> --%>
                                    <input type="submit" id="search" value="<str:label strId="AMPARTNERPORTAL" key="btn_search"/>" name="_target0" class="btnGeneric">
                                    <%-- Steven UPDATE FOR AML.34188 20140730 END --%>
                                </td>
                            </tr>
                        </table>
                    </form:form>
                    <br>
                    <br>        
                	<c:set var="key1">label_partner_remark_1_<%=userSession.getAgent().getPartnerCode()%></c:set>
                    <c:set var="key2">label_partner_remark_2_<%=userSession.getAgent().getPartnerCode()%></c:set>
                    <c:set var="rem_1"><str:label strId="AMPARTNERPORTAL" key="${key1}"/></c:set>
                    <c:set var="rem_2"><str:label strId="AMPARTNERPORTAL" key="${key2}"/></c:set>                         
                    <form id="voidForm" name="voidForm" action="<c:url value="/report.do"/>" method="post">
                        <input type="hidden" name="_target1" value="">
                        <table class="sortable" id="claimtable" width="100%">
                            <thead>
                            <tr>
                                <th width="8%"><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                                <th width="17%"><str:label strId="AMPARTNERPORTAL" key="label_redemption_item_package_code"/></th>
                                <th width="8%"><str:label strId="AMPARTNERPORTAL" key="label_status"/></th>
                                <th width="16%"><str:label strId="AMPARTNERPORTAL" key="label_date"/> (GMT <c:out value="${timeZone}"/>)</th>
                                <th width="5%"><str:label strId="AMPARTNERPORTAL" key="label_agent_id"/></th>
                                <th width="17%"><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                                <th width="17%"><str:label strId="AMPARTNERPORTAL" key="label_receipt_ref"/></th>
                                <c:choose>
		                            <c:when test="${key1 ne rem_1}">
		                            <th width="6%">${rem_1}</th>
		                            </c:when>
		                            <c:otherwise>
		                            <th width="6%"><str:label strId="AMPARTNERPORTAL" key="label_partner_remark_1"/></th>
		                            </c:otherwise>
	                            </c:choose>
	                            
                                <c:choose>
	                                <c:when test="${key2 ne rem_2}">
	                                <th width="6%">${rem_2}</th>
	                                </c:when>
	                                <c:otherwise>
	                                <th width="6%"><str:label strId="AMPARTNERPORTAL" key="label_partner_remark_2"/></th>
	                                </c:otherwise>
                                </c:choose>
                            </tr>
                            </thead>
                            <tbody>
	                        <c:set var="currentLocale" value="${pageContext.response.locale}"/>
	                        <fmt:setLocale value="en" scope="session"/>                            
                            <c:forEach items="${reportForm.claims}" var="claim" varStatus="claimCount">
	                            <c:url value="/voidClaim.do" var="voidClaimURL">
                            		<c:param name="claimNo" value="${claim.claimNo}"/>
                            	</c:url>
                                <tr>
                                    <td>
                                        <c:out value="${claim.claimNo}"/><br>
                                        <c:if test="${!empty claim.completionTime && empty claim.billedDate}">
                                        <a href="<c:out value='${voidClaimURL}'/>">Void</a></c:if>
                                    </td>
                                    <td><c:out value="${claim.packageDescription}"/> (<c:out value="${claim.packageCode}"/>)</td>
                                    <c:choose>
                                    	<%-- YALIN UPDATE FOR AML.34188 20140827 START --%>
                                    	<%-- <c:when test="${empty claim.completionTime}"> --%>
                                    	<%-- <td><str:label strId="AMPARTNERPORTAL" key="label_in_progress"/></td> --%>
                                        <c:when test="${!empty claim.billedDate}">
                                        <td><str:label strId="AMPARTNERPORTAL" key="label_billed"/></td>
                                        <%-- YALIN UPDATE FOR AML.34188 20140827 END --%>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                            	<%-- YALIN UPDATE FOR AML.34188 20140827 START --%>
                                            	<%--<c:when test="${empty claim.billedDate}"> --%>
                                                <c:when test="${!empty claim.completionTime}">
                                                <%-- YALIN UPDATE FOR AML.34188 20140827 END --%>
                                                <td><str:label strId="AMPARTNERPORTAL" key="label_completed"/></td>
                                                </c:when>
                                                <c:otherwise>
                                                <%-- YALIN UPDATE FOR AML.34188 20140827 START --%>
                                                <%-- <td><str:label strId="AMPARTNERPORTAL" key="label_billed"/></td> --%>
                                                <td><str:label strId="AMPARTNERPORTAL" key="label_in_progress"/></td>
                                                <%-- YALIN UPDATE FOR AML.34188 20140827 END --%>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                    <c:choose>
                                        <c:when test="${!empty claim.billedDate}">
                                        <td><fmt:formatDate value="${claim.billedDate}" pattern="dd MMM yyyy"/></td>
                                        </c:when>
                                        <c:otherwise>
                                            <c:choose>
                                                <c:when test="${!empty claim.completionTime}">
                                                <td><fmt:formatDate value="${claim.completionTime}" pattern="dd MMM yyyy HH:mm"/></td>
                                                </c:when>
                                                <c:otherwise>
                                                <td><fmt:formatDate value="${claim.collectionTime}" pattern="dd MMM yyyy HH:mm"/></td>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                    
                                    <c:choose>
	                                    <c:when test="${!empty claim.completionHandledBy}">
	                                    <td><c:out value="${claim.completionHandledBy}"/></td>
	                                    </c:when>
	                                    <c:otherwise>
	                                    <td><c:out value="${claim.collectionHandledBy}"/></td>
	                                    </c:otherwise>
                                    </c:choose>
                                    <td><c:out value="${claim.holderName}"/> <c:out value="${claim.holderFirstName}"/></td>
                                    <td><c:out value="${claim.receiptNo}"/></td>
                                    <td><c:out value="${claim.remarks}"/></td>
                                    <td><c:out value="${claim.remarks2}"/></td>
                                </tr>
                            </c:forEach>
                            <fmt:setLocale value="${currentLocale}" scope="session"/>
                            </tbody>
                        </table>
                    </form>                
                    <%-- Steven ADD FOR AML.34188 20140730 START --%>
                    <input name="prePageCode" type="hidden" value="${reportForm.prePackageCode}">
			        <input name="nextPageCode" type="hidden" value="${reportForm.nextPackageCode}">
				    <div style="float:right">
				        <c:if test="${!empty reportForm.prePackageCode}">
				         	<a href="<c:url value="/report.do?prePageCode=${reportForm.prePackageCode}"/>" style="font-size: 12px"><str:label strId="AMPARTNERPORTAL" key="label_pre"/></a>&nbsp;
				        </c:if>
	                    <c:if test="${!empty reportForm.nextPackageCode}">
	                    	<a href="<c:url value="/report.do?nextPageCode=${reportForm.nextPackageCode}"/>" style="font-size: 12px"><str:label strId="AMPARTNERPORTAL" key="label_next"/></a>
	                    </c:if>
                     </div>
                     <br/>
                     <br/>
                     <div style="float:right">
                        <c:if test="${fn:length(reportForm.claims)>0}">
                        	<str:label strId="AMPARTNERPORTAL" key="label_page"/>&nbsp;<c:out value="${reportForm.currentPage}"/>&nbsp;<str:label strId="AMPARTNERPORTAL" key="label_of"/>&nbsp;<c:out value="${reportForm.totalPage}"/>
                        </c:if>
                     </div>
                     <br/>
                     <br/>
                     <div style="float:right">
                        	<str:label strId="AMPARTNERPORTAL" key="label_total"/>&nbsp;<c:out value="${reportForm.totalNum}"/>
                     </div>
   					 <%-- Steven ADD FOR AML.34188 20140730 END --%>
                	<c:if test="${empty reportForm.claims}"> 
                	<br><br>
                	<str:label strId="AMPARTNERPORTAL" key="info_no_claim"/>
                	</c:if>
                </div>
            </div>
        </div>
<% userSession.setVoidedClaim(""); %>          
        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
