package com.example.traig.smskute;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;

/**
 * Created by traig on 1/16/2018.
 */

public class AdapterFavourit  extends RecyclerView.Adapter<AdapterFavourit.ViewHolder> {

    private ArrayList<String> listItem;
    Context context;
    String filename;

    public AdapterFavourit( Context context,String filename) {
        this.listItem = readFile(filename);
        this.context = context;
        this.filename = filename;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView title;

        public ViewHolder(View itemView) {
            super(itemView);

            title=  itemView.findViewById(R.id.text_content);
        }
    }
    @Override
    public AdapterFavourit.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.main_item, parent, false);
        return  new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(AdapterFavourit.ViewHolder holder, final int position) {
        if(listItem != null)
            holder.title.setText(listItem.get(position));
    }
    @Override
    public int getItemCount() {
        if(listItem != null)
             return listItem.size();
        return 0;
    }


    public ArrayList<String> readFile(String pathname){
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(pathname);
            ObjectInputStream is = new ObjectInputStream(fis);
            listItem = (ArrayList<String>) is.readObject();
            Toast.makeText(context, "DONE", Toast.LENGTH_LONG).show();
            is.close();
            fis.close();
        } catch (FileNotFoundException e) {
            Toast.makeText(context, "ERROR", Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }finally {
            return listItem;
        }

    }
}
