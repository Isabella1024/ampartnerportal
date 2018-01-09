<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %><%PrintWriter writer;try {writer = response.getWriter();writer.write(65279);}catch (Exception e) {}%><%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %><%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %><%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %><%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %><%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %><%@ taglib uri="/WEB-INF/str.tld" prefix="str" %><% String lang = response.getLocale().getLanguage(); %><str:map appCode="AMPARTNERPORTAL" lang="<%=lang%>" cache="true" strId='AMPARTNERPORTAL'/><%@ page import="java.io.PrintWriter" %><%@page import="javax.servlet.http.HttpServletResponse"%><% ((HttpServletResponse)response).setHeader("Pragma", "public"); ((HttpServletResponse)response).setHeader("Cache-Control", "max-age=0");%><% 	response.setContentType("application/CSV"); response.setHeader("content-disposition", "attachment; filename=\"" + "billed.csv\""); %> <str:label strId="AMPARTNERPORTAL" key="label_claim_no"/>,<str:label strId="AMPARTNERPORTAL" key="label_redemption_item"/>,<str:label strId="AMPARTNERPORTAL" key="label_collection_date"/>,<str:label strId="AMPARTNERPORTAL" key="label_membership_no"/>,<str:label strId="AMPARTNERPORTAL" key="label_recipient"/>,<str:label strId="AMPARTNERPORTAL" key="label_receipt_ref"/><c:forEach items="${billingForm.claims}" var="claim" varStatus="claimCount">
<c:out value="${claim.claimNo}"/>,<c:out value="${claim.packageDescription}"/>,<fmt:formatDate value="${claim.collectionTime}" pattern="dd MMM yyyy"/>,<c:out value="${claim.memberID}"/>,<c:out value="${claim.holderName}"/> <c:out value="${claim.holderFirstName}"/>,<c:out value="${claim.receiptNo}"/></c:forEach>