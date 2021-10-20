/*
    MainActivity.java - Represents the Home Screen of the App with the Main Menu
 */

package com.company.cmpt276_asn3.app;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.company.cmpt276_asn3.databinding.ActivityMainBinding;
import com.company.cmpt276_asn3.model.Options;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());



        binding.buttonPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                Intent intent = getNewGameIntent();
                startActivity(intent);
            }
        });

        binding.buttonOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = OptionsActivity.makeIntent(MainActivity.this, false);
                startActivity(intent);
            }
        });

        binding.buttonHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = HelpActivity.makeIntent(MainActivity.this);
                startActivity(intent);
            }
        });
    }

    private Intent getNewGameIntent(){
        Options options = Options.getInstance();
        Intent intent;

        // Force user to options activity if any field is not instantiated
        if (options.getNumMines() == 0 || options.getNumRows() == 0 || options.getNumCols() == 0){
            intent = OptionsActivity.makeIntent(MainActivity.this, true);
        }
        else{
            intent = GamePlayActivity.makeIntent(MainActivity.this);
        }
        return intent;
    }

    public static Intent makeIntent(Context context) {

        return new Intent(context, MainActivity.class);

    }

}
