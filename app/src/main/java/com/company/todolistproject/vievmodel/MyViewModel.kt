package com.company.todolistproject.vievmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.company.todolistproject.repository.FileHelper
import com.company.todolistproject.item.MyItem

class MyViewModel(private val sp: SharedPreferences) : ViewModel() {

    var itemlist: MutableLiveData<ArrayList<MyItem>> = MutableLiveData()
    var itemslistNotDeleted: MutableLiveData<ArrayList<MyItem>> = MutableLiveData()
    var visibleDeleted: MutableLiveData<Boolean> = MutableLiveData(false)

    init {
        itemlist.value = arrayListOf()
        itemslistNotDeleted.value = arrayListOf()
        readItemList()
        readItemListNotDeleted()
        checkNullItemList()
    }

    fun getItemList(): ArrayList<MyItem> {
        return itemlist.value!!
    }

    fun readItemList() {
        itemlist.postValue(FileHelper.readDataJSON(sp))
        itemlist.value = FileHelper.readDataJSON(sp)
    }

    fun readItemListNotDeleted() {
        itemslistNotDeleted.postValue(FileHelper.tempArray(itemlist.value, false))
        itemslistNotDeleted.value = FileHelper.tempArray(itemlist.value, false)
    }

    fun getItemListNotDeleted(): ArrayList<MyItem> {
        return itemslistNotDeleted.value!!
    }

    fun updateItemListNotDeleted() {
        itemslistNotDeleted.postValue(FileHelper.tempArray(itemlist.value, false))
    }

    fun addItem(itemName: String) {
        val tempItemList = getItemList()
        tempItemList.add(FileHelper.addItem(getItemList(), itemName))
        itemlist.postValue(tempItemList)
        updateItemListNotDeleted()
        writeItemList()
    }

    fun writeItemList() {
        FileHelper.writeDataJSON(getItemList(), sp)
    }

    fun sortingNumber() {
        itemlist.postValue(FileHelper.sortingNumber(getItemList()))
        itemslistNotDeleted.postValue(FileHelper.sortingNumber(getItemListNotDeleted()))
    }

    fun sortingDate() {
        itemlist.postValue(FileHelper.sortingDate(getItemList()))
        itemslistNotDeleted.postValue(FileHelper.sortingDate(getItemListNotDeleted()))
    }

    fun visibleDeleted() {
        if (!visibleDeleted.value!!) {
            visibleDeleted.postValue(true)
            itemslistNotDeleted.postValue(getItemList())
        } else {
            visibleDeleted.postValue(false)
            updateItemListNotDeleted()
        }
    }

    fun removeItem(item: MyItem, position: Int) {
        itemlist.postValue(FileHelper.removeItem(getItemList(), item))
        writeItemList()

        if(visibleDeleted.value == false) {
            val tempListNotDeleted = itemslistNotDeleted.value
            tempListNotDeleted?.removeAt(position)
            itemslistNotDeleted.postValue(tempListNotDeleted)
        } else {
            itemslistNotDeleted.postValue(itemlist.value)
        }
    }

    fun changeViewList(item: MyItem) {
        itemlist.postValue(FileHelper.renewItem(getItemList(), item))
        writeItemList()
        if (allRemoved()) {
            visibleDeleted.postValue(false)
            updateItemListNotDeleted()
        } else
            itemslistNotDeleted.postValue(itemlist.value)
    }

    fun checkNullItemList() {
        if (itemlist.value == null)
            itemlist.postValue(arrayListOf())

        if(itemslistNotDeleted.value == null)
            itemslistNotDeleted.postValue(arrayListOf())
    }

    fun allRemoved(): Boolean {
        for (i in 0 until (itemslistNotDeleted.value?.size ?: 0)) {
            if(itemslistNotDeleted.value?.get(i)?.isDeleted == true)
                return false
        }
        return true
    }

    fun checkEquals(itemName: String): Boolean {
        for (i in 0 until (itemlist.value?.size ?: 0)) {
            if (itemlist.value?.get(i)?.text.equals(itemName) == true)
                return true
        }
        return false
    }
}