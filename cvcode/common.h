#ifndef __COMMON_H__
#define __COMMON_H__

#define  LOG_TAG    "NATIVE/GQ"

#ifdef __ANDROID__

#include <android/log.h>
#define  LOGE(...)  __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)
#define  LOGW(...)  __android_log_print(ANDROID_LOG_WARN,  LOG_TAG, __VA_ARGS__)
#define  LOGD(...)  __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#define  LOGI(...)  __android_log_print(ANDROID_LOG_INFO,  LOG_TAG, __VA_ARGS__)

#else // __ANDROID__

#include <stdio.h>
#define  LOGE(...)  printf(__VA_ARGS__)
#define  LOGW(...)  printf(__VA_ARGS__)
#define  LOGD(...)  printf(__VA_ARGS__)
#define  LOGI(...)  printf(__VA_ARGS__)

#endif // __ANDROID__

#endif
