package MatrixOps;
import java.util.ArrayList;
import java.util.List;

public class seriesOps {
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
	
	public seriesOps(double [][]mat1, double [][]mat2) { 
		
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
		
		// inicializa??o das matrizes
		initializeMatrix(m,n,this.soma);
		initializeMatrix(m,n,this.subt);
		initializeMatrix(m,q,this.prod);
		initializeMatrix(n,m,this.trans1);
		initializeMatrix(q, p, this.trans2);
	};
	
	public void initializeMatrix(int dimRows, int dimCols, double [][] matrix) {
		for (int i = 0; i < dimRows; i++) {
			for (int j = 0; j < dimRows; j++) {
				matrix[i][j] = 0.0;
			};
		};
	}
	
	public void transpMatrix(int dimRows, int dimCols, double[][] finalMatrix, double [][] entryMatrix) {
		for (int i =0; i < dimRows; i++) {
			for (int j = 0; j < dimCols; j++) {
				finalMatrix[i][j] = entryMatrix[j][i];
			};
		};
	}
	
	public void runSeriesOps(){
	// verifica se ? poss?vel somar ou subtrair as matrizes (dimens?es iguais)
		if (this.m != this.p || this.n!= this.q) {
			System.out.println("N?o ? poss?vel somar ou subtrair pois as dimens?es das matrizes s?o diferentes");
		} else {
			// como as matrizes possuem a mesma dimens?o nesse caso, mat1.length == mat2.length
			
			//soma 
			for (int i =0; i < this.mat1.length; i++) {
				for (int j = 0; j < this.mat1.length; j++) {
					this.soma[i][j] = this.mat1[i][j]+this.mat2[i][j];
				};
			};
			
			//subtra??o
			for (int i =0; i < this.mat1.length; i++) {
				for (int j = 0; j < this.mat1.length; j++) {
					this.subt[i][j] = this.mat1[i][j]-this.mat2[i][j];
				};
			};
		}
		
		if (n!=p) {
			System.out.println("N?o ? poss?vel multiplicar as matrizes pois o n?mero de colunas da primeira ? diferente do de linhas da segunda");
		} else {
			//multiplica??o 
			for (int i =0; i < this.m; i++) {
				for (int j = 0; j < this.q; j++) {
					for (int k = 0; k < this.n; k++) {
						this.prod[i][j] += this.mat1[i][k]*this.mat2[k][j];
					};
				};
			};
		};
		

		// transposi??o
		transpMatrix(this.n, this.m, this.trans1, this.mat1);
		
		transpMatrix(this.q, this.p, this.trans2, this.mat2);
				
	}
	
	
}
