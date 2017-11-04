package org.swsd.school_yearbook.view.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar=(Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.steepStatusBar();

        setContentView(R.layout.activity_main);

    }

    @Override
    public int bindLayout(){
        return 1;
    }
   @Override
    public  void initView(){

    }
    @Override
    public void initData(){

    }

}
