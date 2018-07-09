package com.musicapplication.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by ashok on 20/6/18.
 */

public class GalleryListModel {

    /**
     * data : {"remaining_count":0,"gallery_list":[{"g_id":"1","gallery_name":"Gallery Name 1","description":"Gallery Name 1 Description 1","main_image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/folder-music.jpg","gallery_images":[{"gi_id":"1","g_id":"1","image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/gallery-images/guitar.jpg","description":"","status":"1","delete_status":"0","create_date":"2018-06-21 12:39:27","modify_date":"2018-06-21 12:39:27"},{"gi_id":"2","g_id":"1","image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/gallery-images/icongal.jpg","description":"","status":"1","delete_status":"0","create_date":"2018-06-21 12:39:38","modify_date":"2018-06-21 12:39:38"}]},{"g_id":"2","gallery_name":"Gallery Name 2","description":"Gallery Name 2 Description 1","main_image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-2/folder-img.jpg","gallery_images":[{"gi_id":"3","g_id":"2","image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-2/gallery-images/piano.jpg","description":"","status":"1","delete_status":"0","create_date":"2018-06-21 12:39:49","modify_date":"2018-06-21 12:39:49"}]}],"message":"Gallery list fetched successfully.","resultCode":"1"}
     */

    @SerializedName("data")
    private Datadata data;

    public Datadata getData() {
        return data;
    }

    public void setData(Datadata data) {
        this.data = data;
    }

    public static class Datadata implements Serializable {
        /**
         * remaining_count : 0
         * gallery_list : [{"g_id":"1","gallery_name":"Gallery Name 1","description":"Gallery Name 1 Description 1","main_image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/folder-music.jpg","gallery_images":[{"gi_id":"1","g_id":"1","image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/gallery-images/guitar.jpg","description":"","status":"1","delete_status":"0","create_date":"2018-06-21 12:39:27","modify_date":"2018-06-21 12:39:27"},{"gi_id":"2","g_id":"1","image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/gallery-images/icongal.jpg","description":"","status":"1","delete_status":"0","create_date":"2018-06-21 12:39:38","modify_date":"2018-06-21 12:39:38"}]},{"g_id":"2","gallery_name":"Gallery Name 2","description":"Gallery Name 2 Description 1","main_image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-2/folder-img.jpg","gallery_images":[{"gi_id":"3","g_id":"2","image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-2/gallery-images/piano.jpg","description":"","status":"1","delete_status":"0","create_date":"2018-06-21 12:39:49","modify_date":"2018-06-21 12:39:49"}]}]
         * message : Gallery list fetched successfully.
         * resultCode : 1
         */

        @SerializedName("remaining_count")
        private int remainingCount;
        @SerializedName("message")
        private String message;
        @SerializedName("resultCode")
        private String resultCode;
        @SerializedName("gallery_list")
        private List<GalleryListdata> galleryList;

        public int getRemainingCount() {
            return remainingCount;
        }

        public void setRemainingCount(int remainingCount) {
            this.remainingCount = remainingCount;
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

        public List<GalleryListdata> getGalleryList() {
            return galleryList;
        }

        public void setGalleryList(List<GalleryListdata> galleryList) {
            this.galleryList = galleryList;
        }

        public static class GalleryListdata implements Serializable {
            /**
             * g_id : 1
             * gallery_name : Gallery Name 1
             * description : Gallery Name 1 Description 1
             * main_image : http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/folder-music.jpg
             * gallery_images : [{"gi_id":"1","g_id":"1","image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/gallery-images/guitar.jpg","description":"","status":"1","delete_status":"0","create_date":"2018-06-21 12:39:27","modify_date":"2018-06-21 12:39:27"},{"gi_id":"2","g_id":"1","image":"http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/gallery-images/icongal.jpg","description":"","status":"1","delete_status":"0","create_date":"2018-06-21 12:39:38","modify_date":"2018-06-21 12:39:38"}]
             */

            @SerializedName("g_id")
            private String gId;
            @SerializedName("gallery_name")
            private String galleryName;
            @SerializedName("description")
            private String description;
            @SerializedName("main_image")
            private String mainImage;
            @SerializedName("gallery_images")
            private List<GalleryImagesdata> galleryImages;

            public String getGId() {
                return gId;
            }

            public void setGId(String gId) {
                this.gId = gId;
            }

            public String getGalleryName() {
                return galleryName;
            }

            public void setGalleryName(String galleryName) {
                this.galleryName = galleryName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }

            public String getMainImage() {
                return mainImage;
            }

            public void setMainImage(String mainImage) {
                this.mainImage = mainImage;
            }

            public List<GalleryImagesdata> getGalleryImages() {
                return galleryImages;
            }

            public void setGalleryImages(List<GalleryImagesdata> galleryImages) {
                this.galleryImages = galleryImages;
            }

            public static class GalleryImagesdata implements  Serializable{
                /**
                 * gi_id : 1
                 * g_id : 1
                 * image : http://184.154.53.181/musicapp/assets/uploads/galleries/gallery-1/gallery-images/guitar.jpg
                 * description :
                 * status : 1
                 * delete_status : 0
                 * create_date : 2018-06-21 12:39:27
                 * modify_date : 2018-06-21 12:39:27
                 */

                @SerializedName("gi_id")
                private String giId;
                @SerializedName("g_id")
                private String gId;
                @SerializedName("image")
                private String image;
                @SerializedName("description")
                private String description;
                @SerializedName("status")
                private String status;
                @SerializedName("delete_status")
                private String deleteStatus;
                @SerializedName("create_date")
                private String createDate;
                @SerializedName("modify_date")
                private String modifyDate;

                public String getGiId() {
                    return giId;
                }

                public void setGiId(String giId) {
                    this.giId = giId;
                }

                public String getGId() {
                    return gId;
                }

                public void setGId(String gId) {
                    this.gId = gId;
                }

                public String getImage() {
                    return image;
                }

                public void setImage(String image) {
                    this.image = image;
                }

                public String getDescription() {
                    return description;
                }

                public void setDescription(String description) {
                    this.description = description;
                }

                public String getStatus() {
                    return status;
                }

                public void setStatus(String status) {
                    this.status = status;
                }

                public String getDeleteStatus() {
                    return deleteStatus;
                }

                public void setDeleteStatus(String deleteStatus) {
                    this.deleteStatus = deleteStatus;
                }

                public String getCreateDate() {
                    return createDate;
                }

                public void setCreateDate(String createDate) {
                    this.createDate = createDate;
                }

                public String getModifyDate() {
                    return modifyDate;
                }

                public void setModifyDate(String modifyDate) {
                    this.modifyDate = modifyDate;
                }
            }
        }
    }
}
