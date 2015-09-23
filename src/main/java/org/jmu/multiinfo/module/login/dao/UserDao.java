package org.jmu.multiinfo.module.login.dao;

import org.jmu.multiinfo.module.login.domain.User;

public interface UserDao {
	public User findUser(int id);
	public User findUserByName(String userName);
}
