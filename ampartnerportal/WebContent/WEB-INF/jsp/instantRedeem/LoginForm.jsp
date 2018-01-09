<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/instantRedeem/head.jsp">
</jsp:include>

<body class="login_page">
	<jsp:include page="/WEB-INF/jsp/component/instantRedeem/loginHeader.jsp"></jsp:include>

    <main>
      <div class="main_content">
            <!-- /Login Box start-->
            <div class="login_box">
                <h1><str:label strId="AMPARTNERPORTAL" key="instant_redeem_title_login_to_redeem"/></h1>
                 <form name="login_form" method="POST" action="<c:url value='/instantRedeem/login.do'/>">
					 <c:if test="${!empty loginForwardAction}">
						<input type="hidden" name="forwardAction" value="<c:url value="${loginForwardAction}"/>"/>
					 </c:if>                
                     <div class="form_element">
                            <div class="input_select">
                                <select id="Language" name="Language" onChange="onChangeLanguage(this.options[this.selectedIndex].value);">
                                    <option value="selectLang"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_label_BtnSelectLang"/></option>
                                    <str:label strId="AMPARTNERPORTAL" key="instant_redeem_select_language_options"/>
                                </select>
                            </div>
                            <input type="text" maxlength="15" placeholder="<str:label strId='AMPARTNERPORTAL' key='instant_redeem_merchant_id_placeholder' />" id="agentId" name="agentId" />
                            <input type="password" maxlength="8" placeholder="<str:label strId='AMPARTNERPORTAL' key='instant_redeem_pin_placeholder' />" id="password" name="password" />
                            <c:if test="${!empty messageCode}">
								<div class="error_msg" id="login_error">
									<fmt:message key="${messageCode}">
										<fmt:param value="${agentId}"/>
									</fmt:message>
								</div>
                            </c:if>
                            <c:if test="${!empty clsexception}">
                            	<div class="error_msg" id="login_error">
									<fmt:message key="${clsexception.errorMessageCode}"/>
								</div>
							</c:if>
                         
                            <button class="btn_submit"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_login" /></button>
                            <div class="forgot_links">
                               
                                 <a href="<c:url value='/forgotPassword.do'/>" class="forgot_link_pin" target="_blank"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_forgot_pin"/></a>
                                <p class="spacer"></p>
                            </div>
                        </div>
                </form>
                <form name="changeLang" id="changeLang" method="GET">
                    <input type="hidden" name="lang" id="lang">
                    <c:if test="${!empty param.memberId}">
                        <input type="hidden" name="memberId" value="${param.memberId }">
                    </c:if>
                    <c:if test="${!empty param.claimNO}">
                        <input type="hidden" name="claimNO" value="${param.claimNO }">
                    </c:if>
                    <c:if test="${!empty param.securityCode}">
                        <input type="hidden" name="securityCode" value="${param.securityCode }">
                    </c:if>
                    <c:if test="${!empty param.mb}">
                        <input type="hidden" name="mb" value="${param.mb }">
                    </c:if>
                    <c:if test="${!empty param.forwardAction}">
                        <input type="hidden" name="forwardAction" value="${param.forwardAction }">
                    </c:if>
                </form>
            </div>
         <!-- /Login Box end-->
        </div>
    </main>

        <jsp:include page="/WEB-INF/jsp/component/instantRedeem/footer.jsp"/>
        <jsp:include page="/WEB-INF/jsp/component/instantRedeem/js/js_include_common.jsp"/>
		<script type="text/javascript">
	        function onChangeLanguage(value) {
	            if (value != 'selectLang') {
	                 $('#changeLang').attr("action", document.URL);
	                 $('#lang').val(value);
	                 $('#changeLang').submit();
	            }
	        }
	        
	        $(document).ready(function(){
	            var selectLang = '${param.lang}';
	            $("#Language option[value='" + selectLang + "']").prop("selected",true);
	            $('select').customSelect();
	        });
		</script>
		<!-- Google Analytics: change UA-XXXXX-X to be your site's ID -->
		<script>
		/*
	      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
	      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
	      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
	      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
	      ga('create', 'UA-54352562-1');
	      ga('send', 'pageview');
	    */
		</script>        
</body>
</html>