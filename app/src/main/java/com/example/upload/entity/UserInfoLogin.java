package com.example.upload.entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UserInfoLogin implements Serializable {
    @SerializedName("user")
    User user;
    @SerializedName("token")
    String token;
    public class User implements Serializable {
        @SerializedName("user")
        UserIn userIn;
        public UserIn getUserIn() {
            return userIn;
        }
        public class UserIn implements Serializable {
            @SerializedName("nickName")
            String nickName;
            @SerializedName("phone")
            String phone;

            @Override
            public String toString() {
                return "UserIn{" +
                        "nickName='" + nickName + '\'' +
                        ", phone='" + phone + '\'' +
                        '}';
            }

            public String getNickName() {
                return nickName;
            }

            public String getPhone() {
                return phone;
            }
        }



        @Override
        public String toString() {
            return "User{" +
                    "userIn=" + userIn +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "UserInfoLogin{" +
                "user=" + user +
                ", token='" + token + '\'' +
                '}';
    }

    public User getUser() {
        return user;
    }

    public String getToken() {
        return token;
    }
}
