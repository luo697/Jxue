package com.example.administrator.jxue;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class ShowMoreActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.show_more_back_image:
                finish();
                break;
        }
    }
}
