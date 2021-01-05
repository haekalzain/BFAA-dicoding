package com.example.githubusersubmission3.presentation

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubusersubmission3.Constants.Companion.EXTRA_USERNAME
import com.example.githubusersubmission3.R
import com.example.githubusersubmission3.Utility
import com.example.githubusersubmission3.data.Items
import com.example.githubusersubmission3.presentation.adapter.OnItemClickCallback
import com.example.githubusersubmission3.presentation.adapter.SearchAdapter
import com.example.githubusersubmission3.viewmodel.SearchViewModel
import com.mancj.materialsearchbar.MaterialSearchBar
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity(), MaterialSearchBar.OnSearchActionListener {

    private val TRUE = "state_true"
    private val FALSE = "state_false"

    private lateinit var searchViewModel: SearchViewModel
    private lateinit var adapter: SearchAdapter
    private val tag = SearchActivity::class.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utility.backGroundColor(this)
        setContentView(R.layout.activity_search)
        showErrorMessage(true)

        searchViewConfig()
        configSearchViewModel(savedInstanceState)
        configRecyclerView()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(TRUE, true)
        outState.putBoolean(FALSE, false)
    }

    private fun searchViewConfig() {
        searchView.setOnSearchActionListener(this)
    }

    override fun onButtonClicked(buttonCode: Int) {}

    override fun onSearchStateChanged(enabled: Boolean) {
        if (!enabled){
            showLoading(false)
            showErrorMessage(true)
            adapter.setData(null)
        }
    }

    override fun onSearchConfirmed(text: CharSequence?) {
        if (text != null && text.isNotEmpty() && text != "") {
            showLoading(true)
            showErrorMessage(false)
            searchViewModel.setSearchUser(text.toString())
        } else {
            showLoading(false)
            showErrorMessage(true)
            adapter.setData(null)
        }

        closeKeyboard()
    }

    private fun configSearchViewModel(savedInstanceState: Bundle?) {

        searchViewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        searchViewModel.getSearchUser().observe(this, Observer {

            if (it != null) {
                adapter.setData(it)
                if (savedInstanceState != null) {
                    showLoading(savedInstanceState.getBoolean(FALSE))
                    showErrorMessage(savedInstanceState.getBoolean(FALSE))
                } else {
                    showLoading(false)
                    showErrorMessage(false)
                }
            }
            if (it.isEmpty()) {
                if (savedInstanceState != null) {
                    showErrorMessage(savedInstanceState.getBoolean(TRUE))
                } else {
                    showErrorMessage(true)
                }
            }
        })

    }

    private fun configRecyclerView() {

        adapter = SearchAdapter()
        adapter.notifyDataSetChanged()

        val width = Resources.getSystem().displayMetrics.widthPixels

        searchRecyclerView.layoutManager = GridLayoutManager(applicationContext, width / 375)
        searchRecyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(items: Items) {
                showSelectedData(items)
            }
        })

    }

    private fun showSelectedData(items: Items) {

        val intentDetail = Intent(this, DetailActivity::class.java)
        intentDetail.putExtra(EXTRA_USERNAME, items.username)
        startActivity(intentDetail)
        Utility.transitionAct(this)
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    private fun showErrorMessage(state: Boolean) {
        if (state) {
            tvSearchUser.visibility = View.VISIBLE
            lottieNotFound.visibility = View.VISIBLE
        } else {
            tvSearchUser.visibility = View.GONE
            lottieNotFound.visibility = View.GONE
        }
    }

    private fun closeKeyboard() {
        val view: View? = this.currentFocus
        if (view != null) {
            val imm: InputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    fun backButton(view: View) {
        Utility.backAct(this)
    }

    override fun finish() {
        super.finish()
        Utility.transitionAct(this)
    }
}