package image_transforms;

public class NeighborResult {
		private double[] positions;
		private double[][] neighbors;
		
		public NeighborResult(double[] positions, double[][] neighbors) {
			// TODO Auto-generated constructor stub
			
			this.positions = positions;
			this.neighbors = neighbors;
	}
		
		public double[] getPositions() {
			return positions; 
		}
		
		public double[][] getNeighbors(){
			return neighbors;
		}

}
