package net.rangesoftware.mengw.omninote.model;

import edu.cmu.xueweiw.omninote.db.Column;
import edu.cmu.xueweiw.omninote.db.Column.DataType;
import edu.cmu.xueweiw.omninote.db.Table;

/**
 * Created by Xuewei Wu on 4/11/16.
 */
@Table(name="user")
public class User extends Model {
	public static final String NAME_COLUMN = "username";
	public static final String EMAIL_COLUMN = "email";
	public static final String PASSWORD_COLUMN = "password";

	@Column(name = NAME_COLUMN, type = DataType.TEXT)
	private String username;

	@Column(name = EMAIL_COLUMN, type = DataType.TEXT)
	private String email;

	@Column(name = PASSWORD_COLUMN, type = DataType.TEXT)
	private String password;
	
	
	public User() {
		
	}

	public User(String userName, String email, String pwd) {
		this.username = userName;
		this.email = email;
		this.password = pwd;
	}

	public Integer getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserName() {
		return username;
	}

	public void setUserName(String userName) {
		this.username = userName;
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
