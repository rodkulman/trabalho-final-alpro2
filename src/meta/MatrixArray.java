package meta;

public class MatrixArray 
{
	private int rows;
    private int cols;
    private double[] data;

    public MatrixArray()
    {
    	this(16, 16);
    }
    
    /**
     * Allocate a matrix with the indicated initial dimensions.
     * @param cols The column (horizontal or x) dimension for the matrix
     * @param rows The row (vertical or y) dimension for the matrix
     */
    public MatrixArray(int cols, int rows) 
    {
        this.rows = rows;
        this.cols = cols;
        data = new double[cols * rows];
    }

    public double get(int col, int row) 
    {
        return data[getIndex(col, row, cols)];
    }

    public void set(int col, int row, double value) 
    {
    	if (col > cols - 1 || row > rows - 1)
    	{
    		resize(cols * 2, row * 2);
    	}
    	
        data[getIndex(col, row, cols)] = value;
    }

    public double[][] getData()
    {
    	double[][] copy = new double[this.rows][this.cols];
    	
    	for (int i = 0; i < this.rows; i++) 
    	{
    		for (int j = 0; j < this.cols; j++) 
    		{
    			copy[i][j] = get(i, j);
    		}
		}
    	
    	return copy;
    }
    
    /**
     * Calculates the index of the indicated row and column for
     * a matrix with the indicated width. This uses row-major ordering
     * of the matrix elements.
     * <p>
     * Note that this is a static method so that it can be used independent
     * of any particular data instance.
     * @param col The column index of the desired element
     * @param row The row index of the desired element
     * @param width The width of the matrix
     */
    private static int getIndex(int col, int row, int width) 
    {
        return row * width + col;
    }
    
    /**
     * Resizes the matrix. The values in the current matrix are placed
     * at the top-left corner of the new matrix. In each dimension, if
     * the new size is smaller than the current size, the data are
     * truncated; if the new size is larger, the remainder of the values
     * are set to 0.
     * @param cols The new column (horizontal) dimension for the matrix
     * @param rows The new row (vertical) dimension for the matrix
     */
    private void resize(int cols, int rows) 
    {
    	double [] newData = new double[cols * rows];
        
        int colsToCopy = Math.min(cols, this.cols);
        int rowsToCopy = Math.min(rows, this.rows);
        
        for (int i = 0; i < rowsToCopy; ++i) 
        {
            int oldRowStart = getIndex(0, i, this.cols);
            int newRowStart = getIndex(0, i, cols);
            System.arraycopy(data, oldRowStart, newData, newRowStart, colsToCopy);
        }
        
        this.rows = rows;
        this.cols = cols;
        
        data = newData;
    }
}
