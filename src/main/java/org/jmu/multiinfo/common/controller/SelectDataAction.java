package org.jmu.multiinfo.common.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jmu.multiinfo.utils.ExcelInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.github.abel533.echarts.axis.CategoryAxis;
import com.github.abel533.echarts.axis.ValueAxis;
import com.github.abel533.echarts.code.Magic;
import com.github.abel533.echarts.code.Tool;
import com.github.abel533.echarts.code.Trigger;
import com.github.abel533.echarts.feature.MagicType;
import com.github.abel533.echarts.json.GsonOption;
import com.github.abel533.echarts.series.Line;

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
public class SelectDataAction {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
	public Map<String, Object> selectData(HttpServletRequest request,HttpSession session,
			@RequestParam(value="row[]", required=false) Integer[] rows,@RequestParam(value="col[]", required=false) Integer[] cols,
			@RequestParam(value="sheet",required=false,defaultValue="0") int sheet) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		StringBuilder sb = new StringBuilder();
		logger.debug((rows!=null?rows.length:0)+" "+(cols!=null?cols.length:0));
		for (int i = 0; i < cols.length; i++) {
			sb.append(cols[i]);
		}
		logger.debug(sb.toString()+"sheet:"+sheet);
		String path = (String) session.getAttribute("path");
		map.put("data", ExcelInfo.getData(path, sheet,rows,cols));
		return map;
		
	}
	
	@RequestMapping(value="/test")
	@ResponseBody
	public ModelAndView test(HttpServletRequest request,HttpSession session) {
		logger.debug("sheet:");
		Map<String, Object> map = new HashMap<String, Object>();
		GsonOption option = new GsonOption();
		option.legend("高度(km)与气温(°C)变化关系");

	    option.toolbox().show(true).feature(Tool.mark, Tool.dataView, new MagicType(Magic.line, Magic.bar), Tool.restore, Tool.saveAsImage);

	    option.calculable(true);
	    option.tooltip().trigger(Trigger.axis).formatter("Temperature : <br/>{b}km : {c}°C");

	    ValueAxis valueAxis = new ValueAxis();
	    valueAxis.axisLabel().formatter("{value} °C");
	    option.xAxis(valueAxis);

	    CategoryAxis categoryAxis = new CategoryAxis();
	    categoryAxis.axisLine().onZero(false);
	    categoryAxis.axisLabel().formatter("{value} km");
	    categoryAxis.boundaryGap(false);
	    categoryAxis.data(0, 10, 20, 30, 40, 50, 60, 70, 80);
	    option.yAxis(categoryAxis);

	    Line line = new Line();
	    line.smooth(true).name("高度(km)与气温(°C)变化关系").data(15, -50, -56.5, -46.5, -22.1, -2.5, -27.7, -55.7, -76.5).itemStyle().normal().lineStyle().shadowColor("rgba(0,0,0,0.4)");
	    option.series(line);
        map.put("option",option);
        ModelAndView mav=new ModelAndView("select/test2",map);
        return mav;
    } 
	
}
