package com.xian.myapp.jni;

/**
 * Created by lucien on 2015/9/13.
 */
public class JNIControl {

    static {
        System.loadLibrary("first_jni");
    }

    /* A native method that is implemented by the 'hello-jni' native library, which is packaged with this application. */
    public native String  stringFromJNI(String str);

    public native String  stringFromJNILongTime(String str);
}
