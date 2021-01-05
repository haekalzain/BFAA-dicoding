package com.example.tes.dicodingsubmission1bfaa.activity;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tes.dicodingsubmission1bfaa.R;
import com.example.tes.dicodingsubmission1bfaa.adapter.UserListAdapter;
import com.example.tes.dicodingsubmission1bfaa.model.User;
import com.example.tes.dicodingsubmission1bfaa.view.UserListView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.tes.dicodingsubmission1bfaa.Constants.ACTIVE_USER;

public class UserListActivity extends AppCompatActivity implements UserListView {
    @BindView(R.id.rv_user_list)
    RecyclerView rvMain;
    private ArrayList<User> userList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        ButterKnife.bind(this);
        userList.addAll(getUserList());
        setUpRecyclerView();
    }

    public ArrayList<User> getUserList() {
        String[] name = getResources().getStringArray(R.array.name);
        String[] userName = getResources().getStringArray(R.array.username);
        TypedArray avatar = getResources().obtainTypedArray(R.array.avatar);
        String[] company = getResources().getStringArray(R.array.company);
        String[] followers = getResources().getStringArray(R.array.followers);
        String[] following = getResources().getStringArray(R.array.following);
        String[] location = getResources().getStringArray(R.array.location);
        String[] repository = getResources().getStringArray(R.array.repository);


        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < name.length; i++) {
            User user = new User();
            user.setName(name[i]);
            user.setAvatar(avatar.getResourceId(i,-1));
            user.setUsername(userName[i]);
            user.setCompany(company[i]);
            user.setRepository(repository[i]);
            user.setLocation(location[i]);
            user.setFollowers(followers[i]);
            user.setFollowing(following[i]);
            userList.add(user);
        }
        return users;
    }


    private void setUpRecyclerView() {
        rvMain.setLayoutManager(new LinearLayoutManager(this));
        UserListAdapter userListAdapter = new UserListAdapter(userList);
        userListAdapter.setMainView(this);
        rvMain.setAdapter(userListAdapter);
    }

    @Override
    public void goToDetail(User user) {
        Intent intent = new Intent(this,DetailUserActivity.class);
        intent.putExtra(ACTIVE_USER,user);
        startActivity(intent);
    }
}