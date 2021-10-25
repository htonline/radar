package com.example.helper;

import android.util.Log;

public class MatrixOperation {
	public static int[][] add(int[][] matrix_a, int[][] matrix_b){  
        int row = matrix_a.length;  
        int col = matrix_a[0].length;  
        int[][] result = new int[row][col];  
          
        if(row != matrix_b.length || col != matrix_b[0].length){  
            System.out.println("Fault");  
        }  
        else{  
            for(int i=0;i<row;i++){  
                for(int j=0;j<col;j++){  
                    result[i][j] = matrix_a[i][j] + matrix_b[i][j];  
                }  
            }  
        }  
        return result;  
    }  
	
	public static double[][] sub(double[][] matrix_a, double[][] matrix_b){  
        int row = matrix_a.length;  
        int col = matrix_a[0].length;  
        double[][] result = new double[row][col];  
          
        if(row != matrix_b.length || col != matrix_b[0].length){  
            System.out.println("Fault");  
        }  
        else{  
            for(int i=0;i<row;i++){  
                for(int j=0;j<col;j++){  
                    result[i][j] = matrix_a[i][j] - matrix_b[i][j];  
//                    Log.e("%%%%%%%%%%%%%%%", i+"лл"+j+"┴л:"+result[i][j]);
                }  
            }  
        }  
        return result;  
    }  
	
	 public static int[][] dot(int[][] matrix_a, int[][] matrix_b){  
	        /* 
	            matrix_a's dimention m*p matrix_b's dimention p*n. 
	            return dimention m*n 
	        */  
	        int row = matrix_a.length;  
	        int col = matrix_a[0].length;  
	        int[][] result = new int[row][col];  
	          
	        if(col != matrix_b.length){  
	            System.out.println("Fault");  
	        }  
	        else{  
	            for(int i=0;i<row;i++){  
	                for(int j=0;j<col;j++){  
	                    result[i][j] = 0;  
	                    for (int k=0;k<col;k++){  
	                        result[i][j] += matrix_a[i][k] * matrix_b[k][j];  
	                    }  
	                }  
	            }  
	        }  
	        return result;  
	    }  

}
