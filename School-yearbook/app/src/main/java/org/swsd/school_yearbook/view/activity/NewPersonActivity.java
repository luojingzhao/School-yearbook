/**
 * author     :  胡俊钦
 * time       :  2017/11/05
 * description:  实现更换头像的逻辑
 * version:   :  1.0
 */

package org.swsd.school_yearbook.view.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.EditText;
import android.widget.Toast;
import org.swsd.school_yearbook.R;


public class NewPersonActivity extends AppCompatActivity {

    private FloatingActionButton btnAddNewPerson;

    private static final int CHOOSE_PHOTO=2;

    private EditText eT_name;
    private EditText eT_address;
    private EditText eT_phone;
    private EditText eT_wechat;
    private EditText eT_email;
    private EditText eT_qq;
    private EditText eT_signature;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_person);
        btnAddNewPerson = (FloatingActionButton) findViewById(R.id.add_new_person);
        btnAddNewPerson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewPerson();
            }
        });

        ImageView chooseFromAlbum=(ImageView)findViewById(R.id.person_photo);
        chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //查看权限
                if(ContextCompat.checkSelfPermission(NewPersonActivity.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)!=
                        PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(NewPersonActivity.this,new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
                    }else{
                    openAlbum();
                }
            }
        });

    }

    private void openAlbum(){
        Intent intent=new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        //打开相册
        startActivityForResult(intent,CHOOSE_PHOTO);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,
                                          int[] grantResults){
        switch( requestCode){
            case 1:
                if(grantResults.length>0&&grantResults[0]==PackageManager.
                        PERMISSION_GRANTED){
                        openAlbum();
                }else{
                    Toast.makeText(this,"You denied the permission",
                            Toast.LENGTH_SHORT).show();
                }
                break;
            default:
        }
    }

    @Override
    protected void onActivityResult(int requestCode,int resultCode,Intent data){
        switch(requestCode){
            case CHOOSE_PHOTO:
                //判断手机系统版本号
                if(resultCode==RESULT_OK){
                    if(Build.VERSION.SDK_INT>=19){
                        //手机系统在4.4及以上的才能使用这个方法处理图片
                        handleImageOnKitKat(data);
                    }else{
                        //手机系统在4.4以下的使用这个方法处理图片
                        handleImageBeforeKitKat(data);
                    }
                }
                break;
            default:
                break;
        }
    }

    @TargetApi(19)
    private void handleImageOnKitKat(Intent data){
        String imagePath=null;
        Uri uri=data.getData();
        //如果是document类型的Uri，，则通过document id处理
        if(DocumentsContract.isDocumentUri(this,uri)){
            String docId=DocumentsContract.getDocumentId(uri);
            if("com.android.providers.media.documents".equals(uri.getAuthority())){
                //解析出数字格式的id
                String id=docId.split(":")[1];
                String selection= MediaStore.Images.Media._ID+"="+id;
                imagePath=getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        selection);
            }else if("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri= ContentUris.withAppendedId(Uri.parse("content:" +
                        "//downloads/public_downloads"),Long.valueOf(docId));
                imagePath=getImagePath(contentUri,null);
            }
        }else if("file".equalsIgnoreCase(uri.getScheme())){
            //如果是file类型的Uri，直接获取图片路径
            imagePath=uri.getPath();
        }
        else if("content".equalsIgnoreCase(uri.getScheme())){
            //如果是content类型的Uri，使用普通方式处理
            imagePath=getImagePath(uri,null);
        }
        //根据图片路径显示图片
        displayImage(imagePath);
    }

    private void handleImageBeforeKitKat(Intent data){
        Uri uri=data.getData();
        String imagePath=getImagePath(uri,null);
        //根据图片路径显示图片
        displayImage(imagePath);
    }

    private String getImagePath(Uri uri,String selection){
        String path=null;
        //通过Uri和selection来获取真实的图片路径
        Cursor cursor =getContentResolver().query(uri,null,selection,null,null);
        if(cursor!=null){
            if(cursor.moveToFirst()){
                path=cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
                cursor.close();
            }
        }
        return path;
    }

    //显示图片
    private void displayImage(String imagePath){
        if(imagePath!=null){
            BitmapFactory.Options options = new BitmapFactory.Options();//解析位图的附加条件
            options.inJustDecodeBounds = true;// 不去解析位图，只获取位图头文件信息
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath,options);
            ImageView picture;
            picture=(ImageView) findViewById(R.id.person_photo);
            picture.setImageBitmap(bitmap);
            int btwidth = options.outWidth;//获取图片的宽度
            int btheight = options.outHeight;//获取图片的高度

            int dx = btwidth/200;//获取水平方向的缩放比例
            int dy = btheight/200;//获取垂直方向的缩放比例

            int s = 1;//设置默认缩放比例

            //如果是水平方向
            if (dx>dy&&dy>1) {
                s = dx;
            }

            //如果是垂直方向
            if (dy>dx&&dx>1) {
                s = dy;
            }
            options.inSampleSize = s;//设置图片缩放比例
            options.inJustDecodeBounds = false;//真正解析位图
            //把图片的解析条件options在创建的时候带上
            bitmap = BitmapFactory.decodeFile(imagePath, options);
            picture.setImageBitmap(bitmap);//设置图片
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewPerson(){
        insertYearBook();
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
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
        //绑定编辑框
        eT_name = (EditText)findViewById(R.id.et_contact_name);
        eT_address = (EditText)findViewById(R.id.et_contact_address);
        eT_phone = (EditText)findViewById(R.id.et_contact_phone);
        eT_wechat = (EditText)findViewById(R.id.et_contact_wechat);
        eT_email = (EditText)findViewById(R.id.et_contact_email);
        eT_qq = (EditText)findViewById(R.id.et_contact_qq);
        eT_signature = (EditText)findViewById(R.id.et_contact_signature);

        // 获取编辑框示文本
        name = eT_name.getText().toString();
        address = eT_address.getText().toString();
        phone = eT_phone.getText().toString();
        wechat = eT_wechat.getText().toString();
        email = eT_email.getText().toString();
        signature = eT_signature.getText().toString();

        //测试
        if(name.equals("")){
            Toast.makeText(this,"姓名为空", Toast.LENGTH_SHORT).show();
        }
        if(!isMobil(phone)) {
            Toast.makeText(this, "不是电话", Toast.LENGTH_SHORT).show();
        }
        if(!isEmail(email)){
            Toast.makeText(this, "不是邮箱", Toast.LENGTH_SHORT).show();
        }

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
}
