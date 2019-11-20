package com.bowen.article.service;

import com.bowen.article.dao.ColumnDao;
import com.bowen.article.entity.Column;
import com.bowen.common.util.IdWorker;
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
public class ColumnService {

    @Autowired
    private ColumnDao columnDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Column> findAll() {
        return columnDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param column
     * @param page
     * @param size
     * @return
     */
    public Page<Column> findSearch(Column column, int page, int size) {
        Specification<Column> specification = createSpecification(column);
        PageRequest pageRequest = PageRequest.of(page - 1, size);
        return columnDao.findAll(specification, pageRequest);
    }


    /**
     * 条件查询
     *
     * @param column
     * @return
     */
    public List<Column> findSearch(Column column) {
        Specification<Column> specification = createSpecification(column);
        return columnDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Column findById(String id) {
        return columnDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param column
     */
    public void add(Column column) {
        column.setId(idWorker.nextId() + "");
        columnDao.save(column);
    }

    /**
     * 修改
     *
     * @param column
     */
    public void update(Column column) {
        columnDao.save(column);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        columnDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param column
     * @return
     */
    private Specification<Column> createSpecification(Column column) {

        return (Specification<Column>) (root, query, cb) -> {
            List<Predicate> predicateList = new ArrayList<Predicate>();
            // ID
            if (StringUtils.isNotEmpty(column.getId())) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + column.getId() + "%"));
            }
            // 专栏名称
            if (StringUtils.isNotEmpty(column.getName())) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + column.getName() + "%"));
            }
            // 专栏简介
            if (StringUtils.isNotEmpty(column.getSummary())) {
                predicateList.add(cb.like(root.get("summary").as(String.class), "%" + column.getSummary() + "%"));
            }
            // 用户ID
            if (StringUtils.isNotEmpty(column.getUserid())) {
                predicateList.add(cb.like(root.get("userid").as(String.class), "%" + column.getUserid() + "%"));
            }
            // 状态
            if (StringUtils.isNotEmpty(column.getState())) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + column.getState() + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
