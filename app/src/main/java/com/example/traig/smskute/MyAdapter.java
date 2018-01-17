package com.example.traig.smskute;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * Created by traig on 1/10/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    favouritItems person ;
    private String[] listItems;
    private Context mContext;
    private favouritItems FavouritItems;
    private String fileName;
    public MyAdapter(String[] listItems, Context mContext,String fileName) {
        this.listItems = listItems;
        this.mContext = mContext;
        this.fileName = fileName;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        person = readFile("data2.ser");
        if(person != null){
            if(person.isLove[position] == false)
            holder.loveBtn.setImageResource(R.drawable.ic_unfavorite);
            else holder.loveBtn.setImageResource(R.drawable.ic_favorite);
        }


        ClipboardManager myClipboard;
        final String temp = listItems[position];
        holder.txtTitle.setText(temp);
        holder.copyBtn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("clipboard data ", temp);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(mContext, "COPIED!", Toast.LENGTH_SHORT).show();
            }
        });
        final boolean[] flag = {true};
        holder.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(android.content.Intent.ACTION_SEND);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Subject...");
                    i.putExtra(Intent.EXTRA_TEXT, temp);
                    mContext.startActivity(i);
            }
        });

        holder.loveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag[0] == true){
                    flag[0] = false;
                    holder.loveBtn.setImageResource(R.drawable.ic_favorite);
                    Toast.makeText(mContext ,"ADDED TO FAVOURIT",Toast.LENGTH_LONG).show();
                }

                else {
                    flag[0] = true;
                    Toast.makeText(mContext ,"REMOVED TO FAVOURIT",Toast.LENGTH_LONG).show();
                holder.loveBtn.setImageResource(R.drawable.ic_unfavorite);}
            }
        });
       // notifyItemChanged(position);
    }

    public favouritItems readFile(String pathname){
        FileInputStream fis = null;

        try {
            fis = mContext.openFileInput(pathname);
            ObjectInputStream is = new ObjectInputStream(fis);
            person = (favouritItems) is.readObject();
            Toast.makeText(mContext, "DONE", Toast.LENGTH_LONG).show();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(mContext, "ERROR", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return person;
        }

    }

    @Override
    public int getItemCount() {
        return listItems.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView txtTitle;
        public ImageButton copyBtn1;
        public ImageButton sendBtn;
        public ImageButton loveBtn;
        public ViewHolder(View itemView) {
            super(itemView);
            txtTitle =  itemView.findViewById(R.id.tv_card);
            copyBtn1 = itemView.findViewById(R.id.copy_btn);
            sendBtn = itemView.findViewById(R.id.card_send);
            loveBtn = itemView.findViewById(R.id.card_love);
        }
    }
}
