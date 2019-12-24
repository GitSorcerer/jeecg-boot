package org.jeecg.modules.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author: GH
 * @Date: 2019/11/18 21:56
 * @Version 1.0
 */
@RestController
@Api("测试")
@Slf4j
@RequestMapping("/test")
public class TestProjectController {

    /**
     * hello world
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/hello")
    @ApiOperation(value = "测试请求")
    public Result<String> hello(HttpServletRequest request) {
        String id = request.getParameter("id");
        id = StringUtils.isNotBlank(id) ? id : "";
        Result<String> result = new Result<String>();
        result.setResult("Hello jeecg-boot-project!" + id);
        result.setSuccess(true);
        return result;
    }
}
