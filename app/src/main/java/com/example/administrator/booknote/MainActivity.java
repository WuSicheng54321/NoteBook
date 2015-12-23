package com.example.administrator.booknote;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MyDatabaseHelper dbHelper;
    ListView contactsView;
    ContentsAdapter adapter;
    List<ItemBean> contactsList = new ArrayList<ItemBean>();
    private long firstTime = 0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarInMain);
        setSupportActionBar(toolbar);
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
                intent.putExtra("data", itemBean.getItemContents());
                startActivity(intent);
            }
        });
        // 新建笔记
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, NewActivity.class);
                startActivity(intent);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.administrator.booknote/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        Log.d("data", "-----MainActivity----onStop");
        // 调出数据库中topic的内容，并将调出的内容放到listView
        ContentsAdapter adapter = new ContentsAdapter(this,
                android.R.layout.simple_list_item_1, contactsList);
        contactsView = (ListView) findViewById(R.id.list_view);
        contactsView.setAdapter(adapter);
        adapter.clear();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.disconnect();
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

    private Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.action_settings:
                    Intent intent = new Intent(MainActivity.this, ActivitySetting.class);
                    startActivity(intent);
                    break;
            }
            return true;
        }
    };

    // 调取数据库内容
    private void readNotebook() {
        dbHelper = new MyDatabaseHelper(this, "NOTEBOOK.db", null, 2);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            Cursor cursor = db.rawQuery("select * from notebook", null);
            while (cursor.moveToNext()) {
                String contents1 = cursor.getString(cursor.getColumnIndex("contents"));
                ItemBean contents2 = new ItemBean(contents1);
                contactsList.add(contents2);
                Log.d("dataMainActivity", contents1);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        if (id == R.id.action_settings) {
            Intent intent = new Intent(MainActivity.this, ActivitySetting.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.ab_search) {
            Toast.makeText(MainActivity.this, "you touch it", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "Main Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://com.example.administrator.booknote/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }
}
