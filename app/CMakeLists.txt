# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)



file(GLOB SRC_LIST ${PROJECT_SOURCE_DIR}/src/main/cpp/*.cpp)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

add_library( # Sets the name of the library.
        native-lib

        # Sets the library as a shared library.
        SHARED

        # Provides a relative path to your source file(s).
        ${SRC_LIST})

#avcodec
add_library(
        avcodec

        SHARED

        IMPORTED

)
SET_TARGET_PROPERTIES(
        avcodec
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavcodec.so
)

#avformat
add_library(
        avformat

        SHARED

        IMPORTED
)
SET_TARGET_PROPERTIES(
        avformat
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavformat.so
)

#avutil
add_library(
        avutil

        SHARED

        IMPORTED
)
SET_TARGET_PROPERTIES(
        avutil
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavutil.so
)


add_library(
        avfilter

        SHARED

        IMPORTED
)
SET_TARGET_PROPERTIES(
        avfilter
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavfilter.so
)
add_library(
        swresample

        SHARED

        IMPORTED
)
SET_TARGET_PROPERTIES(
        swresample
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libswresample.so
)
add_library(
        swscale

        SHARED

        IMPORTED
)

SET_TARGET_PROPERTIES(
        swscale
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libswscale.so
)
add_library(
        x264

        STATIC

        IMPORTED
)

SET_TARGET_PROPERTIES(
        x264
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libx264.a
)
add_library(
        fdk-aac

        STATIC

        IMPORTED
)

SET_TARGET_PROPERTIES(
        fdk-aac
        PROPERTIES IMPORTED_LOCATION
        ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libfdk-aac.a
)
#  不能像下面这样写
#SET_TARGET_PROPERTIES(
#        avcodec
#        avfilter
#        avformat
#        avutil
#        swresample
#        swscale
#        fdk-aac
#        x264
#        PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavcodec.so
#        IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavfilter.so
#        IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavformat.so
#        IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavutil.so
#        IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libswresample.so
#        IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libswscale.so
#         IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libfdk-aac.a
#         IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libx264.a
#
##        PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavfilter.so
##        PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavformat.so
##        PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libavutil.so
##        PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libswresample.so
##        PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libswscale.so
##        PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libfdk-aac.a
##        PROPERTIES IMPORTED_LOCATION ${PROJECT_SOURCE_DIR}/src/main/jni/${ANDROID_ABI}/libx264.a







# 可以一次导入多个路径
include_directories(${PROJECT_SOURCE_DIR}/src/main/cpp/thirdlib/ffmpeg/include
        ${PROJECT_SOURCE_DIR}/src/main/cpp/thirdlib/x264/include
        ${PROJECT_SOURCE_DIR}/src/main/cpp/thirdlib/fdkaac/include)
# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
        native-lib
        avcodec
        avfilter
        avformat
        avutil
        swresample
        swscale
        fdk-aac
        x264
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib})