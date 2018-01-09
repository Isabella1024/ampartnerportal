/*
 * Created on Jun 17, 2009
 *
 */
package com.asiamiles.partnerportal.util;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.NoHttpResponseException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang.StringUtils;

import com.asiamiles.partnerportal.RecoverableSystemException;
import com.asiamiles.partnerportal.SystemException;
import com.cathaypacific.utility.Logger;

/**
 * Helper Class for making HTTP Calls
 * @author CPPBENL
 *
 */
public final class HTTPConnectionHelper {

	private Logger appLogger = AppLogger.getAppLogger();
	
	public HTTPConnectionHelper() {}
	
	/**
	 * Makes a HTTP POST call to the specific URL with the specific post parameters 
	 * @param url 
	 * @param postParams <code>Map&lt;String, String&gt;</code> of the post parameters
	 * @param timeout Timeout in milliseconds
	 * @return the body of the response as String
	 * @throws SystemException
	 * @throws RecoverableSystemException
	 */
	public String postToSite(URL url, Map postParams, int timeout) throws SystemException {

		if (timeout < 0) {
			throw new IllegalArgumentException("Timeout value must be greater than 0");
		}
		
		String body = null;
		
		HttpClient client = new HttpClient();
		PostMethod methodCall = null;
		
		
		try{
		    //Socket
		    client.getHttpConnectionManager().getParams().setSoTimeout(timeout);
		    //Connection
			client.getHttpConnectionManager().getParams().setConnectionTimeout(timeout);
			

			//client.getHostConfiguration().setHost(host,port,http);
	        client.getParams().setParameter("http.protocol.content-charset", "UTF-8");
	        
	        methodCall = new PostMethod(url.toString());
	        // Assign referer for pass the domain secuirty check
            methodCall.setRequestHeader("REFERER",url.toString());

	        if (postParams != null && postParams.size() > 0) {
		        NameValuePair[] data = new NameValuePair[postParams.size()];
	            Iterator it = postParams.entrySet().iterator();
	            int x =0;
	            while (it.hasNext()){
	                Map.Entry entry = (Map.Entry)it.next();
	            	String paramName = (String)entry.getKey();
	                String paramValue = (String)entry.getValue();
	                data[x++] = new NameValuePair(paramName, StringUtils.defaultString(paramValue));
	                appLogger.debug("Request param : " +paramName +"="+ paramValue);
	            }
	            methodCall.setRequestBody(data);
	        }
	        
	        // For Debug purposes only
	        StringBuffer fullUrlString = new StringBuffer(url.toString());
	        fullUrlString.append('?');
	        for (Iterator it = postParams.entrySet().iterator(); it.hasNext(); ) {
                Map.Entry entry = (Map.Entry)it.next();
            	String paramName = (String)entry.getKey();
                String paramValue = (String)entry.getValue();
	        	fullUrlString.append(paramName).append('=').append(paramValue);
	        	if (it.hasNext()) {
	        		fullUrlString.append('&');
	        	}
	        }
	        appLogger.debug("Debug URL: " + fullUrlString);
	        
	        
	        
	        
	        Date startDate = new Date();
	        appLogger.info(" [Request post to : " + url + "- timeout Limit : "+ timeout + "ms]");
	        
	        client.executeMethod(methodCall);

	        //get Body as String
	        body = methodCall.getResponseBodyAsString();
	        //body = methodCall.getResponseBody().
	        
	        byte[] utf8Bytes = body.getBytes("UTF8");

	        body = new String(utf8Bytes, "UTF8");

	        int statuscode = methodCall.getStatusCode();
	        appLogger.info("Status Code : " + statuscode);
	        
	        if ((statuscode == HttpStatus.SC_MOVED_TEMPORARILY) ||
	            (statuscode == HttpStatus.SC_MOVED_PERMANENTLY) ||
	            (statuscode == HttpStatus.SC_SEE_OTHER) ||
	            (statuscode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
	            //AMSiteLogger.error("Status Code: " + statuscode );
	            SystemException e = new SystemException(SystemException.HTTP_CONN_INVALID_RESPONSE, "HTTP Status Code ["+statuscode+"]");
	            throw e;
	        } else if (statuscode != HttpStatus.SC_OK){
	            //AMSiteLogger.error("Case Two Status Code: " + statuscode );
	            SystemException e = new SystemException(SystemException.HTTP_CONN_INVALID_RESPONSE, "HTTP Status Code ["+statuscode+"]");
	            throw e;
	        } else {
	        
	            Header header = methodCall.getResponseHeader("location");
	        }

	        //Get Resposne header value
	        Header[] headers = methodCall.getResponseHeaders();
	        Header tmpHeader = null;
	        
	        if(headers!=null){
	            for (int y =0 ; y < headers.length; y++){
	                //aMap.put(headers[y].getName(),headers[y].getValue());
	            	appLogger.debug("Header from response : " + headers[y].getName() + " = " + headers[y].getValue());
	            }
	        }
	        
	        
	        if( body.length() > 500 ) {
	        	appLogger.info(body.substring(0,500)+ "...");
//	        	appLogger.debug(body.substring(0)); //XXX Enable for debugging CLS Messages
	        } else{
	        	appLogger.info(body.substring(0));
	        }
	        
	        appLogger.info(" [Response Time "+ ((new Date()).getTime() - startDate.getTime()) +  "ms - Status : " + methodCall.getStatusLine().toString() + "]");      

		} catch (org.apache.commons.httpclient.ConnectTimeoutException timeoutE){
			// Connection Timed out - try again later
		    RecoverableSystemException e = new RecoverableSystemException(RecoverableSystemException.HTTP_CONN_TIMEOUT, url.toString(), timeoutE);
		    appLogger.error("Connection Timeout : " + url + ", bytesTransferred" + timeoutE.bytesTransferred, e);
		    throw e;
		} catch (NoHttpResponseException e) {
			// No Response - possibly because the server is under heavy load and dropped the connection
			RecoverableSystemException rse = new RecoverableSystemException(RecoverableSystemException.HTTP_NO_RESPONSE, url.toString(), e);
			appLogger.error("No HTTP Response : " + url, e);
			throw rse;
			
		} catch (HttpException e) {
			// Logical Errors caused by mismatch betwen client and server in interpretation of the HTTP Protocol Spec - NOT Recoverable
			SystemException se = new SystemException(url.toString() + " : " + e.getMessage(), SystemException.HTTP_REQUEST_ERROR);
			throw se;
		} catch (IOException ioException){
			// Other IO Exceptions - treat as though they are not recoverable
		    SystemException e = new SystemException(SystemException.HTTP_IOEXCEPTION, url.toString() + " : " + ioException.getMessage(), ioException);
		    throw e;
		} finally {
		    if(methodCall!=null){
		        methodCall.releaseConnection();
		    }
		}
		return body;
	}
}
