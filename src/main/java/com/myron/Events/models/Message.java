package com.myron.Events.models;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.myron.Events.models.Event;
import com.myron.Events.models.User;

@Entity
@Table(name="messages")
public class Message {
	@Id
	@GeneratedValue
	private Long id;
	
	@Size(min=2, max=255, message="Message must be greater than 2 characters!")
	private String message;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="op_id")
	private User op;
	
	@ManyToOne(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
	@JoinColumn(name="event_id")
	private Event event;
	
	@DateTimeFormat(pattern = "MM/dd/yyy HH:mm:ss")
	private Date createdAt;
	
	@DateTimeFormat(pattern = "MM/dd/yyy HH:mm:ss")
	private Date updatedAt;
	
	public Message() {
		
	}
	
	public Message(String message) {
		this.message = message;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public User getOp() {
		return op;
	}

	public void setOp(User op) {
		this.op = op;
	}

	public Event getEvent() {
		return event;
	}

	public void setEvent(Event event) {
		this.event = event;
	}
	
	@PrePersist
    protected void onCreate(){
		this.setCreatedAt(new Date());
    }

    @PreUpdate
    protected void onUpdate(){
    		this.setCreatedAt(createdAt);
    		this.setUpdatedAt(new Date());
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
	
}
