package image_transforms;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.Stream;
public class ResizeArray {

    /**
     * Resizes a 2D double array using bilinear interpolation.
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
        
        for (int i = 0; i < newH; i++) {
            for (int j = 0; j < newW; j++) {
                double value = newArr[i][j];
                
                if (value == 0) {
                	NeighborResult result = find4Neighbors(arr, i, j, (int) scale);
                	
                	double interpolatedValue = Interpolation.getBilinearValue(result.getNeighbors(), 
                			result.getPositions()[0], result.getPositions()[1]);
                	
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
	
	public static NeighborResult find4Neighbors(double[][] originalImage, int newX, int newY, int scale) {
		
		double oldX = (double)newX / scale;
		double oldY = (double) newY /scale;
		double[] positions = {oldX, oldY};
		
		System.out.println(oldX);
		System.out.println(oldY);
		System.out.println("-------------------");
		
		double[][] neighbors = new double[2][2];
				
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				System.out.println("oldX lower :");
				System.out.println((Math.floor(oldX) + i));
				
				if ((Math.floor(oldX) + i) > originalImage.length - 1||
						(Math.floor(oldY) + j) > originalImage[0].length - 1) {
					System.out.println(".............................");
					neighbors[i][j] = 0;
				
					
				}else {
					int x = (int) (Math.floor(oldX) + i);
					int y = (int) (Math.floor(oldY) + j);
					neighbors[i][j] = originalImage[y][x]; 
					System.out.println("original image");
					System.out.println(originalImage[x][y] );
				
				}	
			}
		}
		
		
		NeighborResult result = new NeighborResult(positions, neighbors);	
		return result;	
		
		
	}

}
