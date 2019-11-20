package com.bowen.qa.controller;

import com.bowen.common.code.StatusCode;
import com.bowen.common.page.PageResult;
import com.bowen.common.result.Result;
import com.bowen.qa.entity.Reply;
import com.bowen.qa.service.ReplyService;
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
@RequestMapping("/reply")
public class ReplyController {

    @Autowired
    private ReplyService replyService;


    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param reply 查询条件封装
     * @param page      页码
     * @param size      页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Reply reply, @PathVariable int page, @PathVariable int size) {
        Page<Reply> pageList = replyService.findSearch(reply, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Reply>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param reply
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Reply reply) {
        return new Result(true, StatusCode.OK, "查询成功", replyService.findSearch(reply));
    }

    /**
     * 增加
     *
     * @param reply
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Reply reply) {
        replyService.add(reply);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param reply
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Reply reply, @PathVariable String id) {
        reply.setId(id);
        replyService.update(reply);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        replyService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
