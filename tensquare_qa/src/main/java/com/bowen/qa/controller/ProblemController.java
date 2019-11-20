package com.bowen.qa.controller;

import com.bowen.common.code.StatusCode;
import com.bowen.common.page.PageResult;
import com.bowen.common.result.Result;
import com.bowen.qa.client.LabelClient;
import com.bowen.qa.entity.Problem;
import com.bowen.qa.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 控制器层
 *
 * @author Administrator
 */
@RestController
@CrossOrigin
@RequestMapping("/problem")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @Autowired
    private HttpServletRequest httpServletRequest;

    @Autowired
    private LabelClient labelClient;

    @GetMapping("/label/{labelid}")
    public Result findLabelById(@PathVariable String labelid) {
        Result result = labelClient.findById(labelid);
        return result;
    }

    @GetMapping("/newlist/{labelid}/{page}/{size}")
    public Result newList(@PathVariable String labelid,
                          @PathVariable int page,
                          @PathVariable int size) {
        Page<Problem> pageData = problemService.newList(labelid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    @GetMapping("/hotlist/{labelid}/{page}/{size}")
    public Result hotList(@PathVariable String labelid,
                          @PathVariable int page,
                          @PathVariable int size) {
        Page<Problem> pageData = problemService.hotList(labelid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    @GetMapping("/waitlist/{labelid}/{page}/{size}")
    public Result waitList(@PathVariable String labelid,
                           @PathVariable int page,
                           @PathVariable int size) {
        Page<Problem> pageData = problemService.waitList(labelid, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageData.getTotalElements(), pageData.getContent()));
    }

    /**
     * 查询全部数据
     *
     * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    public Result findAll() {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findAll());
    }

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result findById(@PathVariable String id) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findById(id));
    }


    /**
     * 分页+多条件查询
     *
     * @param problem 查询条件封装
     * @param page    页码
     * @param size    页大小
     * @return 分页结果
     */
    @RequestMapping(value = "/search/{page}/{size}", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Problem problem, @PathVariable int page, @PathVariable int size) {
        Page<Problem> pageList = problemService.findSearch(problem, page, size);
        return new Result(true, StatusCode.OK, "查询成功", new PageResult<Problem>(pageList.getTotalElements(), pageList.getContent()));
    }

    /**
     * 根据条件查询
     *
     * @param problem
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.POST)
    public Result findSearch(@RequestBody Problem problem) {
        return new Result(true, StatusCode.OK, "查询成功", problemService.findSearch(problem));
    }

    /**
     * 增加
     *
     * @param problem
     */
    @RequestMapping(method = RequestMethod.POST)
    public Result add(@RequestBody Problem problem) {
        String token = (String) httpServletRequest.getAttribute("claims_user");
        if (token == null || "".equals(token)) {
            return new Result(false, StatusCode.ACCESSERROR, "权限不足");
        }
        problemService.add(problem);
        return new Result(true, StatusCode.OK, "增加成功");
    }

    /**
     * 修改
     *
     * @param problem
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public Result update(@RequestBody Problem problem, @PathVariable String id) {
        problem.setId(id);
        problemService.update(problem);
        return new Result(true, StatusCode.OK, "修改成功");
    }

    /**
     * 删除
     *
     * @param id
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable String id) {
        problemService.deleteById(id);
        return new Result(true, StatusCode.OK, "删除成功");
    }

}
