package com.bowen.base.controller;

import com.bowen.base.entity.Label;
import com.bowen.base.service.LabelService;
import com.bowen.common.code.StatusCode;
import com.bowen.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ProjectName: tensquare
 * @Package: com.bowen.base.controller
 * @ClassName: UserController
 * @Author: Bowen
 * @Description: 标签实现层
 * @Date: 2019/11/18 15:51
 * @Version: 1.0.0
 */
@RequestMapping
@RestController("/label")
public class LabelController {

    @Autowired
    private LabelService labelService;

    /**
     *  查询全部列表
     *  @return
     *      
     */
    @GetMapping
    public Result<List> findAll() {
        return new Result<>(true, StatusCode.OK, "查询成功", labelService.findAll());
    }

    /**
     *   根据ID查询标签
     *  @param id
     *  @return
     *      
     */
    @GetMapping("/{id}")
    public Result<Label> findById(@PathVariable String id) {
        System.out.println(labelService.findById(id));
        return new Result<>(true, StatusCode.OK, "查询成功", labelService.findById(id));
    }

    /**
     *  增加标签
     *  @param label
     *  @return
     *      
     */
    @PostMapping
    public Result add(@RequestBody Label label) {
        labelService.add(label);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     *  删除标签
     *  @param id
     *  @return
     *      
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable String id) {
        labelService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

    /**
     *  修改标签
     *  @param label
     *  @return
     *      
     */
    @PutMapping("/{id}")
    public Result update(@PathVariable String id, @RequestBody Label label) {
        label.setId(id);
        labelService.update(label);
        return new Result(true, StatusCode.OK, "修改成功");
    }
}
