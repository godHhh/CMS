package com.briup.demo.mapper.ex;

import java.util.List;

import com.briup.demo.bean.ex.CategoryEx;
/**
 * 处理 查询栏目及其包含的文章信息
 * @author lyh
 *
 */

public interface CategoryExMapper {
	/**
	 * 实现查询所有栏目及其包含的文章信息
	 * @return
	 */
	List<CategoryEx> findAllCategoryExs();
	/**
	 * 实现通过栏目名查询该栏目下的文章信息
	 * @param name
	 * @return
	 */
	CategoryEx findCategoryExByName(String name);
}
