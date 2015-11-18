package com.example.administrator.booknote;

import android.app.ActionBar;
import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity{
    private MyDatabaseHelper dbHelper;
    public static MainActivity instance = null;
    ListView contactsView;
    ContentsAdapter adapter;
    List<ItemBean> contactsList = new ArrayList<ItemBean>();
    private long firstTime = 0;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("data", "-----MainActivity----onCreate");

//      toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInMain);
        setSupportActionBar(toolbar);
        toolbar.setTitle("NoteBook");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                String msg = "";
                switch (item.getItemId()) {
//                    case R.id.ab_search:
//                        search();
//                        break;
                    case R.id.action_settings:
                        Intent intent=new Intent(MainActivity.this,ActivitySetting.class);
                        startActivity(intent);
                        break;
                }
                if (!msg.equals("")) {
                    Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                }
                return true;
            }
        });

        //search


        // 调出数据库中topic的内容，并将调出的内容放到listView
        contactsView = (ListView) findViewById(R.id.list_view);
        ContentsAdapter adapter = new ContentsAdapter(this,
                android.R.layout.simple_list_item_1, contactsList);
        contactsView.setAdapter(adapter);
        readNotebook();

//      listView点击事件,点击后跳转到ReWriteActivity界面，并将topic和contents的内容放到ReWriteActivity的edittextw中
        contactsView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                ItemBean itemBean = contactsList.get(position);
                Intent intent = new Intent(MainActivity.this,
                        ReWriteActivity.class);
                intent.putExtra("data",itemBean.getItemContents());
                Log.d("data", "-----the itemcontent in mainActivity----" + itemBean.getItemContents());
                startActivity(intent);
            }
        });

        //长按删除item
//        contactsView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
//            public boolean onItemLongClick(AdapterView<?> parent, View view,
//                                           int position, long id) {
//                ItemBean content = contactsList.get(position);
////                Log.d("data", content);
//                Log.d("data", "delect");
//                Toast.makeText(MainActivity.this, "this is delect", Toast.LENGTH_SHORT).show();
////                switch (content) {
////                    case "content":
//                        AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
//                        dialog.setTitle("Are you sure to delect this text");
//                        dialog.setMessage("It can't be recovered after delect");
//                        dialog.setCancelable(false);
//                        dialog.setPositiveButton("OK",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                        // TODO Auto-generated method stub
//                                        String topicFromMain;
//                                        Intent intent = getIntent();
//                                        topicFromMain = intent
//                                                .getStringExtra("content");
//                                        SQLiteDatabase db = dbHelper
//                                                .getWritableDatabase();
//                                        db.delete("NOTEBOOK", "contents=?",
//                                                new String[]{topicFromMain});
////                                        Intent intent2 = new Intent(ReWriteActivity.this, MainActivity.class);
////                                        startActivity(intent2);
//                                    }
//                                });
//                        dialog.setNegativeButton("Cancel",
//                                new DialogInterface.OnClickListener() {
//                                    public void onClick(DialogInterface dialog,
//                                                        int which) {
//                                    }
//                                });
//                        dialog.show();
////                        break;
////                    default:
////                        break;
////                }
//                return true;
//            }
//        });
//        adapter.notifyDataSetChanged();

        // 新建笔记
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        Log.d("data", "-----MainActivity----onStop");
       // 调出数据库中topic的内容，并将调出的内容放到listView
        ContentsAdapter adapter = new ContentsAdapter(this,
                android.R.layout.simple_list_item_1, contactsList);
        contactsView = (ListView) findViewById(R.id.list_view);
        contactsView.setAdapter(adapter);
        adapter.clear();
    }

    @Override
    protected void onRestart() {
        // TODO Auto-generated method stub
        super.onRestart();
//        adapter.notifyDataSetChanged();
        Log.d("data", "-----MainActivity----onRestart");
        // 调出数据库中topic的内容，并将调出的内容放到listView
        contactsView = (ListView) findViewById(R.id.list_view);
        adapter = new ContentsAdapter(this,
                android.R.layout.simple_list_item_1, contactsList);
        contactsView.setAdapter(adapter);
        readNotebook();
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
//        adapter.notifyDataSetChanged();
        Log.d("data", "-----MainActivity----onDestroy");
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
//        adapter.notifyDataSetChanged();
        Log.d("data", "-----MainActivity----onStart");
    }

    @Override
    protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        Log.d("data", "-----MainActivity----onResume");
    }

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
//        adapter.notifyDataSetChanged();
        Log.d("data", "-----MainActivity----onPause");
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) { // 如果两次按键时间间隔大于2秒，则不退出
                    Toast.makeText(this, "再按一次退出",
                            Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;// 更新firstTime
                    return true;
                } else { // 两次按键小于2秒时，退出应用
                    ActivityCollector.finishAll();
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

    // 调取数据库内容
    private void readNotebook() {
        dbHelper = new MyDatabaseHelper(this, "NOTEBOOK.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from notebook", null);
            while (cursor.moveToNext()) {
                String contents1 = cursor.getString(cursor.getColumnIndex("contents"));
                ItemBean contents2=new ItemBean(contents1);
                contactsList.add(contents2);
                Log.d("dataMainActivity",contents1 );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void search(){
        SearchView searchView=new SearchView(MainActivity.this);
        searchView.getSuggestionsAdapter();
        Intent intent=getIntent();
        if(Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query=intent.getStringExtra(SearchManager.QUERY);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
