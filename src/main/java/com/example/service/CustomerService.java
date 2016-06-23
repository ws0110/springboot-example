package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.JpaCustomer;
import com.example.domain.User;
import com.example.repository.JpaCustomerRepository;

@Service
@Transactional
public class CustomerService {

	@Autowired
	JpaCustomerRepository jpaCustomerRepository;
	
	
	public List<JpaCustomer> findAll() {
		return jpaCustomerRepository.findAll();
	}
	
	public Page<JpaCustomer> findAll(Pageable pageable){
		return jpaCustomerRepository.findAll(pageable);
	}
	
	public JpaCustomer findOne(Integer id){
		return jpaCustomerRepository.findOne(id);
	}
	
	public JpaCustomer create(JpaCustomer customer, User user){
		customer.setUser(user);
		return jpaCustomerRepository.save(customer);
	}
	
	public JpaCustomer update(JpaCustomer customer, User user){
		customer.setUser(user);
		return jpaCustomerRepository.save(customer);
	}
	
	public void delete(Integer id){
		jpaCustomerRepository.delete(id);
	}
}
