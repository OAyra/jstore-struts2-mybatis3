package com.usemodj.forum.interceptor;

import java.util.Map;

import com.opensymphony.xwork2.Action;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.usemodj.forum.struts.action.RoleAware;

public class AuthorizationInterceptor extends AbstractInterceptor {
	public static final String ROLE = "role";
	@Override
	public String intercept(ActionInvocation ai) throws Exception {
		Map session = ai.getInvocationContext().getSession();
		String role = (String) session.get( ROLE);
		if (null != role) {
			Object o = ai.getAction();
			if (o instanceof RoleAware) {
				RoleAware action = (RoleAware) o;
				action.setRole(role);
			}
			return ai.invoke();
		} else {
			return Action.LOGIN;
		}
	}
}
