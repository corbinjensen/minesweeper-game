package com.company.cmpt276_asn3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Holds underlying 2D array representing the playable game field
public class GameField {
    // 2D array of cells in row major order
    private List<List<Cell>> field = new ArrayList<>();
    private int numRows;
    private int numCols;
    private int numMines;

    // Initialize 2D array here
    public GameField(int height, int width, int numMines){
        this.numRows = height;
        this.numCols = width;
        this.numMines = numMines;

        populateListWithCells();
        setMines(this.numMines);
        updateNumMines();
    }

    public Cell getCell(int row, int col){
        return field.get(row).get(col);
    }

    private void populateListWithCells(){
        for (int i = 0; i < numRows; i++){
            List<Cell> row = new ArrayList<>();

            for (int j = 0; i < numCols; j++){
                // Blank cell has no mine and no count
                Cell c = new Cell(false, false, 0);
                row.add(c);
            }
            field.add(row);
        }
    }

    private void setMines(int numMines){
        Random rand = new Random();
        int rowIndex;
        int colIndex;
        // Randomly sets mines in game field
        for (int i = 0; i < numMines; i++){
            rowIndex = rand.nextInt(numRows + 1);
            colIndex = rand.nextInt(numCols + 1);

            getCell(rowIndex, colIndex).setContainsMine(true);
        }
    }

    private void updateNumMines(){
        for (int i = 0; i < numRows; i++){
            int rowCount = countNumMinesInRow(i);
            for (int j = 0; j < numCols; j++){
                int colCount = countNumMinesInCol(j);
                field.get(i).get(j).setNumMines(rowCount + colCount);
            }
        }
    }

    private int countNumMinesInRow(int index){
        int count = 0;
        List<Cell> row = field.get(index);

        // Count all mines that are not revealed
        for (int i = 0; i < numCols; i++){
            if (row.get(i).isContainsMine() && !row.get(i).isShown()){
                count++;
            }
        }
        return count;
    }

    private int countNumMinesInCol(int index){
        int count = 0;

        for (int i = 0; i < numRows; i++){
            List<Cell> row = field.get(i);

            if (row.get(index).isContainsMine() && !row.get(index).isShown()){
                count++;
            }
        }

        return count;
    }
}
