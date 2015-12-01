package org.jmu.multiinfo.module.basicstatistics.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.jmu.multiinfo.common.bean.DimType;
import org.jmu.multiinfo.common.bean.ExcelBean;
import org.jmu.multiinfo.common.bean.IDealDataCallBack;
import org.jmu.multiinfo.common.util.CalculateUtil;
import org.jmu.multiinfo.utils.ExcelInfoUtil;
import org.springframework.stereotype.Service;

/**
 * 
* @Title: BasicStatisticsService.java 
* @Package org.jmu.multiinfo.module.basicstatistics.service 
* @Description: 基本统计处理
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015年11月4日 下午9:10:36 
* @version V1.0
 */
@Service
public class BasicStatisticsService {
	public Map<String,Object> average(String filePath,int sheet,Integer[] rows,Integer[] cols,DimType dim) throws FileNotFoundException, IOException {
		String[] aveResults;
		final Map<String,Object> map = new HashMap<String,Object>();
		switch(dim){
		    case DIM_X_POSITION:
		    	aveResults=	ExcelInfoUtil.getData(filePath, sheet,rows,cols,new IDealDataCallBack() {
					public String[] dealData(ExcelBean excelBean) {
						// TODO Auto-generated method stub
						Double[][] orialData=CalculateUtil.converTo(excelBean.getData());
						ArrayList<Double> result = CalculateUtil.findColAverage(orialData);
						String[] resultStr=new String[result.size()];
						for(int i=0;i<result.size();i++){
							resultStr[i]=String.valueOf(result.get(i));
						}
						
						map.put("xAxis", excelBean.getxAxis());
						map.put("yAxis", null);
						map.put("title", excelBean.getFileName());
						return resultStr;
					}
				});
				
		    	break;
		    case DIM_Y_POSITION:
		    	aveResults=	ExcelInfoUtil.getData(filePath, sheet,rows,cols,new IDealDataCallBack() {
					public String[] dealData(ExcelBean excelBean) {
						// TODO Auto-generated method stub
						Double[][] orialData=CalculateUtil.converTo(excelBean.getData());
						ArrayList<Double> result = CalculateUtil.findAverage(orialData);
						String[] resultStr=new String[result.size()];
						for(int i=0;i<result.size();i++){
							resultStr[i]=String.valueOf(result.get(i));
						}
						map.put("xAxis", excelBean.getyAxis());
						map.put("yAxis", null);
						map.put("title", excelBean.getFileName());
						return resultStr;
					}
				});
		    	break;
		    default:
		    	aveResults=null;
		    	break;
			
		}
		map.put("result", aveResults);
		return map;
	}
	
	public Map<String,Object> maxValue(String filePath,int sheet,Integer[] rows,Integer[] cols,DimType dim) throws FileNotFoundException, IOException {
		String[] aveResults;
		final Map<String,Object> map = new HashMap<String,Object>();
		switch(dim){
		    case DIM_X_POSITION:
		    	aveResults=	ExcelInfoUtil.getData(filePath, sheet,rows,cols,new IDealDataCallBack() {
					public String[] dealData(ExcelBean excelBean) {
						// TODO Auto-generated method stub
						Double[][] orialData=CalculateUtil.converTo(excelBean.getData());
						ArrayList<Double> result = CalculateUtil.findColMax(orialData);
						String[] resultStr=new String[result.size()];
						for(int i=0;i<result.size();i++){
							resultStr[i]=String.valueOf(result.get(i));
						}
						
						map.put("xAxis", excelBean.getxAxis());
						map.put("yAxis", null);
						map.put("title", excelBean.getFileName());
						return resultStr;
					}
				});
				
		    	break;
		    case DIM_Y_POSITION:
		    	aveResults=	ExcelInfoUtil.getData(filePath, sheet,rows,cols,new IDealDataCallBack() {
					public String[] dealData(ExcelBean excelBean) {
						// TODO Auto-generated method stub
						Double[][] orialData=CalculateUtil.converTo(excelBean.getData());
						ArrayList<Double> result = CalculateUtil.findMax(orialData);
						String[] resultStr=new String[result.size()];
						for(int i=0;i<result.size();i++){
							resultStr[i]=String.valueOf(result.get(i));
						}
						map.put("xAxis", excelBean.getyAxis());
						map.put("yAxis", null);
						map.put("title", excelBean.getFileName());
						return resultStr;
					}
				});
		    	break;
		    default:
		    	aveResults=null;
		    	break;
			
		}
		map.put("result", aveResults);
		return map;
	}
}
