package com.paile.cvcamera;

/**
 * Created by paile on 16-9-4.
 */
public class CvProcess {
    public static native String processImage(String image_path);

    static {
        System.loadLibrary("cvcode");
    }
}
