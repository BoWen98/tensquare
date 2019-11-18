package com.bowen.base.service;

import com.bowen.base.dao.LabelDao;
import com.bowen.base.entity.Label;
import com.bowen.common.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
