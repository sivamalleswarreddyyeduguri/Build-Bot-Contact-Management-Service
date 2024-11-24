package com.buildbot.contactsmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.buildbot.contactsmanagement.entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> 
{
	

}
