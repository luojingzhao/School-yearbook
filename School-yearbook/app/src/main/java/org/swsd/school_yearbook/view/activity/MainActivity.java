/**
 * author     :  胡俊钦
 * time       :  2017/11/04
 * description:  RecyclerView在界面上的实现，title加号的点击实现，搜索栏的实现
 * version:   :  1.0
 */
package org.swsd.school_yearbook.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

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
    private boolean checkboxflag = false;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        allList=mainPresenter.getAllList();
        NoteAdapter adapter = new NoteAdapter(getApplicationContext(),allList);
        recyclerView.setAdapter(adapter);
        addImageView = (ImageView) findViewById(R.id.iv_add_icon);
        addImageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                showPopupMenu(addImageView);
            }
        });

        //长按监听
        adapter.setOnItemClickListener(new NoteAdapter.OnItemOnClickListener() {
            @Override
            public void onItemLongOnClick(View view, int pos) {
                for(int i = 0; i < recyclerView.getChildCount();  i++){
                    View view1 = recyclerView.getChildAt(i);
                    CheckBox checkBox = view1.findViewById(R.id.cb_note);
                    checkBox.setVisibility(View.VISIBLE);
                }
                checkboxflag = true;
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_main);
                frameLayout.setVisibility(View.VISIBLE);
                ImageView deleteImageView = (ImageView) findViewById(R.id.iv_delete_icon);
                deleteImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                    }
                });
            }
        });

        //搜索栏监听
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
                    allList=mainPresenter.getAllList();
                    NoteAdapter adapter = new NoteAdapter(getApplicationContext(),allList);
                    recyclerView.setAdapter(adapter);
                }else{
                    selectedList=mainPresenter.toSelect(s.toString());
                    NoteAdapter adapter = new NoteAdapter(getApplicationContext(),selectedList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                //编辑框内容改变后
                if(s.length()==0){
                    allList=mainPresenter.getAllList();
                    NoteAdapter adapter = new NoteAdapter(getApplicationContext(),allList);
                    recyclerView.setAdapter(adapter);
                }else{
                    selectedList=mainPresenter.toSelect(s.toString());
                    NoteAdapter adapter = new NoteAdapter(getApplicationContext(),selectedList);
                    recyclerView.setAdapter(adapter);
                }

            }
        });}


    private void showPopupMenu(ImageView addImageView) {
        // View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, addImageView);

        // menu布局
        getMenuInflater().inflate(R.menu.menu, popupMenu.getMenu());
        //给菜单绑定监听器
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.add_item:
                        goAddNewPerson();
                        break;
                    case R.id.excel_item:
                        Toast.makeText(MainActivity.this, "导出excel成功，请在文件管理器中查看", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.photo_item:
                        Toast.makeText(MainActivity.this, "导出jpg成功，请在文件管理器中查看", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return false;
            }
        });
        popupMenu.show();
    }

    // 进入到新建同学录界面
    private void goAddNewPerson(){
        Intent intent = new Intent(MainActivity.this,NewPersonActivity.class);
        startActivity(intent);
    }

    //重写返回键
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode==KeyEvent.KEYCODE_BACK && checkboxflag == true){
            for(int i = 0; i < recyclerView.getChildCount();  i++){
                View view1 = recyclerView.getChildAt(i);
                CheckBox checkBox = view1.findViewById(R.id.cb_note);
                checkBox.setVisibility(View.GONE);
            }
            checkboxflag = false;
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_main);
            frameLayout.setVisibility(View.GONE);
        }else if (keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return true;
    }
}
