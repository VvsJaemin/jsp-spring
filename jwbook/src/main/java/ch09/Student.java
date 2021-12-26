package ch09;

import java.sql.Date;

public class Student {
	private int id;
	private String username;
	private String univ;
	private Date bitrh;
	private String email;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUniv() {
		return univ;
	}
	public void setUniv(String univ) {
		this.univ = univ;
	}
	public Date getBitrh() {
		return bitrh;
	}
	public void setBitrh(Date bitrh) {
		this.bitrh = bitrh;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
