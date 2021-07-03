package MatrixOps;
import java.util.Arrays;
import java.util.List;

public class MainOps {
	public static void main(String[] args) {
		int m = 6;
		int n = 6;
		int p = 6;
		int q = 6;
		
		double [][] mat1 = new double[m][n];
		double [][] mat2 = new double[p][q];

		
		// inicialização das matrizes iniciais
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				mat1[i][j] = i+j;
			};
		};
		
		for (int i = 0; i < p; i++) {
			for (int j = 0; j < q; j++) {
				mat2[i][j] = i-j;
			};
		};
		
		
		seriesOps newSeriesOps = new seriesOps(mat1, mat2);
		
		long startTimeSeries = System.currentTimeMillis();
	
		newSeriesOps.runSeriesOps();
		
		long stopTimeSeries = System.currentTimeMillis();
		
		long totalTimeSeries= (stopTimeSeries - startTimeSeries);
		
		ParallelOps newParallelOps = new ParallelOps(mat1, mat2);
		
		long startTimeParallel = System.currentTimeMillis();
	
		newParallelOps.runParallelOps();
		
		long stopTimeParallel = System.currentTimeMillis();
		
		long totalTimeParallel= (stopTimeParallel - startTimeParallel);
		
		System.out.println("Imprimindo os resultados:");
		System.out.println("Soma:");
		System.out.println(Arrays.deepToString(newSeriesOps.soma));
		System.out.println("Subtração:");
		System.out.println(Arrays.deepToString(newSeriesOps.subt));
		System.out.println("Multiplicação:");
		System.out.println(Arrays.deepToString(newSeriesOps.prod));
		System.out.println("Transposta da primeira matriz:");
		System.out.println(Arrays.deepToString(newSeriesOps.trans1));
		System.out.println("Transposta da segunda matriz:");
		System.out.println(Arrays.deepToString(newSeriesOps.trans2));
		System.out.println("Tempo de execução em série: " + totalTimeSeries + "ms");
		
		System.out.println("Soma paralela:");
		System.out.println(Arrays.deepToString(newParallelOps.soma));
		System.out.println("Subtração paralela:");
		System.out.println(Arrays.deepToString(newParallelOps.subt));
		System.out.println("Multiplicação paralela:");
		System.out.println(Arrays.deepToString(newParallelOps.prod));
		System.out.println("Tempo de execução paralela: " + totalTimeParallel + "ms");
		
		
	}

}
