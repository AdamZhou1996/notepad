package com.example.notepad;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import com.example.notepad.DB.DBservice;
import org.litepal.LitePal;
import java.util.List;

public class NoteEditActivity extends Activity {
    private EditText titleEditText = null;
    private EditText contentEditText = null;
    private String noteId = null;
    private long longid;
    private List<DBservice> list;
    int intid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.note_editer);

        titleEditText = (EditText) NoteEditActivity.this
                .findViewById(R.id.title);
        contentEditText = (EditText) NoteEditActivity.this
                .findViewById(R.id.content);

        initNoteEditValue();
        this.findViewById(R.id.cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        NoteEditActivity.this.finish();
                    }
                });
        this.findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String title = titleEditText.getText().toString();
                final String content = contentEditText.getText().toString();

                //判断标题和内容是否为空，不为空才能保存
                if ("".equals(title) || "".equals(content)) {
                    Toast.makeText(NoteEditActivity.this, "标题或者内容不能为空",
                            Toast.LENGTH_LONG).show();
                    return;
                }
                new AlertDialog.Builder(NoteEditActivity.this)
                        .setTitle("提示框")
                        .setMessage("确定保存笔记吗？？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {
                                        //如果noteId不为空那么就是更新操作，为空就是添加操作
                                        DBservice dBservice = new DBservice();
                                        if (null == noteId || "".equals(noteId))
                                        {
                                            dBservice.setTextname(title);
                                            dBservice.setText(content);
                                            dBservice.setTexttime(System.currentTimeMillis());
                                            dBservice.save();
                                        }
                                        else
                                        {
                                            dBservice = list.get(intid);
                                            dBservice.setText(content);
                                            dBservice.save();
                                        }
                                        //结束当前activity
                                        NoteEditActivity.this.finish();
                                        Toast.makeText(NoteEditActivity.this, "保存成功！！",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }).setNegativeButton("取消", null).show();
            }
        });
    }
    private void initNoteEditValue() {
        // 从Intent中获取id的值
        long id = this.getIntent().getLongExtra("id", -1L);
        // 如果有传入id那么id！=-1
        if (id
                != -1L) {
            // 使用noteId保存id
            longid = id;
            noteId = String.valueOf(id);
             intid = (int)id;
            // 查询该id的笔记
             list = LitePal.findAll(DBservice.class);
            StringBuffer stringBuffer = new StringBuffer();

                titleEditText.setText(list.get(intid).getTextname());
                contentEditText.setText(list.get(intid).getText());
        }
    }

}
