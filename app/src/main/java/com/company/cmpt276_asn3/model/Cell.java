package com.company.cmpt276_asn3.model;

// Object that stores information about each individual cell of game field
public class Cell {
    private boolean containsMine;
    private boolean isScanned;
    private int numMines;

    public Cell(boolean containsMine, boolean isScanned, int numMines) {
        this.containsMine = containsMine;
        this.isScanned = isScanned;
        this.numMines = numMines;
    }

    public boolean isContainsMine() {
        return containsMine;
    }

    public void setContainsMine(boolean containsMine) {
        this.containsMine = containsMine;
    }

    public boolean isScanned() {
        return isScanned;
    }

    public void setScanned(boolean scanned) {
        isScanned = scanned;
    }

    public int getNumMines() {
        return numMines;
    }

    public void setNumMines(int numMines) {
        this.numMines = numMines;
    }

    public void decrementNumMines() {
        this.numMines--;
    }
}
