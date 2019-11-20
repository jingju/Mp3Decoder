#include <jni.h>
#include <string>
#include "include/accompany_decoder_controller.h"
#include "common/CommonTools.h"

extern "C"{
#include "libavcodec/avcodec.h"
}

#define LOG_TAG "Mp3Decoder"

AccompanyDecoderController* decoderController;

extern "C" JNIEXPORT jstring JNICALL
Java_com_jingju_ffmpegdecoder_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(avcodec_license());

}


/**初始化*/
extern "C"
JNIEXPORT jint JNICALL
Java_com_jingju_ffmpegdecoder_Mp3Decoder_init(JNIEnv *env, jobject instance, jstring mp3FilePath_,
                                              jstring pcmFilePath_) {
    const char *mp3FilePath = env->GetStringUTFChars(mp3FilePath_, 0);
    const char *pcmFilePath = env->GetStringUTFChars(pcmFilePath_, 0);


    const char* pcmPath = env->GetStringUTFChars(pcmFilePath_, NULL);
    const char* mp3Path = env->GetStringUTFChars(mp3FilePath_, NULL);
    decoderController = new AccompanyDecoderController();
    decoderController->Init(mp3Path, pcmPath);


    // TODO
    env->ReleaseStringUTFChars(mp3FilePath_, mp3FilePath);
    env->ReleaseStringUTFChars(pcmFilePath_, pcmFilePath);

    return 0;//不要忘了返回值
}




/**解码*/
extern "C"
JNIEXPORT void JNICALL
Java_com_jingju_ffmpegdecoder_Mp3Decoder_decode(JNIEnv *env, jobject instance) {

    // TODO
    LOGI("enter Java_com_phuket_tour_decoder_Mp3Decoder_decode...");
    if(decoderController) {
        decoderController->Decode();
    }
    LOGI("leave Java_com_phuket_tour_decoder_Mp3Decoder_decode...");
}



/**销毁*/
extern "C"
JNIEXPORT void JNICALL
Java_com_jingju_ffmpegdecoder_Mp3Decoder_destroy(JNIEnv *env, jobject instance) {
    // TODO
    if(decoderController) {
        decoderController->Destroy();
        delete decoderController;
        decoderController = NULL;
    }
}