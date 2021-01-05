package com.example.githubusersubmission3.presentation.fragment

import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.githubusersubmission3.Constants.Companion.EXTRA_USERNAME
import com.example.githubusersubmission3.R
import com.example.githubusersubmission3.presentation.adapter.FollowersAdapter
import com.example.githubusersubmission3.viewmodel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var followersAdapter: FollowersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        progressBar.visibility = View.VISIBLE

        val getUsername = activity?.intent?.getStringExtra(EXTRA_USERNAME)
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