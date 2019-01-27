package com.chen.fy.dropdownbox;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.PopupWindow;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ItemClickLister {

    private EditText ed_box;
    private PopupWindow popupWindow;

    private List<String> list;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ed_box = findViewById(R.id.ed_box);

        recyclerView = new RecyclerView(this);
        list = new ArrayList<>();
        initData();
        MyAdapter myAdapter = new MyAdapter(list);
        myAdapter.setItemClickLister(this);
        LinearLayoutManager manager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(manager);
        recyclerView.setAdapter(myAdapter);
        ed_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //设置弹窗
                if (popupWindow == null) {
                    popupWindow = new PopupWindow(MainActivity.this);
                    popupWindow.setWidth(ed_box.getWidth());
                    popupWindow.setHeight(DensityUtil.dipToPx(MainActivity.this, 200)); //dpi->px
                    popupWindow.setContentView(recyclerView);   //设置布局
                    popupWindow.setFocusable(true);   //设置焦点
                    popupWindow.setBackgroundDrawable(null);

                }
                popupWindow.showAsDropDown(ed_box, 0, 0); //弹窗的位置在editText下面,距离左和右都为0个距离
            }
        });

    }

    private void initData() {
        for (int i = 0; i < 100; i++) {
            list.add(i + "<------>" + i);
        }
    }

    @Override
    public void onItemClick(int position) {
        //点击后输入框中加入点击的东西
        String msg = list.get(position);
        ed_box.setText(msg);
        //释放popupWindow
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;   //释放资源
        }
    }

    @Override
    public void onLongClick(int position) {

    }
}
