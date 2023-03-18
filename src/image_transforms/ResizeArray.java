package image_transforms;

import java.lang.reflect.Array;
import java.util.Arrays;

public class ResizeArray {
	
	private double scale;
	
	public ResizeArray(double scale) {
		// TODO Auto-generated constructor stub
		this.scale = scale;
	}
	
	public double[][] resize2d(double[][] arr){
		final int H = arr.length;
		final int W = arr[0].length;
		final int paddingSize = 2;
		
		
		int newH = (int) Math.round(H * scale);
		int newW = (int) Math.round(W * scale);
		
		double[][] newArr = new double[newH][newW];
		
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				double value = arr[i][j];
				newArr[(int) Math.round(i * scale)][(int) Math.round(j * scale)] = value;
			}
		}
		newArr = padding2d(newArr, paddingSize);
		

		
		for (int i = paddingSize; i < newH + paddingSize; i++) {
			for (int j = paddingSize; j < newW + paddingSize; j++) {	
				double value = newArr[i][j];
				
				// check if value needs to be interpolated^
				System.out.println(value);
				if (value == 0) {
					
					// create subarray
					double[][] neighbors = new double[4][4];
					for (int ii = 0; ii < neighbors.length; ii++) {
						System.out.println("--------------------");
						System.out.println(i);
						System.out.println(ii);
						System.out.println(j);
						System.out.println(newArr[ii]);
						neighbors[ii]  = Arrays.copyOfRange(newArr[ii], j - 2, j + 2);
					}
					
					double interpolateValue = BicubicInterpolator.getValue(neighbors, i,  j);
					newArr[i][j] = interpolateValue;
				}
				
				
				
			}
		}
		
		//unpad the array
		
		double[][] finalArray = new double[newH][newW];
		

		for (int i = 0; i < newH; i++) {
			for (int j = 0; j < newW; j++) {
				finalArray[i][j] = newArr[i + paddingSize][j + paddingSize];		
			}
		}
		
		return finalArray;
	}
	
	public double[][] padding2d(double[][] arr, int paddingSize){
		final int H = arr.length;
		final int W = arr[0].length;
		
		double[][] newArr = new double[H + 2 *  paddingSize][W + 2 * paddingSize];
		
		for (int i = 0; i < H; i++) {
			for (int j = 0; j < W; j++) {
				double value = arr[i][j];
				newArr[i + paddingSize][j + paddingSize] = value;
			}
		}
		
		return newArr;
		
	}

}
