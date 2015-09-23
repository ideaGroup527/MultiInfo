package org.jmu.multiinfo.module.login.domain;

import java.io.Serializable;
import java.sql.Timestamp;

public class User implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = -2311052130617495550L;

private int id;

private String userName;

private String password;

private Timestamp last_login_time;

private String tel;

private String email;

public int getId() {
	return id;
}

public void setId(int id) {
	this.id = id;
}

public String getUserName() {
	return userName;
}

public void setUserName(String userName) {
	this.userName = userName;
}

public String getPassword() {
	return password;
}

public void setPassword(String password) {
	this.password = password;
}

public Timestamp getLast_login_time() {
	return last_login_time;
}

public void setLast_login_time(Timestamp last_login_time) {
	this.last_login_time = last_login_time;
}

public String getTel() {
	return tel;
}

public void setTel(String tel) {
	this.tel = tel;
}

public String getEmail() {
	return email;
}

public void setEmail(String email) {
	this.email = email;
}


}
