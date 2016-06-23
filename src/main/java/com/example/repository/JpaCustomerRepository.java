package com.example.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.domain.JpaCustomer;

public interface JpaCustomerRepository extends JpaRepository<JpaCustomer, Integer>{

	@Query(value="SELECT x FROM JpaCustomer x ORDER BY x.firstName", nativeQuery=false)
	Page<JpaCustomer> findAllOrderByName(Pageable pageable);
}
