package com.bowen.qa.service;

import com.bowen.common.util.IdWorker;
import com.bowen.qa.dao.ReplyDao;
import com.bowen.qa.entity.Reply;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务层
 *
 * @author Administrator
 */
@Service
public class ReplyService {

    @Autowired
    private ReplyDao replyDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Reply> findAll() {
        return replyDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param reply
     * @param page
     * @param size
     * @return
     */
    public Page<Reply> findSearch(Reply reply, int page, int size) {
        return replyDao.findAll(createSpecification(reply), PageRequest.of(page - 1, size));
    }


    /**
     * 条件查询
     *
     * @param reply
     * @return
     */
    public List<Reply> findSearch(Reply reply) {
        return replyDao.findAll(createSpecification(reply));
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Reply findById(String id) {
        return replyDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param reply
     */
    public void add(Reply reply) {
        reply.setId(idWorker.nextId() + "");
        replyDao.save(reply);
    }

    /**
     * 修改
     *
     * @param reply
     */
    public void update(Reply reply) {
        replyDao.save(reply);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        replyDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param reply
     * @return
     */
    private Specification<Reply> createSpecification(Reply reply) {

        return new Specification<Reply>() {

            @Override
            public Predicate toPredicate(Root<Reply> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // 编号
                if (StringUtils.isNotEmpty(reply.getId())) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + reply.getId() + "%"));
                }
                // 问题ID
                if (StringUtils.isNotEmpty(reply.getProblemid())) {
                    predicateList.add(cb.like(root.get("problemid").as(String.class), "%" + reply.getProblemid() + "%"));
                }
                // 回答内容
                if (StringUtils.isNotEmpty(reply.getContent())) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + reply.getContent() + "%"));
                }
                // 回答人ID
                if (StringUtils.isNotEmpty(reply.getUserid())) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + reply.getUserid() + "%"));
                }
                // 回答人昵称
                if (StringUtils.isNotEmpty(reply.getNickname())) {
                    predicateList.add(cb.like(root.get("nickname").as(String.class), "%" + reply.getNickname() + "%"));
                }
                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
