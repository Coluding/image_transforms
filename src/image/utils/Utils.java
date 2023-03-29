package image.utils;

public class Utils {
	
	 /**
     * Pads a 2D double array with zeros.
     * 
     * @param arr         the input 2D double array
     * @param paddingSize the size of the padding
     * @return            the padded 2D double array
     */
	public static double[][] padding2d(double[][] arr, int paddingSize){
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
