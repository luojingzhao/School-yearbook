package org.swsd.school_yearbook.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.presenter.adapter.NoteAdapter;

import java.util.List;


/**
 * author     :  骆景钊
 * time       :  2017/11/04
 * description:  RecyclerView在界面上的实现，title加号的点击实现
 * version:   :  1.0
 */

public class MainActivity extends AppCompatActivity{

    private ImageView addImaeView;
    private RecyclerView recyclerView;
    private boolean checkboxflag = false;

    //选中的note的电话集合
    private List<String> phoneList;

    //选中的note的email集合
    private List<String> emailList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(layoutManager);
        NoteAdapter adapter = new NoteAdapter();
        recyclerView.setAdapter(adapter);

        //长按监听
        adapter.setOnItemClickListener(new NoteAdapter.OnItemOnClickListener() {
            @Override
            public void onItemLongOnClick(View view, int pos) {
                for(int i = 0; i < recyclerView.getChildCount();  i++){
                    final View recyclerViewChildAt = recyclerView.getChildAt(i);
                    final CheckBox checkBox = (CheckBox) recyclerViewChildAt.findViewById(R.id.cb_note);
                    checkBox.setVisibility(View.VISIBLE);

                    //判断是否选中
                    checkBox.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if(checkBox.isChecked()){
                                TextView nameContent = (TextView) recyclerViewChildAt.findViewById(R.id.tv_note_phone);
                                TextView emailContent = (TextView) recyclerViewChildAt.findViewById(R.id.tv_note_address);
                                phoneList.add(nameContent.getText().toString());
                                emailList.add(emailContent.getText().toString());
                            }
                        }
                    });
                }
                checkboxflag = true;
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_main);
                frameLayout.setVisibility(View.VISIBLE);


            }
        });

        //点击email图标事件
        ImageView emailImageView = (ImageView) findViewById(R.id.iv_main_email);
        emailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "点击了发送邮件按钮", Toast.LENGTH_SHORT).show();
            }
        });

        //点击delete图标事件
        ImageView deleteImageView = (ImageView) findViewById(R.id.iv_main_delete);
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

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
        PopupMenu popupMenu = new PopupMenu(MainActivity.this, addImaeView);

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
                CheckBox checkBox = (CheckBox) view1.findViewById(R.id.cb_note);
                checkBox.setVisibility(View.GONE);
            }
            checkboxflag = false;
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_main);
            frameLayout.setVisibility(View.GONE);
        }else {
            finish();
        }
        return true;
    }
}
