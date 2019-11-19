package com.bowen.base.service;

import com.bowen.base.dao.LabelDao;
import com.bowen.base.entity.Label;
import com.bowen.common.util.IdWorker;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @ProjectName: tensquare
 * @Package: com.bowen.base.service
 * @ClassName: LabelService
 * @Author: Bowen
 * @Description: 标签实现类
 * @Date: 2019/11/18 15:44
 * @Version: 1.0.0
 */
@Service
public class LabelService {

    @Autowired
    private LabelDao labelDao;

    @Autowired
    private IdWorker idWorker;


    /**
     *  分页条件查询
     *  @param searchMap
     *  @param page
     *  @param size
     *  @return
     *      
     */
    public Page<Label> findSearchAndPage(Label label, int page, int size) {
        return labelDao.findAll(labelSpecification(label), PageRequest.of(page - 1, size));
    }

    /**
     *  条件查询
     *  @param searchMap
     *  @return
     *  
     */
    public List<Label> findSearch(Label label) {
        return labelDao.findAll(labelSpecification(label));
    }

    /**
     * 查询全部标签
     *
     * @return      
     */
    public List<Label> findAll() {
        return labelDao.findAll();
    }

    /**
     *  根据ID查询标签
     *  @return
     *   
     */
    public Label findById(String id) {
        return labelDao.findById(id).get();
    }

    /**
     *  增加标签
     *  @param label
     *      
     */
    public void add(Label label) {
        labelDao.save(label);
    }

    /**
     * 修改标签
     *
     * @param label   
     */
    public void update(Label label) {
        labelDao.save(label);
    }

    /**
     *  删除标签
     *  @param id
     *   
     */
    public void deleteById(String id) {
        labelDao.deleteById(id);
    }


    /**
     *  构建查询条件
     *  @param searchMap
     *  @return
     *  
     */
    private Specification<Label> labelSpecification(Label label) {
        return (Specification<Label>) (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotEmpty(label.getLabelname())) {
                predicates.add(criteriaBuilder.like(root.get("labelname").as(String.class), "%" + label.getLabelname() + "%"));
            }
            if (StringUtils.isNotEmpty(label.getState())) {
                predicates.add(criteriaBuilder.like(root.get("state").as(String.class), label.getState()));
            }
            if (StringUtils.isNotEmpty(label.getRecommend())) {
                predicates.add(criteriaBuilder.like(root.get("recommend").as(String.class), label.getRecommend()));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
        };
    }


}
