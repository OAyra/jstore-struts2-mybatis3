package com.usemodj.forum.interceptor;

import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ValidationAware;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginInterceptor extends AbstractInterceptor implements StrutsStatics {
	private static final long serialVersionUID = 4630601143941564841L;
	private static final String USER_HANDLE = "loggedInUser";  
	 private static final String LOGIN_ATTEMPT = "loginAttempt";
	 private static final String REDIRECT_TO = "redirectTo";
	 
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
        //get the session
        Map session = invocation.getInvocationContext().getSession();
        Map parameters = invocation.getInvocationContext().getParameters();
        

        // Is there a "user" object stored in the user's HttpSession?
        Object user = session.get(USER_HANDLE);
        if (user == null) {
            // The user has not logged in yet.
           // Is the user attempting to log in right now?
            String loginAttempt = (String)parameters.get( LOGIN_ATTEMPT);
            if ( !StringUtils.isBlank (loginAttempt) ) { // The user is attempting to log in.
            	return invocation.invoke();
            } 

            //put the address of the action called originally into the session
           	String namespace = invocation.getProxy().getNamespace();
        	String actionName = invocation.getProxy().getActionName();
        	String queryString = ServletActionContext.getRequest().getQueryString();
        	/*
        	int count = 0;
        	Iterator parametersItr = parameters.entrySet().iterator();
        	while (parametersItr.hasNext()) {
    			Map.Entry entry = (Map.Entry) parametersItr.next();

    			// add the parameter onto the request string
    			Object parameterKey = entry.getKey();
    			String[] parameterValue = (String[])entry.getValue();
    			for(int j = 0; j < parameterValue.length; j++){
    				// if it's the first parameter add a '?' else add a '&'
    				queryString += (count == 0) ? "?" : "&";
    				
    				//get the parameter at this point
    				queryString += parameterKey + "=" + parameterValue[j].toString();
    			}
    			count++;
    		}
    		*/
        	String redirectTo = namespace + "/" + actionName +"?" +queryString;

            session.put( REDIRECT_TO, redirectTo);
            // Either the login attempt failed or the user hasn't tried to login yet, 
            // and we need to send the login form.
            Object action = invocation.getAction();
            if (action instanceof ValidationAware) {
                ((ValidationAware) action).addActionError( "Unauthorized access. Please Login first");
            }
            return "login";
        } else {
            return invocation.invoke ();
        }	
    }

}
