<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@page import="com.asiamiles.partnerportal.domain.UserSession"%>
<%@page import="com.asiamiles.partnerportal.domain.BillingForm"%>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_billing_detail"/>
</jsp:include>

<!-- added by Steven 20140730 for pagenation -->
<script language="javascript">
    function submitForm() {
        var MyForm = document.forms["export"];
        MyForm.submit();
    }

    function nextButton(){
		var next="${billingForm.nextPageCode}";
		var length=${fn:length(billingForm.claims)};
		var str="";
		for(var i=0;i<length;i++){
			var check=document.getElementById(i.toString());
			if(check.checked){
				str=str+"1";
				}else{
					str=str+"0";
					}
		}
		
		url="billing.do?nextPageCode="+next+"&post="+str;
		
		location.href=url;
	}

    function preButton(){
		var next="${billingForm.prePageCode}";
		var length=${fn:length(billingForm.claims)};
		var str="";
		for(var i=0;i<length;i++){
			var check=document.getElementById(i.toString());
			if(check.checked){
				str=str+"1";
				}else{
					str=str+"0";
					}
		}
		
		url="billing.do?prePageCode="+next+"&post="+str;
		
		location.href=url;
	}
    
    function pagination(redirectPage){
		var length=${fn:length(billingForm.claims)};
		var str="";
		for(var i=0;i<length;i++){
			var check=document.getElementById(i.toString());
			if(check.checked){
				str=str+"1";
			}else{
				str=str+"0";
			}
		}
		document.getElementById("post").value = str;
		if("pre"==redirectPage){
			document.getElementById("prePageCode").value = "${billingForm.prePageCode}";
		} else if("next"==redirectPage){
			document.getElementById("nextPageCode").value = "${billingForm.nextPageCode}";
		} else {
			return;
		}
		document.forms["pagination"].submit();
	}
</script>
<!-- updated by Steven 20140730 for pagenation -->
<%	UserSession userSession = (UserSession) request.getSession().getAttribute("userSession");
	BillingForm reportForm = (BillingForm) request.getAttribute("billingForm");
	userSession.setAgent(reportForm.getAgent());
	userSession.setUnbillList(reportForm.getUnbillList());
	userSession.setClaims(reportForm.getClaims());
	userSession.setNextClaimNum(reportForm.getNextClaimNum());
	userSession.setNextPackageCode(reportForm.getNextPageCode());
	userSession.setPreClaimNum(reportForm.getPreClaimNum());
	userSession.setPrePackageCode(reportForm.getPrePageCode());
	//userSession.setPageCode(reportForm.getPageCode());
	//userSession.setClaimNum(reportForm.getClaimNum());
	userSession.setEstablishmentCode(reportForm.getEstablishmentCode());
	userSession.setCurrentPage(reportForm.getIndex());
