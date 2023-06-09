//
// Created by 邓昊晴 on 14/6/2020.
//

#ifndef YOLOV5_H
#define YOLOV5_H

#include "ncnn/net.h"

namespace yolocv {
    typedef struct {
        int width;
        int height;
    } YoloSize;
}

typedef struct {
    std::string name;
    int stride;
    std::vector<yolocv::YoloSize> anchors;
} YoloLayerData;

typedef struct BoxInfo {
    float x1;
    float y1;
    float x2;
    float y2;
    float score;
    int label;
} BoxInfo;

class YoloV5 {
public:
    YoloV5(AAssetManager *mgr, const char *param, const char *bin, bool useGPU);

    ~YoloV5();

    std::vector<BoxInfo> detect(JNIEnv *env, jobject image, float threshold, float nms_threshold);
    std::vector<std::string> labels{"rebar"};
private:
    static std::vector<BoxInfo>
    decode_infer(ncnn::Mat &data, int stride, const yolocv::YoloSize &frame_size, int net_size, int num_classes,
                 const std::vector<yolocv::YoloSize> &anchors, float threshold);

    static void nms(std::vector<BoxInfo> &result, float nms_threshold);

    ncnn::Net *Net;
    int input_size = 640;
    int num_class = 1;
    std::vector<YoloLayerData> layers{
            {"663",    16, {{30,  61}, {62,  45},  {59,  119}}},
            {"output", 8,  {{10,  13}, {16,  30},  {33,  23}}},
    };

public:
    static YoloV5 *detector;
    static bool hasGPU;
    static bool toUseGPU;
};


#endif //YOLOV5_H
