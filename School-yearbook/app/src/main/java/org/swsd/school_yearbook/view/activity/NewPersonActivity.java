package org.swsd.school_yearbook.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.model.bean.SchoolyearbookBean;


public class NewPersonActivity extends AppCompatActivity {

    private FloatingActionButton btnAddNewPerson;
    private EditText eT_name;
    private EditText eT_address;
    private EditText eT_phone;
    private EditText eT_wechat;
    private EditText eT_email;
    private EditText eT_qq;
    private EditText eT_signature;
    private boolean isNewPerson = true;
    private SchoolyearbookBean tempDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person);
        bindEditText();
        initBundle();
        btnAddNewPerson = (FloatingActionButton) findViewById(R.id.add_new_person);
        btnAddNewPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPerson();
            }
        });

    }

    private void initBundle(){
        if(getIntent().getExtras() == null){
            Toast.makeText(this, "正在新建联系人", Toast.LENGTH_SHORT).show();
        }else{
            isNewPerson = false;
            Toast.makeText(this, "正在修改联系人", Toast.LENGTH_SHORT).show();
        }

        if (!isNewPerson){
            Bundle bundle = new Bundle();
            bundle = getIntent().getExtras();
            // 抽取Budle中的对象
            tempDB = (SchoolyearbookBean) bundle.getSerializable("note");
            onReloadET();
        }
    }

    // 保存联系人到数据库
    private void addNewPerson(){
        if(isNewPerson){
            insertYearBook();
            Toast.makeText(this, "新建联系人成功", Toast.LENGTH_SHORT).show();
        }
        else {
            upDatePerson();
            Toast.makeText(this, "修改联系人成功", Toast.LENGTH_SHORT).show();
        }
    }

    private void insertYearBook(){
        String name;
        String address;
        String phone;
        String wechat;
        String email;
        String qq;
        String signature;
        String avatarPath;
        // 获取编辑框示文本
        name = eT_name.getText().toString();
        address = eT_address.getText().toString();
        phone = eT_phone.getText().toString();
        wechat = eT_wechat.getText().toString();
        email = eT_email.getText().toString();
        signature = eT_signature.getText().toString();
        qq = eT_qq.getText().toString();

        //测试
        if(!name.equals("") && isMobil(phone) && isEmail(email)){
            SchoolyearbookBean person = new SchoolyearbookBean();
            person.setName(name);
            person.setAddress(address);
            person.setPhone(phone);
            person.setWechat(wechat);
            person.setSignature(signature);
            person.setQq(qq);
            person.setEmail(email);
            person.save();
            Toast.makeText(this, "保存数据成功", Toast.LENGTH_SHORT).show();
            finish();
        }
        else{
            Toast.makeText(this, "请检查姓名，邮箱，电话是否合法！", Toast.LENGTH_SHORT).show();
        }
    }

    public void bindEditText(){
        //绑定编辑框
        eT_name = (EditText)findViewById(R.id.et_contact_name);
        eT_address = (EditText)findViewById(R.id.et_contact_address);
        eT_phone = (EditText)findViewById(R.id.et_contact_phone);
        eT_wechat = (EditText)findViewById(R.id.et_contact_wechat);
        eT_email = (EditText)findViewById(R.id.et_contact_email);
        eT_qq = (EditText)findViewById(R.id.et_contact_qq);
        eT_signature = (EditText)findViewById(R.id.et_contact_signature);
    }
    // 手机验证
    private boolean isMobil(String number){
        /*
        移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
        联通：130、131、132、152、155、156、185、186
        电信：133、153、180、189、（1349卫通）
        总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
        */
        //"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        String num = "[1][358]\\d{9}";
        if (TextUtils.isEmpty(number)) {
            return false;
        } else {
            //matches():字符串是否在给定的正则表达式匹配
            return number.matches(num);
        }
    }

    // 邮箱验证
    public static boolean isEmail(String strEmail) {
        String strPattern = "^[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
        if (TextUtils.isEmpty(strPattern)) {
            return false;
        } else {
            return strEmail.matches(strPattern);
        }
    }

    private void upDatePerson() {
        tempDB.setName(eT_name.getText().toString());
        tempDB.setName(eT_address.getText().toString());
        tempDB.setName(eT_phone.getText().toString());
        tempDB.setName(eT_wechat.getText().toString());
        tempDB.setName(eT_email.getText().toString());
        tempDB.setName(eT_qq.getText().toString());
        tempDB.setName(eT_signature.getText().toString());
        tempDB.save();
    }

    // 重新装载数据
    private void onReloadET(){
        eT_name.setText(tempDB.getName());
        eT_address.setText(tempDB.getAddress());
        eT_phone.setText(tempDB.getPhone());
        eT_wechat.setText(tempDB.getWechat());
        eT_qq.setText(tempDB.getQq());
        eT_signature.setText(tempDB.getSignature());
        eT_email.setText(tempDB.getEmail());
    }
}
