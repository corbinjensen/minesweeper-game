package com.company.cmpt276_asn3.model;

import java.util.ArrayList;
import java.util.List;

// Holds underlying 2D array representing the playable game field
public class GameField {
    // 2D array of cells in row major order
    private List<List<Cell>> field = new ArrayList<>();
    private int numRows;
    private int numCols;

    // Initialize 2D array here
    public GameField(int height, int width){
        this.numRows = height;
        this.numCols = width;

        populateListWithCells();
    }

    public Cell getCell(int row, int col){
        return field.get(row).get(col);
    }

    private void populateListWithCells(){

    }

    private void setMines(){

    }

    private void updateNumMines(){

    }


}
