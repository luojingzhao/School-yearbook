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
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import org.swsd.school_yearbook.R;


public class NewPersonActivity extends AppCompatActivity {

    private FloatingActionButton btnAddNewPerson;
    private static final int CHOOSE_PHOTO=2;
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
            Bitmap bitmap= BitmapFactory.decodeFile(imagePath);
            ImageView picture=new ImageView(this);
            picture.setImageBitmap(bitmap);
        }else{
            Toast.makeText(this,"failed to get image",Toast.LENGTH_SHORT).show();
        }
    }

    private void addNewPerson(){
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        //Connector.getDatabase();
    }
}
