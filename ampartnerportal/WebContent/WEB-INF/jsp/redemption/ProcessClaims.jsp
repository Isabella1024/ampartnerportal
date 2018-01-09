<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_verify_redemption_process_claims"/>
</jsp:include>

<script language="javascript">
    function submitForm() {
        var MyForm = document.forms["refresh"];
        MyForm.submit();
    }      
</script>

<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="redemption"/>
            <jsp:param name="hideLangSwitch" value="true"/>
        </jsp:include>

        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <jsp:include page="/WEB-INF/jsp/redemption/progressBar.jsp">
                        <jsp:param name="selected" value="processClaims"/>
                    </jsp:include>
 
                    <strong><str:label strId="AMPARTNERPORTAL" key="instruction_process_collection"/></strong><br><br>

 					<!-- Support for Spring errors holder -->
                    <spring:bind path="redemptionForm.*">
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

                    <!-- check whether the error is a CLS return error indicates a claim has been collected or completed-->
                    <spring:bind path="redemptionForm.*">
                    <c:forEach var="error" items="${status.errors.globalErrors}">
                	<c:if test="${fn:contains(error,'error_cls_paperlessCollection_10')}">
                	<a href="javascript:submitForm()">Refresh</a><br><br>
                	</c:if>
                	<c:if test="${fn:contains(error,'error_cls_paperlessCollection_11')}">
                	<a href="javascript:submitForm()">Refresh</a><br><br>
                	</c:if>                	
                    </c:forEach>
                    </spring:bind>
                    
                    <form:form commandName="redemptionForm">
                        <table class="sortable" id="claimtable" width="100%">
                            <thead>
                            <tr>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_member_name"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_recipient"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></th>
                                <th><str:label strId="AMPARTNERPORTAL" key="label_status"/></th>
                                <th class="unsortable"><str:label strId="AMPARTNERPORTAL" key="label_select"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${redemptionForm.NARDetails}" var="nar" varStatus="claimCount">
                                <tr>
                                    <td><c:out value="${redemptionForm.memberId}"/></td>
                                    <td><c:out value="${redemptionForm.memberEmbossedName}"/></td>
                                    <td>
                                    <c:choose>
										<c:when test="${nar.claimStatusCode eq 0}">
                                    	<c:out value="${nar.holderFirstName}"/> <c:out value="${nar.holderName}"/>
	                                    </c:when>
	                                    <c:otherwise>
	                                    -
	                                    </c:otherwise>
                                    </c:choose>
                                    </td>
                                    <td><c:out value="${nar.claimNo}"/></td>
                                    <td>
                                    <c:choose>
										<c:when test="${nar.claimStatusCode eq 0}">
                                    	<c:out value="${nar.packageDescription}"/>
	                                    </c:when>
	                                    <c:otherwise>
	                                    -
	                                    </c:otherwise>
                                    </c:choose>
                                    </td>
                                    <td>
										<fmt:formatDate var="consumeEndDate" value="${nar.consumptionEndDate}" pattern="yyyyMMdd" />
										<fmt:formatDate var="expiry_date" value="${expiryDate}" pattern="yyyyMMdd"/>

										<c:choose>
											<c:when test="${nar.claimStatusCode ne 0}"><str:label strId="AMPARTNERPORTAL" key="label_invalid"/></c:when>
	                                        <c:otherwise>
		                                        <c:if test="${empty nar.collectionTime && empty nar.completionTime}">
			                                    	<c:choose>
														<c:when test="${consumeEndDate ge expiry_date}"><span class="open_status"><str:label strId="AMPARTNERPORTAL" key="label_open"/></span></c:when>
														<c:otherwise><str:label strId="AMPARTNERPORTAL" key="label_expired"/></c:otherwise>
													</c:choose>
		                                        </c:if>
		                                        <c:if test="${!empty nar.collectionTime && empty nar.completionTime}"><str:label strId="AMPARTNERPORTAL" key="label_in_progress"/></c:if>
		                                        <c:if test="${!empty nar.collectionTime && !empty nar.completionTime}"><str:label strId="AMPARTNERPORTAL" key="label_completed"/></c:if>
	                                        </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="center">
										<c:choose>
										<c:when test="${nar.claimStatusCode eq 0 && empty nar.collectionTime && empty nar.completionTime && consumeEndDate ge expiry_date}">
											<form:checkbox path="NARDetails[${claimCount.index}].toBeProcessed"/>
										</c:when>
										<c:otherwise>
	                                        <spring:bind path="redemptionForm.NARDetails[${claimCount.index}].toBeProcessed">
                                        	<input type="hidden" name="<c:out value='${status.expression}'/>" value="false"/>
                                            </spring:bind>
                                        	-
										</c:otherwise>
                                        </c:choose>                                    
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <br>
                        <br>
                        <span class="bulletLink"><a href="<c:url value="/redemption.do"/>?_target0"><str:label strId="AMPARTNERPORTAL" key="info_verify_another_redemption"/></a></span>
                        <input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_proceed"/>" name="_target2" class="btnGenericRight"/>
                    </form:form>
                </div>
            </div>
        </div>
        
        <form id="refresh" name="refresh" action="<c:url value="/redemption.do"/>" method="post">
        	<input type="hidden" value="" name="_target1"/>
        </form>

        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
