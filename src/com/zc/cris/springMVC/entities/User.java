package com.zc.cris.springMVC.entities;


public class User {
	
	private Integer id;
	private String name;
	private String password;
	private String age;
	private Address address;
	
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public Address getAddress() {
		return address;
	}
	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", password=" + password + ", age=" + age + ", address=" + address
				+ "]";
	}
	public User() {
		super();
		
	}
	public User(Integer id, String name, String password, String age) {
		super();
		this.id = id;
		this.name = name;
		this.password = password;
		this.age = age;
	}
	public User(String name, String password, String age) {
		super();
		this.name = name;
		this.password = password;
		this.age = age;
	}
	
}
