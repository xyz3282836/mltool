package com.minigee.app.model;

import com.minigee.app.base.BaseModel;

/**
 * Created by Zhou on 2015-11-3.
 *
 * http://rong.36kr.com/api/mobi/news?pageSize=20&columnId=all&pagingAction=up
 * http://rong.36kr.com/api/mobi/news?pageSize=20&lastId=5039182&columnId=all&pagingAction=down
 * http://rong.36kr.com/api/mobi/news/5039237
 *
 *
 */
public class KrNews extends BaseModel {
    public final static String COL_ID = "id";
    public final static String COL_COLUMNID = "columnId";
    public final static String COL_COLUMNNAME = "columnName";
    public final static String COL_COMMENTCOUNT = "commentCount";
    public final static String COL_FEATUREIMG = "featureImg";
    public final static String COL_FEEDID = "feedId";
    public final static String COL_USERNAME = "userName";
    public final static String COL_USERAVATAR = "userAvatar";
    public final static String COL_PUBLISHTIME = "publishTime";
    public final static String COL_TYPE = "type";
    public final static String COL_TITLE = "title";


    private String id;
    private String columnId;
    private String columnName;
    private String commentCount;
    private String featureImg;
    private String feedId;
    private String userName;
    private String userAvatar;
    private String publishTime;
    private String type;
    private String title;


    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getFeatureImg() {
        return featureImg;
    }

    public void setFeatureImg(String featureImg) {
        this.featureImg = featureImg;
    }

    public String getFeedId() {
        return feedId;
    }

    public void setFeedId(String feedId) {
        this.feedId = feedId;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
