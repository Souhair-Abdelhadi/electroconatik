package com.electronicshop.entities;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(
		name = "users"
		)
public class User implements UserDetails {
	
	private static final long serialVersionUID = 2361813820503262419L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotNull
	private String name;
	
	private String slug;
	
	@NotNull
	@Email
	private String email;
	
	@NotNull
	private String phone ; 
	
	@NotNull
	private String password;
	
	private String passwordChangedAt;
	
	private String passwordResetCode;
	
	private Date passwordResetExpires;
	
	private boolean resetCodeVerified;
	
	@Column(columnDefinition = "integer default 1")
	private boolean activated;
	
	private Date createdAt;
	
	private Date updateAt;
	
	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private Set<Order> orders = new HashSet<>();
	
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(
			name = "user_role",
			joinColumns = @JoinColumn(name = "user_id"),
			inverseJoinColumns = @JoinColumn(name = "role_id")
			)
	private Set<Role> roles = new HashSet<>();
	
	public void addRole(Role role) {
		roles.add(role);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
			authorities.add(new SimpleGrantedAuthority(role.getName()));
		}
    
        return authorities;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.name;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.activated;
	}
	
	

}
