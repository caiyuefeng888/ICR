package ICR.com.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import ICR.com.ListView.ReserveAdapter;
import ICR.com.ListView.Reserved2;
import ICR.com.R;
import ICR.com.dao.croom_readDao;
import ICR.com.dao.havereservedDao;

public class HavefinishedActivity extends BaseActivity {
    //String[][] finished_info =  {{"101","财务部会议","2","2019-01-10 08:00:00","2019-01-10 09:00:00"},{"101","财务部会议","2","2019-01-10 08:00:00","2019-01-10 09:00:00"}};
    private String finished_info[][] ;
    ReserveAdapter adapter;
    ListView listView;
    private ArrayList<Reserved2> FinishedList = new ArrayList<Reserved2>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.havefinished);

        hindBar();

        initfinishedList();
        adapter = new ReserveAdapter(
                HavefinishedActivity.this,R.layout.reserved_item,FinishedList);//RoomList传给适配器
        listView = (ListView) findViewById(R.id.Finished_confer);//选择显示的位置
        //点击事件，可以识别点的是哪一个item，并且能得到对应id即position
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Reserved2 reserved = FinishedList.get(position); //定义一个Room2类对象存放点击的那一行的RoomList中的信息
                static_confer_id = reserved.getConfer_id();//获取这个room的名字，传给BaseActivity中的全局变量Static_Room
                Static_Room = reserved.getName();
                Static_Date = reserved.getDate();
                Static_Clock = reserved.getStart();
                Intent intent = new Intent(HavefinishedActivity.this, FinishedReadActivity.class);
                startActivity(intent);
                //Toast.makeText(HavefinishedActivity.this,static_confer_id,Toast.LENGTH_SHORT).show();

            }
        });
        listView.setAdapter(adapter);
    }
    private void initfinishedList(){
        if(static_flag==1) {
            finished_info = havereservedDao.sendLoginRequest(1);
            for (int i = 0; i < finished_info.length; i++) {
                //System.out.println("：   ***开始进入构建UI****"+room_info[i][1]);
                //Reserved2类：会议室名称，会议名称，会议id，会议日期，开始时间，结束时间
                //reserved_info:{会议室名，会议名称，会议id，开始时间，结束时间}
                String date = null, clock1 = null, clock2 = null;
                date = getDate(finished_info[i][3]);//会议开始日期
                clock1 = getClock(finished_info[i][3]);//会议该日期下开始时间
                clock2 = getClock(finished_info[i][4]);//会议该日期下结束时间
                Reserved2 finished = new Reserved2(finished_info[i][0], finished_info[i][1], finished_info[i][2], date, clock1, clock2);
                FinishedList.add(finished);
            }
        }
    }

}
