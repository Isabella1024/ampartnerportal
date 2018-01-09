<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>
<%@ page import="com.cathaypacific.str.MessageMapHandler" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="robots" content="noindex,nofollow"/>
    <title>AM Partner Portal STR Cache Refresh Tool</title>
</head>
<body>

<h2>Welcome to STR Cache Refresh Tool !! </h2>
<%
    String lang = (null == request.getParameter("lang")) ? "" : request.getParameter("lang");
    lang = lang.trim();

    boolean first_load = ("".equals(lang));
%>
<form action="">
    <table cellpadding="2">
        <tr>
            <td>Enter language:</td>
            <td><input type='text' name='lang' id='lang' value='<%=lang%>'> (required, en/zh/sc)</td>
        </tr>
        <tr>
        <tr>
            <td></td>
            <td align="right"><input type="submit" value="Refresh"></td>
        </tr>
    </table>
    <br/>
    <%
        // only reload after user clicks Refresh button
        if (!first_load) {
            MessageMapHandler.getInstance().remove("AMPARTNERPORTAL", lang);
            MessageMapHandler.getInstance().get("AMPARTNERPORTAL", lang);
    %>
    <table width=770 border="1" cellpadding="2">
        <tr>
            <td>Cache Reloaded</td>
        </tr>
    </table>
    <% } %>
</form>
</body>
</html>