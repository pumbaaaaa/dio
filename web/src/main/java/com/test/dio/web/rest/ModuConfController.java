package com.test.dio.web.rest;


import com.test.dio.biz.domain.ModuKpiParamDTO;
import com.test.dio.biz.service.ModuConfService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/web/modu")
public class ModuConfController {

    @Autowired
    private ModuConfService moduConfService;

    @PostMapping("/save")
    @ApiOperation("保存组件指标配置")
    public ResponseEntity<String> saveModuConf(@RequestBody ModuKpiParamDTO param) {

        return ResponseEntity.ok(moduConfService.saveModuConf(param));
    }

}
