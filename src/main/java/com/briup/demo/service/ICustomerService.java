package com.briup.demo.service;

import java.util.List;

import com.briup.demo.bean.Customer;
import com.briup.demo.utils.CustomerException;

public interface ICustomerService {
	/**
	 * 保存或修改用户信息
	 * @param customer
	 */
	void saveOrUpdateCustomer(Customer customer) throws CustomerException;
	/**
	 * 查询所有用户信息
	 * @throws CustomerException
	 */
	List<Customer> findAllCustomers() throws CustomerException;
	/**
	 * 根据id删除用户
	 * @param id
	 * @throws CustomerException
	 */
	void deleteCustomerById(int id) throws CustomerException;
}
