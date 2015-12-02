package org.jmu.multiinfo.common.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/***
 * 
 * @Title: CalculateUtil.java
 * @Package org.jmu.multiinfo.common.util
 * @Description: 公用计算工具
 * @author <a href="mailto:www_1350@163.com">Absurd</a>
 * @date 2015年11月30日 下午10:59:00
 * @version V1.0
 */
public class CalculateUtil {

	
	/***
	 * 
	 * @param data
	 * @return 每个维度最大值
	 */
	public static <T extends Comparable<T>> ArrayList<T> findMax(T[][] data) {
		int rowSize  = data.length;
		int colSize = data[0].length;
		ArrayList<T> maxi = new ArrayList<T>(rowSize);
		T temp;
		for (int i = 0; i < rowSize; i++) {

			temp = data[i][0];
			for (int j = 0; j < colSize; j++) {
				temp = temp.compareTo(data[i][j]) > 0 ? temp : data[i][j];
			}
			maxi.add(temp);
		}
		return maxi;
	}

	/***
	 * 
	 * @param data
	 * @return 每个列最大值
	 */
	public static <T extends Comparable<T>> ArrayList<T> findColMax(T[][] data) {
		int rowSize  = data.length;
		int colSize = data[0].length;
		ArrayList<T> maxi = new ArrayList<T>(colSize);
		for (int i = 0; i < colSize; i++) {
			maxi.add(i, data[0][i]);
		}
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				maxi.set(j,( maxi.get(j).compareTo(data[i][j]) > 0 ? maxi.get(j) : data[i][j]) );
			}
		}
		return maxi;
	}
	
	/***
	 * 
	 * @param data
	 * @return 每个维度最小值
	 */
	public static <T extends Comparable<T>> ArrayList<T> findMin(T[][] data) {
		int rowSize  = data.length;
		int colSize = data[0].length;
		ArrayList<T> mini = new ArrayList<T>(rowSize);
		T temp;
		for (int i = 0; i < rowSize; i++) {

			temp = data[i][0];
			for (int j = 0; j < colSize; j++) {
				temp = temp.compareTo(data[i][j]) < 0 ? temp : data[i][j];
			}
			mini.add(temp);
		}
		return mini;
	}
	
	/***
	 * 
	 * @param data
	 * @return 每个列最小值
	 */
	public static <T extends Comparable<T>> ArrayList<T> findColMin(T[][] data) {
		int rowSize  = data.length;
		int colSize = data[0].length;
		ArrayList<T> mini = new ArrayList<T>(colSize);
		for (int i = 0; i < colSize; i++) {
			mini.add(i, data[0][i]);
		}
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				mini.set(j,( mini.get(j).compareTo(data[i][j]) < 0 ? mini.get(j) : data[i][j]) );
			}
		}
		return mini;
	}
	
	/***
	 * 
	 * @param data
	 * @return 每个维度平均值
	 */
	public static <T extends Number> ArrayList<Double> findAverage(T[][] data){
		int rowSize  = data.length;
		ArrayList<Double> average = new ArrayList<Double>(rowSize);
		for (int i = 0; i < rowSize; i++) {
			average.add(findAverage(data[i]));
		}
		return average;
	}

	private static <T extends Number> Double findAverage(T[] data){
		int rowSize  = data.length;
		Double temp =0.0;
		for (int i = 0; i < rowSize; i++) {
			temp+=data[i].doubleValue();
		}
		return (temp/rowSize);
	}
	
	
	/***
	 * 
	 * @param data
	 * @return 每个列平均值
	 */
	public static <T extends Number> ArrayList<Double> findColAverage(T[][] data){
		
		int rowSize  = data.length;
		int colSize = data[0].length;
		Double[] temp = new Double[rowSize];
		ArrayList<Double> average = new ArrayList<Double>(colSize);
		for (int j = 0; j < colSize; j++) {
			for (int i = 0; i < rowSize; i++) {
				temp[i] = data[i][j].doubleValue();
			}
			average.add(findAverage(temp));
		}
		return average;
	}
	
	/***
	 * 
	 * @param data
	 * @return 每个维度中位数
	 */
	public static <T extends Number> ArrayList<Double> findMedian(T[][] data){
		int rowSize  = data.length;
		int colSize = data[0].length;
		ArrayList<Double> median = new ArrayList<Double>(rowSize);
		T[] temp;
		double res;
		for (int i = 0; i < rowSize; i++) {
			temp = Arrays.copyOf(data[i], colSize);
			Arrays.sort(temp);
			if(colSize%2==0){//偶数长度，中位数
				res = (temp[colSize/2-1].doubleValue()+temp[colSize/2].doubleValue())/2.0;
			}else{
				res = temp[colSize/2].doubleValue();
			}

			median.add(res);
		}
		return median;
	}
	
	
	/***
	 * 
	 * @param data
	 * @return 每个列中位数
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Number> ArrayList<Double> findColMedian(T[][] data){
		int rowSize  = data.length;
		int colSize = data[0].length;
		ArrayList<Double>[] temp =new ArrayList[colSize];
		ArrayList<Double> median =new ArrayList<Double>(rowSize);
		double res;
		
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				if(temp[j]==null) temp[j]=new ArrayList<Double>();
				temp[j].add(data[i][j].doubleValue());
			}
		}
		
		for (int i = 0; i < colSize; i++) {
			Collections.sort(temp[i]);
			if(rowSize%2==0){//偶数长度，中位数
				res = (temp[i].get(rowSize/2-1)+temp[i].get(rowSize/2))/2.0;
			}else{
				res = temp[i].get(rowSize/2);
			}

			median.add(res);
		}
		return median;
	}
	
	
	/***
	 * 
	 * @param data
	 * @return 每个维度方差
	 */
	public static <T extends Number> ArrayList<Double> findVariance(T[][] data){
		int rowSize  = data.length;
		int colSize = data[0].length;
		ArrayList<Double> variance = new ArrayList<Double>(rowSize);
		T[] temp;
		
		
		for (int i = 0; i < rowSize; i++) {
			temp = Arrays.copyOf(data[i], colSize);
			variance.add(findVariance(temp));
		}
		return variance;
	}
	
	private static <T extends Number> Double findVariance(T[] data){
		Double average = findAverage(data);
		Double temp = 0.0;
		int counts  = data.length;
		for (int i = 0; i < counts; i++) {
			temp+=(data[i].doubleValue()-average)*(data[i].doubleValue()-average);
		}
		return (temp/counts);
	}
	
	
	/***
	 * 
	 * @param data
	 * @return 每个维度样本标准差
	 */
	public static <T extends Number> ArrayList<Double> findStdDev(T[][] data){
		int rowSize  = data.length;
		int colSize = data[0].length;
		ArrayList<Double> stdDev = new ArrayList<Double>(rowSize);
		T[] temp;
		
		for (int i = 0; i < rowSize; i++) {
			temp = Arrays.copyOf(data[i], colSize);
			stdDev.add(findStdDev(temp));
		}
		return stdDev;
	}
	
	
	private static <T extends Number> Double findStdDev(T[] data){
		Double average = findAverage(data);
		Double temp = 0.0;
		int counts  = data.length;
		for (int i = 0; i < counts; i++) {
			temp+=(data[i].doubleValue()-average)*(data[i].doubleValue()-average);
		}
		if(temp/counts == 0) return 0.0;
		return Math.sqrt((temp/counts-1));
	}
	
	/***
	 * 
	 * @param data
	 * @return 字符串转数组
	 */
	public static  Double[][] converTo(String[][] data){
		
		int rowSize  = data.length;
		int colSize = data[0].length;
		Double[][] res = new Double[rowSize][colSize];
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				res[i][j] = Double.parseDouble(data[i][j]);
			}
		}
		return res;
	}
	
	/***
	 * 
	 * @param data
	 * @return 转字符串
	 */
	public static <T extends Number> String[][] converTo(T[][] data){
		
		int rowSize  = data.length;
		int colSize = data[0].length;
		String[][] res = new String[rowSize][colSize];
		for (int i = 0; i < rowSize; i++) {
			for (int j = 0; j < colSize; j++) {
				res[i][j] = data[i][j].toString();
			}
		}
		return res;
	}
	
	
	
	public static void main(String[] args) {
		Double[][] aaa = { { 1.6, 3.8, 6.7, 78.0, 9.6 }, { 2.3, 3.6, 5.5, 7.7, 2.8 }, { 6.2, 84.3, 7.3, 7.2, 8.9 },
				{ 11111.1, 2222.2, 3.4, 6.2, 7.2 },{ 11.1, 62.2, 30.4, 6.2, 7.2 } ,{ 1.1, 2.2, 3.3, 4.4, 5.5 }
				,{ 1.0,1.0,1.0,1.0,1.0}};
		BigDecimal[][] bigs = new BigDecimal[aaa.length][aaa[0].length];
		for (int i = 0; i < aaa.length; i++) {
			for (int j = 0; j < aaa[0].length; j++) {
				bigs[i][j] = new BigDecimal(aaa[i][j]);
				
			}
		}
		
		
		ArrayList<Double> a = CalculateUtil.findStdDev(aaa);

		
		for (Object double1 : a) {
			System.out.println(double1);
		}
		
		
	}

}
