package com.company.todolistproject;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

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

    public static ArrayList<MyItem> addWebView(ArrayList<String> lista) {
        ArrayList<MyItem> newList = new ArrayList<MyItem>();
        for(int i=0; i<lista.size(); i++) {
            MyItem item = new MyItem(lista.get(i), false);
            MyItem link = new MyItem("https://www.overdrive.ie/wp-content/uploads/2017/03/Bottom-Ads-bloodstock-150px-X-100px.jpg", true);
            newList.add(item);
            newList.add(link);
        }
        return newList;
    }
}
