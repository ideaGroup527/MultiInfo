package org.jmu.multiinfo.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/***
 * 
* @Title: ExportExcelUtil.java 
* @Package org.jmu.multiinfo.utils 
* @Description: 操作excel通用util
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015年9月23日 上午9:45:30 
* @version V1.0
 */
public class ExportExcelUtil {

	/**
	 * 设置工作薄样式
	 *
	 * @param workbook
	 * @return
	 */
	public static CellStyle setWorkbookStyle(Workbook workbook) {
		// 生成一个说明样式

		CellStyle cellStyle = workbook.createCellStyle();
		// 设置居中
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		// 设置填充前景色和背景色

		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		// cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置线条宽度
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 生成一个字体

		Font font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 8);
		// 字体应用到样式

		cellStyle.setFont(font);

		return cellStyle;
	}

	public void buildExcelDocument(Map<String, Object> model, HSSFWorkbook workbook, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			List<String> titleList = (List<String>) model.get("titleList");
			List<List<String>> dataList = (List<List<String>>) model.get("dataList");
			int sheetNum = Integer.parseInt((String) model.get("sheetNum"));
			ExportExcelUtil.covertDataListChunk2Excel(workbook, titleList, dataList, sheetNum);
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			OutputStream os = response.getOutputStream();
			workbook.write(os);
			os.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出excel主方法
	 *
	 * @param workbook
	 * @param titleList
	 *            标题
	 * @param dataList
	 *            数据
	 * @param sheetNum
	 *            每页行数
	 * @return
	 */
	public static HSSFWorkbook covertDataListChunk2Excel(HSSFWorkbook workbook, List<String> titleList,
			List<List<String>> dataList, int sheetNum) {
		if (sheetNum < 0)
			sheetNum = dataList.size();
		if (sheetNum == 0)
			return null;
		// 设置表格样式
		CellStyle cellStyle = getDataStyle(workbook);
		CellStyle titleStyle = createHeaderCellStyle(workbook);
		int sheetCount = dataList.size() / sheetNum;// 页数
		sheetCount = dataList.size() % sheetNum == 0 ? sheetCount : sheetCount + 1;
		for (int i = 0; i < sheetCount; i++) {// 分页
			String sheetName = "第" + (i + 1) + "页";
			Sheet sheet = workbook.createSheet(sheetName);
			// 设置工作薄样式

			setSheetStyle(sheet);
			int rowCount = 0;
			Row titleRow = sheet.createRow(rowCount++);
			Cell cell = null;
			for (int j = 0; j < titleList.size(); j++) {// 设置标题
				cell = titleRow.createCell(j);
				cell.setCellValue(titleList.get(j));
				cell.setCellStyle(titleStyle);
			}
			for (int k = i * sheetNum; k < (i + 1) * sheetNum && k < dataList.size(); k++) {
				List<String> colList = dataList.get(k);
				Row dataRow = sheet.createRow(rowCount++);
				for (int j = 0; j < colList.size(); j++) {
					cell = dataRow.createCell(j);
					cell.setCellValue(String.valueOf(colList.get(j)));
					cell.setCellStyle(cellStyle);
				}
			}
		}
		return workbook;
	}

	/**
	 * 设置工作薄样式
	 *
	 * @param sheet
	 * @return
	 */
	private static Sheet setSheetStyle(Sheet sheet) {
		// 设置默认列宽度为15个字节
		sheet.setDefaultColumnWidth(20);
		return sheet;
	}

	/**
	 * 设置Excel表格数据样式
	 *
	 * @param workbook
	 * @return
	 */
	private static CellStyle getDataStyle(Workbook workbook) {
		// 生成一个说明样式

		CellStyle cellStyle = workbook.createCellStyle();
		// 设置居中
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_LEFT);
		// 设置填充前景色和背景色

		// cellStyle.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
		cellStyle.setFillBackgroundColor(HSSFColor.GREY_25_PERCENT.index);
		// cellStyle.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		// 设置线条宽度
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// 生成一个字体

		Font font = workbook.createFont();
		font.setColor(HSSFColor.BLACK.index);
		font.setFontHeightInPoints((short) 8);
		// 字体应用到样式

		cellStyle.setFont(font);
		return cellStyle;
	}

	/**
	 * 创建表头标题单元格的样式
	 *
	 * @param workbook
	 * @return
	 */
	public static HSSFCellStyle createHeaderCellStyle(HSSFWorkbook workbook) {
		// 设置表头标题样式
		HSSFFont headerFont = workbook.createFont();
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		HSSFCellStyle result = workbook.createCellStyle();
		result.setFont(headerFont);
		result.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		result.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		result.setFillForegroundColor(HSSFColor.PALE_BLUE.index);
		result.setBorderRight(HSSFCellStyle.BORDER_THIN);
		result.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);

		return result;
	}



	/**
	 * 创建sheet
	 * 
	 * @param workbook
	 * @param titleStyle
	 * @param cellStyle
	 * @param titleList
	 * @param dataList
	 * @param sheetName
	 * @return
	 */
	public static HSSFWorkbook convertDataList2Sheet(HSSFWorkbook workbook, CellStyle titleStyle, CellStyle cellStyle,
			List<String> titleList, List<List<String>> dataList, String sheetName) {
		// 设置表格样式
		// CellStyle cellStyle = getDataStyle(workbook);
		// CellStyle titleStyle=createHeaderCellStyle(workbook);
		Sheet sheet = workbook.createSheet(sheetName);
		// 设置工作薄样式

		setSheetStyle(sheet);
		int rowCount = 0;
		Row titleRow = sheet.createRow(rowCount++);
		Cell cell = null;
		for (int j = 0; j < titleList.size(); j++) {// 设置标题
			cell = titleRow.createCell(j);
			cell.setCellValue(titleList.get(j));
			cell.setCellStyle(titleStyle);
		}
		for (int k = 0; k < dataList.size(); k++) {
			List<String> colList = dataList.get(k);
			Row dataRow = sheet.createRow(rowCount++);
			for (int j = 0; j < colList.size(); j++) {
				cell = dataRow.createCell(j);
				cell.setCellValue(String.valueOf(colList.get(j)));
				cell.setCellStyle(cellStyle);
			}
		}
		return workbook;
	}

	
	/**
	 * 创建excel
	 * 
	 * @param workbook
	 * @param outputFileName
	 */
	public static void buildExcel(Workbook workbook, String outputFileName) {
		try {
			FileOutputStream fileOut = new FileOutputStream(outputFileName);
			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/***
	 * 将文件读取为excel
	 * @param file
	 * @return workbook
	*/
	public static Workbook readExcel(File file){
		 InputStream inp = null;
		 Workbook wb = null;
		try {
			inp = new FileInputStream(file);
			wb = WorkbookFactory.create(inp);
			inp.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (EncryptedDocumentException e) {//如果是加密的excel
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		    
		    return wb;
	}
}
