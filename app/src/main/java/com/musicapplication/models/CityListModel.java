package com.musicapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by anjani on 15/1/18.
 */

public class CityListModel {


    /**
     * data : {"city_list":[{"city_id":"1","name":"New Delhi"}],"message":"City list successfully got.","resultCode":"1"}
     */

    @SerializedName("data")
    private citydata data;

    public citydata getData() {
        return data;
    }

    public void setData(citydata data) {
        this.data = data;
    }

    public static class citydata {
        /**
         * city_list : [{"city_id":"1","name":"New Delhi"}]
         * message : City list successfully got.
         * resultCode : 1
         */

        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("city_list")
        private List<CityListData> cityList;

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

        public List<CityListData> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListData> cityList) {
            this.cityList = cityList;
        }

        public static class CityListData {
            /**
             * city_id : 1
             * name : New Delhi
             */

            @SerializedName("city_id")
            private String cityId;
            @SerializedName("city_name")
            private String name;

            @SerializedName("app_flag")
            private String is_available;

            public String getIs_available() {
                return is_available;
            }

            public void setIs_available(String is_available) {
                this.is_available = is_available;
            }

            public String getCityId() {
                return cityId;
            }

            public void setCityId(String cityId) {
                this.cityId = cityId;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
