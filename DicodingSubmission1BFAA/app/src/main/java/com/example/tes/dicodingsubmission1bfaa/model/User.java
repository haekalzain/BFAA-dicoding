package com.example.tes.dicodingsubmission1bfaa.model;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    public Integer avatar;
    public String username;
    public String name;
    public String location;
    public String company;
    public String repository;
    public String followers;
    public String following;

    public User() {

    }


    public Integer getAvatar() {
        return avatar;
    }

    public void setAvatar(Integer avatar) {
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getFollowing() {
        return following;
    }

    public void setFollowing(String following) {
        this.following = following;
    }

    public static Creator<User> getCREATOR() {
        return CREATOR;
    }

    protected User(Parcel in) {
        if (in.readByte() == 0) {
            avatar = null;
        } else {
            avatar = in.readInt();
        }
        username = in.readString();
        name = in.readString();
        location = in.readString();
        company = in.readString();
        repository = in.readString();
        followers = in.readString();
        following = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        if (avatar == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeInt(avatar);
        }
        parcel.writeString(username);
        parcel.writeString(name);
        parcel.writeString(location);
        parcel.writeString(company);
        parcel.writeString(repository);
        parcel.writeString(followers);
        parcel.writeString(following);
    }
}
