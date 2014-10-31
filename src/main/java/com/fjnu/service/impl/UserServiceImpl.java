package com.fjnu.service.impl;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.fjnu.dao.UserDAO;
import com.fjnu.domain.Friend;
import com.fjnu.domain.User;
import com.fjnu.service.UserService;

@Service
@WebService(serviceName = "userServiceImpl", endpointInterface = "com.fjnu.service.UserService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;

	@Override
	public boolean register(String strUser) {
		User user = null;
		user = JSON.parseObject(strUser, User.class);
		return userDAO.registerUser(user);

	}

	@Override
	public String login(String email, String password) {
		String answer = null;
		answer = JSON.toJSONString(userDAO.userLogin(email, password));
		return answer;
	}

	@Override
	public String getUserInfo(String email) {
		String strUserInfo = null;
		strUserInfo = JSON.toJSONString(userDAO.findUserInfo(email));
		return strUserInfo;
	}

	@Override
	public String getAllUser() {
		String strUsers = null;
		strUsers = JSON.toJSONString(userDAO.findAllUser());
		return strUsers;
	}

	@Override
	public boolean updateUserInfo(String strUser) {
		User user = null;
		user = JSON.parseObject(strUser, User.class);
		return userDAO.changeUserInfo(user);
	}

	@Override
	public int makeFriend(String strFriend) {
		Friend friend = null;
		friend = JSON.parseObject(strFriend, Friend.class);
		return userDAO.addFriend(friend);
	}

	@Override
	public boolean deleteFriend(String strFriend) {
		Friend friend=null;
		friend = JSON.parseObject(strFriend, Friend.class);
		return userDAO.removeFriend(friend);
	}

	@Override
	public String getAllFriend(String email) {
		String strUsers=null;	
		strUsers = JSON.toJSONString(userDAO.findAllFriend(email));
		return strUsers;
	}

}
