package com.myron.Events.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myron.Events.models.Event;


@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
	List<Event> findByState(String state);
	
	@Query(value = "SELECT * FROM events WHERE state not in (?1)", nativeQuery = true)
	List<Event> findNotInState(String state);
}
