package com.example.administrator.booknote;

/**
 * Created by Administrator on 2015/11/14.
 */
import android.app.Activity;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ReWriteActivity extends ActionBarActivity {

    private MyDatabaseHelper dbHelper;
    private EditText textText;
    String contentFromMainInReWrite;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rewritemian);

        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInReW);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setTitle("NoteBook");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";
                switch (item.getItemId()) {
                    case R.id.ab_delect_rewrite:
                        delect();
                        break;
//                    case R.id.action_settings_rewrite:
//                        msg += "Click setting";
//                        break;
                }
                if (!msg.equals("")) {
                    Toast.makeText(ReWriteActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        Log.d("dataRe","-----ReWriteActivity----onCreate");
        dbHelper = new MyDatabaseHelper(this, "NOTEBOOK.db", null, 2);
        textText = (EditText) findViewById(R.id.text_reedittext);
        // 接收从ReadActivity中传递过来的topic，并将它放到topicEdittext中
        // 查找于topic相同的contents，存放到contentsEdittext中
        reWrite();


    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        Log.d("dataRe", "-----ReWriteActivity----onPause");
        String contents = textText.getText().toString();
        if (contents.length() > 0) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("contents", contents);
            db.update("NOTEBOOK", values, "contents=?",
                    new String[] { contentFromMainInReWrite });
            Log.d("dataReWriteActivity", contents);
        } else {
        }

    }
    protected void onStart(){
        super.onStart();
        Log.d("dataRe","-----ReWriteActivity----onStart");
    }
    protected void onResume(){
        super.onResume();
        Log.d("dataRe","-----ReWriteActivity----onResume");
    }
    protected void onStop(){
        super.onStop();
        Log.d("dataRe","-----ReWriteActivity----onStop");
    }
    protected void onDestroy(){
        super.onDestroy();
        Log.d("dataRe","-----ReWriteActivity----onDestroy");
    }
    protected void onRestart(){
        super.onRestart();
        Log.d("dataRe", "-----ReWriteActivity----onRestart");
    }
    private void delect(){
                AlertDialog.Builder dialog=new AlertDialog.Builder(ReWriteActivity.this);
                dialog.setTitle("确认删除便签？");
                dialog.setMessage("删除后不可恢复");
                dialog.setCancelable(false);
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // TODO Auto-generated method stub
                        String topicFromMain;
                        Intent intent=getIntent();
                        topicFromMain=intent.getStringExtra("data");
                        SQLiteDatabase db=dbHelper.getWritableDatabase();
                        db.delete("NOTEBOOK", "contents=?", new String[]{topicFromMain});
                        Intent intent2 =new Intent(ReWriteActivity.this,MainActivity.class);
                        startActivity(intent2);
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                dialog.show();
    }

    private void reWrite() {
        dbHelper = new MyDatabaseHelper(this, "NOTEBOOK.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Intent intent = getIntent();
        contentFromMainInReWrite = intent.getStringExtra("data");
        Log.d("data", "---the contents from main in ---ReWriteActivity--- is---"
                + contentFromMainInReWrite);
        textText.setText(contentFromMainInReWrite);
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_rewrite, menu);
        return true;
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        if (id == R.id.action_settings_rewrite) {
//            return true;
//        }else
            if (id==android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
