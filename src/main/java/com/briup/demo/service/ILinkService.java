package com.briup.demo.service;

import java.util.List;

import com.briup.demo.bean.Link;
import com.briup.demo.utils.CustomerException;

/**
 * 关于链接的相关操作
 * @author Administrator
 *
 */
public interface ILinkService {
	/**
	 * 保存或修改链接信息
	 * @param link
	 */
	void saveOrUpdateLink(Link link) throws CustomerException;
	/**
	 * 查询所有链接信息
	 * @param link
	 * @throws CustomerException
	 */
	List<Link> findAllLinks() throws CustomerException;
	/**
	 * 根据id删除链接
	 * @param link
	 * @throws CustomerException
	 */
	void deleteLinkById(int id) throws CustomerException;
	/**
	 * 根据链接名查询链接
	 */
	List<Link> findLinksByName(String name) throws CustomerException;
}
