package org.jmu.multiinfo.module.login.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jmu.multiinfo.base.dao.BaseJdbcDao;
import org.jmu.multiinfo.module.login.dao.UserDao;
import org.jmu.multiinfo.module.login.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends BaseJdbcDao implements UserDao {

	@Autowired
	protected JdbcTemplate jdbcTemplate;
	
	public User findUser(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public User findUserByName(String userName) {
		// TODO Auto-generated method stub
		String sql = "select * from user where username = ?";
		List<Map<String, Object>> userList=new ArrayList<Map<String, Object>>();
		userList = this.getJdbcTemplate().queryForList(sql, new Object[]{userName});
Map<String,Object> userMap =userList.get(0);
User user = new User();
user.setPassword((String)userMap.get("password"));
user.setId((Integer)userMap.get("id"));
user.setUserName((String)userMap.get("username"));
		return user;
	}

}
