package image.resizing;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;

public class ResizeArray {

    /**
     * Resizes a 2D double array using bilinear or bicubic interpolation.
     * 
     * @param arr          the input 2D double array
     * @param scale        the scaling factor
     * @param interpolation the type of interpolation to use, either "bilinear" or "bicubic"
     * @return             the resized 2D double array
     */
    public static double[][] resize2d(double[][] arr, double scale, String interpolation) throws IllegalArgumentException{
        // Get the height and width of the input array
        final int H = arr.length;
        final int W = arr[0].length;
        // Define the padding size for the input array
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
        
        // Interpolate the empty values in the new array
        for (int i = 0; i < newH; i++) {
            for (int j = 0; j < newW; j++) {
                double value = newArr[i][j];
                // If the value is empty (i.e., 0), interpolate it
                if (value == 0) {
                    // Initialize interpolated value
                    double interpolatedValue = 0;
                    // Determine which interpolation method to use
                    if (interpolation.equals("bilinear")) {
                        // Find the 4 nearest neighbors and their positions
                        NeighborResult result = find4Neighbors(arr, i, j, (int) scale);
                        // Interpolate the value using bilinear interpolation
                        interpolatedValue = Interpolation.getBilinearValue(result.getNeighbors(), 
                                result.getPositions()[0], result.getPositions()[1]);
                    } else if (interpolation.equals("bicubic")) {
                        // Find the 16 nearest neighbors and their positions
                        NeighborResult result = find16Neighbors(arr, i, j, (int) scale);
                        // Interpolate the value using bicubic interpolation
                        interpolatedValue = Interpolation.getBicubicValue(result.getNeighbors(), 
                                result.getPositions()[0], result.getPositions()[1]);
                    } else {
                        throw new IllegalArgumentException("Interpolation must either be bilinear or bicubic."); 
                    }
                    // Set the interpolated value in the new array
                    newArr[i][j] = interpolatedValue;
                }
            }
        }      
        
        return newArr;
    }
    

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
	
	/**
	 * Finds the 4 nearest neighbors for bilinear interpolation.
	 * 
	 * @param originalImage the original image
	 * @param newX          the new x-coordinate
	 * @param newY          the new y-coordinate
	 * @param scale         the scaling factor
	 * @return the NeighborResult object containing the neighbor values and relative positions
	 */
	public static NeighborResult find4Neighbors(double[][] originalImage, int newX, int newY, int scale) throws IllegalArgumentException{
		
		if (originalImage.length < 2 || originalImage[0].length < 2) {
			throw new IllegalArgumentException("Input array must be at least 2x2");
		}
		
	    // Convert the new x and y coordinates to the original image coordinate system
	    double oldX = (double) newX / scale;
	    double oldY = (double) newY / scale;

	    // Calculate the lower and upper bounds for each coordinate
	    double lowerBoundX = Math.floor(oldX);
	    double upperBoundX = Math.floor(oldX) + 1;
	    double lowerBoundY = Math.floor(oldY);
	    double upperBoundY = Math.floor(oldY) + 1;

	    // Calculate the distances between the new coordinate and the bounds
	    double xDistance = (oldX - lowerBoundX) / (upperBoundX - lowerBoundX);
	    double yDistance = (oldY - lowerBoundY) / (upperBoundY - lowerBoundY);

	    // Create an array to hold the positions of the neighbors
	    double[] positions = {xDistance, yDistance};

	    // Create a 2D array to hold the neighbor values
	    double[][] neighbors = new double[4][4];

	    // Iterate over the 2x2 grid of neighbors
	    for (int i = 0; i < 2; i++) {
	        for (int j = 0; j < 2; j++) {

	            // Check if the neighbor is out of bounds
	            if ((lowerBoundX + i) > originalImage.length - 1 || (lowerBoundY + j) > originalImage[0].length - 1) {
	                // Set the neighbor value to zero if out of bounds
	                neighbors[i][j] = 0;
	            } else {
	                // Otherwise, get the value from the original image
	                int x = (int) (lowerBoundX + i);
	                int y = (int) (lowerBoundY + j);
	                neighbors[i][j] = originalImage[y][x];
	            }
	        }
	    }

	    // Create a new NeighborResult object with the positions and neighbor values
	    NeighborResult result = new NeighborResult(positions, neighbors);
	    return result;
	}
	

	/**
	 * Finds the 4 nearest neighbors for bilinear interpolation.
	 * 
	 * @param originalImage the original image
	 * @param newX          the new x-coordinate
	 * @param newY          the new y-coordinate
	 * @param scale         the scaling factor
	 * @return the NeighborResult object containing the neighbor values and relative positions
	 */
	public static NeighborResult find16Neighbors(double[][] originalImage, int newX, int newY, int scale) throws IllegalArgumentException{
		
		if (originalImage.length < 4 || originalImage[0].length < 4) {
			throw new IllegalArgumentException("Input array must be at least 4x4");
		}
		
		
		double oldX = (double)newX / scale;
		double oldY = (double) newY /scale;
		
		double lowerBoundX = Math.floor(oldX); 
		double upperBoundX = Math.floor(oldX) + 1;
		
		double lowerBoundY = Math.floor(oldY); 
		double upperBoundY = Math.floor(oldY) + 1;
		
		double xDistance = (oldX - lowerBoundX) / (upperBoundX - lowerBoundX);
		double yDistance = (oldY - lowerBoundY) / (upperBoundY - lowerBoundY);

		
		double[] positions = {xDistance, yDistance};
	
		double[][] neighbors = new double[4][4];
				
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
			
		
				if (((lowerBoundX + i - 1) > originalImage.length - 1)||
						((lowerBoundY + j - 1) > originalImage[0].length - 1) ||
						((lowerBoundX + i - 1) < 0) ||
						((lowerBoundY + j - 1) < 0)){
					neighbors[i][j] = 0;
				
					
				}else {
					
					int x = (int) (lowerBoundX + i - 1);
					int y = (int) (lowerBoundY + j - 1);
					neighbors[i][j] = originalImage[y][x]; 
				
				}	
			}
		}
		
		NeighborResult result = new NeighborResult(positions, neighbors);	
		return result;
		
	}
	
}
