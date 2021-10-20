package com.company.cmpt276_asn3.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;

import com.company.cmpt276_asn3.R;
import com.company.cmpt276_asn3.databinding.ActivityMainBinding;
import com.company.cmpt276_asn3.model.Options;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        binding.buttonPlayGame.setOnClickListener(new View.OnClickListener() {
            @Override
                public void onClick(View view) {
                Intent intent = getNewGameIntent();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = OptionsActivity.makeIntent(MainActivity.this, false);
            startActivity(intent);
            return true;
        }

        if(id == R.id.action_help) {
            Intent intent = HelpActivity.makeIntent(MainActivity.this);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
