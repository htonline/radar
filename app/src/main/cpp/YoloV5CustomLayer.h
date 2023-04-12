#ifndef YOLOV5_CUSTOMLAYER_H
#define YOLOV5_CUSTOMLAYER_H

#include "ncnn/net.h"

namespace yolocvCus {
    typedef struct {
        int width;
        int height;
    } YoloSize;
}

typedef struct {
    std::string name;
    int stride;
    std::vector<yolocvCus::YoloSize> anchors;
} YoloLayerDataCus;

typedef struct BoxInfoCus {
    float x1;
    float y1;
    float x2;
    float y2;
    float score;
    int label;
} BoxInfoCus;


class YoloV5CustomLayer {
public:
    YoloV5CustomLayer(AAssetManager *mgr, const char *param, const char *bin, bool useGPU);

    ~YoloV5CustomLayer();

    std::vector<BoxInfoCus> detect(JNIEnv *env, jobject image, float threshold, float nms_threshold);
    std::vector<std::string> labels{"peak"};
private:
    static std::vector<BoxInfoCus>
    decode_infer(ncnn::Mat &data, int stride, const yolocvCus::YoloSize &frame_size, int net_size, int num_classes,
                 const std::vector<yolocvCus::YoloSize> &anchors, float threshold);

    static void nms(std::vector<BoxInfoCus> &result, float nms_threshold);

    ncnn::Net *Net;
    int input_size = 320;
    int num_class = 1;
    std::vector<YoloLayerDataCus> layers{
            {"output", 8,  {{10,  13}, {16,  30},  {33,  23}}},
            {"878",    16, {{30,  61}, {62,  45},  {59,  119}}},
            {"898",    32, {{116, 90}, {156, 198}, {373, 326}}},
    };

public:
    static YoloV5CustomLayer *detector;
    static bool hasGPU;
    static bool toUseGPU;

};


#endif //YOLOV5_CUSTOMLAYER_H
