
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
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import org.litepal.crud.DataSupport;
import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.model.bean.SchoolyearbookBean;
import org.swsd.school_yearbook.presenter.ExcelPresenter;
import org.swsd.school_yearbook.presenter.NoteDelete;
import org.swsd.school_yearbook.presenter.adapter.MainPresenter;
import org.swsd.school_yearbook.presenter.adapter.NoteAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jxl.write.WriteException;


/**
 * author     :  骆景钊
 * time       :  2017/11/04
 * description:  RecyclerView在界面上的实现，title加号的点击实现
 * version:   :  1.0
 */
public class MainActivity extends AppCompatActivity implements NoteAdapter.Callback{

    List<SchoolyearbookBean> mSchoolyearbooks;
    private ImageView addImaeView;

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    //存放所有数据的List
    private List<SchoolyearbookBean>allList=new ArrayList<>();
    //存放搜索结果的List
    private List<SchoolyearbookBean>selectedList=new ArrayList<>();
    //MainPresenter对象，用于与View进行交互
    MainPresenter mainPresenter=new MainPresenter();
    private boolean checkboxflag = false;
    private NoteAdapter adapter;

    private static final String TAG = "MainActivity";

    private ImageView addImageView;
    private EditText et_search;
    //选中的note的id集合
    List<Integer> idList = new ArrayList<>();

    //选中的email的集合
    List<String> emailList = new ArrayList<>();


    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView = (RecyclerView) findViewById(R.id.rv_main);
        recyclerView.setLayoutManager(layoutManager);
        mSchoolyearbooks = DataSupport.findAll(SchoolyearbookBean.class);
        adapter = new NoteAdapter(getApplicationContext(), mSchoolyearbooks, this);
        recyclerView.setAdapter(adapter);

        //initData();
        mSchoolyearbooks = new ArrayList<>();
        mSchoolyearbooks.clear();
        mSchoolyearbooks = DataSupport.findAll(SchoolyearbookBean.class);
        adapter = new NoteAdapter(getApplicationContext(), mSchoolyearbooks, this);
        adapter.setOnItemClickListener(new NoteAdapter.OnItemOnClickListener() {
            @Override
            public void onItemLongOnClick(View view, int pos) {
                adapter.checkTemp = true;
                adapter.notifyDataSetChanged();
                checkboxflag = true;
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_main);
                frameLayout.setVisibility(View.VISIBLE);
            }
        });
        recyclerView.setAdapter(adapter);

        //点击email图标事件
        ImageView emailImageView = (ImageView) findViewById(R.id.iv_main_email);
        emailImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goSendEmailActivity();
                for(int i = 0; i < idList.size(); i++){
                    emailList.add(mSchoolyearbooks.get(idList.get(i)).getEmail());
                }
                idList.clear();

                //将选择框隐藏
                adapter.checkTemp = false;
                adapter.notifyDataSetChanged();
                FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_main);
                frameLayout.setVisibility(View.GONE);
            }
        });

        //点击delete图标事件
        ImageView deleteImageView = (ImageView) findViewById(R.id.iv_main_delete);
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this, "点击了删除按钮" + idList.size(), Toast.LENGTH_SHORT).show();

                //删除选中
                for(int i = 0; i < idList.size(); i++){
//                    Log.d("hahaha",mSchoolyearbooks.get(idList.get(i)).getEmail()+"");
                    DataSupport.deleteAll(SchoolyearbookBean.class, "name = ?", mSchoolyearbooks.get(idList.get(i)).getName());
                    mSchoolyearbooks.remove(idList.get(i) - i);
                }
                adapter.notifyDataSetChanged();
                idList.clear();
                recyclerView.scrollToPosition(0);
            }
        });

        addImaeView = (ImageView) findViewById(R.id.iv_add_icon);
        addImaeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenu(addImaeView);
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

            }
        });


    }

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
                        exportExcel();
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

    // 进入群发邮件状态
    private void goSendEmailActivity(){
        Intent intent = new Intent(MainActivity.this, SendEmailActivity.class);
        emailList = new ArrayList<>();
        emailList.add("1009224322@qq.com");
        emailList.add("1009224322@qq.com");
        ArrayList<String> Test = (ArrayList<String>) emailList;
        intent.putStringArrayListExtra("email",Test);
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
            adapter.checkTemp = false;
            adapter.notifyDataSetChanged();
            checkboxflag = false;
            FrameLayout frameLayout = (FrameLayout) findViewById(R.id.fl_main);
            frameLayout.setVisibility(View.GONE);
        }else if (keyCode==KeyEvent.KEYCODE_BACK){
            finish();
        }
        return true;
    }

    public void initData(){

    }

    // 导出excel
    private void exportExcel(){
        try {
            ExcelPresenter.writeExcel("StartDust");
            Toast.makeText(MainActivity.this, "导出excel成功，请在文件管理器中查看", Toast.LENGTH_SHORT).show();
        } catch (WriteException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 10; i++)
        {
            SchoolyearbookBean book = new SchoolyearbookBean();
            book.setName("zyzhang" + i);
            book.setPhone("123456");
            //book.setEmail("@zyzhang");
            book.save();
        }
    }

    @Override
    public void myOnClick(View view) {
        int idNote = (int) view.getTag();
        idList.add(idNote);
    }

    public void backCreateDate(){
        mSchoolyearbooks = DataSupport.findAll(SchoolyearbookBean.class);
    }

}