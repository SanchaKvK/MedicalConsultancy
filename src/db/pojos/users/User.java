package db.pojos.users;

import java.util.Arrays;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "role_name")
@Table(name = "users")

public abstract class User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3902983480527656994L;
	@Id
	@GeneratedValue(generator="users")
	@TableGenerator(name="users", table="sqlite_sequence",
	    pkColumnName="name", valueColumnName="seq", pkColumnValue="users")
	private Integer id;
	private String email;
	@Lob
	private byte[] password;
	private String role_name;

	public User() {
		super();
	}

	public User(Integer id) {
		super();
		this.id = id;
	}
	


	public User(String email, byte[] password, String role_name) {
		super();
		this.email = email;
		this.password = password;
		this.role_name = role_name;
	}

	public User(Integer id, String email, byte[] password, String role_name) {
		super();
		this.id = id;
		this.email = email;
		this.password = password;
		this.role_name = role_name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public byte[] getPassword() {
		return password;
	}

	public void setPassword(byte[] password) {
		this.password = password;
	}
	
	
	

	public String getRole_name() {
		return role_name;
	}

	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + Arrays.toString(password) + "]";
	}

}
