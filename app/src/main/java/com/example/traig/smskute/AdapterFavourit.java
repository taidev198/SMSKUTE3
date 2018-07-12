package com.example.traig.smskute;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by traig on 1/16/2018.
 */

public class AdapterFavourit  extends RecyclerView.Adapter<AdapterFavourit.ViewHolder> {

    private FavoritItem listItem;
    Context context;
    private ShareActionProvider shareActionProvider;
    public AdapterFavourit( Context context,FavoritItem listItem) {

        this.listItem = listItem;
        this.context = context;
        if(this.listItem == null)
        Toast.makeText(context, "EMPTY LIST", Toast.LENGTH_LONG).show();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;
        ImageButton copyBtn1;
        ImageButton sendBtn;
        ImageButton loveBtn;
        ImageButton shareBtn;

         ViewHolder(View itemView) {
            super(itemView);

            title=  itemView.findViewById(R.id.tv_card);
             copyBtn1 = itemView.findViewById(R.id.copy_btn);
             sendBtn = itemView.findViewById(R.id.card_send);
             loveBtn = itemView.findViewById(R.id.card_love);
             shareBtn = itemView.findViewById(R.id.card_share);
        }
    }
    @Override
    public AdapterFavourit.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterFavourit.ViewHolder holder, final int position) {
        if(listItem != null){

            ClipboardManager myClipboard;
            final String temp = listItem.getList().get(position);
            holder.copyBtn1.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("clipboard data ", temp);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(context, "COPIED!", Toast.LENGTH_SHORT).show();
                }
            });
            holder.sendBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "01627249988"));
                    intent.putExtra("sms_body", temp);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });

            holder.shareBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                    sharingIntent.setType("text/plain");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "SHARE Here");
                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, temp);
                    sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(Intent.createChooser(sharingIntent, temp));
                }
            });


            holder.title.setText(listItem.getList().get(position));
            holder.loveBtn.setImageResource(R.drawable.ic_favorite);
            holder.loveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(context ,"REMOVED TO FAVOURIT",Toast.LENGTH_LONG).show();
                    listItem.getList().remove(position);
                    FavoritItem favoritItem =(FavoritItem) readFile(listItem.getPathname().get(position));
                    Log.d(TAG, "filename tab3:" + listItem.getPathname().get(position));
                    favoritItem.getIsFavorible().set(Integer.parseInt(listItem.getPos().get(position)),"false");
                    Log.d(TAG, "isFavor:" +favoritItem.getIsFavorible().get(position));
                    Log.d(TAG, "pos:" + listItem.getPos().get(position));
                    listItem.getPos().remove(position);

                    writeFile(listItem.getPathname().get(position),favoritItem);

                    listItem.getPathname().remove(position);

                    writeFile("ListItems2.ser",listItem);

                    notifyDataSetChanged();
                }
            });
        }

    }
    @Override
    public int getItemCount() {
        if(listItem != null)
             return listItem.getList().size();
        return 0;
    }


     Object readFile(String pathname){
        FileInputStream fis = null;
        Object person = null;
        try {
            fis = context.openFileInput(pathname);
            ObjectInputStream is = new ObjectInputStream(fis);
            person =  is.readObject();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return person;
        }

    }

     void writeFile(String pathname,Object obj){
        FileOutputStream fos = null;
        try {
            fos = context.openFileOutput(pathname, Context.MODE_PRIVATE);
            ObjectOutputStream os = null;
            os = new ObjectOutputStream(fos);
            os.writeObject(obj);
            os.close();
            fos.close();
        } catch (NotSerializableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
