package com.company.todolistproject;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.company.todolistproject.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends FragmentActivity implements RecyclerViewAdapter.ClickListener {

    ActivityMainBinding binding;
    RecyclerViewAdapter rvAdapter = null;
    ArrayList<String> itemlist = new ArrayList<>();
    ArrayList<MyItem> itemslist = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemlist = FileHelper.readData(this);

        binding.button.setOnClickListener(v -> {
            String itemName = binding.editText.getText().toString();
            itemlist.add(itemName);
            FileHelper.writeData(itemlist, getApplicationContext());
//            MyItem item = new MyItem(itemName, false);
//            MyItem link = new MyItem("https://www.overdrive.ie/wp-content/uploads/2017/03/Bottom-Ads-bloodstock-150px-X-100px.jpg", true);
//            itemslist.add(item);
//            itemslist.add(link);
            binding.editText.setText("");
            rvAdapter.notifyDataSetChanged();
        });

//        itemslist = FileHelper.addWebView(itemlist);

        rvAdapter = new RecyclerViewAdapter(itemlist);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(rvAdapter);
        rvAdapter.setClickListener(this);
    }

    @Override
    public void onItemClick(@NonNull String item, @NonNull RecyclerView.ViewHolder holder, int position) {
        Callback callback = () -> {
//            itemslist.remove(position);
//            itemslist.remove(position);
            itemlist.remove(position);
            rvAdapter.notifyDataSetChanged();
//            FileHelper.writeData(itemlist, getApplicationContext());

            Intent serviceIntent = new Intent(this, MyService.class);
            serviceIntent.putExtra("list_remove", itemlist);
            startService(serviceIntent);
        };
        DialogFragment alert = new AlertDialogFragment(callback);
        alert.show(getSupportFragmentManager(), "dialog");
    }
}