// IRemoteService.aidl
package com.xian.myapp.aidl;

// Declare any non-default types here with import statements

interface IRemoteService {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat,
            double aDouble, String aString);

    String getStringAndInt(String str);
    String getStringAndIntLongTime(String str);
    oneway void getStringAndIntLongTimeOneWay(String str);

}
