package com.musicapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashok on 20/6/18.
 */

public class RegistrationModel
{


    /**
     * data : {"user_details":{"user_name":"yogesh","email":"yogeshvishnoi@jasapp.com","user_id":"11","type":"login"},"message":"successfully logged in.","resultCode":"1"}
     */

    @SerializedName("data")
    private Datadata data;

    public Datadata getData() {
        return data;
    }

    public void setData(Datadata data) {
        this.data = data;
    }

    public static class Datadata {
        /**
         * user_details : {"user_name":"yogesh","email":"yogeshvishnoi@jasapp.com","user_id":"11","type":"login"}
         * message : successfully logged in.
         * resultCode : 1
         */

        @SerializedName("user_details")
        private UserDetailsdata userDetails;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;

        public UserDetailsdata getUserDetails() {
            return userDetails;
        }

        public void setUserDetails(UserDetailsdata userDetails) {
            this.userDetails = userDetails;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public String getResultCode() {
            return resultCode;
        }

        public void setResultCode(String resultCode) {
            this.resultCode = resultCode;
        }

        public static class UserDetailsdata {
            /**
             * user_name : yogesh
             * email : yogeshvishnoi@jasapp.com
             * user_id : 11
             * type : login
             */

            @SerializedName("user_name")
            private String userName;
            @SerializedName("email")
            private String email;
            @SerializedName("user_id")
            private String userId;
            @SerializedName("type")
            private String type;
            @SerializedName("profile_image")
            private String profile_image;
            @SerializedName("country_id")
            private String country_id;
            @SerializedName("state_id")
            private String state_id;
            @SerializedName("city_id")
            private String city_id;

            @SerializedName("country_name")
            private String country_name;
            @SerializedName("state_name")
            private String state_name;
            @SerializedName("city_name")
            private String city_name;

            @SerializedName("age")
            private String age;
            @SerializedName("gender")
            private String gender;

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getProfile_image() {
                return profile_image;
            }

            public void setProfile_image(String profile_image) {
                this.profile_image = profile_image;
            }

            public String getCountry_id() {
                return country_id;
            }

            public void setCountry_id(String country_id) {
                this.country_id = country_id;
            }

            public String getState_id() {
                return state_id;
            }

            public void setState_id(String state_id) {
                this.state_id = state_id;
            }

            public String getCity_id() {
                return city_id;
            }

            public void setCity_id(String city_id) {
                this.city_id = city_id;
            }

            public String getCountry_name() {
                return country_name;
            }

            public void setCountry_name(String country_name) {
                this.country_name = country_name;
            }

            public String getState_name() {
                return state_name;
            }

            public void setState_name(String state_name) {
                this.state_name = state_name;
            }

            public String getCity_name() {
                return city_name;
            }

            public void setCity_name(String city_name) {
                this.city_name = city_name;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
