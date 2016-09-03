#!/bin/bash
set -e
ANDROID_TOOLCHAIN_DIR=/home/paile/codes/opencv-android/sdk/native/jni
OpenCV_DIR=/home/paile/codes/opencv-android/sdk/native/jni

function build_abi {
    ABI=$1
    BUILD_DIR=build/$ABI
    echo "Building ABI=$ABI in $BUILD_DIR"
    if [ ! -d $BUILD_DIR ]; then 
        mkdir -p $BUILD_DIR
    fi
    pushd $BUILD_DIR
    cmake -DCMAKE_TOOLCHAIN_FILE=$ANDROID_TOOLCHAIN_DIR/android.toolchain.cmake \
        -DANDROID_NDK=/home/paile/Android/Sdk/ndk-bundle \
        -DANDROID_ABI=$ABI \
        -DANDROID_NATIVE_API_LEVEL=19 \
        -DOpenCV_DIR=$OpenCV_DIR \
        ../..
    make
    popd 
}

build_abi x86_64
build_abi armeabi-v7a
