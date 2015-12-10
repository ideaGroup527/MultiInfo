package org.jmu.multiinfo.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.jmu.multiinfo.common.bean.DimType;
import org.jmu.multiinfo.common.bean.IDealDataCallBack;
import org.jmu.multiinfo.module.basicstatistics.service.BasicStatisticsService;
import org.jmu.multiinfo.utils.ExcelInfoUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.MarkType;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.data.PointData;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Bar;

/**
 * 
* @Title: SelectDataAction.java 
* @Package org.jmu.multiinfo.common.controller 
* @Description: 确定数据范围
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015年11月4日 上午11:03:25 
* @version V1.0
 */
@Controller
@RequestMapping("/select")
public class SelectDataController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public BasicStatisticsService basicStatisticsService;
	
	/**
	 *  数据范围选择
	 * @param request
	 * @param session
	 * @param rows
	 * @param cols
	 * @throws Exception
	 */
	@RequestMapping(value = "/range")
	@ResponseBody
	public ModelAndView selectData(HttpServletRequest request,HttpSession session,
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
		Map<String, Object> result=	basicStatisticsService.average(path, sheet, rows, cols, DimType.DIM_X_POSITION);
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
	
	@RequestMapping(value="/test")
	@ResponseBody
	public ModelAndView test(HttpServletRequest request,HttpSession session) {
		logger.debug("sheet:");
		Map<String, Object> map = new HashMap<String, Object>();
		GsonOption option = new GsonOption();
		option.title().text("某地区蒸发量和降水量").subtext("纯属虚构");
        option.tooltip().trigger(Trigger.axis);
        option.legend("蒸发量", "降水量");
        option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar).show(true), Tool.restore, Tool.saveAsImage);
        option.calculable(true);
        option.xAxis(new CategoryAxis().data("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"));
        option.yAxis(new ValueAxis());

        Bar bar = new Bar("蒸发量");
        bar.data(2.0, 4.9, 7.0, 23.2, 25.6, 76.7, 135.6, 162.2, 32.6, 20.0, 6.4, 3.3);
        bar.markPoint().data(new PointData().type(MarkType.max).name("最大值"), new PointData().type(MarkType.min).name("最小值"));
        bar.markLine().data(new PointData().type(MarkType.average).name("平均值"));

        Bar bar2 = new Bar("降水量");
        bar2.data(2.6, 5.9, 9.0, 26.4, 28.7, 70.7, 175.6, 182.2, 48.7, 18.8, 6.0, 2.3);
        bar2.markPoint().data(new PointData("年最高", 182.2).xAxis(7).yAxis(183).symbolSize(18), new PointData("年最低", 2.3).xAxis(11).yAxis(3));
        bar2.markLine().data(new PointData().type(MarkType.average).name("平均值"));

        option.series(bar, bar2);
        map.put("option",option);
        ModelAndView mav=new ModelAndView("select/test2",map);
        return mav;
    } 
	
}
