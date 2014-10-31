package com.fjnu.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Table;

@Entity
@Table(name = "t_scheme")
public class Scheme {

	private int id;
	private int minute;
	private double bestSpeed;

	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMinute() {
		return minute;
	}

	public void setMinute(int minute) {
		this.minute = minute;
	}

	public double getBestSpeed() {
		return bestSpeed;
	}

	public void setBestSpeed(double bestSpeed) {
		this.bestSpeed = bestSpeed;
	}

}
