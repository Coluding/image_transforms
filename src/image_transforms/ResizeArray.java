package image_transforms;

import java.lang.reflect.Array;
import java.util.Arrays;
public class ResizeArray {

    /**
     * Resizes a 2D double array using bicubic interpolation.
     * 
     * @param arr   the input 2D double array
     * @param scale the scaling factor
     * @return the resized 2D double array
     */
    public static double[][] resize2d(double[][] arr, double scale) {
        final int H = arr.length;
        final int W = arr[0].length;
        final int paddingSize = 2;

        // Calculate the new height and width of the array
        int newH = (int) Math.round(H * scale);
        int newW = (int) Math.round(W * scale);

        // Create a new 2D double array with the new dimensions
        double[][] newArr = new double[newH][newW];

        // Copy the values from the input array to the new array
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                double value = arr[i][j];
                newArr[(int) Math.round(i * scale)][(int) Math.round(j * scale)] = value;
            }
        }

        // Pad the array with zeros
        newArr = padding2d(newArr, paddingSize);

        // Interpolate the missing values using bicubic interpolation
        for (int i = paddingSize; i < newH + paddingSize; i++) {
            for (int j = paddingSize; j < newW + paddingSize; j++) {
                double value = newArr[i][j];

                // Check if value needs to be interpolated
                if (value == 0) {

                    // Create 4x4 subarray of neighbors
                    double[][] neighbors = new double[4][4];
                    for (int ii = 0; ii < neighbors.length; ii++) {
                        neighbors[ii] = Arrays.copyOfRange(newArr[ii], j - 2, j + 2);
                    }

                    // Interpolate the value using bicubic interpolation
                    double interpolateValue = BicubicInterpolator.getValue(neighbors, i, j);
                    newArr[i][j] = interpolateValue;
                }
            }
        }

        // Unpad the array
        double[][] finalArray = new double[newH][newW];
        for (int i = 0; i < newH; i++) {
            for (int j = 0; j < newW; j++) {
                finalArray[i][j] = newArr[i + paddingSize][j + paddingSize];
            }
        }

        return finalArray;
    }

    /**
     * Pads a 2D double array with zeros.
     * 
     * @param arr         the input 2D double array
     * @param paddingSize the size of the padding
     * @return the padded 2D double array
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
