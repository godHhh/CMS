package com.briup.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.briup.demo.bean.Category;
import com.briup.demo.bean.ex.CategoryEx;
import com.briup.demo.service.ICategoryService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.Message;
import com.briup.demo.utils.MessageUtil;
import com.briup.demo.utils.StatusCodeUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 与栏目相关的和前台交互的web层
 * @author lyh
 *
 */
@RestController
@Api(description = "栏目相关接口")
public class CategoryController {
	@Autowired
	private ICategoryService categoryService;
	/**
	 * 查询所有的栏目
	 */
	@GetMapping("/selectCategories")
	@ApiOperation("查询所有的栏目")
	public Message<List<Category>> selectCategories(){
		List<Category> list = categoryService.findAllCategories();
		return MessageUtil.success(list);
	}
	/**
	 * 新增栏目
	 */
	@PostMapping("/addCategory")
	@ApiOperation("新增栏目")
	public Message<String> addCategory(Category category){
		try {
			categoryService.saveOrUpdateCategory(category);
			return MessageUtil.success();
		} catch (CustomerException e) {
			return MessageUtil.error(StatusCodeUtil.ERROR_CODE,"系统错误："+e.getMessage());
		}
	}
	/**
	 * 修改栏目信息
	 */
	@PostMapping("/updateCategory")
	@ApiOperation("修改栏目信息")
	public Message<String> updateCategory(Category category){
			try {
				categoryService.saveOrUpdateCategory(category);
				return MessageUtil.success();
			} catch (CustomerException e) {
				return MessageUtil.error(StatusCodeUtil.ERROR_CODE,"系统错误："+e.getMessage());
			}
	}
	/**
	 * 删除栏目
	 */
	@GetMapping("/deleteCategoryById")
	@ApiOperation("根据栏目id删除栏目")
	public Message<String> deleteCategoryById(int id){
		categoryService.deleteCategoryById(id);
		return MessageUtil.success();
	}
	/**
	 * 根据栏目id查询栏目
	 */
	@GetMapping("/selectCategoryById")
	@ApiOperation("根据栏目id查询指定栏目")
	public Message<Category> selectCategoryById(int id){
		Category category = categoryService.findCategoryById(id);
		return MessageUtil.success(category);
	}
	/**
	 * 通过栏目名查询该栏目下的文章信息
	 */
	@GetMapping("/getCategoryExByName")
	@ApiOperation("根据栏目名查询该栏目下的文章信息")
	public Message<CategoryEx> getCategoryExByName(String name){
		CategoryEx categoryEx = categoryService.findCategoryExByName(name);
		return MessageUtil.success(categoryEx);
	}
}
