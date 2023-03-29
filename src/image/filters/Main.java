package image.filters;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		double[][] input = {{70,1,200,150}, 
				{80,0,150,130}, 
				{20,20,170,200},
				{2,2,3,10}};
		
		double[][] filter = {{0.5,0.5,0.5},
				{0.5,0.5,0.5},
				{0.5,0.5,0.5}};
				
	
		double[][] output = Convolution.apply2DConvolution(input, Filter.sobelHor, "same");

		System.out.println(output[1][1]);
	} 

}

