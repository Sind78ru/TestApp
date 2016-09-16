package com.example.serg.testapp.mylist;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyListContent {

    private static Map<String, MyListItem> ITEM_MAP = new HashMap<>();


    public static void addItem(MyListItem item) {
        ITEM_MAP.put(item.id, item);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    public static List<MyListItem> getITEMS() {
        List<MyListItem> list;
        Collection<MyListItem> coll = ITEM_MAP.values();
        if (coll instanceof List)
            list = (List) coll;
        else
            list = new ArrayList(coll);
        return list;
    }

    public static Map<String, MyListItem> getItemMap() {
        return ITEM_MAP;
    }

    public static class MyListItem {
        public final String id;
        public final String first_name;
        public final String last_name;
        public final String email;
        public final String gender;
        public final String ip_address;

        public MyListItem(String id, String first_name, String last_name, String email, String gender, String ip_address) {
            this.id = id;
            this.first_name = first_name;
            this.last_name = last_name;
            this.email = email;
            this.gender = gender;
            this.ip_address = ip_address;
        }

        @Override
        public String toString() {
            return id + " " + first_name + " " + last_name;
        }
    }
}
