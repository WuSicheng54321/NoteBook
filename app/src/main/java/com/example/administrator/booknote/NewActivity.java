package com.example.administrator.booknote;

/**
 * Created by Administrator on 2015/11/14.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

public class NewActivity extends ActionBarActivity {

    private MyDatabaseHelper dbHelper;
    private EditText textText;
    String topicFromMainInNew;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newmian);
        Log.d("data","---NewActivity---onCreate---");
        dbHelper = new MyDatabaseHelper(this, "NOTEBOOK.db", null, 1);
        textText = (EditText) findViewById(R.id.text_reedittext);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInNew);
        setSupportActionBar(toolbar);
        toolbar.setTitle("NoteBook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";
                switch (item.getItemId()) {
                    case R.id.ab_save_new:
                        Toast.makeText(NewActivity.this,"已保存",Toast.LENGTH_SHORT).show();
                        break;
                }
                if (!msg.equals("")) {
                    Toast.makeText(NewActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        saveInNew();
        Log.d("data", "--the onpause on the newActivity");
        //判断save是否执行，若执行则不执行savaInNew
    }

    protected void onStop(){
        super.onStop();
        Log.d("data", "---NewActivity---onStop---");
    }
    protected  void onStart(){
        super.onStart();
        Log.d("data", "---NewActivity---onStart---");
    }

    protected void onRestart(){
        super.onRestart();
        Log.d("data", "---NewActivity---onRestart---");
    }

    protected void onResume(){
        super.onResume();
        Log.d("data", "---NewActivity---onResume---");
    }

    protected  void onDestroy(){
        super.onDestroy();
        Log.d("data", "---NewActivity---onDestroy---");
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_new, menu);
        return true;
    }

    private void saveInNew() {
        dbHelper = new MyDatabaseHelper(this, "NOTEBOOK.db", null, 2);
        textText = (EditText) findViewById(R.id.text_edittext);

        String contents = textText.getText().toString();
        Log.d("data", contents);
        if (contents.length() != 0) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put("contents", contents);
            Log.d(null, contents);
            db.insert("NOTEBOOK", null, values);
            values.clear();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {

        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
