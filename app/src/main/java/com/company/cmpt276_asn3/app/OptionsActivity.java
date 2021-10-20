/*
    OptionsActivity.java - Represents where the user can adjust settings to change the difficulty of
    the game.
 */
package com.company.cmpt276_asn3.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.company.cmpt276_asn3.R;
import com.company.cmpt276_asn3.model.Options;

public class OptionsActivity extends AppCompatActivity {
    public static final String EXTRA_BOOLEAN = "com.company.cmpt276_asn3.app.OptionsActivity - setup boolean";
    private Options options;
    private Boolean isFirstSetup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        extractDataFromIntent();

        options = Options.getInstance();
        if (isFirstSetup){
            this.setTitle("Set Up Game Options");
        }

        createGameSizeRadioButtons();
        createNumMineRadioButtons();
        setupContinueButton();
    }

    private void extractDataFromIntent() {
        Intent intent = getIntent();
        isFirstSetup = intent.getBooleanExtra(EXTRA_BOOLEAN, false);
    }

    private void createGameSizeRadioButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.gameSize);

        // Grabs button value resources
        int[] numRows = getResources().getIntArray(R.array.num_rows);
        int[] numCols = getResources().getIntArray(R.array.num_columns);

        // Creates buttons and sets on click listener
        for (int i = 0; i < numRows.length; i++){
            int numRow = numRows[i];
            int numCol = numCols[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.game_size_buttons, numRow, numCol));

            button.setOnClickListener(view -> {
                options.setNumRows(numRow);
                options.setNumCols(numCol);
                if (isFirstSetup){
                    checkRadioGroupValid();
                }
            });

            group.addView(button);

            // Remember past user selections
            if (options.getNumRows() == numRow && options.getNumCols() == numCol){
                group.check(group.getChildAt(i).getId());
            }
        }
    }

    private void createNumMineRadioButtons() {
        RadioGroup group = (RadioGroup) findViewById(R.id.numOfMines);
        int[] numMines = getResources().getIntArray(R.array.num_mines);

        // Create buttons and sets on click listener
        for (int i = 0; i < numMines.length; i++) {
            int numMine = numMines[i];
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.num_mines_buttons, numMine));
            button.setOnClickListener(view -> {
                options.setNumMines(numMine);
                if (isFirstSetup){
                    checkRadioGroupValid();
                }
            });

            group.addView(button);
            // Remember past user selections
            if (options.getNumMines() == numMine){
                group.check(group.getChildAt(i).getId());
            }
        }
    }

    private void setupContinueButton() {
        Button button = (Button) findViewById(R.id.continueButton);
        button.setVisibility(View.INVISIBLE);

        button.setOnClickListener(view -> {
            Intent intent = GamePlayActivity.makeIntent(OptionsActivity.this);
            startActivity(intent);
            finish();
        });
    }

    private void checkRadioGroupValid(){
        RadioGroup group1 = (RadioGroup) findViewById(R.id.numOfMines);
        RadioGroup group2 = (RadioGroup) findViewById(R.id.gameSize);
        Button button = (Button) findViewById(R.id.continueButton);

        // Set visibility of continue button if both radio groups have items selected
        if (group1.getCheckedRadioButtonId() != -1 && group2.getCheckedRadioButtonId() != -1){
            button.setVisibility(View.VISIBLE);
        }
    }

    public static Intent makeIntent(Context context, boolean isFirstSetup){
        Intent intent = new Intent(context, OptionsActivity.class);
        intent.putExtra(EXTRA_BOOLEAN, isFirstSetup);

        return intent;
    }
}
