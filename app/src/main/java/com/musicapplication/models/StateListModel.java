package com.musicapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashok on 16/6/18.
 */

public class StateListModel
{


    /**
     * data : {"state_list":[{"state_id":"1","state_name":"Andaman and Nicobar Islands"},{"state_id":"2","state_name":"Andhra Pradesh"},{"state_id":"3","state_name":"Arunachal Pradesh"},{"state_id":"4","state_name":"Assam"},{"state_id":"5","state_name":"Bihar"},{"state_id":"6","state_name":"Chandigarh"},{"state_id":"7","state_name":"Chhattisgarh"},{"state_id":"8","state_name":"Dadra and Nagar Haveli"},{"state_id":"9","state_name":"Daman and Diu"},{"state_id":"10","state_name":"Delhi"},{"state_id":"11","state_name":"Goa"},{"state_id":"12","state_name":"Gujarat"},{"state_id":"13","state_name":"Haryana"},{"state_id":"14","state_name":"Himachal Pradesh"},{"state_id":"15","state_name":"Jammu and Kashmir"},{"state_id":"16","state_name":"Jharkhand"},{"state_id":"17","state_name":"Karnataka"},{"state_id":"18","state_name":"Kenmore"},{"state_id":"19","state_name":"Kerala"},{"state_id":"20","state_name":"Lakshadweep"},{"state_id":"21","state_name":"Madhya Pradesh"},{"state_id":"22","state_name":"Maharashtra"},{"state_id":"23","state_name":"Manipur"},{"state_id":"24","state_name":"Meghalaya"},{"state_id":"25","state_name":"Mizoram"},{"state_id":"26","state_name":"Nagaland"},{"state_id":"27","state_name":"Narora"},{"state_id":"28","state_name":"Natwar"},{"state_id":"29","state_name":"Odisha"},{"state_id":"30","state_name":"Paschim Medinipur"},{"state_id":"31","state_name":"Pondicherry"},{"state_id":"32","state_name":"Punjab"},{"state_id":"33","state_name":"Rajasthan"},{"state_id":"34","state_name":"Sikkim"},{"state_id":"35","state_name":"Tamil Nadu"},{"state_id":"36","state_name":"Telangana"},{"state_id":"37","state_name":"Tripura"},{"state_id":"38","state_name":"Uttar Pradesh"},{"state_id":"39","state_name":"Uttarakhand"},{"state_id":"40","state_name":"Vaishali"},{"state_id":"41","state_name":"West Bengal"}],"message":"state list successfully got","resultCode":"1"}
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
         * state_list : [{"state_id":"1","state_name":"Andaman and Nicobar Islands"},{"state_id":"2","state_name":"Andhra Pradesh"},{"state_id":"3","state_name":"Arunachal Pradesh"},{"state_id":"4","state_name":"Assam"},{"state_id":"5","state_name":"Bihar"},{"state_id":"6","state_name":"Chandigarh"},{"state_id":"7","state_name":"Chhattisgarh"},{"state_id":"8","state_name":"Dadra and Nagar Haveli"},{"state_id":"9","state_name":"Daman and Diu"},{"state_id":"10","state_name":"Delhi"},{"state_id":"11","state_name":"Goa"},{"state_id":"12","state_name":"Gujarat"},{"state_id":"13","state_name":"Haryana"},{"state_id":"14","state_name":"Himachal Pradesh"},{"state_id":"15","state_name":"Jammu and Kashmir"},{"state_id":"16","state_name":"Jharkhand"},{"state_id":"17","state_name":"Karnataka"},{"state_id":"18","state_name":"Kenmore"},{"state_id":"19","state_name":"Kerala"},{"state_id":"20","state_name":"Lakshadweep"},{"state_id":"21","state_name":"Madhya Pradesh"},{"state_id":"22","state_name":"Maharashtra"},{"state_id":"23","state_name":"Manipur"},{"state_id":"24","state_name":"Meghalaya"},{"state_id":"25","state_name":"Mizoram"},{"state_id":"26","state_name":"Nagaland"},{"state_id":"27","state_name":"Narora"},{"state_id":"28","state_name":"Natwar"},{"state_id":"29","state_name":"Odisha"},{"state_id":"30","state_name":"Paschim Medinipur"},{"state_id":"31","state_name":"Pondicherry"},{"state_id":"32","state_name":"Punjab"},{"state_id":"33","state_name":"Rajasthan"},{"state_id":"34","state_name":"Sikkim"},{"state_id":"35","state_name":"Tamil Nadu"},{"state_id":"36","state_name":"Telangana"},{"state_id":"37","state_name":"Tripura"},{"state_id":"38","state_name":"Uttar Pradesh"},{"state_id":"39","state_name":"Uttarakhand"},{"state_id":"40","state_name":"Vaishali"},{"state_id":"41","state_name":"West Bengal"}]
         * message : state list successfully got
         * resultCode : 1
         */

        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("state_list")
        private List<StateListdata> stateList;

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

        public List<StateListdata> getStateList() {
            return stateList;
        }

        public void setStateList(List<StateListdata> stateList) {
            this.stateList = stateList;
        }

        public static class StateListdata {
            /**
             * state_id : 1
             * state_name : Andaman and Nicobar Islands
             */

            @SerializedName("state_id")
            private String stateId;
            @SerializedName("state_name")
            private String stateName;

            public String getStateId() {
                return stateId;
            }

            public void setStateId(String stateId) {
                this.stateId = stateId;
            }

            public String getStateName() {
                return stateName;
            }

            public void setStateName(String stateName) {
                this.stateName = stateName;
            }
        }
    }
}
