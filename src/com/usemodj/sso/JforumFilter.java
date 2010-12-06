package com.usemodj.sso;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.usemodj.jpetstore.domain.Signon;

import uk.co.smartkey.jforumsecuresso.SecurityTools;

public class JforumFilter implements Filter {
	private static Logger logger = Logger.getLogger(JforumFilter.class);
	  protected FilterConfig filterConfig;
	  private final static String FILTER_APPLIED = "_jforum_sso_filter_applied";


	@Override
	public void destroy() {
		this.filterConfig = null;
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		//get your user's details from wherever they are available in  your application
		Signon signon = (Signon)((HttpServletRequest)request).getSession().getAttribute("login");
	    // Ensure that filter is only applied once per request.
		if( signon == null){
			logger.debug("-- signon is null");
			
	    	request.removeAttribute( FILTER_APPLIED);
			//delete the cookie using the predefined cookie name
			Cookie c = new Cookie(SecurityTools.FORUM_COOKIE_NAME, null);
			c.setMaxAge(0);
			c.setPath("/");
			((HttpServletResponse) response).addCookie(c);
			
		}
		else /*if ( request.getAttribute(FILTER_APPLIED) == null)*/ {
			logger.debug("-- signon is not null");
			
	    	request.setAttribute(FILTER_APPLIED, Boolean.TRUE);
 
			//encrypt them using your secret password
			String encryptedData = SecurityTools.getInstance().encryptCookieValues(signon.getEmail(), signon.getUsername());
			 
			//send the cookie using the predefined cookie name
			Cookie c = new Cookie(SecurityTools.FORUM_COOKIE_NAME, encryptedData);
			c.setMaxAge(-1);
			c.setPath("/");
			((HttpServletResponse) response).addCookie(c);
	    }
	    // pass the request on
	    chain.doFilter(request, response);

	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		 this.filterConfig = filterConfig;

	}

}
