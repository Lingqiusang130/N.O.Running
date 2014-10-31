package com.fjnu.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_onesport")
public class OneSport {

	private int id;
	private String date;
	private Date startTime;
	private Date endTime;
	private Integer count;
	private User user;
	private List<MinuteSportData> minuteSportDatas = new ArrayList<MinuteSportData>();

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	// 不能使用lazy加载方式, fetch = FetchType.EAGER
	@JoinColumn(name = "userOneSportKey")
	// 参数在前为参照方
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "oneSportId")
	public List<MinuteSportData> getMinuteSportData() {
		return minuteSportDatas;
	}

	public void setMinuteSportData(List<MinuteSportData> minuteSportDatas) {
		this.minuteSportDatas = minuteSportDatas;
	}

}
