package com.hello.eshop.controller;

import com.github.pagehelper.PageInfo;
import com.hello.eshop.bean.Category;
import com.hello.eshop.bean.Product;
import com.hello.eshop.bean.SearchProduct;
import com.hello.eshop.constant.Constant;
import com.hello.eshop.service.CategoryService;
import com.hello.eshop.service.ProductService;
import com.hello.eshop.service.SearchService;
import com.hello.eshop.utils.DateUtils;
import com.hello.eshop.utils.ExcelUtil;
import com.hello.eshop.utils.ResponseUtil;
import com.hello.eshop.utils.UploadUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台商品Controller
 * @author Hello
 *
 */
@Controller
@RequestMapping("/admin/product")
public class AdminProductController {
	
	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;

	@Autowired
	SearchService searchService;


	/**
	 * 导入数据
	 */
	public String importData(@RequestParam("file") MultipartFile file)throws Exception{
		POIFSFileSystem fs=new POIFSFileSystem(file.getInputStream());
		HSSFWorkbook wb=new HSSFWorkbook(fs);
		HSSFSheet hssfSheet=wb.getSheetAt(0);  // 获取第一个Sheet页
		if(hssfSheet!=null){
			for(int rowNum=1;rowNum<=hssfSheet.getLastRowNum();rowNum++){
				HSSFRow hssfRow=hssfSheet.getRow(rowNum);
				if(hssfRow==null){
					continue;
				}
				Product prod = new Product();
				prod.setProdName(ExcelUtil.formatCell(hssfRow.getCell(0)));
				prod.setShopPrice(Double.parseDouble(ExcelUtil.formatCell(hssfRow.getCell(1))));
				prod.setNum(Integer.parseInt(ExcelUtil.formatCell(hssfRow.getCell(2))));
				prod.setAddTime(DateUtils.paraseDate(ExcelUtil.formatCell(hssfRow.getCell(3)),DateUtils.TIME_PATTERN));
				productService.save(prod);
			}
		}

		return null;
	}

	/**
	 * 下载模板
	 */
	//@RequestMapping("/download")
	public void download(HttpServletResponse response) throws Exception{
		Workbook wb = ExcelUtil.fillExcelDataWithTemplate(new ArrayList<Product>(), "ProductTemplate.xls");
		ResponseUtil.export(response, wb, "模板excel.xls");
	}

	/**
	 * 导出所有数据
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping("/export")
	public void export(HttpServletResponse response) throws Exception {
		List<Product> list = productService.list();
		Workbook wb = ExcelUtil.fillExcelDataWithTemplate(list, "ProductTemplate.xls");
		ResponseUtil.export(response, wb, "导出图书数据.xls");

	}

		
	/**
	 * 上架商品
	 */
	@RequestMapping("/up/{id}")
	public String up(@PathVariable("id")String id){
		productService.up(id);
		return "redirect:/admin/product/list";
	}
	
	/**
	 * 下架商品
	 */
	@RequestMapping("/down/{id}")
	public String downProd(@PathVariable("id")String id){
		productService.down(id);
		return "redirect:/admin/product/list";
	}
	
	/**
	 * 单个批量二合一
	 * 批量删除：1-2-3
	 * 单个删除：1
	 * @param ids
	 * @return
	 */
	@RequestMapping("delete/{ids}")
	public String delete(@PathVariable("ids")String ids,HttpServletRequest request,
						 @RequestParam(value="page",required=true,defaultValue="1") Integer page){
		//批量删除
		if(ids.contains("-")){
			String[] str_ids = ids.split("-");
			List<String> del_ids = new ArrayList<String>();
			// 组装id的集合
			for (String id : str_ids) {
				del_ids.add(id);
			}
			productService.deleteBatch(del_ids);
		}else{
			// 1.删除存储的图片，前提是存储了图片路径
			// OR 2.不应删除，直接保存
			Product product = productService.getById(ids);
			if(StringUtils.isNotBlank(product.getImage())){ // 如果地址不为空
				String imgPath = request.getSession().getServletContext().getRealPath(product.getImage());
				File imgFile = new File(imgPath);
				if(imgFile.exists()){
					imgFile.delete();
				}
			}
			// 删除商品信息
			productService.delete(ids);
			// 删除索引
			searchService.deleteDocument(ids);
		}

		request.setAttribute("msg","删除成功！");
		request.setAttribute("newUrl","product/list?page="+page);
		return "forward:/admin/common/msg.jsp";
	}
	
