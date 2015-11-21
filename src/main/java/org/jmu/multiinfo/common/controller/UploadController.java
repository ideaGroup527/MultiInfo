package org.jmu.multiinfo.common.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jmu.multiinfo.utils.CommonUtil;
import org.jmu.multiinfo.utils.ExcelInfo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;


/***
 * 
* @Title: UploadAction.java 
* @Package org.jmu.multiinfo.component.controller 
* @Description: 文件上传
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015年11月3日 下午3:53:33 
* @version V1.0
 */
@Controller
@RequestMapping("/file")
public class UploadController {
	
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request,
			HttpSession session,@RequestParam("data_file") MultipartFile file,HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if(file.isEmpty()) return null;
		String realPath = request.getSession().getServletContext()
				.getRealPath("/upload");
		// 文件名
		String filename = file.getOriginalFilename();
		// 获取文件的后缀
		String suffix = filename.substring(filename.lastIndexOf("."));
		// 文件MD5
		String md5 = CommonUtil.getMD5(file.getBytes());
		// 保存的路径,文件名保存为MD5值
		String filePath = realPath + "/" + md5 + suffix;
		// 判断文件是否存在。若存在则急速秒传
		File fileLocal = CommonUtil.fileExists(filePath);
		if (fileLocal != null) {
			map.put("jsmc", "极速秒传！");
		} else {
			fileLocal = CommonUtil.saveFileAndReturn(filePath,
					file.getBytes());
		}
		if(fileLocal!=null){
			session.setAttribute("path", filePath);
			response.sendRedirect("/MultiInfo/data-select.html");
		}
		return map;
		
	}
	/**
	 * 
	 * @Description: 读取文件
	 * @author big
	 * @date 2015年11月3日 下午2:21:54
	 */
	@RequestMapping(value = "/read/sheet")
	@ResponseBody
	public Map<String, Object> readFile(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String path = (String) session.getAttribute("path");

		if (path != null && path.length() > 0) {
			map.put("status", true);
			map.put("sheet", ExcelInfo.getSheet(path));
		} else {
			map.put("status", true);
			map.put("msg", "请先上传文件！");
		}
		return map;
	}

	/**
	 * 
	 * @Description: 读取第N个sheet的值
	 * @author big
	 * @date 2015年11月3日 下午2:25:26
	 * @version V1.0
	 */
	@RequestMapping(value = "/read/sheet/{n}")
	@ResponseBody
	public Map<String, Object> readSheet(@PathVariable int n,
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String path = (String) session.getAttribute("path");

		if (path != null && path.length() > 0) {
			map.put("status", true);
			map.put("data", ExcelInfo.getData(path, n));
		} else {
			map.put("status", true);
			map.put("msg", "请先上传文件！");
		}
		return map;
	}
}
