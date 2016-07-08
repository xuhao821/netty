package com.Ian.entity;

import junit.framework.TestCase;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

/**
 * Created by Ian on 2016/6/20.
 */
public class UserInfoTest extends TestCase {
    /**
     * 测试
     * @throws Exception
     */
    public void testCodeCLength() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserName("徐浩").buildUserId(1024);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(userInfo);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The JDK serializable length is : " + b.length);
        System.out.println("----------------------------------------------------------");
        System.out.println("The byte serializable length is : " + userInfo.codeC().length);
    }

    /**
     *
     * @throws Exception
     */
    public void testCodeCTime() throws Exception {
        UserInfo userInfo = new UserInfo();
        userInfo.buildUserName("徐浩").buildUserId(1024);
        ByteArrayOutputStream bos = null;
        ObjectOutputStream os = null;
        long starTime = System.currentTimeMillis();
        for(int i=0;i<1000000;i++){
            bos = new ByteArrayOutputStream();
            os = new ObjectOutputStream(bos);
            os.writeObject(userInfo);
            os.flush();
            os.close();
            byte[] b = bos.toByteArray();
            bos.close();
        }

        System.out.println("The JDK serializable time is : " +  (System.currentTimeMillis()-starTime) + "ms");
        System.out.println("----------------------------------------------------------");
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        starTime = System.currentTimeMillis();
        for(int i=0;i<1000000;i++){
            byte[] b = userInfo.codeC();
        }
        System.out.println("The byte serializable time is : " + (System.currentTimeMillis()-starTime) + "ms");
    }
}
