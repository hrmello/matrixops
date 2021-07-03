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
	public double [][] trans1;
	public double [][] trans2 ;
	
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
		this.trans1 = new double[n][m];
		this.trans2 = new double[q][p];
		
		// inicialização da matriz de soma
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				this.soma[i][j] = 0.0;
			};
		};
		
		// inicialização da matriz de subtração
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				this.subt[i][j] = 0.0;
			};
		};
		
		// inicialização da matriz de produto
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < q; j++) {
				this.prod[i][j] = 0.0;
			};
		};
		
		// inicialização da matriz transposta 1
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < m; j++) {
				this.trans1[i][j] = 0.0;
			};
		};
		
		// inicialização da matriz transposta 2
		for (int i = 0; i < q; i++) {
			for (int j = 0; j < p; j++) {
				this.trans2[i][j] = 0.0;
			};
		};
	};
		
	public void runParallelOps() {
		
		// listas contendo as threads
		List<ThreadSum> threadsSum = new ArrayList<ThreadSum>();
		List<ThreadSubt> threadsSubt = new ArrayList<ThreadSubt>();
		List<ThreadProd> threadsProd = new ArrayList<ThreadProd>();
		
		if (m!=p || n!= q) {
			System.out.println("Não é possível somar ou subtrair pois as dimensões das matrizes são diferentes");
		} else {
			// soma e subtração são paralelizadas por linhas (uma linha por núcleo)
			for (int row = 0; row < this.m; row++) {
				threadsSum.add(new ThreadSum("Thread " + row, row, mat1, mat2, soma));
				threadsSubt.add(new ThreadSubt("Thread " + row, row, mat1, mat2, subt));
			};
		};
		int numberOfSlicesM1 = 3; // dividir a matriz 1 em numberOfSlicesM1 partes 
		int numberOfSlicesM2 = 3;
		
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
		
		for ( ThreadSum thread : threadsSum) {
			thread.start();
		};
		
		for ( ThreadSubt thread : threadsSubt) {
			thread.start();
		};
		
		for ( ThreadProd thread : threadsProd) {
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
		
	};
}
