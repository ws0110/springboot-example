package com.example.api;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.example.domain.JpaCustomer;
import com.example.service.CustomerService;

@RestController
@RequestMapping("/api/customers")
public class CustomerRestController {

	@Autowired
	CustomerService customerService;
	
	@RequestMapping(method = RequestMethod.GET)
	public Page<JpaCustomer> getCustomers(@PageableDefault Pageable pageable){
		
		return customerService.findAll(pageable);
	}
	
	
	@RequestMapping(value = "{id}", method = RequestMethod.GET)
	public JpaCustomer getCustomer(@PathVariable Integer id){
		return customerService.findOne(id);
	}
	
	
	/**
	 * 일반적으로 POST로 작성한 리소스에 접속URL은 HTTP 응답의 Location헤더에 설정 함!! 
	 */
	@RequestMapping(method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)  // 응답 상태
	public ResponseEntity<JpaCustomer> postCustomers(@RequestBody JpaCustomer customer, UriComponentsBuilder uriBuilder){
		
//		JpaCustomer created = customerService.create(customer);
//		
//		URI location = uriBuilder.path("/api/customers/{id}").buildAndExpand(created.getId()).toUri();
//		HttpHeaders headers = new HttpHeaders();
//		headers.setLocation(location);
//		
//		return new ResponseEntity<JpaCustomer>(created, headers, HttpStatus.CREATED);
		return null;
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.PUT)
	public JpaCustomer putCustomers(@PathVariable Integer id, @RequestBody JpaCustomer customer){
		customer.setId(id);
		return null; //customerService.update(customer);
	}
	
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)  // 응답 상태
	public void deleteCustomer(@PathVariable Integer id){
		customerService.delete(id);
	}
}
