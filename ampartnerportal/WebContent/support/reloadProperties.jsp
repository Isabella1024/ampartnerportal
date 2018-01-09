<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="com.asiamiles.partnerportal.SiteProperties" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<META http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<TITLE>AMPartnerPortal Refresh Properties Tool</TITLE>
</HEAD>
<BODY>
<h2>AMPartnerPortal Refresh Properties Tool</h2>

<%
    out.println("<h2>Clean Cache Tool</h2>");
    int action_value = Integer.parseInt((null == request.getParameter("action_select")) ? "0" : request.getParameter("action_select"));
%>
<form>
    <table cellpadding="2">
        <tr>
            <td><b>Select action :</b></td>
            <td><select name="action_select" id="action_select">
                <option value="0">Select One</option>
                <option value="1">AMPartnerSiteProperties</option>
                <option value="2">CLSInterfaceProperties</option>
                <option value="3">PermittedIPAddressProperties</option>
            </select>
            </td>
        </tr>
        <tr>
            <td></td>
            <td align="right"><input type=submit value='Refresh'></td>
        </tr>
    </table>
    <br>
    <table width=770 border="1" cellpadding="2">
        <%
        	WebApplicationContext applicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
            String action_name = "";

            switch (action_value) {
                case 1:
                    SiteProperties amPartnerSiteProperties = (SiteProperties)applicationContext.getBean("SiteProperties");
                    amPartnerSiteProperties.reload();
                    action_name = "amPartnerSiteProperties.reload()";
                    break;
                case 2:
                    SiteProperties clsInterfaceProperties = (SiteProperties)applicationContext.getBean("CLSInterfaceProperties");
                    clsInterfaceProperties.reload();
                    action_name = "clsInterfaceProperties.reload()";
                    break;
                case 3:
                	SiteProperties permittedIpAddressProperties = (SiteProperties)applicationContext.getBean("PermittedIPAddresses");
                	permittedIpAddressProperties.reload();
                	action_name = "PermittedIPAddresses.reload()";
                	break;
              default:
                    action_name = "";
                    break;
            }

            if (!action_name.equals(""))
                out.println("<tr bgcolor='#9999ff'><td width='25%'><b>" + action_name + " executed</b></td></tr>");
        %>
    </table>
</form>
</BODY>
</HTML>
