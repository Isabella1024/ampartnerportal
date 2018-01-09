<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/instantRedeem/head.jsp">
</jsp:include>

<body>
    <jsp:include page="/WEB-INF/jsp/component/instantRedeem/header.jsp"></jsp:include>
<spring:bind path="instantRedeemForm.*">
</spring:bind>
    <main>
    <c:forEach items="${instantRedeemForm.NARDetails}" var="nar" varStatus="claimCount">
      <div class="main_content">
           
            <div class="offer_box_complete">
            <div class="detail_log statusdiv">
                    <span class="status_msg"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_status"/>:</span>
                      <span class="statuscompleted"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_status_completed"/></span>
                </div>
              <!-- /redeem item   start-->
            <div class="detail_log offerstatusimage">
                  <img id="statusimages" src="<%=request.getContextPath()%>/images/instantRedeem/merchant/completed.png">
              </div>
             <div class="detail_log">
                     <div class="merchant_det ba_current">
                           <span><str:label strId="AMPARTNERPORTAL" key="label_membership_no"/></span>
                           <em><c:out value="${instantRedeemForm.memberId}"/></em>
                    </div>
                     <div class="merchant_det ba_after">
                           <span><str:label strId="AMPARTNERPORTAL" key="label_claim_no"/></span>
                          <em><c:out value="${nar.claimNo}"/></em>
                    </div>
                    <p class="spacer"></p>
                    <div class="merchant_det ba_current">
                           <span><str:label strId="AMPARTNERPORTAL" key="instant_redeem_recipient_first_name"/></span>
                           <em><c:out value="${nar.holderFirstName}"/></em>
                    </div>
                     <div class="merchant_det ba_after">
                           <span><str:label strId="AMPARTNERPORTAL" key="instant_redeem_recipient_last_name"/></span>
                          <em><c:out value="${nar.holderName}"/></em>
                    </div>
                    <div class="merchant_det ba_current floatredeemdata">
                           <span><str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/></span>
                            <em>
                                <c:choose>
                                    <c:when test="${nar.claimStatusCode eq 0}">
                                    <c:out value="${nar.packageDescription}"/>
                                    </c:when>
                                    <c:otherwise>
                                    -
                                    </c:otherwise>
                                </c:choose>
                            </em>
                    </div>
                    <div class="merchant_det ba_current floatredeemdata">
                           <span><str:label strId="AMPARTNERPORTAL" key="instant_redeem_receipt_no"/></span>
                            <em><c:out value="${nar.receiptNo}"/></em>
                    </div>
                    <div class="merchant_det ba_current floatredeemdata force_word_break">
                            <span><str:label strId="AMPARTNERPORTAL" key="instant_redeem_staff_no"/>/ <str:label strId="AMPARTNERPORTAL" key="instant_redeem_partner_remarks"/></span>
                             <em><c:out value="${nar.remarks}"/> <c:out value="${nar.remarks2}"/></em>
                    </div>
                    <p class="spacer"></p>
                </div>
             <div class="form_btn">
             <button class="btn_submit" type="button" onclick="window.location.href='<%=request.getContextPath()%>/instantRedeem/qrCodeReader.do'"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_back_to_homepage"/></button>
                 
                  <p class="spacer">&nbsp;</p> 
            </div>
              
             
         <p class="spacer">&nbsp;</p>
        </form>
        </div>
          </div>
    </c:forEach> 
    </main>

    <jsp:include page="/WEB-INF/jsp/component/instantRedeem/footer.jsp"/>
    <jsp:include page="/WEB-INF/jsp/component/instantRedeem/js/js_include_common.jsp"/>
    <!-- Google Analytics: change UA-XXXXX-X to be your site's ID -->
    <script>
    /*
      (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
      (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
      m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
      })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
      ga('create', 'UA-XXXXX-X');
      ga('send', 'pageview');
    */
    </script>
</body>
</html>