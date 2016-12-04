package com.csaba.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class EditListItemActivity extends AppCompatActivity {
    private int pos;
    private EditText etName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_list_item);

        String value = getIntent().getStringExtra("itemLabel");
        pos = getIntent().getIntExtra("pos",0);
        etName = (EditText)findViewById(R.id.editText);
        etName.setText(value);
        /*
         * Place cursor at the end of the existing text in EditText
         */
        etName.setSelection(etName.getText().length());
    }

    public void onSubmit(View view) {
        /*
         * closes this sub-activity and
         * returns to first screen
         * invoked when user clicks on back button
         * or when called from any other method
         */
        this.finish();
    }

    public void saveChanges(View view){
        Intent data = new Intent();
        data.putExtra("label", etName.getText().toString());
        data.putExtra("pos", pos);
        setResult(RESULT_OK, data);
        onSubmit(view);
    }
}
