package com.hello.eshop.utils;

import com.hello.eshop.exception.MessageException;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.Random;

/**
 * 上传文件的工具类
 * @author Hello
 *
 */
public class UploadUtils {
	
	/**
	 * 获取文件的真实名称
	 * (比如/A/B/c.jpg->c.jpg)
	 * @param name
	 * @return
	 */
	public static String getRealName(String name){
		// 获取最后一个"/"
		Integer index = name.lastIndexOf("\\");
		return name.substring(index + 1);
	}
	
	/**
	 * 获取随机名称
	 * 重点是组合ImageName + 图片的后缀.jpg(.png)
	 * @return
	 */
	public static String getImageName(String realName){
		Integer index = realName.lastIndexOf(".");
		if(index == -1){
			return IDUtils.genImageName();
		}
		return IDUtils.genImageName() + realName.substring(index);
		
	}
	
	/**
	 * 获取文件目录，可以获取256个随机目录
	 * @return
	 */
	public static String getDir(){
		String str = "0123456789ABCDEF";
		Random random = new Random();
		return "/"+str.charAt(random.nextInt(16)) + "/" + str.charAt(random.nextInt(16));
	}
	
	/**
	 * 上传图片的操作
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static String uploadImage(MultipartFile file,HttpServletRequest request) throws IOException{
		//如果文件不为空，写入上传路径
		 if(!file.isEmpty()) {
	        //上传文件root路径
			String uploadPath = request.getSession().getServletContext().getRealPath("/products");
	        //上传文件名
	        String fileName = file.getOriginalFilename();//最初文件名
	        String realName = getRealName(fileName);//获取文件的真实名称
	        String imageName = getImageName(realName);//获取时间生成的图片名称
	        String dir = getDir();//获取随机文件目录
	        
	        File dirFile = new File(uploadPath,dir);//创建上传图片的随机目录
	        
	        //判断随机路径是否存在，如果不存在就创建一个
	        if (!dirFile.exists()) { 
	     	   dirFile.mkdirs();
	        }
	        //将上传文件保存到一个目标文件当中
	        file.transferTo(new File(dirFile + File.separator + imageName));
			return "/products" + dir + "/" + imageName;
		}else{
			throw new MessageException("上传文件失败了...");
	    } 
	}
}
