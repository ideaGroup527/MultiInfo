package org.jmu.multiinfo.module.login.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.jmu.multiinfo.module.login.domain.User;
import org.jmu.multiinfo.module.login.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller("LoginController")
@RequestMapping("/login")
public class LoginController {
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private LoginService loginService;
	
	@RequestMapping("/exportExcel")
	public ResponseEntity<byte[]> exportExcel() throws IOException {
		HttpHeaders headers = new HttpHeaders();  
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);  
		headers.setContentDispositionFormData("attachment", "test.xls");  
		
		Workbook wb = new HSSFWorkbook();
		 CreationHelper createHelper = wb.getCreationHelper();
		Sheet s = wb.createSheet();
		 CellStyle cellStyle = wb.createCellStyle();
		 cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
	        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
	        cellStyle.setLeftBorderColor(IndexedColors.GREEN.getIndex());
	        cellStyle.setBorderRight(CellStyle.BORDER_THIN);
//	        cellStyle.setFillForegroundColor(HSSFColor.LIME.index);
//	        cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
	        Font font = wb.createFont();
	        font.setColor(HSSFColor.RED.index);
	        cellStyle.setFont(font);

	       for(int rownum = 0; rownum < 30; rownum++) {
			Row r = s.createRow(rownum);
			for(int cellnum = 0; cellnum < 10; cellnum ++) {
				Cell c = r.createCell(cellnum);
				c.setCellValue((double)rownum + (cellnum/10));
				c.setCellStyle(cellStyle);
				if(cellnum>5)
					c.setCellType(Cell.CELL_TYPE_ERROR);
			}
		}
		// Save
		FileOutputStream out = new FileOutputStream("workbook.xls");
		wb.write(out);
		out.close();
		logger.debug("out the file");
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File("workbook.xls")),  
		                                      headers, HttpStatus.CREATED);  
	}
	
	@RequestMapping("/check")
	public void Login(@ModelAttribute("user")User user){
		if(loginService.Login(user)){
			logger.debug("login");
		}else
		logger.debug("err");
	}
	
}
