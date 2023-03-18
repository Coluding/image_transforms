package image_transforms;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method 

		ResizeArray resize = new ResizeArray(2);
		
		double[][] arr = {{100,20}, {300,40}};
		
		arr = resize.resize2d(arr);
		
		
		System.out.println(arr.length);
		System.out.println(arr[2][3]);
	}

}
