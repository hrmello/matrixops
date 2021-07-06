package MatrixOps;

import java.util.ArrayList;
import java.util.List;


public class ParallelOps {
	private int m;
	private int n;
	private int p;
	private int q;
	private double [][] mat1;
	private double [][] mat2;
	public double [][] soma;
	public double [][] subt;
	public double [][] prod;
	public double [][] transp1;
	public double [][] transp2 ;
	
	public ParallelOps(double [][]mat1, double [][]mat2) { 
			
		int m = mat1.length;
		int n = mat1[0].length;
		int p = mat2.length;
		int q = mat2[0].length;
		
		this.m = m;
		this.n = n;
		this.p = p;
		this.q = q;
		
		this.mat1 = mat1;
		this.mat2 = mat2;
		this.soma = new double[m][n];
		this.subt = new double[m][n];
		this.prod = new double[m][q];
		this.transp1 = new double[n][m];
		this.transp2 = new double[q][p];
		
		// inicialização das matrizes
		initializeMatrix(m,n,this.soma);
		initializeMatrix(m,n,this.subt);
		initializeMatrix(m,q,this.prod);
		initializeMatrix(n,m,this.transp1);
		initializeMatrix(q, p, this.transp2);
	};
		
	public void initializeMatrix(int dimRows, int dimCols, double [][] matrix) {
		for (int i = 0; i < dimRows; i++) {
			for (int j = 0; j < dimRows; j++) {
				matrix[i][j] = 0.0;
			};
		};
	}
	
	public void runParallelOps() {
		
		// listas contendo as threads
		List<ThreadSum> threadsSum = new ArrayList<ThreadSum>();
		List<ThreadSubt> threadsSubt = new ArrayList<ThreadSubt>();
		List<ThreadProd> threadsProd = new ArrayList<ThreadProd>();
		List<ThreadTransp> threadsTransp = new ArrayList<ThreadTransp>();
		
		if (m!=p || n!= q) {
			System.out.println("Não é possível somar ou subtrair pois as dimensões das matrizes são diferentes");
		} else {
			// soma, subtração e transposta são paralelizadas por linhas (uma linha por núcleo)
			for (int row = 0; row < this.m; row++) {
				threadsSum.add(new ThreadSum("Thread " + row, row, mat1, mat2, soma));
				threadsSubt.add(new ThreadSubt("Thread " + row, row, mat1, mat2, subt));
				threadsTransp.add(new ThreadTransp("Thread " + row, row, mat1, transp1));
				threadsTransp.add(new ThreadTransp("Thread " + row + " 2", row, mat2, transp2));
				
			};
		};
		
		// etapas para multiplicação
		int numberOfSlicesM1 = 2; // dividir a matriz 1 em numberOfSlicesM1 partes 
		int numberOfSlicesM2 = 2; // dividir a matriz 2 em numberOfSlicesM2 partes 
		
		int stepsRow = 0;
		int stepsCol = 0;
		
		if (mat1.length%2 == 0) {
			stepsRow = Math.round(mat1.length/numberOfSlicesM1);
		} else {
			stepsRow = Math.round(mat1.length/numberOfSlicesM1) + 1;
		};
		
		if (mat2.length%2 == 0) {
			stepsCol = Math.round(mat2.length/numberOfSlicesM2);
		} else {
			stepsCol = Math.round(mat2.length/numberOfSlicesM2) + 1;
		};
		
		System.out.println("steps Row e steps Col" + " " + stepsRow + " " + stepsCol);
		int threadNum = 0;
		for (int rowMult = 0; rowMult < numberOfSlicesM1; rowMult++) {
			for (int colMult = 0; colMult < numberOfSlicesM2; colMult++) {
				System.out.println("startRow = " + stepsRow*rowMult);
				System.out.println("endRow = " + stepsRow*(rowMult+1));
				System.out.println("startCol = " + stepsCol*colMult);
				System.out.println("endCol = " + stepsCol*(colMult+1));
				System.out.println("--------------------------------");
				threadsProd.add(new ThreadProd("Thread " + threadNum, stepsRow*rowMult, stepsRow*(rowMult+1), stepsCol*colMult, stepsCol*(colMult+1), mat1, mat2, prod));
				threadNum++;
			}
		}
		
		// start e join das threads
		for ( ThreadSum thread : threadsSum) {
			thread.start();
		};
		
		for ( ThreadSubt thread : threadsSubt) {
			thread.start();
		};
		
		for ( ThreadProd thread : threadsProd) {
			thread.start();
		};
		
		for ( ThreadTransp thread : threadsTransp) {
			thread.start();
		};
		
		for ( ThreadSum thread : threadsSum) {
			try {thread.join();} catch (Exception e) {System.out.println(e);}
		};
		
		for ( ThreadSubt thread : threadsSubt) {
			try {thread.join();} catch (Exception e) {System.out.println(e);}
		};
		
		for ( ThreadProd thread : threadsProd) {
			try {thread.join();} catch (Exception e) {System.out.println(e);}
		};
		
		for ( ThreadTransp thread : threadsTransp) {
			try {thread.join();} catch (Exception e) {System.out.println(e);}
		};
		
	};
}
