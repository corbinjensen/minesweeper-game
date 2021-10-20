/*
    GamePlayActivity.java - Represents the screen where the user can play the game.
 */

package com.company.cmpt276_asn3.app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

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

        setUpNewGame();
        populateButtons();
        updateNumScanCounter();
        updateNumMinesCounter();
    }

    private void setUpNewGame(){
        options = Options.getInstance();
        game = new GameField(options.getNumRows(), options.getNumCols(), options.getNumMines());
        buttons = new Button[options.getNumRows()][options.getNumCols()];
        rand = new Random();
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

            updateNumMinesCounter();
            checkForWin();
        }
        updateButtonText();
        updateNumScanCounter();
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
                    String text = String.valueOf(cell.getNumMines());
                    button.setText(text);
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

    private void updateNumMinesCounter() {
        TextView textView = (TextView) findViewById(R.id.catCounterText);
        textView.setText(getString(R.string.mine_counter, game.getMineCounter(), options.getNumMines()));
    }

    private void updateNumScanCounter() {
        TextView textView = (TextView) findViewById(R.id.scanCounterText);
        textView.setText(getString(R.string.scan_counter, game.getScanCounter()));
    }

    private void checkForWin() {
        if (game.getMineCounter() == 0){
            // Call congratulation screen here!
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setCancelable(true);
            builder.setPositiveButton("YAY", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int i) {
                    dialog.dismiss();
                    Intent intent = MainActivity.makeIntent(GamePlayActivity.this);
                    startActivity(intent);
                }
            });
            builder.setMessage("Congrats, You Found All The Cats!!");
            builder.show();
        }
    }

    public static Intent makeIntent(Context context){
        return new Intent(context, GamePlayActivity.class);
    }
}
