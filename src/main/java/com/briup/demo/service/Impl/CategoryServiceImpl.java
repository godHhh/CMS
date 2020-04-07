package com.briup.demo.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.ArticleExample;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.CategoryExample;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.mapper.ArticleMapper;
import com.briup.demo.mapper.CategoryMapper;
import com.briup.demo.mapper.ex.CategoryExMapper;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;
/**
 * 操作栏目的service功能类
 * @author lyh
 *
 */
@Service
public class CategoryServiceImpl implements ICategoryService{
	@Autowired
	private CategoryMapper categoryMapper;
	@Autowired
	private ArticleMapper articleMapper;
	//栏目的拓展dao
	@Autowired
	private CategoryExMapper categoryExMapper;
	@Override
	public List<Category> findAllCategories() throws CustomerException {
		
		return categoryMapper.selectByExample(new CategoryExample());
	}

	@Override
	public void saveOrUpdateCategory(Category category) throws CustomerException {
		if(category==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE,"参数为空！");
		}
		//判断category对象的id值是否为空
		if(category.getId()==null) {
			//是，则添加栏目信息
			//不能添加重复的栏目
			CategoryExample example = new CategoryExample();
			example.createCriteria().andNameEqualTo(category.getName());
			List<Category> list = categoryMapper.selectByExample(example);
			CategoryExample example2 = new CategoryExample();
			example2.createCriteria().andCodeEqualTo(category.getCode());
			List<Category> list2 = categoryMapper.selectByExample(example2);
			if(list.size()>0||list2.size()>0) {
				throw new CustomerException(StatusCodeUtil.NOTALLOWED_CODE, "已有存在的栏目编号或栏目名称");
			}else {
			categoryMapper.insert(category);
			}
		}else {
			//否，则修改栏目信息
			categoryMapper.updateByPrimaryKey(category);
		}
	}

	@Override
	public void deleteCategoryById(int id) throws CustomerException {
		//删除栏目的同时，需要先删除栏目中包含的文章信息
		ArticleExample example = new ArticleExample();
		example.createCriteria().andCategoryIdEqualTo(id);
		articleMapper.deleteByExample(example);
		categoryMapper.deleteByPrimaryKey(id);
	}

	@Override
	public Category findCategoryById(int id) throws CustomerException {
		return categoryMapper.selectByPrimaryKey(id);
	}

	@Override
	public List<CategoryEx> findAllCategoryEx() throws CustomerException {
		
		return categoryExMapper.findAllCategoryExs();
	}

	@Override
	public CategoryEx findCategoryExByName(String name) throws CustomerException {
		
		return categoryExMapper.findCategoryExByName(name);
	}

}
