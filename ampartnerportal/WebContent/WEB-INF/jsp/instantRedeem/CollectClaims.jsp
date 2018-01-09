<%@ page import="com.asiamiles.partnerportal.domain.UserSession" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/instantRedeem/head.jsp">
</jsp:include>
<c:set var="invalidReceiptNo" value="false"/>
<c:set var="invalidStaffNo" value="false"/>
<c:set var="invalidRemarks" value="false"/>
<spring:bind path="instantRedeemForm.*">
	<c:forEach var="error" items="${status.errors.globalErrors}">
		<span class="error">
			<c:if test="${error.code eq 'invalidRequest'}">
				<c:redirect url="${error.defaultMessage}" />
			</c:if>
			<c:if test="${error.code eq 'claimExpired'}">
				<c:redirect url="${error.defaultMessage}" />
			</c:if>
			<c:if test="${error.code eq 'claimCompleted'}">
				<c:redirect url="${error.defaultMessage}" />
			</c:if>
			<c:if test="${error.code eq 'invalidPackageCode'}">
                <c:redirect url="${error.defaultMessage}" />
            </c:if>
            <c:if test="${error.code eq 'invalidMemberNo'}">
                <c:redirect url="${error.defaultMessage}" />
            </c:if>
            <c:if test="${error.code eq 'invalidClaim'}">
            	<c:redirect url="${error.defaultMessage}" />
            </c:if>
		</span><br/>
	</c:forEach>	
	<c:forEach var="error" items="${status.errors.fieldErrors}">
		<c:if test="${error.field eq 'NARDetails[0].receiptNo'}">
			<c:set var="invalidReceiptNo" value="true"/>
		</c:if>
		<c:if test="${error.field eq 'NARDetails[0].remarks'}">
			<c:set var="invalidStaffNo" value="true"/>
		</c:if>
		<c:if test="${error.field eq 'NARDetails[0].remarks2'}">
			<c:set var="invalidRemarks" value="true"/>
		</c:if>
	</c:forEach>
</spring:bind>

<body>
	<jsp:include page="/WEB-INF/jsp/component/instantRedeem/header.jsp"></jsp:include>
    <main>
	<c:forEach items="${instantRedeemForm.NARDetails}" var="nar" varStatus="claimCount">
      <div class="main_content">
            <div class="offer_box">
            <div class="detail_log statusdiv">
                    <span class="status_msg"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_status"/>:</span>
                    <span class="statusopen"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_status_open"/></span>
                </div>
              <!-- /redeem item   start-->
            <div class="detail_log offerstatusimage">
                  <img id="statusimages" src="<%=request.getContextPath()%>/images/instantRedeem/merchant/open.png">
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
                            ${personalOffer.itemName }
                            </em>
                    </div>
                    <p class="spacer"></p>
                </div>
                
             <div class="detail_log" id="item_detail">
                 <h1><a href="javascript:void(0)" class="btn_show"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_details"/></a></h1>
                 <div id="item_detail_content">
                 	${personalOffer.detail }
                 	<hr class="sep_line" />
                	<div class="item_detail_desc">
                     	<h2> <str:label strId="AMPARTNERPORTAL" key="instant_redeem_redemption_period"/>:</h2>
                    	${personalOffer.redemptionStartDate } - ${personalOffer.redemptionEndDate }
                	</div>
                    <div class="item_detail_desc">
                    	<c:choose>
                    		<c:when test="${not empty personalOffer.deliveryCollectionNote and not empty personalOffer.deliveryCollectionPeriod}" >
	                    		<h2> ${personalOffer.deliveryCollectionNote}:</h2>
	                    		${personalOffer.deliveryCollectionPeriod }
                    		</c:when>
                    		<c:otherwise>
		                    	<h2> <str:label strId="AMPARTNERPORTAL" key="instant_redeem_award_consumption_period"/>:</h2>
		                        ${personalOffer.deliveryCollectionStartDate} - ${personalOffer.deliveryCollectionEndDate}
                        	</c:otherwise>
                        </c:choose>
                    </div>
                    <div class="item_detail_desc">
                     	<h2> <str:label strId="AMPARTNERPORTAL" key="instant_redeem_available_country"/>:</h2>
                        ${personalOffer.iredeemAvailableCountry }
                    </div>            
                	<p class="spacer"></p>
                </div>
            </div>
            <!-- /TC start-->
            <div class="detail_log" id="item_tc">
                 <h1><a href="javascript:void(0)" class="btn_show"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_terms_and_conditions"/></a></h1>
                <div  id="item_tc_content">
                  ${personalOffer.termsAndConditions }
                 </div>
                <p class="spacer"></p>
            </div>
       	</div>
       </div>

          <div class="main_content"> 
              <div class="offer_box">
          <form commandName="instantRedeemForm" id="actionForm" method="POST">
            <div class=" detail_log" id="fillDetails">
                <h1><str:label strId="AMPARTNERPORTAL" key="instant_redeem_fill_in_to_collect"/></h1>
                <span><str:label strId="AMPARTNERPORTAL" key="instant_redeem_fill_in_msg"/></span>
            
               <!-- <div id="fill_merchant_details">-->
               		<c:if test="${invalidReceiptNo eq 'false'}">
                    <div class="input_field">
                    </c:if>
                    <c:if test="${invalidReceiptNo eq 'true'}">
                    <div class="input_field error_field">
                    </c:if>
                        <label  for="receipt_no"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_receipt_no"/></label>
                        <input path="NARDetails[${claimCount.index}].receiptNo" type="text" placeholder="<str:label strId="AMPARTNERPORTAL" key="instant_redeem_input_receipt_no"/>" id="receipt_no" name="NARDetails[${claimCount.index}].receiptNo" maxlength="25"/>
                    </div>
                    <div class="input_field">
                        <label for="staff_no"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_staff_no"/></label>
                        <input path="NARDetails[${claimCount.index}].remarks" type="text" placeholder="<str:label strId="AMPARTNERPORTAL" key="instant_redeem_input_staff_no"/>" id="staff_no" maxlength="50" name="NARDetails[${claimCount.index}].remarks" />            
                    </div>
                    <div class="input_field">
                        <label for="partner_rem"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_partner_remarks"/></label>
                        <textarea path="NARDetails[${claimCount.index}].remarks2" class="remarks" placeholder="<str:label strId="AMPARTNERPORTAL" key="instant_redeem_input_remarks"/>" id="remark" maxlength="200" name="NARDetails[${claimCount.index}].remarks2"></textarea>
                    </div>

                <!-- </div>-->
                <div class="item_detail_desc">
                     <!-- <str:label strId="AMPARTNERPORTAL" key="instant_redeem_mandatory_field"/><br />-->
                </div>
            </div>
            
			<div class="form_btn">
				<input type="submit" class="btn_submit" name="_cancel" value="<str:label strId="AMPARTNERPORTAL" key="instant_redeem_cancel"/>"></input>
				<input type="submit" class="btn_submit" name="_finish" value="<str:label strId="AMPARTNERPORTAL" key="instant_redeem_collect"/>"></input>
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

       <script>
            $( document ).ready(function(){
                $("#item_tc_content").hide();
                $("#item_how_to_content").hide();
                $("#item_detail_content").hide();

                $('#remark').autosize();

            });
           
      </script>
      <script type="text/javascript">
        $(document).ready(function(){
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
      ga('create', 'UA-XXXXX-X');
      ga('send', 'pageview');
    */
    </script>
	
</body>
</html>
