package com.fjnu.dao.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.SetSimpleValueTypeSecondPass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.fjnu.dao.UserDAO;
import com.fjnu.domain.Friend;
import com.fjnu.domain.User;

@Repository
public class UserDAOImpl implements UserDAO {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public boolean registerUser(User user) {

		Session session = sessionFactory.openSession();
		try {
			session.save(user);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			session.flush();
			session.close();
		}
	}

	@Override
	public int userLogin(String email, String password) {

		Session session = sessionFactory.openSession();
		Query q = session.createQuery(" from User t where t.email=:email ")
				.setString("email", email);
		List<User> users = (List<User>) q.list();// 返回一个对象集合
		if (users.size() > 0) {
			User user = users.get(0);
			if (user.getPassword().equals(password)) {
				return 1;
			} else {
				return -1;// 密码错误
			}

		}
		return -2;// 用户不存在
	}

	@Override
	public User findUserInfo(String email) {
		Session session = sessionFactory.openSession();
		User user = null;
		try {
			user = (User) session.get(User.class, email);
		} catch (Exception e) {
		}
		return user;
	}

	@Override
	public List<User> findAllUser() {
		Session session = sessionFactory.openSession();
		Query q = session.createQuery(" from User ");
		List<User> users = new ArrayList<User>();
		users = (List<User>) q.list();
		return users;
	}

	@Override
	public boolean changeUserInfo(User user) {
		Session session = sessionFactory.openSession();
		try {
			session.update(user);
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			session.flush();
			session.close();
		}
	}

	@Override
	public int addFriend(Friend friend) {
		Session session = sessionFactory.openSession();
		try {
			Query q = session
					.createQuery(
							" from Friend t where t.myEmail=:myEmail and t.anotherEmail=:anotherEmail ")
					.setString("myEmail", friend.getMyEmail())
					.setString("anotherEmail", friend.getAnotherEmail());
			List<Friend> friends = (List<Friend>) q.list();// 返回一个对象集合
			if (friends.size() == 0) {
				session.save(friend);
				session.flush();
				return 1;
			}
			return -1;// 你们已经是好友了
		} catch (Exception e) {
			return -2;// 添加好友失败
		} finally {
			session.flush();
			session.close();
		}
	}

	@Override
	public boolean removeFriend(Friend friend) {
		Session session = sessionFactory.openSession();
		try {
			Query q = session
					.createQuery(
							" delete Friend t where t.myEmail=:myEmail and t.anotherEmail=:anotherEmail ")
					.setString("myEmail", friend.getMyEmail())
					.setString("anotherEmail", friend.getAnotherEmail());
			q.executeUpdate();
			session.flush();
			return true;
		} catch (Exception e) {
			return false;
		} finally {
			session.flush();
			session.close();
		}

	}

	@Override
	public List<User> findAllFriend(String email) {
		List<User> users = new ArrayList<User>();
		List<Friend> friends = null; 
		Session session = sessionFactory.openSession();
		Query q = session.createQuery(" from Friend t where t.myEmail=:email").setString("email", email);
		friends = (List<Friend>)q.list();
		for(int i=0;i<friends.size();i++){
			User u = findUserInfo(friends.get(i).getAnotherEmail());
			users.add(u);
		}		
		return users;
	}

}
