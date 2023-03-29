package image.filters;

import image.utils.Utils;

public class Convolution {
	
	
	/**
	 * Applies 2D convolution operation on the input matrix using the given filter matrix.
	 *
	 * @param input   the input matrix
	 * @param filter  the filter matrix
	 * @param padding the padding type, "same" is the only option currently supported
	 * @return the output matrix after convolution
	 * @throws IllegalArgumentException if input array is smaller than filter
	 */
	public static double[][] apply2DConvolution(double[][] input, double[][] filter, String padding) {

	    if (input.length < filter.length || input[0].length < filter.length) {
	        throw new IllegalArgumentException("Input array is smaller than filter!");
	    }

	    // Pad the input if padding type is "same"
	    switch (padding) {
	        case "same":
	            input = Utils.padding2d(input, 1);
	            break;
	        default:
	            break;
	    }

	    // Calculate the output matrix size
	    int outputRows = input.length - filter.length + 1;
	    int outputCols = input[0].length - filter.length + 1;

	    // Create the output matrix
	    double[][] output = new double[outputRows][outputCols];

	    // Apply convolution on each position in the output matrix
	    for (int i = 0; i < outputRows; i++) {
	        for (int j = 0; j < outputCols; j++) {
	            output[i][j] = applyOneConvOperation(input, i, j, filter);
	        }
	    }

	    return output;
	}

	/**
	 * Applies one convolution operation on the input matrix at the given position using the given filter matrix.
	 *
	 * @param input  the input matrix
	 * @param x      the row index of the top-left corner of the filter
	 * @param y      the column index of the top-left corner of the filter
	 * @param filter the filter matrix
	 * @return the result of convolution operation
	 */
	private static double applyOneConvOperation(double[][] input, int x, int y, double[][] filter) {

	    double result = 0;

	    // Iterate over each element in the filter and multiply with the corresponding element in the input matrix
	    for (int i = 0; i < filter.length; i++) {
	        for (int j = 0; j < filter[0].length; j++) {
	            // Get the corresponding element from the input matrix
	            double inputValue;
	            try {
	                inputValue = input[x + i][y + j];
	            } catch (IndexOutOfBoundsException e) {
	                // If the position is outside the input matrix, assume input value to be zero
	                inputValue = 0;
	            }
	            // Multiply the filter element with the input element and add to the result
	            result += filter[i][j] * inputValue;
	        }
	    }

	    return result;
	}
}
