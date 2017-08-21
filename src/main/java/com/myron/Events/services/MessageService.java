package com.myron.Events.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myron.Events.models.Message;
import com.myron.Events.repositories.MessageRepository;

@Service
public class MessageService {
	private MessageRepository messageRepository;
	
	public MessageService(MessageRepository messageRepository) {
		this.messageRepository = messageRepository;
	}
	
	public void addMessage(Message message) {
		messageRepository.save(message);
	}
	
	public List<Message> findAllByEvent(Long id){
		return messageRepository.findByEventId(id);
	}
}
