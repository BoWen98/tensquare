package com.bowen.article.controller;

import com.bowen.article.entity.Column;
import com.bowen.article.service.ColumnService;
import com.bowen.common.code.StatusCode;
import com.bowen.common.page.PageResult;
import com.bowen.common.result.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/column")
public class ColumnController {

    @Autowired
    private ColumnService columnService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param column 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Column column, @PathVariable int page, @PathVariable int size) {
        Page<Column> pageList = columnService.findSearch(column, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Column>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param column
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Column column) {
        return new Result(true, StatusCode.OK, "查询成功", columnService.findSearch(column));
    }

    /**
     * 增加
     *
     * @param column
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Column column) {
        columnService.add(column);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param column
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Column column, @PathVariable String id) {
        column.setId(id);
        columnService.update(column);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        columnService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
