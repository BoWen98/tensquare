package com.bowen.gathering.service;

import com.bowen.common.util.IdWorker;
import com.bowen.gathering.dao.GatheringDao;
import com.bowen.gathering.entity.Gathering;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
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
public class GatheringService {

    @Autowired
    private GatheringDao gatheringDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Gathering> findAll() {
        return gatheringDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param gathering
     * @param page
     * @param size
     * @return
     */
    public Page<Gathering> findSearch(Gathering gathering, int page, int size) {
        return gatheringDao.findAll(createSpecification(gathering), PageRequest.of(page - 1, size));
    }


    /**
     * 条件查询
     *
     * @param gathering
     * @return
     */
    public List<Gathering> findSearch(Gathering gathering) {
        return gatheringDao.findAll(createSpecification(gathering));
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    @Cacheable(value = "gathering", key = "#id")
    public Gathering findById(String id) {
        return gatheringDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param gathering
     */
    public void add(Gathering gathering) {
        gathering.setId(idWorker.nextId() + "");
        gatheringDao.save(gathering);
    }

    /**
     * 修改
     *
     * @param gathering
     */
    @CacheEvict(value = "gathering", key = "#gathering.id")
    public void update(Gathering gathering) {
        gatheringDao.save(gathering);
    }

    /**
     * 删除
     *
     * @param id
     */
    @CacheEvict(value = "gathering", key = "#id")
    public void deleteById(String id) {
        gatheringDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param gathering
     * @return
     */
    private Specification<Gathering> createSpecification(Gathering gathering) {

        return (Specification<Gathering>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            // 编号
            if (StringUtils.isNotEmpty(gathering.getId())) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + gathering.getId() + "%"));
            }
            // 活动名称
            if (StringUtils.isNotEmpty(gathering.getName())) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + gathering.getName() + "%"));
            }
            // 大会简介
            if (StringUtils.isNotEmpty(gathering.getSummary())) {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%" + gathering.getSummary() + "%"));
            }
            // 详细说明
            if (StringUtils.isNotEmpty(gathering.getDetail())) {
                predicateList.add(cb.like(root.get("detail").as(String.class), "%" + gathering.getDetail() + "%"));
            }
            // 主办方
            if (StringUtils.isNotEmpty(gathering.getSponsor())) {
                predicateList.add(cb.like(root.get("sponsor").as(String.class), "%" + gathering.getSponsor() + "%"));
            }
            // 活动图片
            if (StringUtils.isNotEmpty(gathering.getImage())) {
                predicateList.add(cb.like(root.get("image").as(String.class), "%" + gathering.getImage() + "%"));
            }
            // 举办地点
            if (StringUtils.isNotEmpty(gathering.getAddress())) {
                predicateList.add(cb.like(root.get("address").as(String.class), "%" + gathering.getAddress() + "%"));
            }
            // 是否可见
            if (StringUtils.isNotEmpty(gathering.getState())) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + gathering.getState() + "%"));
            }
            // 城市
            if (StringUtils.isNotEmpty(gathering.getCity())) {
                predicateList.add(cb.like(root.get("city").as(String.class), "%" + gathering.getCity() + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
