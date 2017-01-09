package com.magicbooks.wp.magicbooks.models.json;
import java.io.Serializable;

/**
 * Created by wp on 2016/12/30.
 */
public class NotificationJson implements Serializable{

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

        private String id;
        private String title;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getBody() {
            return body;
        }

        public void setBody(String body) {
            this.body = body;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getNotify_type() {
            return notify_type;
        }

        public void setNotify_type(String notify_type) {
            this.notify_type = notify_type;
        }

        private String body;
        private String category;
        private String status;
        private String notify_type;

    }
}
