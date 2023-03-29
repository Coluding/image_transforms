package image.filters;


public class Filter {
	
	public static double[][] edgeDetectionVert = {{-1,0,1},
			{-1,0,1},
			{-1,0,1}};
	
	public static double[][] edgeDetectionHor = {{1,1,1},
			{0,0,0},
			{-1,-1,-1}};
	
	public static double[][] sobelVert = {{-1,0,1},
			{-2,0,2},
			{-1,0,1}};
	
	public static double[][] sobelHor = {{1,2,1},
			{0,0,0},
			{-1,-2,-1}};
	
	
	
	

}
