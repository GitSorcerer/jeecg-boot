package org.jeecg.modules.project.controller;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.jeecg.common.api.vo.Result;
import org.jeecg.modules.project.entity.TreeModel;
import org.jeecg.modules.utils.BaseUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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

    @GetMapping(value = "/treeTest")
    @ApiOperation(value = "生成树")
    public Result<List<TreeModel>> treeTest(HttpServletRequest request) {
        String id = request.getParameter("id");
        id = StringUtils.isNotBlank(id) ? id : "";
        Result<List<TreeModel>> result = new Result< >();
        //
        //

        result.setResult(getDate());
        result.setSuccess(true);
        return result;
    }

    public List<TreeModel> getDate() {
        List<TreeModel> treeList = new ArrayList<>();
        TreeModel tree  = new TreeModel();
        tree.setTitle("中国");
        tree.setKey("0");
        tree.setParentKey(" ");
        treeList.add(tree);

        TreeModel tree1 = new TreeModel();
        tree1.setTitle("湖北");
        tree1.setKey("1");
        tree1.setParentKey("0");
        treeList.add(tree1);

        TreeModel tree1_1 = new TreeModel();
        tree1_1.setTitle("武汉");
        tree1_1.setKey("1-1");
        tree1_1.setParentKey("1");
        treeList.add(tree1_1);

        TreeModel tree1_2 = new TreeModel();
        tree1_2.setTitle("孝感");
        tree1_2.setKey("1-2");
        tree1_2.setParentKey("1");
        treeList.add(tree1_2);

        TreeModel tree1_2_1 = new TreeModel();
        tree1_2_1.setTitle("孝昌");
        tree1_2_1.setKey("1-2-1");
        tree1_2_1.setParentKey("1-2");
        treeList.add(tree1_2_1);

        TreeModel tree1_2_2 = new TreeModel();
        tree1_2_2.setTitle("安陆");
        tree1_2_2.setKey("1-2-2");
        tree1_2_2.setParentKey("1-2");
        treeList.add(tree1_2_2);

        TreeModel tree2 = new TreeModel();
        tree2.setTitle("湖南");
        tree2.setKey("2");
        tree2.setParentKey("0");
        treeList.add(tree2);

        TreeModel tree2_1 = new TreeModel();
        tree2_1.setTitle("长沙");
        tree2_1.setKey("2-1");
        tree2_1.setParentKey("2");
        treeList.add(tree2_1);

        TreeModel tree2_2 = new TreeModel();
        tree2_2.setTitle("岳阳");
        tree2_2.setKey("2-2");
        tree2_2.setParentKey("2");
        treeList.add(tree2_2);

        TreeModel tree3 = new TreeModel();
        tree3.setTitle("浙江");
        tree3.setParentKey("0");
        tree3.setKey("3");
        treeList.add(tree3);

        log.debug("长度:{}", treeList.size());
        log.debug(JSONObject.toJSONString(treeList));

        treeList = BaseUtils.recursiveGenerateTree(treeList, " ");
        return treeList;
    }
}
