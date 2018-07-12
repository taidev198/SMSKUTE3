package com.example.traig.smskute;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import java.io.InputStream;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

/**
 * Created by traig on 1/10/2018.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private  ArrayList<String> listItems;
    private Context mContext;
    private String fileName;
    private FavoritItem listItemFavourit;
    private FavoritItem favoritItem;
    public MyAdapter(ArrayList<String> listItems, Context mContext,String fileName) {
        notifyDataSetChanged();
        this.listItems = listItems;
        this.mContext = mContext;
        this.fileName = fileName;
        //init data
        favoritItem = (FavoritItem) readFile(fileName);
        if (favoritItem == null) {//check if whether null or not
            //init data
            favoritItem = new FavoritItem(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(listItems.size()));

        }
        if (favoritItem.getIsFavorible().size() == 0) {
            for (int i = 0; i < listItems.size(); i++) {
                favoritItem.getIsFavorible().add("false");
            }
            Log.d(TAG, "filename tab1:" + fileName);
        }

        listItemFavourit = (FavoritItem) readFile("ListItems2.ser");
        if (listItemFavourit == null)
            listItemFavourit = new FavoritItem(new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>(), new ArrayList<String>());
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        if(favoritItem.getIsFavorible().get(position).equals("true"))
            holder.loveBtn.setImageResource(R.drawable.ic_favorite);
            else holder.loveBtn.setImageResource(R.drawable.ic_unfavorite);

        ClipboardManager myClipboard;
        final String temp = listItems.get(position);
        holder.txtTitle.setText(temp);
        holder.copyBtn1.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                    ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("clipboard data ", temp);
                    clipboard.setPrimaryClip(clip);
                    Toast.makeText(mContext, "COPIED!", Toast.LENGTH_SHORT).show();
            }
        });
        holder.sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("sms:" + "01627249988"));
                intent.putExtra("sms_body", temp);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(intent);
            }
        });

        holder.shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, temp);
                sharingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(Intent.createChooser(sharingIntent, temp));
            }
        });

        holder.loveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    if(favoritItem.getIsFavorible().get(position).equals("false") ){//size = 0 or not equal pos
                    //convert to String
                    favoritItem.getPos().add(Integer.toString(position));//save pos favorit
                    listItemFavourit.getPos().add(Integer.toString(position));//save pos favorit
                    listItemFavourit.getPathname().add(fileName);
                    listItemFavourit.getList().add(listItems.get(position));
                    holder.loveBtn.setImageResource(R.drawable.ic_favorite);
                    favoritItem.getIsFavorible().set(position,"true");
                    Toast.makeText(mContext ,"ADDED TO FAVOURIT",Toast.LENGTH_LONG).show();
                    //wirte to file
                    writeFile("ListItems2.ser",listItemFavourit);

                    writeFile(fileName,favoritItem);
//                        notifyDataSetChanged();
                        //notifyItemChanged(position);
                }
                else {
                    favoritItem.getPos().remove(favoritItem.getPos().size() -1);//remove at current pos
                    listItemFavourit.getList().remove(listItemFavourit.getList().size() -1);
                    listItemFavourit.getPathname().remove(listItemFavourit.getPathname().size() -1);
                    listItemFavourit.getPos().remove(listItemFavourit.getPos().size()-1);
                    Toast.makeText(mContext ,"REMOVED TO FAVOURIT",Toast.LENGTH_LONG).show();
                    holder.loveBtn.setImageResource(R.drawable.ic_unfavorite);

                    favoritItem.getIsFavorible().set(position,"false");
                    writeFile("ListItems2.ser",listItemFavourit);
                    writeFile(fileName,favoritItem);
                        //notifyDataSetChanged();
                        //notifyItemChanged(position);
                }
            }
        });
    }
    public Object readFile(String pathname){
        FileInputStream fis = null;
        Object person = null;
        try {
            fis = this.mContext.openFileInput(pathname);
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


    public Object readFileAssets(String pathname){
        InputStream fis ;
        Object person = null;
       // AssetManager assetManager = mContext.getAssets();
        try {
            fis =  this.mContext.getAssets().open(pathname);
            ObjectInputStream is = new ObjectInputStream(fis);
            person =  is.readObject();
            is.close();
            fis.close();
        }  catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return person;

    }

    private void writeFile(String pathname,Object obj){
        FileOutputStream fos = null;
        try {
            fos = mContext.openFileOutput(pathname, Context.MODE_PRIVATE);
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

    @Override
    public int getItemCount() {
        return listItems.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder{
         TextView txtTitle;
         ImageButton copyBtn1;
         ImageButton sendBtn;
         ImageButton loveBtn;
         ImageButton shareBtn;
         ViewHolder(View itemView) {
            super(itemView);
            txtTitle =  itemView.findViewById(R.id.tv_card);
            copyBtn1 = itemView.findViewById(R.id.copy_btn);
            sendBtn = itemView.findViewById(R.id.card_send);
            loveBtn = itemView.findViewById(R.id.card_love);
            shareBtn = itemView.findViewById(R.id.card_share);
        }
    }
}
