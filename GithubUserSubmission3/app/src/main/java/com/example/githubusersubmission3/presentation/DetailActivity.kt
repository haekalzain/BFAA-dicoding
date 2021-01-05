package com.example.githubusersubmission3.presentation

import android.content.ContentValues
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.githubusersubmission3.Constants.Companion.EXTRA_USERNAME
import com.example.githubusersubmission3.Constants.Companion.IS_ABOUT_US
import com.example.githubusersubmission3.R
import com.example.githubusersubmission3.Utility
import com.example.githubusersubmission3.data.entity.Favorite
import com.example.githubusersubmission3.db.DatabaseContract.NoteColumns.Companion.AVATAR
import com.example.githubusersubmission3.db.DatabaseContract.NoteColumns.Companion.CONTENT_URI
import com.example.githubusersubmission3.db.DatabaseContract.NoteColumns.Companion.TYPE
import com.example.githubusersubmission3.db.DatabaseContract.NoteColumns.Companion.USERNAME
import com.example.githubusersubmission3.db.DatabaseContract.NoteColumns.Companion.USER_ID
import com.example.githubusersubmission3.presentation.adapter.ViewPagerAdapter
import com.example.githubusersubmission3.viewmodel.DetailViewModel
import com.example.githubusersubmission3.viewmodel.FavoriteViewModel
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private val tag = DetailActivity::class.simpleName
    private lateinit var detailViewModel: DetailViewModel
    private lateinit var favoriteViewModel: FavoriteViewModel
    private lateinit var favorite: Favorite

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Utility.backGroundColor(this)
        setContentView(R.layout.activity_detail)
        showLoading(true)

        val getUsername = intent.getStringExtra(EXTRA_USERNAME)
        val isAbout = intent.getBooleanExtra(IS_ABOUT_US, false)
        if (isAbout)
            favButton.visibility = View.GONE
        else
            favButton.visibility = View.VISIBLE

        configDetailViewModel(getUsername)
        configViewPager()

    }

    private fun checkFavButton(favorite: Favorite) {

        val uriWithId = Uri.parse(CONTENT_URI.toString() + "/" + favorite.userId)
        favoriteViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FavoriteViewModel::class.java)

        favoriteViewModel.getQueryById(this, uriWithId).observe(this, Observer {

            if (it.isNullOrEmpty()) {
                favButton.setBackgroundResource(R.drawable.ripple_fav)
                favButton()
            } else {
                favButton.setBackgroundResource(R.drawable.ripple_unfav)
                unFavButton(uriWithId)
            }

        })
    }

    private fun favButton() {

        favButton.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                val values = ContentValues()
                values.put(USER_ID, favorite.userId)
                values.put(USERNAME, favorite.username)
                values.put(TYPE, favorite.type)
                values.put(AVATAR, favorite.avatar)
                contentResolver?.insert(CONTENT_URI, values)

            }

            favButton.setBackgroundResource(R.drawable.ripple_unfav)
            val toast = Toast.makeText(
                this,
                getString(R.string.add_user_fav, favorite.username),
                Toast.LENGTH_SHORT
            )
            toast.show()
            toast.setGravity(Gravity.CENTER, 0, 0)
            Log.d(tag, "favored")
            checkFavButton(favorite)

        }

    }

    private fun unFavButton(uriWithId: Uri) {

        favButton.setOnClickListener {

            GlobalScope.launch(Dispatchers.IO) {
                contentResolver?.delete(uriWithId, null, null)
            }
            favButton.setBackgroundResource(R.drawable.ripple_fav)
            val toast = Toast.makeText(
                this,
                getString(R.string.remove_user_fav, favorite.username),
                Toast.LENGTH_SHORT
            )
            toast.show()
            toast.setGravity(Gravity.CENTER, 0, 0)
            checkFavButton(favorite)

        }

    }

    private fun configDetailViewModel(getUsername: String?) {

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        detailViewModel.setDetailUser(getUsername!!)
        detailViewModel.getDetailUser().observe(this, Observer {


            usernameDetail.text = getUsername
            nameDetail.text = Utility.getData(it?.name)
            companyDetail.text = Utility.getData(it?.company)
            locationDetail.text = Utility.getData(it?.location)
            repositoryDetail.text = getString(R.string.repository, it?.repository)
            followersDetail.text = getString(R.string.followers_d, it?.followers)
            followingDetail.text = getString(R.string.following_d, it?.following)
            Utility.setImageGlideFromAct(this, it?.avatar, avatarDetail)
            Utility.setImageGlideFromAct(this, it?.avatar, avatarDetailBackground)
            if (it != null) {
                favorite = Favorite(
                    it.userId,
                    it.username,
                    it.type,
                    it.avatar
                )
                checkFavButton(favorite)
            }
            showLoading(false)
        })
    }

    private fun showLoading(state: Boolean) {

        if (state) {
            progressBar.visibility = View.VISIBLE
            avatarDetail.visibility = View.INVISIBLE
            nameDetail.visibility = View.INVISIBLE
            companyDetail.visibility = View.INVISIBLE
            locationDetail.visibility = View.INVISIBLE
            repositoryDetail.visibility = View.INVISIBLE
            followersDetail.visibility = View.INVISIBLE
            followingDetail.visibility = View.INVISIBLE
            tabs.visibility = View.INVISIBLE
            viewPager.visibility = View.INVISIBLE
        } else {
            progressBar.visibility = View.GONE
            avatarDetail.visibility = View.VISIBLE
            nameDetail.visibility = View.VISIBLE
            companyDetail.visibility = View.VISIBLE
            locationDetail.visibility = View.VISIBLE
            repositoryDetail.visibility = View.VISIBLE
            followersDetail.visibility = View.VISIBLE
            followingDetail.visibility = View.VISIBLE
            tabs.visibility = View.VISIBLE
            viewPager.visibility = View.VISIBLE
        }

    }

    private fun configViewPager() {
        val viewPagerAdapter = ViewPagerAdapter(this, supportFragmentManager)
        viewPager.adapter = viewPagerAdapter
        tabs.setupWithViewPager(viewPager)
    }

    fun backButton(view: View) {
        Utility.backAct(this)
    }

    override fun finish() {
        super.finish()
        Utility.transitionAct(this)
    }
}