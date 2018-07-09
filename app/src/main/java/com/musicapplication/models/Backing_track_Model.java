package com.musicapplication.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ashok on 6/7/18.
 */

public class Backing_track_Model {

    /**
     * data : {"bt_category_title":"Cover Music","bt_category_sub_title":"Popular Songs","bt_category_image":"http://184.154.53.181/musicapp/assets/uploads/backingtracks/images/cover_music_img.png","backing_track_list":[{"backing_track_id":"1","bt_category_id":"1","backing_track_name":"Maid","background_image":"http://184.154.53.181/musicapp/assets/uploads/backingtracks/images/Maid.png","backing_track_file":"Maid.mp3","backing_track_url":"http://184.154.53.181/musicapp/assets/uploads/backingtracks/Maid.mp3"}],"message":"Backing track list successfully got","resultCode":"1"}
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
         * bt_category_title : Cover Music
         * bt_category_sub_title : Popular Songs
         * bt_category_image : http://184.154.53.181/musicapp/assets/uploads/backingtracks/images/cover_music_img.png
         * backing_track_list : [{"backing_track_id":"1","bt_category_id":"1","backing_track_name":"Maid","background_image":"http://184.154.53.181/musicapp/assets/uploads/backingtracks/images/Maid.png","backing_track_file":"Maid.mp3","backing_track_url":"http://184.154.53.181/musicapp/assets/uploads/backingtracks/Maid.mp3"}]
         * message : Backing track list successfully got
         * resultCode : 1
         */

        @SerializedName("bt_category_title")
        private String btCategoryTitle;
        @SerializedName("bt_category_sub_title")
        private String btCategorySubTitle;
        @SerializedName("bt_category_image")
        private String btCategoryImage;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("remaining_count")
        private int remaining_count;

        public int getRemaining_count() {
            return remaining_count;
        }

        public void setRemaining_count(int remaining_count) {
            this.remaining_count = remaining_count;
        }
        @SerializedName("backing_track_list")
        private List<BackingTrackListdata> backingTrackList;

        public String getBtCategoryTitle() {
            return btCategoryTitle;
        }

        public void setBtCategoryTitle(String btCategoryTitle) {
            this.btCategoryTitle = btCategoryTitle;
        }

        public String getBtCategorySubTitle() {
            return btCategorySubTitle;
        }

        public void setBtCategorySubTitle(String btCategorySubTitle) {
            this.btCategorySubTitle = btCategorySubTitle;
        }

        public String getBtCategoryImage() {
            return btCategoryImage;
        }

        public void setBtCategoryImage(String btCategoryImage) {
            this.btCategoryImage = btCategoryImage;
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

        public List<BackingTrackListdata> getBackingTrackList() {
            return backingTrackList;
        }

        public void setBackingTrackList(List<BackingTrackListdata> backingTrackList) {
            this.backingTrackList = backingTrackList;
        }

        public static class BackingTrackListdata {
            /**
             * backing_track_id : 1
             * bt_category_id : 1
             * backing_track_name : Maid
             * background_image : http://184.154.53.181/musicapp/assets/uploads/backingtracks/images/Maid.png
             * backing_track_file : Maid.mp3
             * backing_track_url : http://184.154.53.181/musicapp/assets/uploads/backingtracks/Maid.mp3
             */

            @SerializedName("backing_track_id")
            private String backingTrackId;
            @SerializedName("bt_category_id")
            private String btCategoryId;
            @SerializedName("backing_track_name")
            private String backingTrackName;
            @SerializedName("background_image")
            private String backgroundImage;
            @SerializedName("backing_track_file")
            private String backingTrackFile;
            @SerializedName("backing_track_url")
            private String backingTrackUrl;

            @SerializedName("duration")
            private int duration;

            public int getDuration() {
                return duration;
            }

            public void setDuration(int duration) {
                this.duration = duration;
            }

            public String getBackingTrackId() {
                return backingTrackId;
            }

            public void setBackingTrackId(String backingTrackId) {
                this.backingTrackId = backingTrackId;
            }

            public String getBtCategoryId() {
                return btCategoryId;
            }

            public void setBtCategoryId(String btCategoryId) {
                this.btCategoryId = btCategoryId;
            }

            public String getBackingTrackName() {
                return backingTrackName;
            }

            public void setBackingTrackName(String backingTrackName) {
                this.backingTrackName = backingTrackName;
            }

            public String getBackgroundImage() {
                return backgroundImage;
            }

            public void setBackgroundImage(String backgroundImage) {
                this.backgroundImage = backgroundImage;
            }

            public String getBackingTrackFile() {
                return backingTrackFile;
            }

            public void setBackingTrackFile(String backingTrackFile) {
                this.backingTrackFile = backingTrackFile;
            }

            public String getBackingTrackUrl() {
                return backingTrackUrl;
            }

            public void setBackingTrackUrl(String backingTrackUrl) {
                this.backingTrackUrl = backingTrackUrl;
            }
        }
    }
}
