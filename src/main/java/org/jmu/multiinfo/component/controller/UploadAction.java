package org.jmu.multiinfo.component.controller;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jmu.multiinfo.utils.CommonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
public class UploadAction {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@RequestMapping(value = "/upload")
	@ResponseBody
	public Map<String, Object> uploadFile(HttpServletRequest request,
			HttpSession session,@RequestParam("data_file") MultipartFile file) throws Exception {
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
			
		}
		return map;
		
	}
	
}
