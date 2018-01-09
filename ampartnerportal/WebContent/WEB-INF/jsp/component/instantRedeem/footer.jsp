<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/jsp/component/jsp_include.jsp" %>
<%@ page import="java.util.*" %>
<%@ page import="org.apache.commons.lang.StringUtils"%> 
<%-- STR include --%>
<%@ taglib uri="/WEB-INF/str.tld" prefix="str" %>

<div class="footer">
  
     <div class="choose_lang"> 
     <form action="#" id="languageForm" name="languageForm" method="post">
        <%
        Map paramMap = request.getParameterMap();
        for(Iterator paramKeyIter = paramMap.keySet().iterator(); paramKeyIter.hasNext(); ){
              String paramName =  (String) paramKeyIter.next();
              String[] paramValue = (String[]) paramMap.get(paramName);
              if (!StringUtils.equals(paramName, "lang")) {
        %>
              <input type="hidden" name="<%=paramName %>" id="<%=paramName %>" value="<%=paramValue[0] %>">     
        <%
             }
        }
        %>
     </form>
         <a href="javascript:void(0)" onclick="changeFooterLang('en');">English</a> <span> | </span>
         <a href="javascript:void(0)" onclick="changeFooterLang('zh');">&#x7E41;&#x9AD4;&#x4E2D;&#x6587;</a> <span> | </span>
         <a href="javascript:void(0)" onclick="changeFooterLang('sc');">&#x7B80;&#x4F53;&#x4E2D;&#x6587;</a> 
     </div>
     
                
     <img id="img-buffer" src="<%=request.getContextPath()%>/images/instantRedeem/logo.jpg" />
     <div class="copyrights">
         <a href="<%=request.getContextPath()%>/copyright.do"><str:label strId="AMPARTNERPORTAL" key="footer_copyright"/></a> <str:label strId="AMPARTNERPORTAL" key="footer_copyright_2"/>
         <p class="links_logout"><span> | </span> <a href="<%=request.getContextPath()%>/instantRedeem/logout.do"><str:label strId="AMPARTNERPORTAL" key="nav_logout"/></a></p>
     </div>
      
</div>
   
<script type="text/javascript">
    
    function changeFooterLang(lang){
    	var viewPath = "<%=request.getServletPath()%>";
    	if(viewPath.indexOf("Acknowledgement")!=-1) {
    		var homePath = "<%=request.getContextPath()%>/instantRedeem/qrCodeReader.do?lang=" + lang;
    		document.location.href = homePath;
    	} else {
		    var url = document.URL;
		    if (document.location.search == "") {
		        url += "?lang=" + lang;
		    } else {
			    if (url.indexOf("lang=") < 0 ) {
			        url += "&lang=" + lang;
			    } else {
			        url = url.replace("lang=${param.lang}", "lang="+lang);
			    }
			}
		    $("#languageForm").attr("action", url);
		    $("#languageForm").submit();
    	}  
	}

</script>