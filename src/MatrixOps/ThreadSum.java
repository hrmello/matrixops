package MatrixOps;

public class ThreadSum extends Thread {
	private int row;
	private double [][] mat1;
	private double [][] mat2;
	private double[][] partialMatrix;
	
	public ThreadSum(String name, int row, 
			double [][] mat1, double [][] mat2, double [][] partialMatrix){
		super(name);
		this.row = row;
		this.partialMatrix = partialMatrix;
		this.mat1 = mat1;
		this.mat2 = mat2;
	}
	
	public void run() {
		for (int j = 0; j < mat1.length; j++) {
			partialMatrix[row][j] = mat1[row][j]+mat2[row][j];
		}
	}
}
