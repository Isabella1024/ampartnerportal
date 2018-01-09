<script type="text/javascript" src="<%=request.getContextPath()%>/script/instantRedeem/jquery.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/instantRedeem/jquery.placeholder.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/instantRedeem/jquery.customSelect.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/instantRedeem/jquery.autosize.min.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/script/instantRedeem/aml_redeem.js"></script>
<script type="text/javascript">
	function getMobileOperatingSystem() {
	  var userAgent = navigator.userAgent || navigator.vendor || window.opera;
	
	  if( userAgent.match( /iPad/i ) || userAgent.match( /iPhone/i ) || userAgent.match( /iPod/i ) )
	  {
	    return 'iOS';
	
	  }
	  else if( userAgent.match( /Android/i ) )
	  {
	
	    return 'Android';
	  }
	  else
	  {
	    return 'unknown';
	  }
	}
</script>