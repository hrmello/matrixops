package MatrixOps;

public class ThreadTransp extends Thread {
	private int row;
	private double [][] mat;
	private double[][] partialMatrix;
	
	public ThreadTransp(String name, int row, 
			double [][] mat, double [][] partialMatrix){
		super(name);
		this.row = row;
		this.partialMatrix = partialMatrix;
		this.mat = mat;
	}
	
	public void run() {
		for (int j = 0; j < mat[0].length; j++) {
			partialMatrix[j][row] = mat[row][j];
		}
	}
}

