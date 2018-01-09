<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_verify_redemption_enter_claims"/>
</jsp:include>
<body>

    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="redemption"/>
        </jsp:include>
        
        <div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <jsp:include page="/WEB-INF/jsp/redemption/progressBar.jsp">
                        <jsp:param name="selected" value="enterClaims"/>
                    </jsp:include>
                    
					<!-- Support for Spring errors holder -->
                    <spring:bind path="redemptionForm.*">
                        <c:forEach var="error" items="${status.errors.globalErrors}">
                            <span class="error">
                            	<spring:message message="${error}" arguments="${error.arguments}" htmlEscape="true"/>
                            </span><br/>
                        </c:forEach>
                        <c:forEach var="error" items="${status.errors.fieldErrors}">
                            <c:if test="${error.field eq 'memberId'}">
                            <span class="error"><spring:message message="${error}" arguments="${error.arguments}" htmlEscape="true"/></span><br/>
                            </c:if>
                            <c:if test="${error.field eq 'memberEmbossedName'}">
                            <span class="error"><spring:message message="${error}" arguments="${error.arguments}" htmlEscape="true"/></span><br/>
                            </c:if>
                        </c:forEach>
                        <br>
                    </spring:bind>
					                  
                    <form:form commandName="redemptionForm" id="redemptionForm">
                        <table>
                            <tr>
                                <td>
                                <!-- check whether the error is a CLS return error indicates invalid member ID-->
			                    <spring:bind path="redemptionForm.*">
                                <c:forEach var="error" items="${status.errors.globalErrors}">
                            	<c:if test="${fn:contains(error,'error_cls_paperlessRetrieval_04')}">
                            	<span class="error"><str:label strId="AMPARTNERPORTAL" key="info_membership_no"/></span>
                            	<c:set var="invalidMemberId" value="true"/>
                            	</c:if>
		                        </c:forEach>
		                        </spring:bind>
		                        <c:if test="${invalidMemberId ne 'true'}">
		                        <form:label path="memberId" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="info_membership_no"/></form:label>
                            	</c:if>
		                        &nbsp;
		                        </td>
                                <td>
                                    <form:input path="memberId" size="30" maxlength="10"/>
                                </td>
                            </tr>
                            <%-- CPPYOW DEL for iRedeem 20120314
                            <tr>
                                <td>
								<!-- check whether the error is a CLS return error indicates invalid member embossed name-->
			                    <spring:bind path="redemptionForm.*">
                                <c:forEach var="error" items="${status.errors.globalErrors}">
                            	<c:if test="${fn:contains(error,'error_cls_paperlessRetrieval_05')}">
                            	<span class="error"><str:label strId="AMPARTNERPORTAL" key="info_name_on_card"/></span>
                            	<c:set var="invalidMemberName" value="true"/>
                            	</c:if>
		                        </c:forEach>
		                        </spring:bind>
		                        <c:if test="${invalidMemberName ne 'true'}">
		                        <form:label path="memberEmbossedName" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="info_name_on_card"/></form:label>
                            	</c:if>
		                        &nbsp;                                
                                </td>
                                <td>
                                    <form:input path="memberEmbossedName" size="30" maxlength="26"/>
                                </td>
                            </tr>
                            --%>
                        </table>
                        <table class="tbl_content" width="100%">
                            <thead>
                            <tr>
                                <th width="40%"><str:label strId="AMPARTNERPORTAL" key="label_claim_no_must"/></th>
                                <th width="60%"><str:label strId="AMPARTNERPORTAL" key="label_security_code_must"/></th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${redemptionForm.claims}" var="claim" varStatus="claimCount">
                                <tr <c:if test="${claimCount.count%2!=1}">class="alt"</c:if>>
                                    <td>
                                        <form:errors path="claims[${claimCount.index}].claimNumber" cssClass="errorBlock"/>
                                        <form:input path="claims[${claimCount.index}].claimNumber" size="30" maxlength="9" cssErrorClass=""/>
                                    </td>
                                    <td>
                                        <form:errors path="claims[${claimCount.index}].securityCode" cssClass="errorBlock"/>
										<spring:bind path="claims[${claimCount.index}].securityCode">
											<input type="password" name="<c:out value="${status.expression}"/>" value="<c:out value="${status.value}"/>" size="40" maxlength="12"/>
										</spring:bind>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>

						<input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_submit"/>" name="_target1" class="btnGenericRight"/>
                    </form:form>
                </div>
            </div>
        </div>
        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>
</html>
