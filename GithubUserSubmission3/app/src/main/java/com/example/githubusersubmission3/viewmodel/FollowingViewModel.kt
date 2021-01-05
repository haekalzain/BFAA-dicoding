package com.example.githubusersubmission3.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubusersubmission3.data.FollowingResponse
import com.example.githubusersubmission3.service.DataResource
import com.example.githubusersubmission3.service.NetworkProvider
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val tag = FollowingViewModel::class.simpleName

    val listFollowingUser = MutableLiveData<ArrayList<FollowingResponse>>()

    fun setFollowingUser(query: String) {

        val dataSource = NetworkProvider.providesHttpAdapter().create(DataResource::class.java)
        dataSource.followingUser(query)
            .enqueue(object : Callback<ArrayList<FollowingResponse>> {
                override fun onResponse(
                    call: Call<ArrayList<FollowingResponse>>,
                    response: Response<ArrayList<FollowingResponse>>
                ) {
                    val items = response.body()
                    listFollowingUser.postValue(items)
                    Log.d(tag, "setFollowingUser: onResponse")
                }

                override fun onFailure(call: Call<ArrayList<FollowingResponse>>, t: Throwable) {
                    Log.d(tag, "setFollowingUser: onFailure = $t")
                }
            })

    }

    fun getFollowingUser(): LiveData<ArrayList<FollowingResponse>> {
        return listFollowingUser
    }

}