	/**
	 * 根据id更新商品，并跳到当前页
	 * @param product
	 * @return
	 */
	@RequestMapping("/update")
	public String update(Product product,@RequestParam("file") MultipartFile file, HttpServletRequest request,
		 @RequestParam(value="page",required=true,defaultValue="1") Integer page) throws IOException {
		// 1.如果上传的新的图片，不应该删除之前的图片，用户的订单的图片应该是旧的图片，再设置新的图片和路径
		// 或者删除之前更新所有订单详表中所有该图片途径
		// OR 直接删，订单查不到就查不到，空白显示
		if(!file.isEmpty()){
			String imgPath = request.getSession().getServletContext().getRealPath(product.getImage());
			File imgFile = new File(imgPath);// 创建File对象
			if(imgFile.exists()){
				imgFile.delete();
			}

			String imagePath = UploadUtils.uploadImage(file,request);
			product.setImage(imagePath); // 重新设置新图片的地址
		}
		// 更新商品
		productService.update(product);

		request.setAttribute("msg","修改成功！");
		request.setAttribute("newUrl","product/list?page="+page);
		return "forward:/admin/common/msg.jsp";
	}
	
	/**
	 * 根据id查询商品
	 */
	@RequestMapping("/update/{id}")
	public String getById(@PathVariable("id")String id,Model model){
		
		Product product = productService.getById(id);
		model.addAttribute("prod", product);// 存储商品信息
		List<Category> list = categoryService.listByCid(Constant.CATEGORY_BOOK_PID);
		model.addAttribute("pList", list);// 存储一级分类
		// 根据分类id确定该分类下的子类
		List<Category> cList = categoryService.listByCid(product.getBigCateId());
		model.addAttribute("cList", cList);// 存储对应二级分类
		return "forward:/admin/product/update.jsp";
	}
	
	/**
	 * 新增商品，并跳到末页
	 * 上传文件会自动绑定到MultipartFile中
	 *
	 * 1、支持JSR303校验
	 * 2、导入Hibernate-Validator
	 * @param product
	 * @param request
	 * @param file
	 * @return
	 */
	@RequestMapping(value="/save",method=RequestMethod.POST)
	public String save(Product product, HttpServletRequest request,
					   @RequestParam("file") MultipartFile file) throws IOException {
		/*if(result.hasErrors()){
			// 校验失败，应该返回失败，在模态框中显示校验失败的错误信息
			List<FieldError> errors = result.getFieldErrors();
			Map<String, Object> map = new HashMap<>();
			for (FieldError fieldError : errors) {
				map.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
			request.setAttribute("msg","添加商品失败~");
			request.setAttribute("map",map);
			request.setAttribute("newUrl","product/list");
		}else{}*/
		// 封装上传文件操作
		String imagePath = UploadUtils.uploadImage(file,request);
		// 存储的图片路径只能放相对路径，相对于tomcat的webapp下的位置
		product.setImage(imagePath);
		productService.save(product);

		request.setAttribute("msg","添加成功！");
		request.setAttribute("newUrl","product/list?page="+Integer.MAX_VALUE);
		return "forward:/admin/common/msg.jsp";

	}
	
	/**
	 * 查询出一级分类列表，转发到添加商品页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/add")
	public String add(Model model){
		List<Category> list = categoryService.listByCid(Constant.CATEGORY_BOOK_PID);
		model.addAttribute("list", list);
		return "forward:/admin/product/add.jsp";
	}
	
	/**
	 * 查询商品列表
	 * 有查询条件就按条件查，没有就all
	 * @param model
	 * @param pageNum
	 * @return
	 */
	@RequestMapping("/list")
	public String list(Model model,SearchProduct search,
			@RequestParam(value="page",required=true,defaultValue="1") Integer pageNum){
		// 查询所有商品
		PageInfo<Product> pageInfo = productService.list(search,pageNum,Constant.PRODUCT_SHOW_PAGE);
		model.addAttribute("pageInfo", pageInfo);
		// 查询所有一级分类
		List<Category> list = categoryService.listByCid(Constant.CATEGORY_BOOK_PID);
		model.addAttribute("pList", list);
		// 回显查询条件
		model.addAttribute("search", search);
		return "forward:/admin/product/list.jsp";
	}
	
}
