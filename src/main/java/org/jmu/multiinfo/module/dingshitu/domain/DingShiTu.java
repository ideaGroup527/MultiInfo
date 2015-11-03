package org.jmu.multiinfo.module.dingshitu.domain;

import java.util.List;

/***
 * 
* @Title: DingShiTu.java 
* @Package org.jmu.multiinfo.module.dingshitu.domain 
* @Description: 丁氏图
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015年9月23日 上午11:25:59 
* @version V1.0
 */
public class DingShiTu {
	/**
	 * 纵向
	 */
public static final int DING_ZXDB=1;

/**
 * 横向
 */
public static final int DING_HXDB=2;

/**
 * 全表
 */
public static final int DING_QBDB=3;

	private int width = 800;
	private int height = 550;

	/**
	 *  图片标题
	 */
	private String chartTitle = "丁     氏     图";

	
	/**
	 * 各个线段的名称
	 */
	private List<String> dingTitle;

	/**
	 * X轴的名称
	 */
	private String xTitle;

	
	/**
	 * Y轴的名称
	 */
	private String yTitle;

	
	/***
	 * X、Y轴对应的值
	 */
	private double[][] value;

	
	/**
	 * X轴
	 */
	private List<String> xScale;
	
	/***
	 * 最大极限
	 */
	private double allMax;
	
	/***
	 * 最小极限
	 */
	private double allMin;
	
	/**
	 *  文字在左右false左true右
	 */
	private boolean left_right;
	
	/**
	 *  文字在上下false上true下
	 */
	private boolean up_down;
	
	private int hangLeng;
	
	private int zongLength;

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public String getChartTitle() {
		return chartTitle;
	}

	public void setChartTitle(String chartTitle) {
		this.chartTitle = chartTitle;
	}

	public List<String> getDingTitle() {
		return dingTitle;
	}

	public void setDingTitle(List<String> dingTitle) {
		this.dingTitle = dingTitle;
	}

	public String getxTitle() {
		return xTitle;
	}

	public void setxTitle(String xTitle) {
		this.xTitle = xTitle;
	}

	public String getyTitle() {
		return yTitle;
	}

	public void setyTitle(String yTitle) {
		this.yTitle = yTitle;
	}

	public double[][] getValue() {
		return value;
	}

	public void setValue(double[][] value) {
		this.value = value;
	}

	public List<String> getxScale() {
		return xScale;
	}

	public void setxScale(List<String> xScale) {
		this.xScale = xScale;
	}

	public double getAllMax() {
		return allMax;
	}

	public void setAllMax(double allMax) {
		this.allMax = allMax;
	}

	public double getAllMin() {
		return allMin;
	}

	public void setAllMin(double allMin) {
		this.allMin = allMin;
	}

	public boolean isLeft_right() {
		return left_right;
	}

	public void setLeft_right(boolean left_right) {
		this.left_right = left_right;
	}

	public boolean isUp_down() {
		return up_down;
	}

	public void setUp_down(boolean up_down) {
		this.up_down = up_down;
	}

	public int getHangLeng() {
		return hangLeng;
	}

	public void setHangLeng(int hangLeng) {
		this.hangLeng = hangLeng;
	}

	public int getZongLength() {
		return zongLength;
	}

	public void setZongLength(int zongLength) {
		this.zongLength = zongLength;
	}



}
