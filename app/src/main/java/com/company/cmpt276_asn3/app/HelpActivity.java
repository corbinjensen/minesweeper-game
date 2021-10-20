/*
    HelpActivity.java - Represents an About Page with citations, and instructions for the user.
 */
package com.company.cmpt276_asn3.app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

import com.company.cmpt276_asn3.R;

public class HelpActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);
        courseHyperlink();
    }

    private void courseHyperlink() {
        TextView courseLink = findViewById(R.id.textView8);
        courseLink.setMovementMethod(LinkMovementMethod.getInstance());

    }

    public static Intent makeIntent(Context context){
        return new Intent(context, HelpActivity.class);
    }
}
