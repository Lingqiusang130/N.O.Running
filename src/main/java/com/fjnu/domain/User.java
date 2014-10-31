package com.fjnu.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_user")
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	private String email;
	private String password;
	private String nickName;
	private String sex;
	private double height;
	private double weight;
	private String birthday;
	private String description;
	private byte[] protrait;
	private List<OneSport> oneSports = new ArrayList<OneSport>();
	private List<Model> models = new ArrayList<Model>();
	private List<Friend> friends = new ArrayList<Friend>();

	@Id
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	@Lob
	@Basic(fetch = FetchType.LAZY)
	@Column(name = "protrait", columnDefinition = "BLOB", nullable = true)
	public byte[] getProtrait() {
		return protrait;
	}

	public void setProtrait(byte[] protrait) {
		this.protrait = protrait;
	}

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
	public List<OneSport> getOneSports() {
		return oneSports;
	}

	public void setOneSports(List<OneSport> oneSports) {
		this.oneSports = oneSports;
	}

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
	public List<Model> getModels() {
		return models;
	}

	public void setModels(List<Model> models) {
		this.models = models;
	}

	@OneToMany(mappedBy = "user", cascade = { CascadeType.ALL })
	public List<Friend> getFriends() {
		return friends;
	}

	public void setFriends(List<Friend> friends) {
		this.friends = friends;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
