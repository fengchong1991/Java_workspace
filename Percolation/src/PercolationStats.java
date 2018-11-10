import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

	private static final double CONFIDENCE = 1.96;
	private final int trails;
	private final double[] means;
	
	private double mean;
	private double stdDev;
	private double confiLow;
	private double confiHi;
	
    public PercolationStats(int n, int trials) {
    	
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        
    	this.trails = trials;
    	this.means = new double[trials]; 
    	
    	for (int i = 0; i < trials; i++) {
    		
    		Percolation model = new Percolation(n);
    		int pointer = 0;
    		while (!model.percolates()) {
    			
    			int row = StdRandom.uniform(1, n + 1);
    			int col = StdRandom.uniform(1, n + 1);
    			
    			model.open(row, col);
    			pointer++;
    		}
    		
    		means[i] = (double) model.numberOfOpenSites()/(n*n);
    	}
    	
    	mean = StdStats.mean(means);
    	stdDev = StdStats.stddev(means);
    	confiLow = mean-CONFIDENCE*stdDev/Math.sqrt(trails);
    	confiHi = mean+CONFIDENCE*stdDev/Math.sqrt(trails);
    }
    
    public double mean() {
    	return mean;
    }
    
    public double stddev() {
    	return stdDev;
    }
    
    public double confidenceLo() {
    	return confiLow;
    }
    
    public double confidenceHi() {
    	return confiHi;
    }
       
    public static void main(String[] args) {
    	//
    }
}
