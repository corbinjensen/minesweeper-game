package com.company.cmpt276_asn3.model;

// Singleton class to store user selected options
public class Options {
    private int numRows;
    private int numCols;
    private int numMines;

    // Singleton support
    private static Options instance;

    private Options(){
    }

    public static Options getInstance() {
        if (instance == null){
            instance = new Options();
        }
        return instance;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }
}
