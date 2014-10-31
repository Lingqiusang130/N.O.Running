/**
 * 
 */
package com.fjnu.domain;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * @author 林秋
 * 
 */
@Entity
@Table(name = "t_friend")
public class Friend {
	private int id;
	private String myEmail;// 我的邮箱
	private String anotherEmail;// 好友邮箱
	private User user;// 我(为了做双向关联使用)

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMyEmail() {
		return myEmail;
	}

	public void setMyEmail(String myEmail) {
		this.myEmail = myEmail;
	}

	public String getAnotherEmail() {
		return anotherEmail;
	}

	public void setAnotherEmail(String anotherEmail) {
		this.anotherEmail = anotherEmail;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	// 不能使用lazy加载方式, fetch = FetchType.EAGER
	@JoinColumn(name = "userFriendkey")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
