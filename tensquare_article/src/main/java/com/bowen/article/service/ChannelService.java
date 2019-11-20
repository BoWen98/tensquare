package com.bowen.article.service;

import com.bowen.article.dao.ChannelDao;
import com.bowen.article.entity.Channel;
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
public class ChannelService {

    @Autowired
    private ChannelDao channelDao;

    @Autowired
    private IdWorker idWorker;

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Channel> findAll() {
        return channelDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param channel
     * @param page
     * @param size
     * @return
     */
    public Page<Channel> findSearch(Channel channel, int page, int size) {
        return channelDao.findAll(createSpecification(channel), PageRequest.of(page - 1, size));
    }


    /**
     * 条件查询
     *
     * @param channel
     * @return
     */
    public List<Channel> findSearch(Channel channel) {
        Specification<Channel> specification = createSpecification(channel);
        return channelDao.findAll(specification);
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Channel findById(String id) {
        return channelDao.findById(id).get();
    }

    /**
     * 增加
     *
     * @param channel
     */
    public void add(Channel channel) {
        channel.setId(idWorker.nextId() + "");
        channelDao.save(channel);
    }

    /**
     * 修改
     *
     * @param channel
     */
    public void update(Channel channel) {
        channelDao.save(channel);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        channelDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param channel
     * @return
     */
    private Specification<Channel> createSpecification(Channel channel) {

        return (Specification<Channel>) (root, query, cb) -> {

            List<Predicate> predicateList = new ArrayList<Predicate>();
            // ID
            if (StringUtils.isNotEmpty(channel.getId())) {
                predicateList.add(cb.like(root.get("id").as(String.class), "%" + channel.getId() + "%"));
            }
            // 频道名称
            if (StringUtils.isNotEmpty(channel.getName())) {
                predicateList.add(cb.like(root.get("name").as(String.class), "%" + channel.getName() + "%"));
            }
            // 状态
            if (StringUtils.isNotEmpty(channel.getState())) {
                predicateList.add(cb.like(root.get("state").as(String.class), "%" + channel.getState() + "%"));
            }

            return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

        };

    }

}
