package com.briup.buke.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.briup.buke.bean.Article;
import com.briup.buke.bean.Category;
import com.briup.buke.bean.Chapter;
import com.briup.buke.bean.ChapterPack;
import com.briup.buke.service.IArticleService;
import com.briup.buke.service.ICategoryService;
import com.briup.buke.service.IChapterService;
import com.briup.buke.utils.Message;
import com.briup.buke.utils.MessageUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(description="章节相关接口")
public class ChapterController {
	@Autowired
	private IChapterService chapterService;
	@Autowired
	private IArticleService articleService;
	@Autowired
	private ICategoryService categoryService;
	
	@ResponseBody
	@RequestMapping(value="/background/chapter/saveOrUpdate",method=RequestMethod.POST)
	@ApiOperation("更新或插入章节")
	public String saveOrUpdate(Chapter chapter){
		try {
			chapterService.saveOrUpdate(chapter);
			return "更新成功！";
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return  e.getMessage();
		}
	}
	@RequestMapping("/background/chapter/findAll")
	public String findAll(HttpServletRequest request) {
		List<ChapterPack> cpList = new ArrayList<>();
		List<Chapter> chapterList = chapterService.findAll();
		for(Chapter chapter:chapterList) {
			String articleTitle = articleService.findArticleNameByArticleId(chapter.getArticleId());
			ChapterPack cp = new ChapterPack(chapter.getId(), chapter.getSubtitle(), chapter.getContent(), chapter.getArticleId(), articleTitle);
			cpList.add(cp);
		}
		List<Article> articleList = articleService.findAll();
		request.setAttribute("articleList",articleList);
		request.setAttribute("chapterList", cpList);
		return "background/chapter";
	}
	
	@RequestMapping("/background/chapter/findById/{id}")
	@ResponseBody
	public ChapterPack findById(@PathVariable Long id) {
		try {
			Chapter chapter = chapterService.findById(id);
			String articleTitle = articleService.findArticleNameByArticleId(chapter.getArticleId());
			ChapterPack cp = new ChapterPack(chapter.getId(), chapter.getSubtitle(), chapter.getContent(), chapter.getArticleId(), articleTitle);
			return cp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	
	@RequestMapping("/test/toChapter/{id}")
	@ResponseBody
	public ChapterPack test(@PathVariable Long id) {
		try {
			Chapter chapter = chapterService.findById(id);
			String articleTitle = articleService.findArticleNameByArticleId(chapter.getArticleId());
			ChapterPack cp = new ChapterPack(chapter.getId(), chapter.getSubtitle(), chapter.getContent(), chapter.getArticleId(), articleTitle);
			return cp;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return null;
		}
		
	}
	@RequestMapping("/foreground/toChapter/{id}")
	@ApiOperation("根据id查找章节")
	public String toChapter(@PathVariable Long id,HttpServletRequest request){
		try {
			Chapter chapter = chapterService.findById(id);
			String articleName = articleService.findArticleNameByArticleId(chapter.getArticleId());
			System.out.println("articleId:"+chapter.getArticleId());
			Long categoryId = articleService.findCategoryIdByArticleId(chapter.getArticleId());
			//id大时出错
			System.out.println("categoryId:"+categoryId);
			System.out.println("id大时出错");
			Category category = categoryService.findById(categoryId);
			System.out.println("id大时没有出错");
			//传递上一章下一章id
			Long nextId=++id;
			Long preId=--id;
			request.setAttribute("nextId",nextId);
			request.setAttribute("preId",preId);
			//传递栏目名栏目id
			request.setAttribute("categoryName",category.getName());
			request.setAttribute("categoryId",categoryId);
			System.out.println("------------");
			//传递文章名文章id
			request.setAttribute("articleName", articleName);
			request.setAttribute("articleId", chapter.getArticleId());
			//传递章节id
			request.setAttribute("chapterId", chapter.getId());
			request.setAttribute("chapter", chapter);
			System.out.println("即将返回页面");
			return "foreground/chapter";
		} catch (Exception e) {
			System.out.println("Error!!!!!!!!");
			return null;
		}
	}
	
	@RequestMapping("/background/chapter/deleteById/{id}")
	@ApiOperation("根据id删除章节")
	@ResponseBody
	public String deleteById(@PathVariable Long id){
		try {
			chapterService.deleteById(id);
			return "删除成功！";
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	@ApiOperation("根据文章ID查询文章所有章节")
	@RequestMapping("/background/chapter/findByArticleId/{articleId}")
	public String findByArticleId(@PathVariable Long articleId,HttpServletRequest request){
		List<ChapterPack> cpList = new ArrayList<>();
		List<Chapter> chapterList = chapterService.findByArticleId(articleId);
		for(Chapter chapter:chapterList) {
			String articleTitle = articleService.findArticleNameByArticleId(chapter.getArticleId());
			ChapterPack cp = new ChapterPack(chapter.getId(), chapter.getSubtitle(), chapter.getContent(), chapter.getArticleId(), articleTitle);
			cpList.add(cp);
		}
		List<Article> articleList = articleService.findAll();
		request.setAttribute("articleList",articleList);
		request.setAttribute("chapterList", cpList);
		return "background/chapter";
	}
}
