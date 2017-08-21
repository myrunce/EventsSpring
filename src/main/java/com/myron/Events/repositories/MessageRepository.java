package com.myron.Events.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myron.Events.models.Message;

@Repository
public interface MessageRepository extends CrudRepository<Message, Long>{
	List<Message> findByEventId(Long id);
}
