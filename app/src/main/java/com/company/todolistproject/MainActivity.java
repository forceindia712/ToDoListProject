package com.company.todolistproject;

import android.content.SharedPreferences;
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
    ArrayList<MyItem> itemlist = new ArrayList<>();
    ArrayList<MyItem> itemslistNotDeleted = new ArrayList<>();
    SharedPreferences sp;
    Boolean visibleDeleted = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sp = getSharedPreferences("MyList", MODE_PRIVATE);
        itemlist = FileHelper.readDataJSON(sp);
        itemslistNotDeleted = FileHelper.tempArray(itemlist, false);

        if(itemlist == null)
            itemlist = new ArrayList<>();

        if(itemslistNotDeleted == null)
            itemslistNotDeleted = new ArrayList<>();

        binding.button.setOnClickListener(v -> {
            String itemName = binding.editText.getText().toString();
            itemlist.add(FileHelper.addItem(itemlist, itemName));
            itemslistNotDeleted.clear();
            itemslistNotDeleted.addAll(FileHelper.tempArray(itemlist, false));
            FileHelper.writeDataJSON(itemlist, sp);
            binding.editText.setText("");
            rvAdapter.notifyDataSetChanged();
        });


        binding.button3.setOnClickListener(v -> {

            ArrayList<MyItem> tempItemList = FileHelper.sortingNumber(itemlist);
            ArrayList<MyItem> tempItemNotDeleted = FileHelper.sortingNumber(itemslistNotDeleted);

            itemlist.clear();
            itemlist.addAll(tempItemList);

            itemslistNotDeleted.clear();
            itemslistNotDeleted.addAll(tempItemNotDeleted);

            rvAdapter.notifyDataSetChanged();
        });

        binding.button4.setOnClickListener(v -> {

            ArrayList<MyItem> tempItemList = FileHelper.sortingDate(itemlist);
            ArrayList<MyItem> tempItemNotDeleted = FileHelper.sortingDate(itemslistNotDeleted);

            itemlist.clear();
            itemlist.addAll(tempItemList);

            itemslistNotDeleted.clear();
            itemslistNotDeleted.addAll(tempItemNotDeleted);

            rvAdapter.notifyDataSetChanged();
        });

        binding.button5.setOnClickListener(v -> {
            if(!visibleDeleted) {
                visibleDeleted = true;
                itemslistNotDeleted.clear();
                itemslistNotDeleted.addAll(itemlist);

                rvAdapter.notifyDataSetChanged();
            } else {
                visibleDeleted = false;
                itemslistNotDeleted.clear();
                itemslistNotDeleted.addAll(FileHelper.tempArray(itemlist, false));

                rvAdapter.notifyDataSetChanged();
            }
        });

        rvAdapter = new RecyclerViewAdapter(itemslistNotDeleted);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(rvAdapter);
        rvAdapter.setClickListener(this);
    }

    @Override
    public void onItemClick(@NonNull MyItem item, @NonNull RecyclerView.ViewHolder holder, int position) {
        Callback callback = () -> {
            itemlist = FileHelper.removeItem(itemlist, position);
            FileHelper.writeDataJSON(itemlist, sp);
            itemslistNotDeleted.remove(position);
            rvAdapter.notifyDataSetChanged();
        };
        DialogFragment alert = new AlertDialogFragment(callback);
        alert.show(getSupportFragmentManager(), "dialog");
    }

    @Override
    public void onButtonClick(@NonNull MyItem item, @NonNull RecyclerView.ViewHolder holder, int position) {
        itemlist = FileHelper.renewItem(itemlist, position);
        FileHelper.writeDataJSON(itemlist, sp);
        itemslistNotDeleted.clear();
        itemslistNotDeleted.addAll(itemlist);
        rvAdapter.notifyDataSetChanged();
    }

}