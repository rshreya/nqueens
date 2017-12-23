/*
 * NQueens.java
 *
 * Author: Shreya Radesh (sradesh@ucsc.edu). 1/24/2017
 *
 * This program places n queens on an n by n chessboard such that no queen attacks another. It then writes the coordinates of each
 * queen to a file, solution.txt. If there is no solution, program prints "no solution" to solution.txt.
 *
 * The program accepts command line arguments for n (the size of the chessboard) and the coordinates of the first queen.
 *
 */

import java.io.*;
import java.util.Scanner;
//package nQueens;

class NQueens {
	boolean[][] board;
	int size;
	int givenCol;
	int givenRow;

	//col and row are indexed from 1 as per user input
	//void setUp sets up the board, initializes key variables, and runs the nQueens() method
	void setUp(int n, int inCol, int inRow) throws IOException{

		PrintWriter out = new PrintWriter(new FileWriter("solution.txt"));


		//initialize board
		board = new boolean[n][n];
		//initialize size
		size = n;
		//initialize givenCol
		givenCol = inCol-1; //col -1 so that givenCol is 0 indexed
		//initialize givenRow
		givenRow = inRow-1; //row -1 so that givenRow is 0 indexed;


		//set entire board to false
		for (int i = 0; i < size; i++){
			for (int j = 0; j < size; j++){
				board[i][j] = false;
			}
		}


		//put in the first queen
		placeQueen(givenCol, givenRow);



		//run nQueens() on n


			if (nQueens(n)== false){
				out.println("No Solution");
			} else {
				for (int i = size-1; i >= 0; i--){
					for (int j = 0; j < size; j++){
						if (board[j][i] == true){
							//System.out.println((j+1) + " " + (i+1));
							out.println((j+1) + " " + (i+1));
						} else {
							//System.out.print("F");
						}
					}
					//System.out.println();
				}
			}
			out.close();
	}

	//boolean nQueens() is a recursive method that returns true if it is possible to place n queens on an n by n chessboard given the coordinate of the first queen
	//and false otherwise.
	boolean nQueens(int n){
		int boardCol = size - n;

		if (n==0){
			return true;
		} else {
			if (boardCol == givenCol){
				return nQueens(n-1);
			} else {
				for (int k=0; k < size; k++){


					if (isAttacking(boardCol,k) == true){ //use isAttacking() to check if a queen can be placed at that spot
						if (k == (size-1)){
							return false;
						}
					} else {
						placeQueen(boardCol, k);

						if (nQueens(n-1)== true){
							return true;
						}
						removeQueen(boardCol,k);


					}
				}
				return false;
			}

		}
	}



	//checks if a queen placed in that row and column would be attacking any other queen
	//returns true if another queen is being attacked, false otherwise
	// parameters row and col are indexed from 0
	boolean isAttacking(int col, int row) {

		//check if another queen in same row
		//check if another queen in same col

		for (int i = 0; i < size; i++){
			if (board[col][i] == true && i!= row){
				return true;
			} else if (board[i][row] ==  true && i!= col){
				return true;
			}
		}
		//check if another queen in same diag (left to right/negative slope)
		//check if another queen in same diag (right to left/positive slope)
		for (int i = 1; i < size; i++) {

			// any queens in the diag to the upper right
			if (((i + col) <= size - 1) && ((i + row) <= size - 1)) {
				if (board[col + i][row + i] == true) {
					return true;
				}
			}

			// check if any queens in diag to the lower left
			if (((col - i) >= 0) && (((row - i) >= 0))) {
				if (board[col - i][row - i] == true) {
					return true;
				}
			}

			// check if any queens in diag to the upper left
			if (((col - i) >= 0) && (((i + row) <= size - 1))) {
				if (board[col - i][row + i] == true) {
					return true;
				}
			}

			// check if any queens in diag to the lower right
			if (((col + i) <= size - 1) && (((row - i) >= 0))) {
				if (board[col + i][row - i] == true) {
					return true;
				}
			}
		}
		return false;
	}

	//removes queen in the row and column passed in via the parameters by setting that spot in board to false
	//Parameters row and col are indexed from 0.
	void removeQueen(int col, int row) {
		board[col][row] = false;

	}

	//places a queen in the row and column passed in via the parameters by setting that spot in board to true
	//Parameters row and col are indexed from 0.
	void placeQueen(int col, int row) {
		board[col][row] = true;
	}

	//prints the board for testing purposes
	void testPrint(){
		for (int i = size-1; i >= 0; i--){
			for (int j = 0; j < size; j++){
				if (board[j][i] == true){
					System.out.print("T");
				} else {
					System.out.print("F");
				}
			}
			System.out.println();
		}
	}


	//main method!
	public static void main(String[]args) throws IOException{
		int n;
		int inCol;
		int inRow;
    NQueens nq = new NQueens();
    n = Integer.parseInt(args[0]);
		inCol = Integer.parseInt(args[1]);
		inRow = Integer.parseInt(args[2]);
		nq.setUp(n,inCol,inRow);

	}
}
