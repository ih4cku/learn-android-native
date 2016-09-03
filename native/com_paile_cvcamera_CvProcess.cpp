#include <jni.h>
#include <string>

#include "common.h"
#include "com_paile_cvcamera_CvProcess.h"
#include "process.h"

using namespace std;

string jstr2cstr(JNIEnv *env, jstring jstr) {
    const char* sz = env->GetStringUTFChars(jstr, NULL);
    string cstr(sz);
    env->ReleaseStringUTFChars(jstr, sz);
    return cstr;
}

jstring cstr2jstr(JNIEnv *env, string cstr) {
    return env->NewStringUTF(cstr.c_str());
}

jstring JNICALL Java_com_paile_cvcamera_CvProcess_processImage
    (JNIEnv *env, jobject thiz, jstring jstr_image_path) {
    LOGD("in jni");
    string image_path = jstr2cstr(env, jstr_image_path);
    LOGD("processImage()");
    string out_str = processImage(image_path);
    jstring jstr_out_str = cstr2jstr(env, out_str);
    LOGD("out jni");
    return jstr_out_str;
};
