package com.company.todolistproject;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Locale;

public class FileHelper {

    public FileHelper() {
    }
    public static final String FILENAME = "listinfoa.dat";

    public static void writeData(ArrayList<String> item, Context context) {
        try {
            FileOutputStream fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            ObjectOutputStream oas = new ObjectOutputStream(fos);
            oas.writeObject(item);
            oas.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> readData(Context context) {
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
        for (int i = 0; i < itemlist.size(); i++) {
            editor.putString("itemlist" + i, itemlist.get(i));
        }
        editor.putInt("size", itemlist.size());
        editor.apply();
    }

    public static ArrayList<String> readDataSP(SharedPreferences sp) {
        int size = sp.getInt("size", 0);
        ArrayList<String> itemlist = new ArrayList<String>();
        for (int i = 0; i < size; i++) {
            itemlist.add(sp.getString("itemlist" + i, ""));
        }
        return itemlist;
    }

    public static ArrayList<MyItem> readDataJSON(SharedPreferences sp) {
        ArrayList<MyItem> itemlist = new Gson().fromJson(sp.getString("item", ""), new TypeToken<ArrayList<MyItem>>() {
        }.getType());

        return itemlist;
    }

    public static void writeDataJSON(ArrayList<MyItem> itemlist, SharedPreferences sp) {

        String jsonString = new Gson().toJson(itemlist);
        SharedPreferences.Editor editor = sp.edit();

        editor.clear();
        editor.putString("item", jsonString);
        editor.putInt("size", itemlist.size());
        editor.apply();
    }

    public static ArrayList<MyItem> tempArray(ArrayList<MyItem> itemlist, Boolean isDeleted) {
        ArrayList<MyItem> tempArray = new ArrayList<>();

        if (itemlist != null) {
            if (isDeleted) return itemlist;
            else {
                for (int i = 0; i < itemlist.size(); i++) {
                    if (!itemlist.get(i).isDeleted()) {
                        tempArray.add(itemlist.get(i));
                    }
                }
                return tempArray;
            }
        }
        return tempArray;
    }

    public static MyItem addItem(ArrayList<MyItem> itemlist, String text) {

        long id = 0;

        if (itemlist == null || itemlist.size() == 0)
            id = 0;
        else
            id = itemlist.get(itemlist.size() - 1).getId() + 1;

        LocalDateTime data = LocalDateTime.now();
        String date = data.toString();

        return new MyItem(id, date, text, false);
    }

    public static ArrayList<MyItem> removeItem(ArrayList<MyItem> itemlist, int position) {
        itemlist.get(position).setDeleted(true);
        return itemlist;
    }

    public static String toDate(String date) {
        if (date != null) {
            LocalDateTime dateTime = LocalDateTime.parse(date);
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
            String formattedDate = dateTime.format(myFormatObj);
            return formattedDate;
        }
        return null;
    }

    public static ArrayList<MyItem> sortingNumber(ArrayList<MyItem> itemlist) {

        int tempTab[] = new int[itemlist.size()];
        for (int i = 0; i < itemlist.size(); i++) {
            tempTab[i] = itemlist.get(i).getText().length();
        }

        Arrays.sort(tempTab);
        ArrayList<Boolean> usage = new ArrayList<>(Arrays.asList(new Boolean[itemlist.size()]));
        Collections.fill(usage, Boolean.TRUE);
        ArrayList<MyItem> tempArray = new ArrayList<>();
        for (int i = 0; i < itemlist.size(); i++) {
            for (int j = 0; j < itemlist.size(); j++) {
                if (tempTab[i] == itemlist.get(j).getText().length() && usage.get(j) == Boolean.TRUE) {
                    tempArray.add(itemlist.get(j));
                    usage.set(j, false);
                    j = 9999;
                }
            }
        }
        return tempArray;
    }

    public static ArrayList<MyItem> sortingDate(ArrayList<MyItem> itemlist) {

        ArrayList<String> tempArray = new ArrayList<>();
        for (int i = 0; i < itemlist.size(); i++) {
            tempArray.add(itemlist.get(i).getData());
        }

        Collections.sort(tempArray);
        ArrayList<Boolean> usage = new ArrayList<>(Arrays.asList(new Boolean[itemlist.size()]));
        Collections.fill(usage, Boolean.TRUE);
        ArrayList<MyItem> tempArrayTwo = new ArrayList<>();
        for (int i = 0; i < itemlist.size(); i++) {
            for (int j = 0; j < itemlist.size(); j++)
                if (tempArray.get(i).equals(itemlist.get(j).getData()) && usage.get(j) == Boolean.TRUE) {
                    tempArrayTwo.add(itemlist.get(j));
                    usage.set(j, false);
                    j = 9999;
                }
        }
        return tempArrayTwo;
    }

    public static ArrayList<MyItem> renewItem(ArrayList<MyItem> itemlist, int position) {
        LocalDateTime data = LocalDateTime.now();
        String date = data.toString();
        itemlist.get(position).setDeleted(false);
        itemlist.get(position).setData(date);
        return itemlist;
    }
}