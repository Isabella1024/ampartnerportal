<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/instantRedeem/head.jsp">
</jsp:include>

<body>
	<jsp:include page="/WEB-INF/jsp/component/instantRedeem/header.jsp"></jsp:include>

    <main>
      <div class="main_content">
           
		<div class="offer_box_complete">
            <div class="detail_log statusdiv">
                    <span class="status_msg"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_redeem_another_offer" /></span>
                      <p><str:label strId="AMPARTNERPORTAL" key="instant_redeem_qrcode_status_msg" /></p>
                </div>
              <!-- /redeem item   start-->
            <div class="detail_log offerErrorImg">
                  <img id="statusimages" src="<%=request.getContextPath()%>/images/instantRedeem/merchant/redeem-another-code.png">
              </div>
     
            
              <div class="form_btn">
             <button class="btn_submit" type="button"><str:label strId="AMPARTNERPORTAL" key="instant_redeem_launch_qrcode_reader" /></button>
                 
                  <p class="spacer">&nbsp;</p> 
            </div>
              
             
         <p class="spacer">&nbsp;</p>
		</form>
		</div>
	  </div>
    </main>

	<jsp:include page="/WEB-INF/jsp/component/instantRedeem/footer.jsp"/>
	<jsp:include page="/WEB-INF/jsp/component/instantRedeem/js/js_include_common.jsp"/>
	<script type="text/javascript">
        $(document).ready(function(){
            $('.btn_submit').bind("click", function(e) {
            	if(getMobileOperatingSystem() == "Android") {
					location.href= "<str:label strId='AMPARTNERPORTAL' key='instant_redeem_launch_qrcode_reader_android' />";
            	} else if(getMobileOperatingSystem() == "iOS") {
					location.href= "<str:label strId='AMPARTNERPORTAL' key='instant_redeem_launch_qrcode_reader_ios' />";
            	} 
            });
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
