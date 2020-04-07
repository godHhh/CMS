package com.briup.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.demo.bean.Customer;
import com.briup.demo.service.ICustomerService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.Message;
import com.briup.demo.utils.MessageUtil;
import com.briup.demo.utils.StatusCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 与用户相关的和前端交互的web层
 * @author lyh
 *
 */
@RestController
@Api(description = "用户相关接口")
public class CustomerController{
	@Autowired
	private ICustomerService customerService;
	@PostMapping("/addCustomer")
	@ApiOperation("新增用户")
	public Message<String> addCustomer(Customer customer){
		try {
			customerService.saveOrUpdateCustomer(customer);
			return MessageUtil.success();
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE, "系统错误："+e.getMessage());
		}
	}
	@PostMapping("/updateCustomer")
	@ApiOperation("修改用户信息")
	public Message<String> updateCustomer(Customer customer){
		customerService.saveOrUpdateCustomer(customer);
		return MessageUtil.success();
	}
	@GetMapping("/selectCustomer")
	@ApiOperation("查询所有用户")
	public Message<List<Customer>> selectCustomer(){
		List<Customer> list = customerService.findAllCustomers();
		return MessageUtil.success(list);
	}
	@GetMapping("/deleteCustomerById")
	@ApiOperation("根据id删除用户")
	public Message<String> deleteCustomerById(int id){
		customerService.deleteCustomerById(id);
		return MessageUtil.success();
	}
}
