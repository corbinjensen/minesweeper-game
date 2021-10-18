package com.company.cmpt276_asn3.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Holds 2D array of Cell obj in row major order representing the playable game field
public class GameField {
    private List<List<Cell>> field = new ArrayList<>();
    private int numRows;
    private int numCols;
    // TODO: make observer pattern to watch numMines and numScans and update appropriate text view
    private int numMines;
    private int numScans;

    public GameField(int height, int width, int numMines){
        this.numRows = height;
        this.numCols = width;
        this.numMines = numMines;
        this.numScans = 0;

        populateListWithCells();
        setMines(this.numMines);
        updateNumMines();
    }

    // Public interface methods

    public Cell getCell(int row, int col){
        return field.get(row).get(col);
    }

    // Note: May need change return type depending on use
    public void scanCell(int row, int col){
        Cell c = getCell(row, col);

        // 3 possible cases
        // Contains mine -> dont scan, remove mine and reveal cell
        // No mine -> reveal and scan
        // Cell already shown -> means used to have mine -> not scanned
        if (c.isContainsMine()){
            c.setShown(true);
            c.setContainsMine(false); // remove mine after finding
        }
        else {
            c.setScanned(true);
            c.setShown(true);
            incrementScans();
        }
    }

    private void incrementScans(){
        numScans++;
    }

    private void populateListWithCells(){
        for (int i = 0; i < numRows; i++){
            field.add(new ArrayList<>());

            for (int j = 0; j < numCols; j++){
                // Blank cell has no mine and no count
                Cell c = new Cell(false, false, false, 0);
                field.get(i).add(c);
            }
        }
    }

    private void setMines(int numMines){
        Random rand = new Random();
        int rowIndex;
        int colIndex;
        for (int i = 0; i < numMines; i++){
            boolean isMineSet = false;
            // Loop until a valid cell is set as a mine
            while (!isMineSet){
                rowIndex = rand.nextInt(numRows);
                colIndex = rand.nextInt(numCols);
                Cell c = getCell(rowIndex, colIndex);

                if (!c.isContainsMine()){
                    c.setContainsMine(true);
                    isMineSet = true;
                }
            }
        }
    }

    public void updateNumMines(){
        for (int i = 0; i < numRows; i++){
            int rowCount = countNumMinesInRow(i);
            for (int j = 0; j < numCols; j++){
                int colCount = countNumMinesInCol(j);
                field.get(i).get(j).setNumMines(rowCount + colCount);
            }
        }
    }

    private int countNumMinesInRow(int rowIndex){
        int count = 0;

        for (int i = 0; i < numCols; i++){
            Cell c = getCell(rowIndex, i);
            // Count all mines not already revealed
            if (c.isContainsMine() && c.isShown()){
                count++;
            }
        }
        return count;
    }

    private int countNumMinesInCol(int colIndex){
        int count = 0;

        for (int i = 0; i < numRows; i++){
            Cell c = getCell(i, colIndex);
            // Count all mines not already revealed
            if (c.isContainsMine() && c.isShown()){
                count++;
            }
        }
        return count;
    }
}
