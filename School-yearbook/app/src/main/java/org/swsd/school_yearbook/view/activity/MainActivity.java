package org.swsd.school_yearbook.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.litepal.crud.DataSupport;
import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.model.bean.SchoolyearbookBean;
import org.swsd.school_yearbook.presenter.adapter.NoteAdapter;

import java.util.List;

/**
 * author     :  骆景钊
 * time       :  2017/11/04
 * description:  RecyclerView在界面上的实现，title加号的点击实现
 * version:   :  1.0
 */

public class MainActivity extends AppCompatActivity{

    List<SchoolyearbookBean>mSchoolyearbooks;
    private ImageView addImaeView;
    private NoteAdapter adapter;


    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //initData();

        //从数据库获取所有同学信息，RecyclerView展示
        mSchoolyearbooks = DataSupport.findAll(SchoolyearbookBean.class);

        Log.d(TAG, "zxzhang" + mSchoolyearbooks.toString() + String.valueOf(mSchoolyearbooks.size()));

        adapter = new NoteAdapter(getApplicationContext(),mSchoolyearbooks);
        recyclerView.setAdapter(adapter);

        addImaeView = (ImageView) findViewById(R.id.iv_add_icon);
        addImaeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(addImaeView);
            }
        });

    }

    private void showPopupMenu(ImageView addImaeView) {

        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(this, addImaeView);

        // menu布局
        popupMenu.getMenuInflater().inflate(R.menu.add_item, popupMenu.getMenu());
        popupMenu.show();
    }

}
