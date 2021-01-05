package com.example.githubusersubmission3.presentation

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubusersubmission3.Constants.Companion.EXTRA_USERNAME
import com.example.githubusersubmission3.R
import com.example.githubusersubmission3.Utility
import com.example.githubusersubmission3.data.entity.Favorite
import com.example.githubusersubmission3.presentation.adapter.FavoriteAdapter
import com.example.githubusersubmission3.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : AppCompatActivity() {

    private val tag = FavoriteActivity::class.simpleName
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utility.backGroundColor(this)
        setContentView(R.layout.activity_favorite)

        configRecyclerView()
        configQueryAll()
    }

    override fun onResume() {
        super.onResume()

        configRecyclerView()
        configQueryAll()
    }

    private fun configQueryAll() {

        progressBar.visibility = View.VISIBLE

        favoriteViewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)
        favoriteViewModel.getQueryAll(this).observe(this, Observer {

            progressBar.visibility = View.GONE

            if (it.size > 0) {
                adapter.favorites = it
                lottieNotFound.visibility = View.GONE
            } else {
                lottieNotFound.visibility = View.VISIBLE
                adapter.favorites = it
            }

        })

    }

    private fun configRecyclerView() {

        adapter = FavoriteAdapter()
        val width = Resources.getSystem().displayMetrics.widthPixels

        favoriteRecyclerView.layoutManager = GridLayoutManager(applicationContext, width / 375)
        favoriteRecyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(favorite: Favorite) {
                showSelectedData(favorite)
            }
        })

        adapter.notifyDataSetChanged()

    }

    fun showSelectedData(favorite: Favorite) {
        val intentDetail = Intent(this, DetailActivity::class.java)
        intentDetail.putExtra(EXTRA_USERNAME, favorite.username)
        startActivity(intentDetail)

        Utility.transitionAct(this)
    }

    fun backButton(view: View) {
        Utility.backAct(this)
    }

    override fun finish() {
        super.finish()
        Utility.transitionAct(this)
    }
}