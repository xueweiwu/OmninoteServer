package net.rangesoftware.mengw.omninote.model;

import edu.cmu.xueweiw.omninote.db.Column;
import edu.cmu.xueweiw.omninote.db.Column.DataType;

/**
 * Created by Xuewei Wu on 4/11/16.
 */
public class User extends Model {

	@Column(name = "username", type = DataType.TEXT)
	private String userName;

	@Column(name = "email", type = DataType.TEXT)
	private String email;

	@Column(name = "password", type = DataType.TEXT)
	private String password;

	public User(int id, String userName, String email) {
		this.id = id;
		this.userName = userName;
		this.email = email;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

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

}
