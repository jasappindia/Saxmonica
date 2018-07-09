package com.musicapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ashok on 30/6/18.
 */

public class UpdateProfileModel {


    /**
     * data : {"user_details":{"user_id":"18","user_name":"kumarsanu","email":"xfsdf@jasappqq.com","profile_image":"http://184.154.53.181/musicapp/assets/uploads/users/dp_icon.png","country_id":"2","state_id":"2","city_id":"2"},"message":"Successfully updated user profile","resultCode":"1"}
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
         * user_details : {"user_id":"18","user_name":"kumarsanu","email":"xfsdf@jasappqq.com","profile_image":"http://184.154.53.181/musicapp/assets/uploads/users/dp_icon.png","country_id":"2","state_id":"2","city_id":"2"}
         * message : Successfully updated user profile
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
             * user_id : 18
             * user_name : kumarsanu
             * email : xfsdf@jasappqq.com
             * profile_image : http://184.154.53.181/musicapp/assets/uploads/users/dp_icon.png
             * country_id : 2
             * state_id : 2
             * city_id : 2
             */

            @SerializedName("user_id")
            private String userId;
            @SerializedName("user_name")
            private String userName;
            @SerializedName("email")
            private String email;
            @SerializedName("profile_image")
            private String profileImage;
            @SerializedName("country_id")
            private String countryId;
            @SerializedName("state_id")
            private String stateId;
            @SerializedName("city_id")
            private String cityId;

            public String getUserId() {
                return userId;
            }

            public void setUserId(String userId) {
                this.userId = userId;
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

            public String getProfileImage() {
                return profileImage;
            }

            public void setProfileImage(String profileImage) {
                this.profileImage = profileImage;
            }

            public String getCountryId() {
                return countryId;
            }

            public void setCountryId(String countryId) {
                this.countryId = countryId;
            }

            public String getStateId() {
                return stateId;
            }

            public void setStateId(String stateId) {
                this.stateId = stateId;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }
        }
    }
}
