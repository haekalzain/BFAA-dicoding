package com.example.tes.dicodingsubmission1bfaa.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.tes.dicodingsubmission1bfaa.R;
import com.example.tes.dicodingsubmission1bfaa.model.User;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.tes.dicodingsubmission1bfaa.Constants.ACTIVE_USER;

public class DetailUserActivity extends AppCompatActivity {
    String TAG = this.getClass().getName();
    User user;
    @BindView(R.id.img_user)
    CircleImageView civUser;
    @BindView(R.id.txt_username)
    TextView tvUsername;
    @BindView(R.id.txt_name)
    TextView tvName;
    @BindView(R.id.txt_location)
    TextView tvLocation;
    @BindView(R.id.txt_company)
    TextView tvCompany;
    @BindView(R.id.txt_repository)
    TextView tvRepository;
    @BindView(R.id.txt_followers)
    TextView tvFollowers;
    @BindView(R.id.txt_following)
    TextView tvFollowing;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user);
        ButterKnife.bind(this);


        user = getIntent().getParcelableExtra(ACTIVE_USER);
        try {
            tvUsername.setText(user.getUsername());
            Glide.with(this)
                    .load(user.getAvatar())
                    .apply(new RequestOptions().override(55, 55))
                    .into(civUser);
            tvCompany.setText(user.getCompany());
            tvName.setText(user.getName());
            tvFollowers.setText(user.getFollowers());
            tvFollowing.setText(user.getFollowing());
            tvRepository.setText(user.getRepository());
            tvLocation.setText(user.getLocation());
        } catch (Exception e) {
            Log.e(TAG,"FAIL",e.fillInStackTrace());
        }

    }
}