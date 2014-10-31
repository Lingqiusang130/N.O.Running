package com.fjnu.dao;

import java.util.List;

import com.fjnu.domain.Friend;
import com.fjnu.domain.User;

public interface UserDAO {

	public boolean registerUser(User user);

	public int userLogin(String email, String password);

	public User findUserInfo(String email);

	public List<User> findAllUser();

	public boolean changeUserInfo(User user);

	public int addFriend(Friend friend);

	public boolean removeFriend(Friend friend);
	
	public List<User> findAllFriend(String email);
}