%> 
<body>
    <div id="main_container">
		<c:choose>
        <c:when test="${fn:length(billingForm.unbilledEstablishments) > 1}">
	        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
	        <jsp:param name="nav" value="billing"/>
	        <jsp:param name="hideLangSwitch" value="true"/>
	    	</jsp:include>
        </c:when>
        <c:otherwise>
		    <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
		    <jsp:param name="nav" value="billing"/>
		    </jsp:include>            
        </c:otherwise>
        </c:choose>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <a href="javascript:submitForm()" class="downloadIcon">
                    	<img alt="Download" src="images/btn_download.gif" height="26px" width="26px"/>
                    	<str:label strId="AMPARTNERPORTAL" key="label_download"/>
                    </a>                
                    <a href="javascript:window.print()" class="printIcon">
                        <img src="images/btn_print.gif" alt="Print"/>
                        <str:label strId="AMPARTNERPORTAL" key="label_print"/>
                    </a>
                    <strong><c:out value="${billingForm.selectedUnbilledEstablishment.establishmentName}"/></strong>
                    <br>
                    <c:set var="currentLocale" value="${pageContext.response.locale}"/>
                    <strong>
                    	<fmt:message key="info_bill_for_completed_claims">
	                		<fmt:param>
		                		<fmt:setLocale value="en" scope="session"/> 
		                        <fmt:formatDate value="${billingForm.selectedUnbilledEstablishment.oldestCollectionTimestamp}" pattern="dd MMM yyyy"/> 
		                        <fmt:setLocale value="${currentLocale}" scope="session"/>
	                        </fmt:param>
	                		<fmt:param>
		                		<fmt:setLocale value="en" scope="session"/> 
		                        <fmt:formatDate value="${billingForm.cutOffDate}" pattern="dd MMM yyyy"/> 
		                        <fmt:setLocale value="${currentLocale}" scope="session"/>
	                        </fmt:param>
                	    </fmt:message>
                    </strong><br><br>

					<form:form id="export" name="export" commandName="billingForm" target="_blank">
					<input type="hidden" name="_target4" value="">
					</form:form>
					<%--CPPJULI ADD FOR AML.34188 20140926 START --%>
					<form:form id="pagination" name="pagination" commandName="billingForm">
						<input type="hidden" name="_target1" value="">
						<input type="hidden" name="nextPageCode" id="nextPageCode" value="">
						<input type="hidden" name="prePageCode" id="prePageCode" value="">
						<input type="hidden" name="post" id="post" value="">
					</form:form>
					<%--CPPJULI ADD FOR AML.34188 20140926 START --%>
					
                     <!-- Support for Spring errors holder -->
                    <spring:bind path="billingForm.*">
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
                    
                    <form:form commandName="billingForm">
                        <table class="sortable" id="billTable" width="100%">
                            <thead>
                            <tr>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_collection_date"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_receipt_ref"/></th>
                                <th class="unsortable"><str:label strId="AMPARTNERPORTAL" key="label_select"/></th>
                            </tr>
                            </thead>
                            <tbody>
	                        <fmt:setLocale value="en" scope="session"/>                               
                            <c:forEach items="${billingForm.claims}" var="claim" varStatus="claimCount">
                                <tr>
                                    <td><c:out value="${claim.claimNo}"/></td>
                                    <td><c:out value="${claim.packageDescription}"/></td>
                                    <td><fmt:formatDate value="${claim.collectionTime}" pattern="dd MMM yyyy"/></td>
                                    <td><c:out value="${claim.memberID}"/></td>
                                    <td><c:out value="${claim.holderName}"/> <c:out value="${claim.holderFirstName}"/></td>
                                    <td><c:out value="${claim.receiptNo}"/></td>
	                                <td class="center">
	                                    <form:checkbox path="claims[${claimCount.index}].toBeBilled" id="${claimCount.index}"/>
	                                </td>                                   
                                </tr>
                            </c:forEach>
                            <fmt:setLocale value="${currentLocale}" scope="session"/>
                            </tbody>
                        </table>
                         <!-- added by Steven 20140730 for pagenation -->
				        <input name="prePageCode" type="hidden" value="${billingForm.prePageCode}">
			            <input name="nextPageCode" type="hidden" value="${billingForm.nextPageCode}">
				        <div style="float:right">
				        
				        <c:if test="${!empty billingForm.prePageCode}">
				        <a href="javascript:pagination('pre')"><str:label strId="AMPARTNERPORTAL" key="label_pre"/></a>&nbsp;				     
				        </c:if>
	                    <c:if test="${!empty billingForm.nextPageCode}">
	                    <a href="javascript:pagination('next')"><str:label strId="AMPARTNERPORTAL" key="label_next"/></a>	                  
	                    </c:if>	                   
                        </div>
                        <br/>
                        <br/>
                        <div style="float:right">
                        <c:if test="${fn:length(billingForm.claims) > 1}">
                        <str:label strId="AMPARTNERPORTAL" key="label_page"/>&nbsp;<c:out value="${billingForm.index}"/>&nbsp;<str:label strId="AMPARTNERPORTAL" key="label_of"/>&nbsp;<c:out value="${billingForm.totalPage}"/>
                        </c:if>                                              
                        </div>
                        <br/>
                        <br/>
                        <div style="float:right">
                        <str:label strId="AMPARTNERPORTAL" key="label_total"/>&nbsp;<c:out value="${billingForm.totalNum}"/>
                        </div>
                        
                        <br>
                        <c:choose>
                        	<c:when test="${fn:length(billingForm.claims) == 0}">
                        		<str:label strId="AMPARTNERPORTAL" key="info_no_completed_claim"/>
                        		<br><br><br>	                        	
	                        	<strong><str:label strId="AMPARTNERPORTAL" key="info_total_completed_claims"/> 0</strong><br>
                        	</c:when>
                        	<c:otherwise>
		                        <strong><str:label strId="AMPARTNERPORTAL" key="info_total_completed_claims"/> <c:out value="${billingForm.totalNum}"/></strong><br><br>
		
		                        <strong><form:label path="invoiceNo" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="info_invoice_no_must"/></form:label></strong>
		                        <form:input path="invoiceNo" maxlength="10"/>
		                        <br>
						    </c:otherwise>
	                    </c:choose>
                        <br>	              
                        <c:if test="${fn:length(billingForm.unbilledEstablishments) > 1}">
	                        <span class="bulletLink"><a href="<c:url value="/billing.do"/>?_target0"><str:label strId="AMPARTNERPORTAL" key="info_back_to_previous"/></a></span>
	                    </c:if>
		                        
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_bill"/>" name="_target2" class="btnGenericRight" onClick=""/>
                 </form:form>
                </div>
            </div>
        </div>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>