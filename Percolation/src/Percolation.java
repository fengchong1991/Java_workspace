

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    
	private final WeightedQuickUnionUF weightQU;
	private final WeightedQuickUnionUF weightQU2;
	private boolean[] weightQUStatus;
	private int numOfOpenSites;
	private final int size;
	private final int topVirtualIndex;
	private final int bottomVirtualIndex;
	
	// Create n by n grid
	public Percolation(int n) {
		
		if (n <= 0) {
			throw new java.lang.IllegalArgumentException();
		}
		
		// Add two virtual node to the graph
		weightQU = new WeightedQuickUnionUF(n*n+2);
		weightQU2 = new WeightedQuickUnionUF(n*n+2);
		
		weightQUStatus = new boolean[n*n];
		
		numOfOpenSites = 0;
		size = n;
		topVirtualIndex = n*n;
		bottomVirtualIndex = n*n+1;
	}
	
	// Open site (row, col) if it is not open already
	public void open(int row, int col) {
		
		checkIndex(row, col);
		int index = getIndex(row, col);
		
		if (!isOpen(row, col)) {
			weightQUStatus[index] = true;
			numOfOpenSites++;
			
		 	// Union this with the surrounding sites
			
			// Union left
			if (col > 1 && isOpen(row, col-1)) {
				weightQU.union(index, index-1);
				weightQU2.union(index, index-1);
			}
			
			// Union right
			if (col < size && isOpen(row, col+1)) {
				weightQU.union(index, index+1);
				weightQU2.union(index, index+1);
			}
			
			// Union top
			if (row > 1 && isOpen(row-1, col)) {
				weightQU.union(index, index-size);
				weightQU2.union(index, index-size);
			}
			
			// Union bottom
			if (row < size && isOpen(row+1, col)) {
				weightQU.union(index, index+size);
				weightQU2.union(index, index+size);
			} 
			
			if (row == 1) {
				weightQU.union(index, topVirtualIndex);
				weightQU2.union(index, topVirtualIndex);
			}
			
			if (row == size) {
				weightQU.union(index, bottomVirtualIndex);
			}
		}				
	}
	
	// Is site (row, col) open?
	public boolean isOpen(int row, int col) {
		checkIndex(row, col);
		return weightQUStatus[getIndex(row, col)];
	}
	
	// Is site (row, col) full?
	public boolean isFull(int row, int col) {
		checkIndex(row, col);
		return weightQU2.connected(topVirtualIndex, getIndex(row, col));		
	}
	
	// Number of open sites
	public int numberOfOpenSites() {
		return numOfOpenSites;
	}
	
	// Does the system percolate?
	public boolean percolates() {
		return weightQU.connected(topVirtualIndex, bottomVirtualIndex);		
	}
	
	private int getIndex(int row, int col) {
		return (row-1)*size+(col-1);
	}
	
	private void checkIndex(int row, int col) {
		if (row < 1 || row > size || col < 1 || col > size) {
			throw new java.lang.IllegalArgumentException();
		}
	}
	
    public static void main(String[] args) {
    	//
/*    	Percolation model = new Percolation(1);
    	model.open(1, 1);
    	System.out.print(model.percolates());*/
    }
}
