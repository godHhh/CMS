package com.briup.demo.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Customer;
import com.briup.demo.bean.CustomerExample;
import com.briup.demo.mapper.CustomerMapper;
import com.briup.demo.service.ICustomerService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;

/**
 * 操作用户的service功能类
 * @author lyh
 *
 */
@Service
public class CustomerServiceImpl implements ICustomerService{
	@Autowired
	private CustomerMapper customerMapper;
	@Override
	public void saveOrUpdateCustomer(Customer customer) throws CustomerException {
		//参数为引用类型，要做判空处理
		if(customer==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE,"参数为空！" );
		}
		//判断link对象的id是否为空，如果为空则新增链接，如果不为空则修改链接
		if(customer.getId()==null) {
			customerMapper.insert(customer);
		}else {
			customerMapper.updateByPrimaryKeySelective(customer);
		}
	}

	@Override
	public List<Customer> findAllCustomers() throws CustomerException {
		return customerMapper.selectByExample(new CustomerExample());
	}

	@Override
	public void deleteCustomerById(int id) throws CustomerException {
		customerMapper.deleteByPrimaryKey(id);
	}
	
}
