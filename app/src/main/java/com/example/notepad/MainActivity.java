package com.example.notepad;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.notepad.DB.DBservice;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<DBservice> alltext = LitePal.findAll(DBservice.class);
    ArrayList<String> notetitle;
    ListView li;
    ArrayAdapter<String> adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.findViewById(R.id.addNote).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent in = new Intent(MainActivity.this,NoteEditActivity.class);
                startActivity(in);
            }
        });
        swipeRefreshLayout = findViewById(R.id.sr1);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        MainActivity.this.onResume();
                    }
                },1000);
            }
        });
      /*notetitle = new ArrayList<String>();
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<alltext.size();i++) {
            stringBuffer.append("名称：");
            stringBuffer.append(alltext.get(i).getTextname());
            stringBuffer.append("时间：");
            stringBuffer.append(alltext.get(i).getTexttime());
            notetitle.add(stringBuffer.toString());
            stringBuffer.setLength(0);
        }
         adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,notetitle);
        //adapter.add(notetitle.get(0));
        li = (ListView)findViewById(R.id.listNote);
        li.setAdapter(adapter);*/
       listcreatt();
       initListNoteListener();

    }
    private void listcreatt()
    {
        notetitle = new ArrayList<String>();
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuffer stringBuffer = new StringBuffer();
        for(int i=0;i<alltext.size();i++) {
            stringBuffer.append("名称：");
            stringBuffer.append(alltext.get(i).getTextname());
            stringBuffer.append("时间：");
            stringBuffer.append(dateformat.format(alltext.get(i).getTexttime()));
            notetitle.add(stringBuffer.toString());
            stringBuffer.setLength(0);
        }
        adapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1,notetitle);
        //adapter.add(notetitle.get(0));
        li = (ListView)findViewById(R.id.listNote);
        li.setAdapter(adapter);
    }
    private void initListNoteListener() {
        // 长按删除
        ((ListView) this.findViewById(R.id.listNote)).setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, final long id) {
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("提示框")
                        .setMessage("确认删除该笔记？？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0,int arg1) {
                                        //DBService.deleteNoteById((int) id);
                                        alltext = LitePal.findAll(DBservice.class);
                                        int dataid = alltext.get((int)id).getId();
                                        LitePal.delete(DBservice.class,dataid);
                                        alltext = LitePal.findAll(DBservice.class);
                                        //删除后刷新列表
                                        MainActivity.this.onResume();
                                        Toast.makeText(
                                                MainActivity.this,
                                                "删除成功！！",
                                                Toast.LENGTH_LONG)
                                                .show();
                                    }
                                }).setNegativeButton("取消", null).show();
                return true;
            }
        });
        ((ListView) this.findViewById(R.id.listNote))
                .setOnItemClickListener(new AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        Intent in = new Intent(MainActivity.this,NoteEditActivity.class);
                        in.putExtra("id", id);
                        startActivity(in);
                        // 将id数据放置到Intent，切换视图后可以将数据传递过去
                        //in.putExtra("id", id);
                    }
                });

    }

    /**
     * 当从另一个视图进入该视图会调用该方法
     */
    @Override
    protected void onResume() {
        super.onResume();
        alltext = LitePal.findAll(DBservice.class);
        // 要求刷新主页列表笔记
      listcreatt();
      adapter.notifyDataSetChanged();
    }






}
