/*
 * implement image processing 
 */
#include <string>
#include <vector>

#include <opencv2/core/core.hpp>
#include <opencv2/highgui/highgui.hpp>
#include <opencv2/imgproc/imgproc.hpp>
#include <opencv2/features2d/features2d.hpp>
#include <opencv2/nonfree/nonfree.hpp>

#include "common.h"
#include "process.h"

using namespace std;
using namespace cv;

string processImage(string image_path) {
    LOGD(image_path.c_str());

    Mat image = imread(image_path, 0);
    Mat out_image;
    if (!image.data) {
        LOGD("image not exist");
        return "";
    }

    SurfFeatureDetector detector(400);
    vector<KeyPoint> kps;
    detector.detect(image, kps);
    drawKeypoints(image, kps, out_image, Scalar::all(-1), DrawMatchesFlags::DEFAULT);

    string out_path = image_path.substr(0, image_path.length()-4) + "_out.jpg";
    imwrite(out_path, out_image);
    return out_path;
}
