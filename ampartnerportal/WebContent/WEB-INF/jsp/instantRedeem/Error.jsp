<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>

<html>
<jsp:include page="/WEB-INF/jsp/component/instantRedeem/head.jsp">
</jsp:include>

<body>
	<jsp:include page="/WEB-INF/jsp/component/instantRedeem/header.jsp"></jsp:include>

    <main>
      <div class="main_content">
            <!-- /Login Box start-->
            <div class="error_box">
                <h1><str:label strId="AMPARTNERPORTAL" key="instant_redeem_error_title" /></h1>
               
                <span class="error_desc">
                <str:label strId="AMPARTNERPORTAL" key="instant_redeem_generic_system_error" />
                </span>
            </div>
         <!-- /Login Box end-->
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
