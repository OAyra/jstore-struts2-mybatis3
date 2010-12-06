package com.usemodj.jpetstore.domain;

import java.io.Serializable;

import com.usemodj.struts.Role;

public class Signon implements Serializable {
	private String username;
	private String password;
	private String passwordConfirm;
	private String passwordNew;
	private Role role;
	private String email;
	
	public Signon(String username, String password){
		this.username = username;
		this.password = password;
	}
	public Signon() {
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPasswordConfirm() {
		return passwordConfirm;
	}
	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
	}
	public String getPasswordNew() {
		return passwordNew;
	}
	public void setPasswordNew(String passwordNew) {
		this.passwordNew = passwordNew;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
