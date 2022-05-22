/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.modules.system.service;

import me.zhengjie.modules.system.domain.DetectionInformation;
import me.zhengjie.modules.system.service.dto.DetectionInformationDto;
import me.zhengjie.modules.system.service.dto.DetectionInformationQueryCriteria;
import org.springframework.data.domain.Pageable;
import java.util.Map;
import java.util.List;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @description 服务接口
* @author LJL
* @date 2022-04-19
**/
public interface DetectionInformationService {

    /**
    * 查询数据分页
    * @param criteria 条件
    * @param pageable 分页参数
    * @return Map<String,Object>
    */
    Map<String,Object> queryAll(DetectionInformationQueryCriteria criteria, Pageable pageable);

    /**
    * 查询所有数据不分页
    * @param criteria 条件参数
    * @return List<DetectionInformationDto>
    */
    List<DetectionInformationDto> queryAll(DetectionInformationQueryCriteria criteria);

    /**
     * 根据ID查询
     * @param detectionId ID
     * @return DetectionInformationDto
     */
    DetectionInformationDto findById(Long detectionId);

    /**
    * 创建
    * @param resources /
    * @return DetectionInformationDto
    */
    DetectionInformationDto create(DetectionInformation resources);

    /**
    * 编辑
    * @param resources /
    */
    void update(DetectionInformation resources);

    /**
    * 多选删除
    * @param ids /
    */
    void deleteAll(Long[] ids);

    /**
    * 导出数据
    * @param all 待导出的数据
    * @param response /
    * @throws IOException /
    */
    void download(List<DetectionInformationDto> all, HttpServletResponse response) throws IOException;

    void downloadFile(String detectionData, String detectionPhotos, String radarPhotos, String detectionSummary, String others, HttpServletRequest request, HttpServletResponse response);

    void updateRadarPhoto(Long detectionInfomationId, String avatar);

    void updateDetectionSummary(Long detectionInfomationId, String avatar);

    void uploadOthers(Long detectionInfomationId, String avatar);

    void downloadOneFile(String oneFile, HttpServletRequest request, HttpServletResponse response);
}