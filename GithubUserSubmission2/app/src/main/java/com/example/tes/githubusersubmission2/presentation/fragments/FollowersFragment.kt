package com.example.tes.githubusersubmission2.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tes.githubusersubmission2.R
import com.example.tes.githubusersubmission2.Utility.Constants.EXTRA_DATA
import com.example.tes.githubusersubmission2.adapter.FollowersAdapter
import com.example.tes.githubusersubmission2.model.UserItems
import com.example.tes.githubusersubmission2.viewModel.FollowersViewModel
import kotlinx.android.synthetic.main.fragment_followers.*

class FollowersFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    private lateinit var followersAdapter: FollowersAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val intent =
            activity?.intent?.getParcelableExtra(EXTRA_DATA) as UserItems?
        val getUsername = intent?.username

        if (getUsername != null) {
            configFollowersViewModel(getUsername)
        }

        configRecyclerView()
    }

    private fun configFollowersViewModel(getUsername: String) {
        val followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)
        followersViewModel.setFollowersUser(getUsername, activity!!)
        followersViewModel.getFollowersUser().observe(activity!!, Observer {
            if (it != null) {
                followersAdapter.setFollowersData(it)
                lottieNotFound.visibility = View.GONE
            }
            if (it.isEmpty()) {
                lottieNotFound.visibility = View.VISIBLE
            }
        })
    }

    private fun configRecyclerView() {
        followersAdapter = FollowersAdapter()
        followersAdapter.notifyDataSetChanged()

        followersRecyclerView.layoutManager = LinearLayoutManager(activity)
        followersRecyclerView.adapter = followersAdapter
        followersRecyclerView.setHasFixedSize(true)
    }
}