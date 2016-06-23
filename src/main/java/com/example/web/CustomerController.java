package com.example.web;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.domain.JpaCustomer;
import com.example.service.CustomerService;
import com.example.service.LoginUserDetails;

@Slf4j
@Controller
@RequestMapping("customers")
public class CustomerController {

	@Autowired
	CustomerService customerService;
	
	/**
	 * 리스트
	 * @param model
	 * @param form
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String list(Model model, @ModelAttribute CustomerForm form){
		
		List<JpaCustomer> list = customerService.findAll();
		model.addAttribute("list", list);
		
		return "customers/list";
	}
	
	/**
	 * 등록
	 * @param form
	 * @param result
	 * @param model
	 * @return
	 */
	@RequestMapping(value="create", method=RequestMethod.POST)
	public String create(@Validated CustomerForm form, BindingResult result, Model model,
			@AuthenticationPrincipal LoginUserDetails userDetails){
		
		log.info(">>>>>>>>>>> " + form);
		
		if(result.hasErrors()){
			return list(model, form);
		}
		
		JpaCustomer customer = new JpaCustomer();
		BeanUtils.copyProperties(form, customer);
		customerService.create(customer, userDetails.getUser());
		
		return "redirect:/customers";
	}
	
	
	@RequestMapping(value="edit", params="form", method=RequestMethod.GET)
	public String editForm(@RequestParam Integer id, @ModelAttribute CustomerForm form){
		
		JpaCustomer customer = customerService.findOne(id);
		BeanUtils.copyProperties(customer, form);
		return "customers/edit";
	}
	
	@RequestMapping(value="edit", params="goToTop", method=RequestMethod.GET)
	public String goToTop(){
		return "redirect:/customers";
	}
	
	/**
	 * 수정 실행
	 * @param id
	 * @param form
	 * @param result
	 * @param userDetails
	 * @return
	 */
	@RequestMapping(value="edit", method=RequestMethod.POST)
	public String edit(@RequestParam Integer id, @Validated CustomerForm form, BindingResult result,
			@AuthenticationPrincipal LoginUserDetails userDetails){
		
		log.info(">>>>>>>>>>> " + form);
		
		if(result.hasErrors()){
			return editForm(id, form);
		}
		
		JpaCustomer customer = new JpaCustomer();
		BeanUtils.copyProperties(form, customer);
		customer.setId(id);
		customerService.update(customer, userDetails.getUser());
		
		return "redirect:/customers";
	}
	
	
	/**
	 *삭제 실행
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.POST)
	public String delete(@RequestParam Integer id){
		
		customerService.delete(id);
		return "redirect:/customers";
	}
}
