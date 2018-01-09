<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@ include file="/WEB-INF/jsp/component/instantRedeem/js/js_include_common.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/head.jsp">
    <jsp:param name="title" value="title_verify_membership"/>
</jsp:include>



<body>
    <div id="main_container">
        <jsp:include page="/WEB-INF/jsp/component/headerNav.jsp">
            <jsp:param name="nav" value="membership"/>
        </jsp:include>
		<div id="content">
            <div id="mod_form" class="mod">
                <div class="bd">
                    <table class="progress_bar" width="100%">
                    	<tr>
            				<td width="1px" class="selected"></td>
            				<td >
                				<strong><str:label strId="AMPARTNERPORTAL" key="info_membership_detail"/></strong>
            				</td>
            			</tr>
                    </table>
                    <div style="padding-left: 18px">
                    <strong><str:label strId="AMPARTNERPORTAL" key="info_input_help"/></strong><br>
                    </div>
                   
                    
                   
                   
                    <form:form commandName="membership" cssStyle="padding:18px">
                    	<table width="100%"></table>
                    	
                    	<table width="100%" >
                    		<c:choose>
                    			<c:when test="${!empty clsexception}">
                    				<td><span class="error"><str:label strId="AMPARTNERPORTAL" key="label_membership_memberid_must"/></span></td>
                    			</c:when>
                   		 		<c:otherwise>
                    				<td>
                    					<form:label path="memberId" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_membership_memberid_must"/></form:label>
                    				</td>
                    			</c:otherwise>
                    		</c:choose>
                    			<td width="30%" ><form:input path="memberId" size="30" maxlength="10" id="member_id"/></td>
                    			<td width="20%"></td><td width="30%"></td>
                    		</tr>
                    		<tr height="50px">
                    		    <td width="20%" class="label"><form:label path="lastName" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_membership_lastname_must"/></form:label></td>
                    			<td width="30%"><form:input path="lastName" size="30" id="last_name"/></td>
                    			<td width="20%" class="label"><form:label path="firstName" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_membership_firstname_must"/></form:label></td>
                    			<td width="30%"><form:input path="firstName" size="30" id="first_name"/></td>
                    		</tr>
                    		<tr height="50px"><td width="20%" class="label"><form:label path="checkingMethod" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_membership_checkingmethod"/></form:label></td>
                    			<td width="30%">
                    				<form:select path="checkingMethod" id="checkingMethod" onchange="disablediv()" >
                    					<form:option value="F" id="F" ><str:label strId="AMPARTNERPORTAL" key="label_membership_fullnameoprion"/></form:option>
                    					<form:option value="S" id="S" ><str:label strId="AMPARTNERPORTAL" key="label_membership_specificnameoprion"/></form:option>
                    				</form:select>
                    			</td>
                    			<td width="20%"></td><td width="30%"></td>
                    		</tr>
                    		<tr height="60px"><td width="20%" height="20%" ></td>
                    			<td width="30%" class="label" >
                    			<div id="div">
                    				<form:label path="lastNameChar" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_membership_namechar_must"/><form:input path="lastNameChar" cssStyle="width:15px" maxlength="2" id="lastName_char"/><str:label strId="AMPARTNERPORTAL" key="label_membership_lastnamechar_must"/></form:label><br>
                    				<form:label path="firstNameChar" cssClass="label" cssErrorClass="error"><str:label strId="AMPARTNERPORTAL" key="label_membership_namechar_must"/><form:input  path="firstNameChar" cssStyle="width:15px" maxlength="2" id="firstName_char"/><str:label strId="AMPARTNERPORTAL" key="label_membership_firstnamechar_must"/></form:label><br>
                    				<form:label path="isSwapName" cssClass="label" cssErrorClass="error"><form:checkbox path="isSwapName" value="NeedSwapName" id="swap_checkbox"/><str:label strId="AMPARTNERPORTAL" key="label_membership_isswapsame"/></form:label><br>
                    			</div>
                    			</td>
                    			<td width="20%"></td><td width="30%"></td>
                    		</tr>
                    	</table>
                    	<div style="padding-bottom: 15px">
                    	<input type="submit" value="<str:label strId="AMPARTNERPORTAL" key="btn_submit"/>" class="btnGenericRight" style="margin-left: 5px;"/>
                    	<input type="button" value="<str:label strId="AMPARTNERPORTAL" key="btn_reset"/>" class="btnGenericRight" onclick="resetValue()"/>
                    	</div>
                    </form:form>
             
                    <div id="resultTable" >
                    <table class="progress_bar" width="100%" >
                    <tr>
            				<td width="1px" class="selected"></td>
            				<td >
                    <strong><str:label strId="AMPARTNERPORTAL" key="result"/></strong>
                            </td>
            		</tr>
            		</table>
            		</div>
            		<div style="padding-left: 18px;">
            		 <c:if test="${!empty message}">
                        <p><c:out value="${message}"/></p>
                    </c:if>
                    <c:if test="${!empty clsexception}">
                    	<span class="error"><str:label strId="AMPARTNERPORTAL" key="${clsexception}"/></span><br/>
                    </c:if>
                    <c:if test="${!empty errormessage}">
                    	<span class="error"><str:label strId="AMPARTNERPORTAL" key="${errormessage}"/></span><br/>
                    </c:if>
                     <!-- Support for Spring errors holder -->
                    <spring:bind path="membership.*">
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
                       
                    </spring:bind>
                    
                    <c:if test="${!empty result}">
                    	 <strong><p><str:label strId="AMPARTNERPORTAL" key="${result}"/></p></strong>
                    </c:if>
                    </div>
        		</div>
            </div>
        </div>
        <jsp:include page="/WEB-INF/jsp/component/footer.jsp"/>
    </div>
</body>

<script type="text/javascript">
	membershipdisableddiv();
	function membershipdisableddiv(){
		var checkingMethodOption = "${membership.checkingMethod}"
		var OptionVal = "S";
		if(OptionVal==checkingMethodOption){
			$("#div").attr("style","display:block");
		}else{
			$("#div").attr("style","display:none");
		}
	}
	function disablediv(){
		var optionVal = $("#checkingMethod").val();
		
		if("F"==optionVal){
			$("#div").attr("style","display:none");
		}else{
			$("#div").attr("style","display:block");
		}
	}
	function resetValue(){
	    $("#member_id").val(""); 	
	    $("#last_name").val(""); 
	    $("#first_name").val(""); 
	    $("#checkingMethod").val("S"); 
	    $("#div").attr("style","display:block");
	    $("#firstName_char").val("4");
	    $("#lastName_char").val("12");
	    $("#swap_checkbox").prop('checked',false);
	}
</script>
</html>