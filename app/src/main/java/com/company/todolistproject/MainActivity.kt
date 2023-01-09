package com.company.todolistproject

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.company.todolistproject.databinding.ActivityMainBinding
import com.company.todolistproject.vievmodel.MyViewModel
import com.company.todolistproject.vievmodel.MyViewModelFactory

class MainActivity : FragmentActivity(), RecyclerViewAdapter.ClickListener {

    lateinit var binding: ActivityMainBinding
    lateinit var rvAdapter: RecyclerViewAdapter
    var itemList: ArrayList<MyItem> = arrayListOf()
    lateinit var sp: SharedPreferences
    lateinit var viewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        sp = getSharedPreferences("MyList", MODE_PRIVATE)

        viewModel = ViewModelProvider(this, MyViewModelFactory(sp))[MyViewModel::class.java]

        initUI()
        observeUI()
    }

    private fun observeUI() {
        viewModel.itemslistNotDeleted.observe(this) {
            itemList.clear()
            itemList.addAll(it)
            rvAdapter.notifyDataSetChanged()
        }
    }

    private fun initUI() {
        rvAdapter = RecyclerViewAdapter(itemList)
        binding.recyclerView.layoutManager = LinearLayoutManager(baseContext)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = rvAdapter
        rvAdapter.setClickListener(this)

        binding.buttonAdd.setOnClickListener {
            val itemName = binding.editText.text.toString()
            if(viewModel.checkEquals(itemName) == true)
                Toast.makeText(baseContext, "It is not possible to add the same note!", Toast.LENGTH_LONG).show()
            else
                viewModel.addItem(itemName)

            binding.editText.setText("")
        }
        binding.buttonSortNumber.setOnClickListener {
            viewModel.sortingNumber()
        }
        binding.buttonSortDate.setOnClickListener {
            viewModel.sortingDate()
        }
        binding.buttonVisibleDeleted.setOnClickListener {
            viewModel.visibleDeleted()
        }
    }

    override fun onItemClick(item: MyItem, holder: RecyclerView.ViewHolder, position: Int) {
        val callback = Callback {
            viewModel.removeItem(item, position)
        }
        val alert: DialogFragment =
            AlertDialogFragment(callback)
        alert.show(supportFragmentManager, "dialog")
    }

    override fun onButtonClick(item: MyItem, holder: RecyclerView.ViewHolder, position: Int) {
        viewModel.changeViewList(position)
    }
}