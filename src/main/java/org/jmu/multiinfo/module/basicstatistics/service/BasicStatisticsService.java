package org.jmu.multiinfo.module.basicstatistics.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jmu.multiinfo.common.bean.DimType;
import org.jmu.multiinfo.common.bean.ExcelBean;
import org.jmu.multiinfo.common.bean.IDealDataCallBack;
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
						String[][] orialData=excelBean.getData();
						int ysize = orialData.length;
						int xsize = orialData[0].length;
						double[] result=new double[xsize];
						for(int i=0;i<xsize;i++) result[i]=0.0;
						for(int i=0;i<ysize;i++){
							for(int j=0;j<xsize;j++){
								result[j]+=Double.valueOf(orialData[i][j]);
							}
						}
						String[] resultStr=new String[xsize];
						for(int i=0;i<xsize;i++){
							resultStr[i]=String.valueOf(result[i]/xsize);
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
						String[][] orialData=excelBean.getData();
						int ysize = orialData.length;
						int xsize = orialData[0].length;
						double[] result=new double[ysize];
						for(int i=0;i<ysize;i++) result[i]=0.0;
						for(int i=0;i<ysize;i++){
							for(int j=0;j<xsize;j++){
								result[i]+=Double.valueOf(orialData[i][j]);
							}
						}
						String[] resultStr=new String[ysize];
						for(int i=0;i<ysize;i++){
							resultStr[i]=String.valueOf(result[i]/ysize);
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
						String[][] orialData=excelBean.getData();
						int ysize = orialData.length;
						int xsize = orialData[0].length;
						double[] result=new double[xsize];
						for(int i=0;i<ysize;i++) result[i]=0.0;
						for(int i=0;i<ysize;i++){
							for(int j=0;j<xsize;j++){
								if(i==0)
								result[j]=Double.valueOf(orialData[i][j]);
								else
									result[j] = Double.valueOf(orialData[i][j])>result[j]?Double.valueOf(orialData[i][j]):result[j];
							}
						}
						String[] resultStr=new String[xsize];
						for(int i=0;i<xsize;i++){
							resultStr[i]=String.valueOf(result[i]);
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
						String[][] orialData=excelBean.getData();
						int ysize = orialData.length;
						int xsize = orialData[0].length;
						double[] result=new double[ysize];
						for(int i=0;i<ysize;i++) result[i]=0.0;
						for(int i=0;i<ysize;i++){
							for(int j=0;j<xsize;j++){
								if(j==0)
									result[i]=Double.valueOf(orialData[i][j]);
									else
										result[i] = Double.valueOf(orialData[i][j])>result[j]?Double.valueOf(orialData[i][j]):result[j];

							}
						}
						String[] resultStr=new String[ysize];
						for(int i=0;i<ysize;i++){
							resultStr[i]=String.valueOf(result[i]);
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
