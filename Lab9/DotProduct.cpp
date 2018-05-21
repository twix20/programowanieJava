#include <cstdio>
#include "DotProduct.h"
using namespace std;

double multiply_vectors(JNIEnv *env, jobject thisObj, jobjectArray a, jobjectArray b)
{
    jclass doubleClass = env->FindClass("java/lang/Double");
    jmethodID midDoubleInit = env->GetMethodID(doubleClass, "<init>", "(D)V");
    jfieldID f = env->GetFieldID(doubleClass, "value", "D");

    jsize a_size = env->GetArrayLength( a );
    jsize b_size = env->GetArrayLength( b );

    double result = 0;
    for(int i = 0; i < a_size; i++) {

        jobject a_element = env->GetObjectArrayElement(a, i);
        jobject b_element = env->GetObjectArrayElement(b, i);

        double a_val = env->GetDoubleField(a_element, f);
        double b_val = env->GetDoubleField(b_element, f);

        result += a_val * b_val;
    }

    return result;
}


// Implementation of native method sayHello() of HelloJNI class
JNIEXPORT jobject JNICALL Java_DotProduct_multi01(JNIEnv *env, jobject thisObj, jobjectArray a, jobjectArray b)
{
    //Arrange
    jclass doubleClass = env->FindClass("java/lang/Double");
    jmethodID midDoubleInit = env->GetMethodID(doubleClass, "<init>", "(D)V");
    
    //Calculate
    double result = multiply_vectors(env, thisObj, a, b);

    // Call back constructor to allocate a new instance, with an int argument
    jobject newObj = env->NewObject(doubleClass, midDoubleInit, result);
    return newObj;
}

JNIEXPORT jobject JNICALL Java_DotProduct_multi02(JNIEnv *env, jobject thisObj, jobjectArray a)
{
    //Arrange
    jclass doubleClass = env->FindClass("java/lang/Double");
    jmethodID midDoubleInit = env->GetMethodID(doubleClass, "<init>", "(D)V");

    jclass dotProductClass = env->FindClass("DotProduct");
    jmethodID midGetB = env->GetMethodID(dotProductClass, "getB", "()[Ljava/lang/Double;");

    //Calculate
    jobjectArray b = (jobjectArray) env->CallObjectMethod(thisObj, midGetB);
    double result = multiply_vectors(env, thisObj, a, b);

    // Call back constructor to allocate a new instance, with an int argument
    jobject newObj = env->NewObject(doubleClass, midDoubleInit, result);
    return newObj;
}


JNIEXPORT void JNICALL Java_DotProduct_multi03(JNIEnv *env, jobject thisObj)
{
    jclass doubleClass = env->FindClass("java/lang/Double");
    jmethodID midDoubleInit = env->GetMethodID(doubleClass, "<init>", "(D)V");

    jclass dotProductClass = env->FindClass("DotProduct");
    jmethodID midMulti04 = env->GetMethodID(dotProductClass, "multi04", "()V");
    jmethodID midSetA = env->GetMethodID(dotProductClass, "setA", "([Ljava/lang/Double;)V");
    jmethodID midSetB = env->GetMethodID(dotProductClass, "setB", "([Ljava/lang/Double;)V");

    int vector_length = 0;
    printf("Podaj dlugosc wektorow i wpisz te wektory\n");
    scanf("%d", &vector_length);

    //Alocate Double[] arrays
    jobjectArray a = env->NewObjectArray(vector_length, doubleClass, NULL);
    jobjectArray b = env->NewObjectArray(vector_length, doubleClass, NULL);

    //Populate arrays
    double v;
    printf("Podaj vector A\n");
    for(int i = 0; i < vector_length; i++) {
        scanf("%lf", &v);
        jobject objSum = env->NewObject(doubleClass, midDoubleInit, (double)v);
        env->SetObjectArrayElement(a, i, objSum);
    }
    printf("Podaj vector B\n");
    for(int i = 0; i < vector_length; i++) {
        scanf("%lf", &v);
        jobject objSum = env->NewObject(doubleClass, midDoubleInit, (double)v);
        env->SetObjectArrayElement(b, i, objSum);
    }

    //Save arrays using setA and setB methos on DotProduct
    env->CallVoidMethod(thisObj, midSetA, a);
    env->CallVoidMethod(thisObj, midSetB, b);

    //Calculate and save with setC
    env->CallVoidMethod(thisObj, midMulti04);
}