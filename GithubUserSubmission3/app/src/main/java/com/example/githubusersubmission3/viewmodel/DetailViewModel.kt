package com.example.githubusersubmission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission3.data.DetailResponse
import com.example.githubusersubmission3.service.DataResource
import com.example.githubusersubmission3.service.NetworkProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val tag = DetailViewModel::class.simpleName

    private val listUserSearch = MutableLiveData<DetailResponse>()

    fun setDetailUser(query: String) {

        val dataSource = NetworkProvider.providesHttpAdapter().create(DataResource::class.java)
        dataSource.detailUser(query)
            .enqueue(object : Callback<DetailResponse> {

                override fun onResponse(
                    call: Call<DetailResponse>,
                    response: Response<DetailResponse>
                ) {
                    listUserSearch.postValue(response.body())
                    Log.d(tag, "setDetailUser: onResponse")
                }

                override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                    Log.d(tag, "setDetailUser: onFailure = $t")
                }
            })
    }

    fun getDetailUser(): LiveData<DetailResponse?> {
        Log.d(tag, "getDetailUser")
        return listUserSearch
    }
}