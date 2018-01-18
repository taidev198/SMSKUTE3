package com.example.traig.smskute;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by traig on 1/18/2018.
 */

public class FavoritItem implements Serializable {
    ArrayList<String> pathname;
    ArrayList<String> pos;
    ArrayList<String> ListItem;

    public ArrayList<String> getPathname() {
        return pathname;
    }

    public ArrayList<String> getPos() {
        return pos;
    }

    public ArrayList<String> getListItem() {
        return ListItem;
    }

    public FavoritItem(ArrayList<String> pathname, ArrayList<String> pos,ArrayList<String> ListItem) {
        this.ListItem = ListItem;
        this.pathname = pathname;
        this.pos = pos;
    }
}
