package org.swsd.school_yearbook.view.activity;

import android.os.Bundle;

import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.base.BaseActivity;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected int bindLayout() {
        return 0;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
