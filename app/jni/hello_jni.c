#include <string.h>
#include <jni.h>



jstring Java_com_xian_myapp_jni_JNIControl_stringFromJNI(JNIEnv* env, jobject thiz, jstring str){
	return (*env)->NewStringUTF(env, "Hello from JNI !");
}

jstring Java_com_xian_myapp_jni_JNIControl_stringFromJNILongTime(JNIEnv* env, jobject thiz, jstring str){

	  return (*env)->NewStringUTF(env, "Hello from JNI long!");
}