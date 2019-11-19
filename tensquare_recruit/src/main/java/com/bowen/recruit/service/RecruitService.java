package com.bowen.recruit.service;

import com.bowen.common.util.IdWorker;
import com.bowen.recruit.dao.RecruitDao;
import com.bowen.recruit.entity.Recruit;
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
public class RecruitService {

    @Autowired
    private RecruitDao recruitDao;

    @Autowired
    private IdWorker idWorker;

    public List<Recruit> recommend() {
        return recruitDao.findTop6ByStateOrderByCreatetimeDesc("2");
    }

    public List<Recruit> newlist() {
        return recruitDao.findTop6ByStateNotOrderByCreatetimeDesc("0");
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Recruit> findAll() {
        return recruitDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param recruit
     * @param page
     * @param size
     * @return
     */
    public Page<Recruit> findSearch(Recruit recruit, int page, int size) {
        return recruitDao.findAll(createSpecification(recruit), PageRequest.of(page - 1, size));
    }


    /**
     * 条件查询
     *
     * @param recruit
     * @return
     */
    public List<Recruit> findSearch(Recruit recruit) {
        Specification<Recruit> specification = createSpecification(recruit);
        return recruitDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Recruit findById(String id) {
        return recruitDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param recruit
     */
    public void add(Recruit recruit) {
        recruit.setId(idWorker.nextId() + "");
        recruitDao.save(recruit);
    }

    /**
     * 修改
     *
     * @param recruit
     */
    public void update(Recruit recruit) {
        recruitDao.save(recruit);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        recruitDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param recruit
     * @return
     */
    private Specification<Recruit> createSpecification(Recruit recruit) {

        return (Specification<Recruit>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            // ID
            if (StringUtils.isNotEmpty(recruit.getId())) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + recruit.getId() + "%"));
            }
            // 职位名称
            if (StringUtils.isNotEmpty(recruit.getJobname())) {
                predicateList.add(cb.like(root.get("jobname").as(String.class), "%" + recruit.getJobname() + "%"));
            }
            // 薪资范围
            if (StringUtils.isNotEmpty(recruit.getSalary())) {
                predicateList.add(cb.like(root.get("salary").as(String.class), "%" + recruit.getSalary() + "%"));
            }
            // 经验要求
            if (StringUtils.isNotEmpty(recruit.getCondition())) {
                predicateList.add(cb.like(root.get("condition").as(String.class), "%" + recruit.getCondition() + "%"));
            }
            // 学历要求
            if (StringUtils.isNotEmpty(recruit.getEducation())) {
                predicateList.add(cb.like(root.get("education").as(String.class), "%" + recruit.getEducation() + "%"));
            }
            // 任职方式
            if (StringUtils.isNotEmpty(recruit.getType())) {
                predicateList.add(cb.like(root.get("type").as(String.class), "%" + recruit.getType() + "%"));
            }
            // 办公地址
            if (StringUtils.isNotEmpty(recruit.getAddress())) {
                predicateList.add(cb.like(root.get("address").as(String.class), "%" + recruit.getAddress() + "%"));
            }
            // 企业ID
            if (StringUtils.isNotEmpty(recruit.getEid())) {
                predicateList.add(cb.like(root.get("eid").as(String.class), "%" + recruit.getEid() + "%"));
            }
            // 状态
            if (StringUtils.isNotEmpty(recruit.getState())) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + recruit.getState() + "%"));
            }
            // 网址
            if (StringUtils.isNotEmpty(recruit.getUrl())) {
                predicateList.add(cb.like(root.get("url").as(String.class), "%" + recruit.getUrl() + "%"));
            }
            // 标签
            if (StringUtils.isNotEmpty(recruit.getLabel())) {
                predicateList.add(cb.like(root.get("label").as(String.class), "%" + recruit.getLabel() + "%"));
            }
            // 职位描述
            if (StringUtils.isNotEmpty(recruit.getContent2())) {
                predicateList.add(cb.like(root.get("content1").as(String.class), "%" + recruit.getContent1() + "%"));
            }
            // 职位要求
            if (StringUtils.isNotEmpty(recruit.getContent2())) {
                predicateList.add(cb.like(root.get("content2").as(String.class), "%" + recruit.getContent2() + "%"));
            }
            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
