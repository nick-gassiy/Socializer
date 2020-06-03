package com.android.badoonmysql.Users;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.ArrayList;
import java.util.List;

public class User implements Parcelable {

    private static long sqlID;
    private long mysqlID;
    private String ID;
    private String name;
    private String gender;
    private String mail;
    private String password;
    private String birthday;
    private Permissions permissions;
    private String mainPhoto;
    private String mainPhotoOnDevice;
    private double latitude;
    private double longitude;
    private String bio;
    private String city;

    public User() {
        ID = "";
        gender = "";
        name = "";
        mail ="";
        password = "";
        birthday = "";
        permissions = Permissions.SIMPLE;
        mainPhoto = "";
        latitude = -1.0;
        longitude = -1.0;
        bio = "";
        city = "";
    }

    protected User(Parcel in) {
        mysqlID = in.readLong();
        ID = in.readString();
        name = in.readString();
        gender = in.readString();
        mail = in.readString();
        password = in.readString();
        birthday = in.readString();
        mainPhoto = in.readString();
        mainPhotoOnDevice = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
        bio = in.readString();
        city = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mysqlID);
        dest.writeString(ID);
        dest.writeString(name);
        dest.writeString(gender);
        dest.writeString(mail);
        dest.writeString(password);
        dest.writeString(birthday);
        dest.writeString(mainPhoto);
        dest.writeString(mainPhotoOnDevice);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeString(bio);
        dest.writeString(city);
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

    //Геттеры
    public String getName() {
        return name;
    }
    public String getMail() {
        return mail;
    }
    public String getPassword() {
        return password;
    }
    public String getBirthday() {
        return birthday;
    }
    public Permissions getPermissions() {
        return permissions;
    }
    public String getGender() {
        return gender;
    }
    public String getID() {
        return ID;
    }
    public String getMainPhoto() {
        return mainPhoto;
    }
    public String getBio() {
        return bio;
    }
    public String getMainPhotoOnDevice() {
        return mainPhotoOnDevice;
    }
    public double getLatitude() {
        return latitude;
    }
    public double getLongitude() {
        return longitude;
    }
    public long getMysqlID() {
        return mysqlID;
    }
    public String getCity() {
        return city;
    }

    //Сеттеры
    public void setName(String name) {
        this.name = name;
    }
    public void setMail(String mail) {
        this.mail = mail;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }
    public void setPermissions(Permissions permissions) {
        this.permissions = permissions;
    }
    public void setID(String ID) {
        this.ID = ID;
    }
    public void setMainPhoto(String mainPhoto) {
        this.mainPhoto = mainPhoto;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public void setMainPhotoOnDevice(String mainPhotoOnDevice) {
        this.mainPhotoOnDevice = mainPhotoOnDevice;
    }
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setMysqlID(long mysqlID) {
        this.mysqlID = mysqlID;
    }

    @Override
    public int describeContents() {
        return 0;
    }
}
