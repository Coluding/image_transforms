package image.resizing;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method 

		
		double[][] arr = {{10,20}, {30,40}};
		
		double[][] arr4 = {{1,2,3,4},
				{5,6,7,8},
				{9,10,11,12},
				{13,14,15,16}
		};
		
		double[][] newArr = ResizeArray.resize2d(arr4, 2, "bicubic");
		
		System.out.println(newArr[0][7]);		
		
		//System.out.println(ResizeArray.find16Neighbors(arr4, 2,1, 2).getNeighbors()[0][0]);
		
		//System.out.println(Interpolation.getBilinearValue(arr, 0.6, 0.5 ));
		
//		System.out.println(arr.length);
//		System.out.println(arr[1][2]);
	}

}
