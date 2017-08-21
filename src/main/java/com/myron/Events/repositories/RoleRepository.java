package com.myron.Events.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.myron.Events.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	List<Role> findAll();

	List<Role> findByName(String name);
}