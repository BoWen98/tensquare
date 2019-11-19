package com.bowen.recruit.service;

import com.bowen.common.util.IdWorker;
import com.bowen.recruit.dao.EnterpriseDao;
import com.bowen.recruit.entity.Enterprise;
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
public class EnterpriseService {

    @Autowired
    private EnterpriseDao enterpriseDao;

    @Autowired
    private IdWorker idWorker;


    public List<Enterprise> hotList(String ishot) {
        return enterpriseDao.findByIshot(ishot);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Enterprise> findAll() {
        return enterpriseDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param enterprise
     * @param page
     * @param size
     * @return
     */
    public Page<Enterprise> findSearch(Enterprise enterprise, int page, int size) {
        return enterpriseDao.findAll(createSpecification(enterprise), PageRequest.of(page - 1, size));
    }


    /**
     * 条件查询
     *
     * @param enterprise
     * @return
     */
    public List<Enterprise> findSearch(Enterprise enterprise) {
        return enterpriseDao.findAll(createSpecification(enterprise));
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Enterprise findById(String id) {
        return enterpriseDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param enterprise
     */
    public void add(Enterprise enterprise) {
        enterprise.setId(idWorker.nextId() + "");
        enterpriseDao.save(enterprise);
    }

    /**
     * 修改
     *
     * @param enterprise
     */
    public void update(Enterprise enterprise) {
        enterpriseDao.save(enterprise);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        enterpriseDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param enterprise
     * @return
     */
    private Specification<Enterprise> createSpecification(Enterprise enterprise) {

        return (Specification<Enterprise>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            // ID
            if (StringUtils.isNotEmpty(enterprise.getId())) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + enterprise.getId() + "%"));
            }
            // 企业名称
            if (StringUtils.isNotEmpty(enterprise.getName())) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + enterprise.getName() + "%"));
            }
            // 企业简介
            if (StringUtils.isNotEmpty(enterprise.getSummary())) {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%" + enterprise.getSummary() + "%"));
            }
            // 企业地址
            if (StringUtils.isNotEmpty(enterprise.getAddress())) {
                predicateList.add(cb.like(root.get("address").as(String.class), "%" + enterprise.getAddress() + "%"));
            }
            // 标签列表
            if (StringUtils.isNotEmpty(enterprise.getLabels())) {
                predicateList.add(cb.like(root.get("labels").as(String.class), "%" + enterprise.getLabels() + "%"));
            }
            // 坐标
            if (StringUtils.isNotEmpty(enterprise.getCoordinate())) {
                predicateList.add(cb.like(root.get("coordinate").as(String.class), "%" + enterprise.getCoordinate() + "%"));
            }
            // 是否热门
            if (StringUtils.isNotEmpty(enterprise.getIshot())) {
                predicateList.add(cb.like(root.get("ishot").as(String.class), "%" + enterprise.getIshot() + "%"));
            }
            // LOGO
            if (StringUtils.isNotEmpty(enterprise.getLogo())) {
                predicateList.add(cb.like(root.get("logo").as(String.class), "%" + enterprise.getLogo() + "%"));
            }
            // URL
            if (StringUtils.isNotEmpty(enterprise.getUrl())) {
                predicateList.add(cb.like(root.get("url").as(String.class), "%" + enterprise.getUrl() + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
