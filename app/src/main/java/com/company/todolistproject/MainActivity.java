package com.company.todolistproject;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        itemlist = FileHelper.readData(this);

        binding.button.setOnClickListener(v -> {
            String itemName = binding.editText.getText().toString();
            itemlist.add(itemName);
            binding.editText.setText("");
            FileHelper.writeData(itemlist, getApplicationContext());
            rvAdapter.notifyDataSetChanged();
        });

        rvAdapter = new RecyclerViewAdapter(itemlist);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(rvAdapter);
        rvAdapter.setClickListener(this);
    }

    @Override
    public void onItemClick(@NonNull String item, @NonNull RecyclerView.ViewHolder holder, int position) {
        Callback callback = () -> {
            itemlist.remove(position);
            rvAdapter.notifyDataSetChanged();
            FileHelper.writeData(itemlist, getApplicationContext());
        };
        DialogFragment alert = new AlertDialogFragment(callback);
        alert.show(getSupportFragmentManager(), "dialog");
    }
}