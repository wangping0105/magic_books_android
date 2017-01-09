package com.magicbooks.wp.magicbooks.models.json;
import java.io.Serializable;

/**
 * Created by wp on 2016/12/30.
 */
public class LoginJson implements Serializable{

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

        private String user_token;

        private String avatar_url;
        private String name;
        private String email;

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        private Integer id;//true 用户id

        public String getUser_token() {
            return user_token;
        }

        public void setUser_token(String user_token) {
            this.user_token = user_token;
        }

        public String getAvatar_url() {
            return avatar_url;
        }

        public void setAvatar_url(String avatar_url) {
            this.avatar_url = avatar_url;
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }
    }
}
