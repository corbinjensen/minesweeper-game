package com.company.cmpt276_asn3.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

import com.company.cmpt276_asn3.R;
import com.company.cmpt276_asn3.model.Cell;
import com.company.cmpt276_asn3.model.GameField;
import com.company.cmpt276_asn3.model.Options;

import java.util.Random;

public class GamePlayActivity extends AppCompatActivity {
    private Options options;
    private GameField game;
    private Random rand;

    Button[][] buttons;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_play);

        // TODO: Handle options creation for 0 parameters
        // TODO: Extract activity setup as a function and cleanup
        options = Options.getInstance();
        game = new GameField(options.getNumRows(), options.getNumCols(), options.getNumMines());
        buttons = new Button[options.getNumRows()][options.getNumCols()];
        rand = new Random();

        populateButtons();
    }

    private void populateButtons() {
        TableLayout tableOfCells = (TableLayout) findViewById(R.id.tableOfCells);

        for (int row = 0; row < options.getNumRows(); row++){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableLayout.LayoutParams(
                    TableLayout.LayoutParams.MATCH_PARENT,
                    TableLayout.LayoutParams.MATCH_PARENT,
                    1.0f
                    ));
            tableOfCells.addView(tableRow);

            // Fill TableRow with buttons
            for (int col = 0; col < options.getNumCols(); col++){
                // Copying for loop indices to pass as cell coordinates
                final int FINAL_ROW = row;
                final int FINAL_COL = col;

                // Create new button and set layouts
                Button button = new Button(this);
                button.setLayoutParams(new TableRow.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.MATCH_PARENT,
                        1.0f
                        ));

                // Make text fit on small buttons
                button.setPadding(0,0, 0,0);
                // Set default button image
                button.setBackgroundResource(R.drawable.bush);

                button.setOnClickListener(view -> cellButtonClicked(FINAL_ROW, FINAL_COL));

                tableRow.addView(button);
                // Add to button to array for later accessing
                buttons[row][col] = button;
            }
        }
    }

    public void cellButtonClicked(int row, int col){
        // NOTE: May not need to lock button size, all images same size
        lockButtonSizes();
        Button button = buttons[row][col];

        if (game.scanCellForMine(row, col)){
            // draw image and make it fit within button
            int newWidth = button.getWidth();
            int newHeight = button.getHeight();
            Bitmap originalBitmap = getRandomImage();
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, newWidth, newHeight, true);
            Resources resource = getResources();
            button.setBackground(new BitmapDrawable(resource, scaledBitmap));

            // TODO: Update mine counter textView here
        }
        updateButtonText();
        // TODO: Update total scan counter textView here
    }

    private Bitmap getRandomImage() {
        int randNum = rand.nextInt(3);
        Bitmap originalBitmap;

        if (randNum == 0){
            originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.grey_cat_found);
        }
        else if (randNum == 1){
            originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.yellow_cat_found);
        }
        else{
            originalBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.orange_cat_found);
        }
        return originalBitmap;
    }

    private void updateButtonText() {
        for (int row = 0; row < options.getNumRows(); row++){
            for (int col = 0; col < options.getNumCols(); col++){
                Button button = buttons[row][col];
                Cell cell = game.getCell(row, col);

                if (cell.isScanned()){
                    button.setText("" + cell.getNumMines());
                }
            }
        }
    }

    private void lockButtonSizes() {
        for (int row = 0; row < options.getNumRows(); row++){
            for (int col = 0; col < options.getNumCols(); col++){
                Button button = buttons[row][col];

                int width = button.getWidth();
                int height = button.getHeight();

                button.setMinWidth(width);
                button.setMaxWidth(width);
                button.setMinHeight(height);
                button.setMaxHeight(height);
            }
        }
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, GamePlayActivity.class);
    }
}
