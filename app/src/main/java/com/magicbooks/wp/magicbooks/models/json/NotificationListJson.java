package com.magicbooks.wp.magicbooks.models.json;
import com.magicbooks.wp.magicbooks.models.Notification;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wp on 2016/12/30.
 */
public class NotificationListJson implements Serializable{

    private static final long serialVersionUID = 1L;

    private int code;

    private String message;

    private DataInfo data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataInfo getData() {
        return data;
    }

    public void setData(DataInfo data) {
        this.data = data;
    }

    public static class DataInfo implements Serializable{
        private static final long serialVersionUID = 1L;
        public DataInfo(){}

        private List<Notification> notifications;
        private String page;
        private String perPage;
        private String totalCount;

        public List<Notification> getNotifications() {
            return notifications;
        }

        public void setNotifications(List<Notification> notifications) {
            this.notifications = notifications;
        }

        public String getPage() {
            return page;
        }

        public void setPage(String page) {
            this.page = page;
        }

        public String getPerPage() {
            return perPage;
        }

        public void setPerPage(String perPage) {
            this.perPage = perPage;
        }

        public String getTotalCount() {
            return totalCount;
        }

        public void setTotalCount(String totalCount) {
            this.totalCount = totalCount;
        }
    }
}
