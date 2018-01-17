package com.example.traig.smskute;

import java.util.ArrayList;

/**
 * Created by traig on 1/11/2018.
 */

public class favouritItems {
    ArrayList<String> ListItem;
    boolean[] isLove;

    public favouritItems() {
    }

    public ArrayList<String> getListItem() {
        return ListItem;
    }

    public void setListItem(ArrayList<String> listItem) {
        ListItem = listItem;
    }

    public boolean[] getIsLove() {
        return isLove;
    }

    public void setIsLove(boolean[] isLove) {
        this.isLove = isLove;
    }
}
