package com.csaba.todoapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOError;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText etNewItem;
    private ArrayList<String> items;
    private ArrayAdapter<String> itemsAdapter;
    private ListView lvItems;
    private Button btnAddItem;
    private final int REQUEST_CODE = 20;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvItems = (ListView)findViewById(R.id.lvItems);
        readItems();
        itemsAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, items);
        lvItems.setAdapter(itemsAdapter);
        setupListViewListener();
    }

    public void addItem(View view){
        EditText et = (EditText)findViewById(R.id.etNewItem);
        String text = et.getText().toString();
        itemsAdapter.add(text);
        et.setText("");
        writeItems();
    }

    public void setupListViewListener(){

        lvItems.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapter, View item, int pos, long id) {
            /*
             *  alternate way
             *
             *  TextView tv = (TextView)item;
             *  itemsAdapter.remove(tv.getText().toString());
             *
             */
                items.remove(pos);
                itemsAdapter.notifyDataSetChanged();
                writeItems();
                return true;
            }
        });

        lvItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
                Intent i = new Intent(MainActivity.this, EditListItemActivity.class);
                String abc = itemsAdapter.getItem(pos);
                TextView tv = (TextView) view;
                i.putExtra("itemLabel", tv.getText().toString());
                i.putExtra("pos",pos);
                startActivityForResult(i, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==RESULT_OK && requestCode == REQUEST_CODE){
            String name = data.getExtras().getString("label");
            int pos = data.getExtras().getInt("pos", 0);
            items.set(pos, name);
            itemsAdapter.notifyDataSetChanged();
            /*
             *  alternate way
             *
             *  itemsAdapter.remove(items.get(pos));
             *  itemsAdapter.insert(name,pos);
             *
             */
            writeItems();

        }
    }

    public void readItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir,"todo.txt");
        try{
            items = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e){
            items = new ArrayList<String>();
        }
    }
    private void writeItems(){
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try{
            FileUtils.writeLines(todoFile, items);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
