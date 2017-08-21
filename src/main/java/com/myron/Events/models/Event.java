package com.myron.Events.models;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name="events")
public class Event {
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=2, max=255, message="Event name must be greater than 2 characters")
	private String name;
	
	@NotNull(message="Date must not be left empty")
	@Future(message="Date must be a future date!")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date date;
	
	@Size(min=2, max=255, message="City name must be greater than 2 characters")
	private String city;
	
	private String state;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User host;
	
	@OneToMany(mappedBy="event")
	private List<Message> messages;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "people_attending", 
			joinColumns = @JoinColumn(name = "event_id"), 
			inverseJoinColumns = @JoinColumn(name = "user_id")
			)	
	private List<User> peopleGoing;
	
public Event() {}
	
	public Event(String name, Date date, String city, String state) {
		this.name = name;
		this.date = date;
		this.city = city;
		this.state = state;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}


	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public User getHost() {
		return host;
	}

	public void setHost(User host) {
		this.host = host;
	}

	public List<Message> getMessages() {
		return messages;
	}

	public void setMessages(List<Message> messages) {
		this.messages = messages;
	}

	public List<User> getPeopleGoing() {
		return peopleGoing;
	}

	public void setPeopleGoing(List<User> peopleGoing) {
		this.peopleGoing = peopleGoing;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}
		
}
