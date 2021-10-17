package com.company.cmpt276_asn3.model;

// Interface for the GameField obj
public class GameManager {
    GameField game;

    // Singleton support
    private static GameManager instance;

    private GameManager(){
        // private constructor to prevent accidental instantiation
    }
    public static GameManager getInstance(){
        if (instance == null){
            instance = new GameManager();
        }
        return instance;
    }

    public void createNewGame(Options options){
        int height = options.getNumRows();
        int width = options.getNumCols();
        int numMines = options.getNumMines();

        game = new GameField(height, width, numMines);
    }
}
