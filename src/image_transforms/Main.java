package image_transforms;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method 

		
		double[][] arr = {{10,20}, {30,40}};
		
		arr = ResizeArray.resize2d(arr, 7);
		
		System.out.println(arr[1][9]);
		
		
		
		//System.out.println(Interpolation.getBilinearValue(arr, 0.6, 0.5 ));
		
//		System.out.println(arr.length);
//		System.out.println(arr[1][2]);
	}

}
