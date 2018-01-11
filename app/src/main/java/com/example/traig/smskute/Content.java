package com.example.traig.smskute;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Content extends AppCompatActivity {
    android.widget.Toolbar toolbar;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_recycler_view);
        int contentText;
        toolbar = findViewById(R.id.toolbar1);
        Bundle bundle = getIntent().getExtras();

        toolbar.setTitle(bundle.getString("title"));
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view1);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new MyAdaper(getResources().getStringArray(R.array.title1),this);
        recyclerView.setAdapter(adapter);
        recyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//hello
            }
        });

        //--ListView
       // final ListView listView = (ListView)findViewById(R.id.listView2);



      //  setSupportActionBar(toolbar);
            contentText = bundle.getInt("pos");
//            ContentAdapter adapter;
////            if (contentText == 0){
//                    adapter = new ContentAdapter(getApplicationContext(),0,getResources().getStringArray(R.array.title1));
//                    listView.setAdapter(adapter);
//                    listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String temp = (String)listView.getItemAtPosition(position);
//                long viewID = view.getId();
//                if(viewID == R.id.copy){
//                    ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
//                    ClipData clip = ClipData.newPlainText("clipboard data ", temp);
//                    clipboard.setPrimaryClip(clip);
//                    Toast.makeText(Content.this, "COPIED!", Toast.LENGTH_SHORT).show();
//                }else if(viewID == R.id.send){
//                    Intent i = new Intent(android.content.Intent.ACTION_SEND);
//                    i.setType("text/plain");
//                    i.putExtra(Intent.EXTRA_SUBJECT, "Subject...");
//                    i.putExtra(Intent.EXTRA_TEXT, temp);
//                    startActivity(Intent.createChooser(i, "Apps that can respond to this"));
//                }
//            }
//        });
////             else if(contentText == 1){
////                     adapter = new ContentAdapter(Content.this,0,getResources().getStringArray(R.array.title2));
////                    listView.setAdapter(adapter);
////            }
//
//       // }
//
//
//
//        //        btn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View v) {
////
////                //place your TextView's text in clipboard
////                ClipboardManager clipboard = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
////                ClipData myClip;
////
////                myClip = ClipData.newPlainText("text", (CharSequence) myClipboard);
////                myClipboard.setPrimaryClip(myClip);
////                 //Toast.makeText(getApplicationContext(),"COPIED",Toast.LENGTH_SHORT).show();
////            }
////        });
//
//
//        ImageButton img = (ImageButton) findViewById(R.id.iconBack);
//        img.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

    }

    public void SendSms(View view,String Phone,String temp){
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(Phone, null, temp, null, null);
            Toast.makeText(getApplicationContext(), "SMS sent.", Toast.LENGTH_LONG).show();
        }
        catch (Exception e) {
            Toast.makeText(getApplicationContext(),"SMS failed, please try again.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }
}
