package com.company.cmpt276_asn3.model;

import java.util.ArrayList;
import java.util.List;

// Holds underlying 2D array representing the playable game field
public class GameField {
    // 2D array of cells in row major order
    // field[0] is first row, etc
    private List<List<Cell>> field = new ArrayList<>();

    // Life cycle follows:
    // Make game field -> populate array with cell objects -> randomly set mines -> update numMines
    // UI queries game obj about cell status
    // Update numMines as game progresses

    // May need separate interface class to interact with game field?
}
