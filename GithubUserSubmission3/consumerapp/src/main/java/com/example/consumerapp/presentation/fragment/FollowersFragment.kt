package com.example.consumerapp.presentation.fragment

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.consumerapp.R
import com.example.consumerapp.presentation.adapter.FollowersAdapter
import com.example.consumerapp.presentation.DetailActivity
import com.example.consumerapp.viewModel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followersAdapter: FollowersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar.visibility = View.VISIBLE

        val getUsername = activity?.intent?.getStringExtra(DetailActivity.EXTRA_USERNAME)
        if (getUsername != null) {
            configFollowersViewModel(getUsername)
        }

        configRecyclerView()
    }

    private fun configFollowersViewModel(getUsername: String) {
        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        followersViewModel.setFollowersUser(getUsername)
        followersViewModel.getFollowersUser().observe(activity!!, Observer {
            if (it != null) {
                followersAdapter.setFollowersData(it)
                progressBar.visibility = View.GONE
                lottieNotFound.visibility = View.GONE
            }
            if (it.isEmpty()) {
                progressBar.visibility = View.GONE
                lottieNotFound.visibility = View.VISIBLE
            }
        })
    }

    private fun configRecyclerView() {
        followersAdapter = FollowersAdapter()
        followersAdapter.notifyDataSetChanged()

        val width = Resources.getSystem().displayMetrics.widthPixels

        followersRecyclerView.layoutManager = GridLayoutManager(activity, width / 375)
        followersRecyclerView.adapter = followersAdapter
    }
}