package com.myron.Events.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.myron.Events.models.Event;
import com.myron.Events.repositories.EventRepository;

@Service
public class EventService {
	private EventRepository eventRepository;
	
	public EventService(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	public void addEvent(Event event) {
		eventRepository.save(event);
	}
	
	public List<Event> allEvents(){
		return (List<Event>) eventRepository.findAll();
	}
	
	public List<Event> eventsInArea(String state){
		return eventRepository.findByState(state);
	}
	
	public List<Event> eventsOutOfArea(String state){
		return eventRepository.findNotInState(state);
	}
	
	public Event findOne(Long id) {
		return eventRepository.findOne(id);
	}
	
	public void updateEvent(Event event) {
		eventRepository.save(event);
	}
	
	public void deleteEvent(Event event) {
		eventRepository.delete(event);
	}
}
