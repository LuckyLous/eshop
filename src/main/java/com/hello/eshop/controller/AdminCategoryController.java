package com.hello.eshop.controller;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.Category;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.service.CategoryService;
import com.hello.eshop.utils.ListUtils;
import com.hello.eshop.utils.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 后台分类Controller
 * @author Hello
 *
 */
@Controller
@RequestMapping("/admin/category")
public class AdminCategoryController {
	
	@Autowired
	private CategoryService categoryService;


	@RequestMapping("/checkName")
	@ResponseBody
	public Boolean checkName(String name){
		Category cate = categoryService.checkName(name);
		if(cate != null){
			return false;//重复了，返回false
		}else{
			return true;//不重复，返回true
		}
	}


	/**
	 * ajax根据一级分类的id查询其子类
	 * 后台商品管理才会调用这里
	 */
	@RequestMapping("/list/by/{cid}")
	@ResponseBody
	public Result listByCid(@PathVariable("cid") Integer cid){
		// 拿到所有分类的List集合
		List<Category> list = categoryService.listByCid(cid);
		if(ListUtils.isNotEmpty(list)){
			return  Result.success().add("cList", list);
		}else{
			return Result.fail();
		}
	}
	
	/**
	 * 删除二级分类
	 * @param id
	 * @return
	 */
	@RequestMapping("/small/delete/{id}")
	public String deleteSmall(@PathVariable("id") Integer id,Model model,
							  @RequestParam(value="page",required=true,defaultValue="1") Integer page) {
		categoryService.deleteSmall(id);
		model.addAttribute("msg","删除成功！");
		model.addAttribute("newUrl","category/small/list?page="+page);
		return "forward:/admin/common/msg.jsp";
		
	}
	
	/**
	 * 更新二级分类
	 * @param category
	 * @return
	 */
	@RequestMapping("/small/update")
	public String updateSmall(Category category,Model model,
							  @RequestParam(value="page",required=true,defaultValue="1") Integer page){
		categoryService.updateSmall(category);
		model.addAttribute("msg","修改成功！");
		model.addAttribute("newUrl","category/small/list?page="+page);
		return "forward:/admin/common/msg.jsp";
	}
	
	/**
	 * 跳转到修改二级分类的页面
	 * 修改一下
	 * @param model
	 * @return
	 */
	@RequestMapping("/small/update/{id}")
	public String updateSmall(@PathVariable("id") Integer id,Model model){
		// 根据二级分类id查询信息
		Category cate = categoryService.getById(id);
		model.addAttribute("cate", cate);
		// 查询所有一级分类
		List<Category> list = categoryService.listByCid(Constant.CATEGORY_BOOK_PID);
		model.addAttribute("list", list);
		// 把数据显示到页面的下拉列表中
		return "forward:/admin/small-category/update.jsp";
	}
	
	/**
	 * 保存二级分类，然后重定向
	 * tb_category中is_parent默认为0，叶子节点
	 * @param category
	 * @return
	 */
	@RequestMapping("/small/save")
	public String saveSmall(Category category,Model model){
		categoryService.saveSmall(category);
		model.addAttribute("msg","保存成功！");
		model.addAttribute("newUrl","category/small/list?page="+Integer.MAX_VALUE);
		return "forward:/admin/common/msg.jsp";
	}
	
	/**
	 * 跳转到添加二级分类的页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/small/add")
	public String saveSmall(Model model){
		// 查询所有一级分类
		List<Category> list = categoryService.listByCid(Constant.CATEGORY_BOOK_PID);
		// 把数据显示到页面的下拉列表中
		model.addAttribute("list", list);
		return "forward:/admin/small-category/add.jsp";
	}

	/**
	 * 查询二级分类列表,分页显示
	 */
	@RequestMapping("/small/list")
	public String list(Model model,@RequestParam(value="parentId",required=false) Integer pid,
		 @RequestParam(value="page",required=true,defaultValue="1")Integer page){
		
		PageInfo<Category> pageInfo = categoryService.list(pid,page,Constant.CATEGORY_SHOW_PAGE);
		model.addAttribute("pageInfo", pageInfo);

		List<Category> list = categoryService.listByCid(Constant.CATEGORY_BOOK_PID); // 查询一级分类
		model.addAttribute("pList",list);
		return "forward:/admin/small-category/list.jsp";
		
	}
	
	// ====	一级分类	=======
	
	/**
	 * 删除一级分类，结束后重定向到findAll
	 */
	@RequestMapping("/delete/{id}")
	public String delete(@PathVariable("id") Integer id){
		categoryService.delete(id);
		return "redirect:/admin/category/list";
	}
	
	
	/**
	 * 更新一级分类，调用service，将封装的cate信息更新到数据库
	 * 然后重定向到findAll
	 */
	@RequestMapping("/update")
	public String update(Category category){
		categoryService.update(category);
		return "redirect:/admin/category/list";
	}
	
	/**
	 * 跳转到一级分类的修改页面，先查询一次，然后存储到model，转发到页面显示
	 */
	@RequestMapping("/update/{id}")
	public String update(@PathVariable("id") Integer id,Model model){
		Category category = categoryService.getById(id);
		model.addAttribute("cate", category);
		return "forward:/admin/category/update.jsp";
	}
	
	/**
	 * 保存一级分类的方法，添加成功后重定向到findAll(即跳转到list.jsp，但是要查询一次)
	 * @param category
	 * @return
	 */
	@RequestMapping("/save")
	public String save(Category category){
		categoryService.save(category);
		return "redirect:/admin/category/list";
		
	}
	
	/**
	 * 查询所有一级分类，转发到list页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model){
		List<Category> list = categoryService.listByCid(Constant.CATEGORY_BOOK_PID);
		model.addAttribute("list", list);
		return "forward:/admin/category/list.jsp";
		
	}
}
