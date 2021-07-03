package MatrixOps;

public class ThreadProd extends Thread {
	private int startRow;
	private int endRow;
	private int startCol;
	private int endCol;
	private double [][] mat1;
	private double [][] mat2;
	private double[][] partialMatrix;
	
	public ThreadProd(String name, int startRow, int endRow, int startCol, int endCol, 
			double [][] mat1, double [][] mat2, double [][] partialMatrix){
		super(name);
		this.partialMatrix = partialMatrix;
		this.mat1 = mat1;
		this.mat2 = mat2;
		this.startRow = startRow;
		this.endRow = endRow;
		this.startCol =startCol;
		this.endCol = endCol;	
	}
	
	public void run() {
		for (int i = startRow; i < Math.min(endRow, mat1.length); i++) {
			for (int j = startCol; j < Math.min(endCol, mat2.length); j++) {
				for (int k = 0; k < mat1.length; k++) {
					partialMatrix[i][j] += mat1[i][k]*mat2[k][j];
				}
			}
		}
	}
}
