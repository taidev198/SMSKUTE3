package com.example.traig.smskute;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by traig on 1/18/2018.
 */

public class FavoritItem implements Serializable {
    private ArrayList<String> pathname;
    private ArrayList<String> pos;
    private ArrayList<String> list;
    private ArrayList<String> isFavorible;

    public ArrayList<String> getPathname() {
        return pathname;
    }
    public void setIsFavorable(String bool){
        if(this.list.size() != 0){
            this.list.remove(this.list.size() -1);
            this.list.add(bool);
        }
        else this.list.add(bool);
    }
    public ArrayList<String> getPos() {
        return pos;
    }

    public ArrayList<String> getList() {
        return list;
    }
    public ArrayList<String> getIsFavorible(){
        return isFavorible;
    }

    public FavoritItem(ArrayList<String> pathname, ArrayList<String> pos,ArrayList<String> list,ArrayList<String> isFavorible) {
        this.list = list;
        this.pathname = pathname;
        this.pos = pos;
        this.isFavorible = isFavorible;
    }
}
