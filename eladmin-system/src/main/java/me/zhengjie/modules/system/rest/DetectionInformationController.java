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
package me.zhengjie.modules.system.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.modules.system.domain.DetectionInformation;
import me.zhengjie.modules.system.service.DetectionInformationService;
import me.zhengjie.modules.system.service.dto.DetectionInformationQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://el-admin.vip
* @author LJL
* @date 2022-04-19
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "api/detection_information管理")
@RequestMapping("/api/detectionInformation")
public class DetectionInformationController {

    private final DetectionInformationService detectionInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('detectionInformation:list')")
    public void exportDetectionInformation(HttpServletResponse response, DetectionInformationQueryCriteria criteria) throws IOException {
        detectionInformationService.download(detectionInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询api/detection_information")
    @ApiOperation("查询api/detection_information")
    @PreAuthorize("@el.check('detectionInformation:list')")
    public ResponseEntity<Object> queryDetectionInformation(DetectionInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(detectionInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增api/detection_information")
    @ApiOperation("新增api/detection_information")
    @PreAuthorize("@el.check('detectionInformation:add')")
    public ResponseEntity<Object> createDetectionInformation(@Validated @RequestBody DetectionInformation resources){
        return new ResponseEntity<>(detectionInformationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改api/detection_information")
    @ApiOperation("修改api/detection_information")
    @PreAuthorize("@el.check('detectionInformation:edit')")
    public ResponseEntity<Object> updateDetectionInformation(@Validated @RequestBody DetectionInformation resources){
        detectionInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除api/detection_information")
    @ApiOperation("删除api/detection_information")
    @PreAuthorize("@el.check('detectionInformation:del')")
    public ResponseEntity<Object> deleteDetectionInformation(@RequestBody Long[] ids) {
        detectionInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}