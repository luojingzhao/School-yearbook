package org.swsd.school_yearbook.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import org.swsd.school_yearbook.R;
import org.swsd.school_yearbook.presenter.SendEmail;
import java.util.ArrayList;
import java.util.List;

public class SendEmailActivity extends AppCompatActivity {

    private List<String> eMailList = new ArrayList<>();
    private EditText eMailContent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        eMailList = getIntent().getStringArrayListExtra("email");
        eMailContent = (EditText) findViewById(R.id.email_content);
        final Button sendEmail = (Button) findViewById(R.id.send_email);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendEmail.setClickable(false);
                Toast.makeText(SendEmailActivity.this, "发送邮件中，请不要进行任何操作", Toast.LENGTH_SHORT).show();
                sendEmailAll();
            }
        });
    }

    private void sendEmailAll(){
        for (String address: eMailList) {
            sendEmailOne(address);
            Log.d("熊立强", "成功发送邮件给： " + address);
        }
    }
    // 发送邮件Presenter
    private void sendEmailOne(final String eMailAddress){
        // 启用一个线程 发送邮件
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    SendEmail.send(eMailContent.getText().toString(),eMailAddress);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(SendEmailActivity.this, "邮件发送成功！", Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    finish();
                }
            }
        }).start();
    }
}
