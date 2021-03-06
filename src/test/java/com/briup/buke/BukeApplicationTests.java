package com.briup.buke;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.briup.buke.bean.Article;
import com.briup.buke.bean.Category;
import com.briup.buke.bean.Chapter;
import com.briup.buke.dao.ArticleDao;
import com.briup.buke.dao.CategoryDao;
import com.briup.buke.dao.ChapterDao;
import com.briup.buke.service.ICategoryService;
import com.briup.buke.service.IChapterService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BukeApplicationTests {
	@Autowired
	private ArticleDao articleDao;
	@Autowired
	private ICategoryService categoryService;
	@Autowired
	private CategoryDao categoryDao;
	@Autowired
	private ChapterDao chapterDao;
	@Autowired
	private IChapterService chapterService;
	@Test
	public void test() throws Exception {
		System.out.println("---------------------------");
		Long categoryId = articleService.findCategoryIdByArticleId(chapter.getArticleId());
		//List<Article> articles = category.getArticles();
		System.out.println(chapter);
		//System.out.println(articles);
		System.out.println("---------------------------");
	}
	@Test
	public void contextLoads() {
		
		
	}

}
