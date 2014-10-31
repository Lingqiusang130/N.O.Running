package com.fjnu.domain;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_model")
public class Model {

	private int id;
	private Date startTime;
	private Date endTime;
	private Parameter parameter;
	private PidPara pidPara;
	private User user;
	private List<Scheme> schemes = new ArrayList<Scheme>();

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "parameterId")
	public Parameter getParameter() {
		return parameter;
	}

	public void setParameter(Parameter parameter) {
		this.parameter = parameter;
	}

	@OneToMany(cascade = { CascadeType.ALL })
	@JoinColumn(name = "modelSchemeId")
	public List<Scheme> getSchemes() {
		return schemes;
	}

	public void setSchemes(List<Scheme> schemes) {
		this.schemes = schemes;
	}

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE,
			CascadeType.REFRESH })
	// 不能使用lazy加载方式, fetch = FetchType.EAGER
	@JoinColumn(name = "userModelkey")
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@OneToOne(cascade = { CascadeType.ALL })
	@JoinColumn(name = "pidParaId")
	public PidPara getPidPara() {
		return pidPara;
	}

	public void setPidPara(PidPara pidPara) {
		this.pidPara = pidPara;
	}

}
