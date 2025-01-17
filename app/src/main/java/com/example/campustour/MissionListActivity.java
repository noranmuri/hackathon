package com.example.campustour;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MissionListActivity extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mission_list);

        Intent mypage_intent = getIntent();
        String userid = mypage_intent.getStringExtra("Userid");

        ListView listView = findViewById(R.id.missionView);
        SingerAdapter adapter = new SingerAdapter();
        ArrayList<String> missions = new ArrayList<>();

        db.collection("mission")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                missions.add(document.getString("title").toString());
                            }
                        }
                    }
                });

        db.collection("users")
                .whereEqualTo("id", userid.toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List<String> date = (List<String>)document.get("mission");
                                for(String str: missions) {
                                    int index = missions.indexOf(str);
                                    if(date.get(index).equals("")) {
                                        //미완료
                                        adapter.addItem(new SingerItem(str, date.get(index), R.drawable.bcomplete1, index));
                                    } else {
                                        //완료
                                        adapter.addItem(new SingerItem(str, date.get(index), R.drawable.complete1, index));
                                    }
                                }
                            }
                            listView.setAdapter(adapter);
                        }
                    }
                });

        //아이템 클릭 시 미션리스트
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SingerItem item = (SingerItem)adapter.getItem(i);
                Intent intent = new Intent(getApplicationContext(), MissionActivity.class);
                intent.putExtra("title", item.name);
                intent.putExtra("u_id", userid);
                intent.putExtra("m_id", item.id);
                startActivity(intent);
            }
        });
    }

    class SingerAdapter extends BaseAdapter {
        ArrayList<SingerItem> items = new ArrayList<SingerItem>();

        @Override
        public int getCount(){
            return items.size();
        }
        public void addItem(SingerItem item){
            items.add(item);
        }

        public void addList(ArrayList<String> list){
            for(String str:list){
                //Log.d(str,"str" + str.toString());
                this.addItem(new SingerItem(str.toString(),"2021-01-01",R.drawable.complete1));
            }
        }

        @Override
        public Object getItem(int position){
            return items.get(position);
        }

        @Override
        public long getItemId(int position){
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent){
            SingerItemView singerItemView = null;
            if(convertView==null){
                singerItemView = new SingerItemView(getApplicationContext());
            } else {
                singerItemView = (SingerItemView)convertView;
            }
            SingerItem item = items.get(position);
            singerItemView.setName(item.getName());
            singerItemView.setMobile(item.getMobile());
            singerItemView.setImage(item.getResld());
            return singerItemView;
        }
    }
}