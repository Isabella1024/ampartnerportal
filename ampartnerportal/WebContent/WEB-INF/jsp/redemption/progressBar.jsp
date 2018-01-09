<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%-- STR include --%>
<%@ taglib uri="/WEB-INF/str.tld" prefix="str" %>
<%-- Init STR Common --%>
<% String lang = response.getLocale().getLanguage(); %>
<str:map appCode="AMPARTNERPORTAL" lang="<%=lang%>" cache="true" strId='AMPARTNERPORTAL'/>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<table class="progress_bar" width="100%">
    <c:if test="${param.selected == 'enterClaims'}">
        <tr>
            <td width="1%" class="selected"></td>
            <td width="24%">
                <strong><str:label strId="AMPARTNERPORTAL" key="info_enter_claim"/></strong>
            </td>
            <td width="1%" class="unselected"></td>
            <td width="24%">
                <str:label strId="AMPARTNERPORTAL" key="info_process_collection"/>
            </td>
            <td width="1%" class="unselected"></td>
            <td width="24%">
                <str:label strId="AMPARTNERPORTAL" key="info_complete_collection"/>
            </td>
            <td width="1%" class="unselected"></td>
            <td width="24%">
                <str:label strId="AMPARTNERPORTAL" key="info_acknowledgement"/>
            </td>
        </tr>
    </c:if>
    <c:if test="${param.selected == 'processClaims'}">
        <tr>
            <td width="1%" class="unselected"></td>
            <td width="24%" class="alt">
                <str:label strId="AMPARTNERPORTAL" key="info_enter_claim"/>
            </td>
            <td width="1%" class="selected"></td>
            <td width="24%">
                <strong><str:label strId="AMPARTNERPORTAL" key="info_process_collection"/></strong>
            </td>
            <td width="1%" class="unselected"></td>
            <td width="24%">
                <str:label strId="AMPARTNERPORTAL" key="info_complete_collection"/>
            </td>
            <td width="1%" class="unselected"></td>
            <td width="24%">
                <str:label strId="AMPARTNERPORTAL" key="info_acknowledgement"/>
            </td>
        </tr>
    </c:if>
    <c:if test="${param.selected == 'completeClaims'}">
        <tr>
            <td width="1%" class="unselected"></td>
            <td width="24%" class="alt">
                <str:label strId="AMPARTNERPORTAL" key="info_enter_claim"/>
            </td>
            <td width="1%" class="unselected"></td>
            <td width="24%" class="alt">
                <str:label strId="AMPARTNERPORTAL" key="info_process_collection"/>
            </td>
            <td width="1%" class="selected"></td>
            <td width="24%">
                <strong><str:label strId="AMPARTNERPORTAL" key="info_complete_collection"/></strong>
            </td>
            <td width="1%" class="unselected"></td>
            <td width="24%">
                <str:label strId="AMPARTNERPORTAL" key="info_acknowledgement"/>
            </td>
        </tr>
    </c:if>
    <c:if test="${param.selected == 'acknowledgement'}">
        <tr>
            <td width="1%" class="unselected"></td>
            <td width="24%" class="alt">
                <str:label strId="AMPARTNERPORTAL" key="info_enter_claim"/>
            </td>
            <td width="1%" class="unselected"></td>
            <td width="24%" class="alt">
                <str:label strId="AMPARTNERPORTAL" key="info_process_collection"/>
            </td>
            <td width="1%" class="unselected"></td>
            <td width="24%" class="alt">
                <str:label strId="AMPARTNERPORTAL" key="info_complete_collection"/>
            </td>
            <td width="1%" class="selected"></td>
            <td width="24%">
                <strong><str:label strId="AMPARTNERPORTAL" key="info_acknowledgement"/></strong>
            </td>
        </tr>
    </c:if>
</table>