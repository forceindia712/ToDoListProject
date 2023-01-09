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
import java.util.Random;

public class FileHelper {

    public static final String FILENAME = "listinfoa.dat";
    static Random liczba = new Random();
    static ArrayList<String> listURL = new ArrayList<>();

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

    public static ArrayList<MyItem> WebView(ArrayList<String> list) {
        ArrayList<MyItem> newList = new ArrayList<MyItem>();

        listURL.add("https://www.overdrive.ie/wp-content/uploads/2017/03/Bottom-Ads-bloodstock-150px-X-100px.jpg");
        listURL.add("https://github.com/");
        listURL.add("https://www.facebook.com/");

        for(int i=0; i<list.size(); i++) {
            MyItem item = new MyItem(list.get(i), false);
            MyItem link = new MyItem(listURL.get(liczba.nextInt(3)), true);
            newList.add(item);
            newList.add(link);
        }
        return newList;
    }

    public static MyItem addURL() {
        listURL.add("https://www.overdrive.ie/wp-content/uploads/2017/03/Bottom-Ads-bloodstock-150px-X-100px.jpg");
        listURL.add("https://github.com/");
        listURL.add("https://www.facebook.com/");

        MyItem link = new MyItem(listURL.get(liczba.nextInt(3)), true);

        return link;
    }
}
