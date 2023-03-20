package image_transforms;

/**
 * This class provides methods for interpolating values using cubic and bilinear interpolation.
 */
public class Interpolation {

    /**
     * Calculates the interpolated value of x using cubic interpolation, based on the four values in the array p.
     * 
     * @param p the array of four values
     * @param x the x-coordinate to interpolate at
     * @return the interpolated value at x
     */
    public static double getCubicValue(double[] p, double x) {
        // Compute the cubic interpolation using the formula for the spline
        return p[1] + 0.5 * x * (p[2] - p[0] + x * (2.0 * p[0] - 5.0 * p[1] + 4.0 * p[2] - p[3] + x * (3.0 * (p[1] - p[2]) + p[3] - p[0])));
    }

    /**
     * Calculates the interpolated value of (x, y) using bicubic interpolation, based on the 16 values in the 4x4 array p.
     * 
     * @param p the 4x4 array of values
     * @param x the x-coordinate to interpolate at
     * @param y the y-coordinate to interpolate at
     * @return the interpolated value at (x, y)
     */
    public static double getBicubicValue(double[][] p, double x, double y) throws IllegalArgumentException {
    	
    	if (p.length < 4 || p[0].length <4 ) {
    		throw new IllegalArgumentException("Array must be at least 4x4");
    	}
        // Calculate the interpolated value for each of the four rows using cubic interpolation
        double[] arr = new double[4];
        arr[0] = getCubicValue(p[0], y);
        arr[1] = getCubicValue(p[1], y);
        arr[2] = getCubicValue(p[2], y);
        arr[3] = getCubicValue(p[3], y);

        // Calculate the final interpolated value using cubic interpolation on the four row values
        return getCubicValue(arr, x);
    }

    /**
     * Calculates the interpolated value of x using linear interpolation, based on the two values in the array p.
     * 
     * @param p the array of two values
     * @param x the x-coordinate to interpolate at
     * @return the interpolated value at x
     */
    public static double getLinearValue(double[] p, double x) {
        // Compute the linear interpolation by finding the distance between the two points and multiplying by x
        return p[0] * (1 - x)  + x * p[1];
    }

    /**
     * Calculates the interpolated value of (x, y) using bilinear interpolation, based on the four values in the 2x2 array p.
     * 
     * @param p the 2x2 array of values
     * @param x the x-coordinate to interpolate at
     * @param y the y-coordinate to interpolate at
     * @return the interpolated value at (x, y)
     */
    public static double getBilinearValue(double[][] p, double x, double y) {
        // Calculate the interpolated value for each of the two rows using linear interpolation
        double[] arr = new double[2];
        arr[0] = getLinearValue(p[0], x);
        arr[1] = getLinearValue(p[1], x);

        // Calculate the final interpolated value using linear interpolation on the two row values
        return getLinearValue(arr, y);
    }
}