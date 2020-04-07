package com.briup.demo.service.Impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.briup.demo.bean.Article;
import com.briup.demo.bean.ArticleExample;
import com.briup.demo.bean.Category;
import com.briup.demo.bean.CategoryExample;
import com.briup.demo.mapper.ArticleMapper;
import com.briup.demo.mapper.CategoryMapper;
import com.briup.demo.service.IArticleService;
import com.briup.demo.utils.CustomerException;
import com.briup.demo.utils.StatusCodeUtil;
/**
 * 实现文章管理相关的逻辑类
 * @author lyh
 *
 */
@Service
public class ArticleServiceImpl implements IArticleService {
	@Autowired
	private ArticleMapper articleMapper;
	@Autowired
	private CategoryMapper categoryMapper;
	@Override
	public void saveOrUpdateArticle(Article article) throws CustomerException {
		if(article==null) {
			throw new CustomerException(StatusCodeUtil.ERROR_CODE, "参数为空");
		}
		if(article.getId()==null) {
			//需要额外增加两条数据
			article.setPublishdate(new Date());
			article.setClicktimes(0);
			articleMapper.insert(article);
		}else {
			//article.setPublishdate(new Date());
			articleMapper.updateByPrimaryKey(article);
		}
	}

	@Override
	public void deleteArticleById(int id) throws CustomerException {
		articleMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<Article> findArticleByCondition(String keyStr, String condition) throws CustomerException {
		/**
		 * 分三种情况:
		 * 1.没有添加任何条件，则查询所有文章；
		 * 2.没有指定栏目，但指定了查询的关键字，则根据关键字查询满足条件的所有文章；
		 * 3.指定栏目，没有指定了查询的关键字，则根据栏目查询其中的所有文章；
		 * 4.指定栏目，同时指定查询的关键字，则根据栏目和关键字查询满足条件的文章。
		 */
		keyStr=keyStr==null?"":keyStr.trim();
		condition=condition==null?"":condition.trim();
		ArticleExample example = new ArticleExample();
		if("".equals(keyStr)&&"".equals(condition)) {
			//情况1
			return articleMapper.selectByExample(example);
		}else if(!"".equals(keyStr)&&"".equals(condition)) {
			//情况2
			example.createCriteria().andTitleLike("%"+keyStr+"%");
			return articleMapper.selectByExample(example);
		}else if(!"".equals(condition)&&"".equals(keyStr)) {
			//情况3
			 CategoryExample categoryExample = new CategoryExample();
			 categoryExample.createCriteria().andNameEqualTo(condition);
			 List<Category> categories = categoryMapper.selectByExample(categoryExample);
			 if(categories.size()>0) {
				 //根据栏目信息找到里面所有的文章
				 example.createCriteria().andCategoryIdEqualTo(categories.get(0).getId()); 
			 }else {
				 throw new CustomerException(StatusCodeUtil.ERROR_CODE, "没有指定的搜索栏目");
			 }
			return articleMapper.selectByExample(example);
		}else{
			//情况4 
			CategoryExample categoryExample = new CategoryExample();
			categoryExample.createCriteria().andNameEqualTo(condition);
			List<Category> categories = categoryMapper.selectByExample(categoryExample);
			//and的方式拼接sql语句
			example.createCriteria().andCategoryIdEqualTo(categories.get(0).getId()).andTitleLike("%"+keyStr+"%");
			//or的方式，拼接条件
			//example.or().andTitleLike("%"+keyStr+"%");
			return articleMapper.selectByExample(example);
		}
	}

	@Override
	public Article findArticleById(int id) throws CustomerException {
		return articleMapper.selectByPrimaryKey(id);
	}

	@Override
	public Article findArticleByName(String name) throws CustomerException {
		ArticleExample example = new ArticleExample();
		example.createCriteria().andTitleEqualTo(name);
		List<Article> list = articleMapper.selectByExample(example);
		//添加点击次数
		Integer clickTimes=list.get(0).getClicktimes()==null?0:list.get(0).getClicktimes();
		list.get(0).setClicktimes(++clickTimes);
		this.saveOrUpdateArticle(list.get(0));
		return list.get(0);
	}

}
