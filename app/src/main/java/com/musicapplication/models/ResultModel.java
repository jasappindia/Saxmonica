package com.musicapplication.models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by anjani on 14/12/17.
 */

public class ResultModel {


    /**
     * data : {"c_mobile_number":"78474744444","message":"OTP sent on mobile no.","resultCode":"1","register_status":"new_user"}
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
         * c_mobile_number : 78474744444
         * message : OTP sent on mobile no.
         * resultCode : 1
         * register_status : new_user
         */

        @SerializedName("c_mobile_number")
        private String cMobileNumber;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("register_status")
        private String registerStatus;

        public String getCMobileNumber() {
            return cMobileNumber;
        }

        public void setCMobileNumber(String cMobileNumber) {
            this.cMobileNumber = cMobileNumber;
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

        public String getRegisterStatus() {
            return registerStatus;
        }

        public void setRegisterStatus(String registerStatus) {
            this.registerStatus = registerStatus;
        }
    }
}
