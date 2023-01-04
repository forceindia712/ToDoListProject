package com.company.todolistproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Set;

public class FileHelper {

    public static final String FILENAME = "listinfoa.dat";

    public static void writeData(ArrayList<String> item, Context context)
    {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            oas.writeObject(item);
            oas.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readData(Context context)
    {
        ArrayList<String> itemlist = null;

        try {
            FileInputStream fis = context.openFileInput(FILENAME);
            ObjectInputStream ois = new ObjectInputStream(fis);
            itemlist = (ArrayList<String>) ois.readObject();
        } catch (FileNotFoundException e) {
            itemlist = new ArrayList<>();
            Toast.makeText(context, "The file " + FILENAME + " was not found.", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return itemlist;
    }

    public static void writeDataSP(ArrayList<String> itemlist, SharedPreferences sp) {
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        for(int i=0; i<itemlist.size(); i++) {
            editor.putString("itemlist" + i, itemlist.get(i));
        }
        editor.putInt("size", itemlist.size());
        editor.apply();
    }

    public static ArrayList<String> readDataSP(SharedPreferences sp) {
        int size = sp.getInt("size", 0);
        ArrayList<String> itemlist = new ArrayList<String>();
        for(int i=0; i<size; i++) {
            itemlist.add(sp.getString("itemlist" + i, ""));
        }
        return itemlist;
    }
}