cmake_minimum_required(VERSION 2.8)

project(cvcode)

set(OpenCV_CONFIG_PATH ${OpenCV_DIR})

find_package(OpenCV REQUIRED)

add_library(cvcode SHARED com_paile_cvcamera_CvProcess.cpp process.cpp) 
target_link_libraries(cvcode log android ${OpenCV_LIBS})
