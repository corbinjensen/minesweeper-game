package com.company.cmpt276_asn3.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.company.cmpt276_asn3.R;
import com.company.cmpt276_asn3.model.Options;

public class OptionsActivity extends AppCompatActivity {
    private Options options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);

        options = Options.getInstance();

        createRadioButtons();
    }

    private void createRadioButtons() {
        RadioGroup gameSizeGroup = (RadioGroup) findViewById(R.id.gameSize);
        RadioGroup numMinesGroup = (RadioGroup) findViewById(R.id.numOfMines);

        // numRows and numCols guaranteed to be same length
        int[] numRows = getResources().getIntArray(R.array.num_rows);
        int[] numCols = getResources().getIntArray(R.array.num_columns);
        int[] numMines = getResources().getIntArray(R.array.num_mines);

        // Grab dimensions from resource files and create radio buttons
        for (int i = 0; i < numRows.length; i++){
            int numRow = numRows[i];
            int numCol = numCols[i];

            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.game_size_buttons, numRow, numCol));

            // Set options on button click
            button.setOnClickListener(view -> {
                options.setNumRows(numRow);
                options.setNumCols(numCol);
            });

            gameSizeGroup.addView(button);
        }

        // Set up number of mines radio group
        for (int numMine : numMines) {
            RadioButton button = new RadioButton(this);
            button.setText(getString(R.string.num_mines_buttons, numMine));

            button.setOnClickListener(view -> options.setNumMines(numMine));

            numMinesGroup.addView(button);
        }
    }
}
