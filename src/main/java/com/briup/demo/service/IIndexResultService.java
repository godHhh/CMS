package com.briup.demo.service;

import com.briup.demo.bean.ex.IndexResult;
import com.briup.demo.utils.CustomerException;

/**
 * 首页数据管理的Service层接口
 * @author lyh
 *
 */
public interface IIndexResultService {
	/**
	 * 查询首页需要的所有数据
	 * @return
	 * @throws CustomerException
	 */
	IndexResult findIndexAllResult() throws CustomerException;
}
