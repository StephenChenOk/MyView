package com.chen.fy.contactinterface;
/**
 * 联系人界面,带索引
 */

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {

    private TextView tv_word;

    private RecyclerView recyclerView;

    private Handler handler = new Handler();

    private ArrayList<Persons> lists = new ArrayList<>();

    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_main);
        tv_word = findViewById(R.id.tv_word);
        IndexView indexView = findViewById(R.id.iv_main);
        indexView.setOnIndexChangeListen(new IndexView.OnIndexChangeListen() {
            @Override
            public void onIndexChange(String word) {
               indexChange(word);
               recyclerViewChange(word);
            }
        });
        initData();
        layoutManager = new GridLayoutManager(this, 1);//1 表示列数
        recyclerView.setLayoutManager(layoutManager);
        MyAdapter myAdapter = new MyAdapter(lists);
        recyclerView.setAdapter(myAdapter);
    }

    /**
     * 点击索引栏跳转相应的通讯录界面
     */
    private void recyclerViewChange(String word) {
        for(int i=0;i<lists.size();i++){
            if(word.equals(lists.get(i).getWord())){
                MoveToPosition(layoutManager,i);
                return;
            }
        }
    }

    /**
     * 点击索引,显示字母在屏幕中间
     */
    private void indexChange(String word){
        tv_word.setVisibility(View.VISIBLE);
        tv_word.setText(word);
        handler.removeCallbacksAndMessages(null);  //清除消息
        handler.postDelayed(new Runnable() {   //让任务延迟进行时,都可用这个方法
            @Override
            public void run() {    //也是运行在主线程,实现延迟2s再执行下面语句
                tv_word.setVisibility(View.GONE);
            }
        }, 2000);
    }

    private void initData() {
        Persons person1 = new Persons("陈一生");
        Persons person2 = new Persons("陈生");
        Persons person3 = new Persons("张三");
        Persons person4 = new Persons("李四");
        Persons person5 = new Persons("王五");
        Persons person6 = new Persons("刘麻子");
        Persons person7 = new Persons("七七七");
        Persons person8 = new Persons("八哥哥");
        Persons person9 = new Persons("九霄美");
        Persons person10 = new Persons("十叔公");
        Persons person11 = new Persons("十一少");
        Persons person12 = new Persons("谢霆锋");
        Persons person13 = new Persons("马云");
        Persons person14 = new Persons("张国荣");
        Persons person15 = new Persons("陈奕迅");
        Persons person16 = new Persons("佟丽娅");
        Persons person17 = new Persons("马化腾");
        Persons person18 = new Persons("迪丽热巴");
        Persons person19 = new Persons("库里");
        Persons person20 = new Persons("汤普森");

        lists.add(person1);
        lists.add(person2);
        lists.add(person3);
        lists.add(person4);
        lists.add(person5);

        lists.add(person6);
        lists.add(person7);
        lists.add(person8);
        lists.add(person9);
        lists.add(person10);

        lists.add(person11);
        lists.add(person12);
        lists.add(person13);
        lists.add(person14);
        lists.add(person15);

        lists.add(person16);
        lists.add(person17);
        lists.add(person18);
        lists.add(person19);
        lists.add(person20);

        Collections.sort(lists, new Comparator<Persons>() {
            @Override
            public int compare(Persons o1, Persons o2) {
                return o1.getPinyin().compareTo(o2.getPinyin());
            }
        });
    }

    /**
     * RecyclerView 移动到当前位置，
     *
     * @param manager  设置RecyclerView对应的manager
     * @param n  要跳转的位置
     */
    public static void MoveToPosition(LinearLayoutManager manager, int n) {
        manager.scrollToPositionWithOffset(n, 0);
      //  manager.setStackFromEnd(true);
    }
}
