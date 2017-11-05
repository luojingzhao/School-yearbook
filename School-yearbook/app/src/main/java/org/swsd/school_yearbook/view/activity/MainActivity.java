/**
 * author     :  胡俊钦
 * time       :  2017/11/04
 * description:  RecyclerView在界面上的实现，title加号的点击实现，搜索栏的实现
 * version:   :  1.0
 */
package org.swsd.school_yearbook.view.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.model.bean.SchoolyearbookBean;
import org.swsd.school_yearbook.presenter.adapter.MainPresenter;
import org.swsd.school_yearbook.presenter.adapter.NoteAdapter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{

    private ImageView addImageView;
    private EditText et_search;
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    //存放所有数据的List
    private List<SchoolyearbookBean>allList=new ArrayList<>();
    //存放搜索结果的List
    private List<SchoolyearbookBean>selectedList=new ArrayList<>();
    //MainPresenter对象，用于与View进行交互
    MainPresenter mainPresenter=new MainPresenter();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        allList=mainPresenter.getAllList();
        NoteAdapter adapter = new NoteAdapter(allList);
        recyclerView.setAdapter(adapter);
        addImageView = (ImageView) findViewById(R.id.iv_add_icon);
        addImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(addImageView);
            }
        });

        et_search=(EditText)findViewById(R.id.et_main_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //编辑框内容改变前

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //编辑框内容改变时
                if(s.length()==0){

                }else{
                    selectedList=mainPresenter.toSelect(s.toString());
                    NoteAdapter adapter = new NoteAdapter(selectedList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //编辑框内容改变后

            }
        });}

        private void showPopupMenu(ImageView addImageView) {


        PopupMenu popupMenu = new PopupMenu(MainActivity.this, addImageView);
        // View当前PopupMenu显示的相对View的位置

        // menu布局
        getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        //给菜单绑定监听器
       // popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

        //});

    }
}
