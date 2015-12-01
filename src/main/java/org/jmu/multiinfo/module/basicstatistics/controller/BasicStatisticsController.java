package org.jmu.multiinfo.module.basicstatistics.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jmu.multiinfo.common.bean.DimType;
import org.jmu.multiinfo.module.basicstatistics.service.BasicStatisticsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;

/**
 * 
* @Title: BasicStatisticsAction.java 
* @Package org.jmu.multiinfo.module.basicstatistics.controller 
* @Description: 基本统计
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015年11月4日 下午9:05:16 
* @version V1.0
 */
@Controller
@RequestMapping("/basic")
public class BasicStatisticsController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public BasicStatisticsService basicStatisticsService;
	
	/**
	 *  平均
	 * @param request
	 * @param session
	 * @param rows
	 * @param cols
	 * @throws Exception
	 */
	@RequestMapping(value = "/average")
	@ResponseBody
	public ModelAndView average(HttpServletRequest request,HttpSession session,
			@RequestParam(value="row[]", required=false) Integer[] rows,@RequestParam(value="col[]", required=false) Integer[] cols,
			@RequestParam(value="sheet",required=false,defaultValue="0") int sheet,@RequestParam(value="dim",required=false) String dimStr) throws Exception{
		logger.debug("Enter range");
		Map<String, Object> map = new HashMap<String, Object>();
		GsonOption option = new GsonOption();
		StringBuilder sb = new StringBuilder();
		DimType dim = null;
		if(rows==null){
			rows=new Integer[]{1,2,3};
			dim = DimType.DIM_Y_POSITION;
		}
		if(cols==null){
			cols=new Integer[]{2,3,5};
			dim = DimType.DIM_X_POSITION;
		}
		logger.debug((rows!=null?rows.length:0)+" "+(cols!=null?cols.length:0));
		for (int i = 0; i < cols.length; i++) {
			sb.append(cols[i]);
		}
		logger.debug(sb.toString()+"sheet:"+sheet);
		String path = (String) session.getAttribute("path");
		
		Map<String, Object> result=	basicStatisticsService.average(path, sheet, rows, cols, dim);
		String[] xAxis = (String[]) result.get("xAxis");
		 option.xAxis(new CategoryAxis().data(xAxis));
		 option.yAxis(new ValueAxis());
		 option.legend("蒸发量");
		 Bar bar = new Bar("蒸发量");
		 String[] data = (String[])  result.get("result");
		 bar.data(data);
		 option.series(bar);
		 map.put("option", option);
		 ModelAndView mav=new ModelAndView("average",map);
		return mav;
	}
	
	
	/**
	 *  最大值
	 * @param request
	 * @param session
	 * @param rows
	 * @param cols
	 * @throws Exception
	 */
	@RequestMapping(value = "/maxValue")
	@ResponseBody
	public ModelAndView maxValue(HttpServletRequest request,HttpSession session,
			@RequestParam(value="row[]", required=false) Integer[] rows,@RequestParam(value="col[]", required=false) Integer[] cols,
			@RequestParam(value="sheet",required=false,defaultValue="0") int sheet) throws Exception{
		logger.debug("Enter range");
		Map<String, Object> map = new HashMap<String, Object>();
		GsonOption option = new GsonOption();
		StringBuilder sb = new StringBuilder();
		if(rows==null){
			rows=new Integer[]{1,2,3};
		}
		if(cols==null){
			cols=new Integer[]{2,3,5};
		}
		logger.debug((rows!=null?rows.length:0)+" "+(cols!=null?cols.length:0));
		for (int i = 0; i < cols.length; i++) {
			sb.append(cols[i]);
		}
		logger.debug(sb.toString()+"sheet:"+sheet);
		String path = (String) session.getAttribute("path");
		Map<String, Object> result=	basicStatisticsService.maxValue(path, sheet, rows, cols, DimType.DIM_X_POSITION);
		String[] xAxis = (String[]) result.get("xAxis");
		 option.xAxis(new CategoryAxis().data(xAxis));
		 option.yAxis(new ValueAxis());
		 option.legend("蒸发量");
		 Bar bar = new Bar("蒸发量");
		 String[] data = (String[])  result.get("result");
		 bar.data(data);
		 option.series(bar);
		 map.put("option", option);
		 ModelAndView mav=new ModelAndView("select/test1",map);
		return mav;
	}
}
