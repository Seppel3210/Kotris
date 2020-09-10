#include "coldclear.h"
#include "coldclear_java.h"

JNIEXPORT jlong JNICALL Java_ColdClear_ccLaunchAsync
        (JNIEnv *env, jobject this, jlong options, jlong weights)
{
    return cc_launch_async(options, weights)
}

JNIEXPORT jlong JNICALL Java_ColdClear_ccLaunchWithBoardAsync
        (JNIEnv *env, jobject this, jlong options, jlong weights, jlong field, jint bag_remain, jint int hold, jboolean b2b, jint combo)
{
    return cc_launch_with_board_async(options, weights, field, bag_remain, hold, b2b, combo)
}

JNIEXPORT void JNICALL Java_ColdClear_ccDestroyAsync
        (JNIEnv *env, jobject this, jlong bot)
{
    cc_destroy_async(bot)
}

JNIEXPORT void JNICALL Java_ColdClear_ccResetAsync
(JNIEnv *env, jobject this, jlong bot, jlong field, jboolean b2b, jint combo)
{

}

JNIEXPORT void JNICALL Java_ColdClear_ccAddNextPieceAsync
(JNIEnv *env, jobject this, jlong, jint)
{

}

JNIEXPORT void JNICALL Java_ColdClear_ccRequestNextMove
(JNIEnv *env, jobject this, jlong, jint)
{

}

JNIEXPORT jint JNICALL Java_ColdClear_ccPollNextMove
        (JNIEnv *env, jobject this, jlong bot)
{
    cc_block_next_move(bot, move, plan, plan_length)
}

JNIEXPORT jint JNICALL Java_ColdClear_ccBlockNextMove
        (JNIEnv *env, jobject this, jlong bot)
{
    cc_block_next_move(bot, move, plan, plan_length)
}

JNIEXPORT jlong JNICALL Java_ColdClear_ccDefaultOptions
        (JNIEnv *env, jobject this)
{
    return cc_default_options();
}

JNIEXPORT jlong JNICALL Java_ColdClear_ccDefaultWeights
        (JNIEnv *env, jobject this)
{
    return cc_default_weights();
}

JNIEXPORT jlong JNICALL Java_ColdClear_ccFastWeights
        (JNIEnv *env, jobject this)
{
    return cc_fast_weights();
}