package com.tianshouzhi.dragon.common.domain;

import java.util.Date;

/**
 * Created by TIANSHOUZHI336 on 2017/3/28.
 */
public class User {
	private Integer id;

	private String name = "tianshouzhi";

	private UserAccount userAccount;

	Date date=new Date();

	public User() {
	}

	public User(Integer id, String name) {
		this.id = id;
		this.name = name;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public UserAccount getUserAccount() {
		return userAccount;
	}

	public void setUserAccount(UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@Override
	public String toString() {
		return "User{" + "id=" + id + ", dsName='" + name + '\'' + ", userAccount=" + userAccount + '}';
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
}
