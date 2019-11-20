package com.bowen.qa.service;

import com.bowen.common.util.IdWorker;
import com.bowen.qa.dao.ProblemDao;
import com.bowen.qa.entity.Problem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ProblemService {

    @Autowired
    private ProblemDao problemDao;

    @Autowired
    private IdWorker idWorker;

    public Page<Problem> newList(String labelid, int page, int rows) {
        return problemDao.newList(labelid, PageRequest.of(page - 1, rows));
    }

    public Page<Problem> hotList(String labelid, int page, int rows) {
        return problemDao.hotList(labelid, PageRequest.of(page - 1, rows));
    }

    public Page<Problem> waitList(String labelid, int page, int rows) {
        return problemDao.waitList(labelid, PageRequest.of(page - 1, rows));
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Problem> findAll() {
        return problemDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param problem
     * @param page
     * @param size
     * @return
     */
    public Page<Problem> findSearch(Problem problem, int page, int size) {
        return problemDao.findAll(createSpecification(problem), PageRequest.of(page - 1, size));
    }


    /**
     * 条件查询
     *
     * @param problem
     * @return
     */
    public List<Problem> findSearch(Problem problem) {
        return problemDao.findAll(createSpecification(problem));
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Problem findById(String id) {
        return problemDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param problem
     */
    public void add(Problem problem) {
        problem.setId(idWorker.nextId() + "");
        problemDao.save(problem);
    }

    /**
     * 修改
     *
     * @param problem
     */
    public void update(Problem problem) {
        problemDao.save(problem);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        problemDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param problem
     * @return
     */
    private Specification<Problem> createSpecification(Problem problem) {

        return (Specification<Problem>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            // ID
            if (StringUtils.isNotEmpty(problem.getId())) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + StringUtils.isNotEmpty(problem.getId()) + "%"));
            }
            // 标题
            if (StringUtils.isNotEmpty(problem.getTitle())) {
                predicateList.add(cb.like(root.get("title").as(String.class), "%" + StringUtils.isNotEmpty(problem.getTitle()) + "%"));
            }
            // 内容
            if (StringUtils.isNotEmpty(problem.getContent())) {
                predicateList.add(cb.like(root.get("content").as(String.class), "%" + StringUtils.isNotEmpty(problem.getContent()) + "%"));
            }
            // 用户ID
            if (StringUtils.isNotEmpty(problem.getUserid())) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + StringUtils.isNotEmpty(problem.getUserid()) + "%"));
            }
            // 昵称
            if (StringUtils.isNotEmpty(problem.getNickname())) {
                predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + StringUtils.isNotEmpty(problem.getNickname()) + "%"));
            }
            // 是否解决
            if (StringUtils.isNotEmpty(problem.getSolve())) {
                predicateList.add(cb.like(root.get("solve").as(String.class), "%" + StringUtils.isNotEmpty(problem.getSolve()) + "%"));
            }
            // 回复人昵称
            if (StringUtils.isNotEmpty(problem.getReplyname())) {
                predicateList.add(cb.like(root.get("replyname").as(String.class), "%" + StringUtils.isNotEmpty(problem.getReplyname()) + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
