package org.swsd.school_yearbook.view.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.swsd.school_yearbook.R;


public class NewPersonActivity extends AppCompatActivity {

    private FloatingActionButton btnAddNewPerson;
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

    }

    private void addNewPerson(){
        Toast.makeText(this, "添加成功", Toast.LENGTH_SHORT).show();
        //Connector.getDatabase();
    }
}
