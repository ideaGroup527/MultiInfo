package org.jmu.multiinfo.module.dingshitu.domain;

import java.util.List;

/***
 * 
* @Title: DingShiTu.java 
* @Package org.jmu.multiinfo.module.dingshitu.domain 
* @Description: ����ͼ
* @author  <a href="mailto:www_1350@163.com">Absurd</a>
* @date 2015��9��23�� ����11:25:59 
* @version V1.0
 */
public class DingShiTu {
	/**
	 * ����
	 */
public static final int DING_ZXDB=1;

/**
 * ����
 */
public static final int DING_HXDB=2;

/**
 * ȫ��
 */
public static final int DING_QBDB=3;

	private int width = 800;
	private int height = 550;

	/**
	 *  ͼƬ����
	 */
	private String chartTitle = "��     ��     ͼ";

	
	/**
	 * �����߶ε�����
	 */
	private List<String> dingTitle;

	/**
	 * X�������
	 */
	private String xTitle;

	
	/**
	 * Y�������
	 */
	private String yTitle;

	
	/***
	 * X��Y���Ӧ��ֵ
	 */
	private double[][] value;

	
	/**
	 * X��
	 */
	private List<String> xScale;
	
	/***
	 * �����
	 */
	private double allMax;
	
	/***
	 * ��С����
	 */
	private double allMin;
	
	/**
	 *  ����������false��true��
	 */
	private boolean left_right;
	
	/**
	 *  ����������false��true��
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
