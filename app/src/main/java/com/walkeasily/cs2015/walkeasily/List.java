package com.walkeasily.cs2015.walkeasily;


import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;
import com.daimajia.swipe.adapters.BaseSwipeAdapter;
import com.tuesda.walker.circlerefresh.CircleRefreshLayout;

import java.util.logging.Handler;

public class List extends Activity {

    private Intent intent;
    private Bundle options;
    private TextView info;
    private Button button1;
    private Button button2;
    private CircleRefreshLayout circleRefreshLayout;
    private android.os.Handler handler;
    private ListView listView;
    private BaseSwipeAdapter basicSwipeAdpter;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        listView=(ListView)findViewById(R.id.list);
        textView=(TextView)findViewById(R.id.empty);
        basicSwipeAdpter=new BaseSwipeAdapter() {
            @Override
            public int getSwipeLayoutResourceId(int i) {
                return R.id.swip;
            }

            @Override
            public View generateView(int i, ViewGroup viewGroup) {
                return LayoutInflater.from(List.this).inflate(R.layout.list_item, null);
            }

            @Override
            public void fillValues(final int i, final View view) {
                TextView t = (TextView)view.findViewById(R.id.name);
                t.setText((i + 1) + ".");
                info=(TextView)view.findViewById(R.id.getMoreInfo);
                info.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        intent=new Intent(List.this,Infomation.class);
                        options=new Bundle();
                        startActivity(intent,options);
                    }
                });


                button1=(Button)view.findViewById(R.id.listButton1);
                button1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(List.this,"sdws",Toast.LENGTH_LONG).show();
                    }
                });



                button2=(Button)view.findViewById(R.id.listButton2);
                button2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(List.this,"button2",Toast.LENGTH_LONG).show();

                    }
                });
            }

            @Override
            public int getCount() {
                return 50;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }
        };

        circleRefreshLayout=(CircleRefreshLayout)findViewById(R.id.circlerefreshlayout);
        circleRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

                listView.setAdapter(basicSwipeAdpter);
                textView.setText("");
            }

            @Override
            public void refreshing() {
               new Thread(runnable).start();


            }
        });

        handler=new android.os.Handler(new android.os.Handler.Callback() {
            @Override
            public boolean handleMessage(Message msg) {
                circleRefreshLayout.finishRefreshing();
                return false;
            }
        });



    }




    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(5000);
                Message message= handler.obtainMessage();
                handler.sendMessage(message);

            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }
    };


}
