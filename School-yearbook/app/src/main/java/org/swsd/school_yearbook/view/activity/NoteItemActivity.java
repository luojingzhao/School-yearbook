package org.swsd.school_yearbook.view.activity;

import android.os.Bundle;

import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.base.BaseActivity;


public class NoteItemActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.steepStatusBar();
        setContentView(R.layout.note_item);
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
