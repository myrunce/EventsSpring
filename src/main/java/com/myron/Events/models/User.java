package com.myron.Events.models;


import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import javax.persistence.JoinColumn;

@Entity
@Table(name="users")
public class User {
    
    @Id
    @GeneratedValue
    private Long id;
    @Email
    private String username;
    @Size(min=1)
    private String firstName;
    @Size(min=1)
    private String lastName;
    @Size(min=10)
    private String password;
    @Size(min=2, max=255)
	private String city;
	
	private String state;
	
    @Transient
    private String passwordConfirmation;
    private Date createdAt;
    private Date updatedAt;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "role_id"))
    private List<Role> roles;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "people_attending", 
        joinColumns = @JoinColumn(name = "user_id"), 
        inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private List<Event> eventsAttending;
    
    @OneToMany(mappedBy="host", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Event> eventsHosting;
    
    @OneToMany(mappedBy="op", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	private List<Message> messages;
    
    public User() {
    }
    
    public User(String username, String firstName, String lastName, String city, String state, String password) {
		this.username = username;
		this.firstName = firstName;
		this.lastName = lastName;
		this.city = city;
		this.state = state;
		this.password = password;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPasswordConfirmation(String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    
    
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@PrePersist
    protected void onCreate(){
    this.setCreatedAt(new Date());
    }

    @PreUpdate
    protected void onUpdate(){
    this.setUpdatedAt(new Date());
    }


	public List<Event> getEventsAttending() {
		return eventsAttending;
	}

	public void setEventsAttending(List<Event> eventsAttending) {
		this.eventsAttending = eventsAttending;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public List<Event> getEventsHosting() {
		return eventsHosting;
	}

	public void setEventsHosting(List<Event> eventsHosting) {
		this.eventsHosting = eventsHosting;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}
      
}
