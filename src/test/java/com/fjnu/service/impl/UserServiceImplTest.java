package com.fjnu.service.impl;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.hibernate.dialect.FirebirdDialect;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fjnu.domain.Friend;
import com.fjnu.domain.MinuteSportData;
import com.fjnu.domain.OneSport;
import com.fjnu.domain.User;
import com.fjnu.service.UserService;

public class UserServiceImplTest {

	@BeforeClass
	public static void setUpBeforeClass() {
	}

	@AfterClass
	public static void tearDownAfterClass() {
	}

	@Test
	public void testRegister() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		UserService service = (UserServiceImpl) ctx.getBean("userServiceImpl");

		User user = new User();
		user.setEmail("137590335@qq.com");
		user.setPassword("123456");
		user.setNickName("good girl");
		user.setSex("女");
		user.setBirthday("1994");
		user.setHeight(164.0);
		user.setWeight(45.5);
		user.setDescription("我是一个乐观开朗的大学生，兴趣爱好编程");
		File file = new File("e:\\testPhoto\\2.jpg");
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		byte[] b = new byte[(int) file.length()];
		try {
			fis.read(b);
		} catch (IOException e) {

			e.printStackTrace();
		}
		user.setProtrait(b);
		// Friend friend = new Friend();
		// friend.setMyEmail(user.getEmail());
		// friend.setAnotherEmail("137590332@qq.com");
		// List<Friend> friends = new ArrayList<Friend>();
		// friends.add(friend);
		// user.setFriends(friends);
		String strUser = JSON.toJSONString(user);
		System.out.println(service.register(strUser));

	}

	@Test
	public void testLogin() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		UserService service = (UserServiceImpl) ctx.getBean("userServiceImpl");

		System.out.println(service.login("137590332@qq.com", "123456"));
	}

	@Test
	public void testGetUserInfo() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		UserService service = (UserServiceImpl) ctx.getBean("userServiceImpl");
		User u = JSON.parseObject(service.getUserInfo("137590335@qq.com"),
				User.class);

		if (u == null) {
			System.out.println("不存在此用户");
		}
		if (u != null) {
			for (int i = 0; i < u.getFriends().size(); i++) {
				System.out.println(u.getFriends().get(i).getAnotherEmail());
			}
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream("E://9.jpg");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.write(u.getProtrait());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				fos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//System.out.println(u.getFriends().get(0).getAnotherEmail());
			// System.out.println(u.getModels().get(0).getId());
			// System.out.println(u.getOneSports().get(0).getId());
		}
	}

	@Test
	public void testGetAllUser() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		UserService service = (UserServiceImpl) ctx.getBean("userServiceImpl");
		List<User> users = JSON.parseArray(service.getAllUser(), User.class);
		for (int i = 0; i < users.size(); i++) {
			System.out.println(users.get(i).getEmail());
		}
	}

	@Test
	public void testUpadateUserInfo() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		UserService service = (UserServiceImpl) ctx.getBean("userServiceImpl");
		User user = new User();
		user.setEmail("137590334@qq.com");
		user.setNickName("你好");
		user.setBirthday("1993-10-10");
		user.setPassword("123456789");
		String strUser = JSON.toJSONString(user);
		System.out.println(service.updateUserInfo(strUser));
	}

	@Test
	public void testMakeFriend() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		UserService service = (UserServiceImpl) ctx.getBean("userServiceImpl");
		User user = new User();
		user.setEmail("137590335@qq.com");

		Friend friend = new Friend();
		friend.setMyEmail(user.getEmail());
		friend.setAnotherEmail("137590334@qq.com");
		friend.setUser(user);
		// friend.setAnotherUser(user);
		String strFriend = JSON.toJSONString(friend);
		System.out.println(service.makeFriend(strFriend));
	}

	@Test
	public void testDeleteFriend() {
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		UserService service = (UserServiceImpl) ctx.getBean("userServiceImpl");
		User user = new User();
		user.setEmail("137590332@qq.com");

		Friend friend = new Friend();
		friend.setMyEmail(user.getEmail());
		friend.setAnotherEmail("137590337@qq.com");
		friend.setUser(user);
		// friend.setAnotherUser(user);
		String strFriend = JSON.toJSONString(friend);
		System.out.println(service.deleteFriend(strFriend));
	}
	
	@Test
	public void testGetALLFriend(){
		ApplicationContext ctx = new ClassPathXmlApplicationContext(
				"applicationContext.xml");
		UserService service = (UserServiceImpl) ctx.getBean("userServiceImpl");
		User user = new User();
		user.setEmail("137590334@qq.com");
		
		String strUsers=service.getAllFriend(user.getEmail());
		List<User> users = JSON.parseArray(strUsers, User.class);
		for(int i=0;i<users.size();i++){
			System.out.println(users.get(i).getEmail());
		}
		
	}
	
}
