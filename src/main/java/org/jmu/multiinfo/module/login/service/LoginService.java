package org.jmu.multiinfo.module.login.service;

import org.jmu.multiinfo.module.login.dao.UserDao;
import org.jmu.multiinfo.module.login.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserDao userDao;
public boolean Login(User user){
	User userRt=	userDao.findUserByName(user.getUserName());
	logger.debug("comp"+userRt.getPassword()+"sp"+user.getPassword());
	if(userRt.getPassword().equals(user.getPassword())){
		return true;
	}else{
		return false;
	}
	
}
}
