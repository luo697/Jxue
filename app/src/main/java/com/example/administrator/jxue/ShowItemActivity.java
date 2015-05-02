package com.example.administrator.jxue;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;


public class ShowItemActivity extends ActionBarActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_item);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.show_item_back_image:
                finish();
                break;
            case R.id.show_item_learnandbuy:
                Intent intent = new Intent(ShowItemActivity.this,ShowMoreActivity.class);
                startActivity(intent);
                break;
        }
    }
}
