package com.Ian.entity;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created by Ian on 2016/6/20.
 */
public class UserInfo implements Serializable{

    /**
     * 序列化
     */
    private  static final  long  serialVersionUID = 1L;

    private String userName;

    private int userId;

    public UserInfo buildUserName(String userName){
        this.userName = userName;
         return this;
    }

    public UserInfo buildUserId(int userId){
        this.userId = userId;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] codeC(){
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] value = this.userName.getBytes();
        buffer.putInt(value.length);
        buffer.put(value);
        buffer.putInt(this.userId);
        buffer.flip();
        value=null;
        byte[] result = new byte[buffer.remaining()];
        buffer.get(result);
        return result;
    }
}
