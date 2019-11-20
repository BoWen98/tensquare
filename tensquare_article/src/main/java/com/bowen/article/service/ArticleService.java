package com.bowen.article.service;

import com.bowen.article.dao.ArticleDao;
import com.bowen.article.entity.Article;
import com.bowen.common.util.IdWorker;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
@Transactional
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;


    @Autowired
    private RedisTemplate redisTemplate;


    public void updateState(String id) {
        articleDao.updateState(id);
    }

    public void addThumbup(String id) {
        articleDao.addThumbup(id);
    }

    /**
     * 查询全部列表
     *
     * @return
     */
    public List<Article> findAll() {
        return articleDao.findAll();
    }


    /**
     * 条件查询+分页
     *
     * @param article
     * @param page
     * @param size
     * @return
     */
    public Page<Article> findSearch(Article article, int page, int size) {
        return articleDao.findAll(createSpecification(article), PageRequest.of(page - 1, size));
    }


    /**
     * 条件查询
     *
     * @param article
     * @return
     */
    public List<Article> findSearch(Article article) {
        return articleDao.findAll(createSpecification(article));
    }

    /**
     * 根据ID查询实体
     *
     * @param id
     * @return
     */
    public Article findById(String id) {
        //从缓存中查找对象
        Article article = (Article) redisTemplate.opsForValue().get("article_" + id);

        if (article == null) {
            article = articleDao.findById(id).get();
            //存入缓存
            redisTemplate.opsForValue().set("article_" + id, article);
        }
        return article;
    }

    /**
     * 增加
     *
     * @param article
     */
    public void add(Article article) {
        article.setId(idWorker.nextId() + "");
        articleDao.save(article);
    }

    /**
     * 修改
     *
     * @param article
     */
    public void update(Article article) {
        redisTemplate.delete("article_" + article.getId());
        articleDao.save(article);
    }

    /**
     * 删除
     *
     * @param id
     */
    public void deleteById(String id) {
        redisTemplate.delete("article_" + id);
        articleDao.deleteById(id);
    }

    /**
     * 动态条件构建
     *
     * @param article
     * @return
     */
    private Specification<Article> createSpecification(Article article) {

        return new Specification<Article>() {

            @Override
            public Predicate toPredicate(Root<Article> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicateList = new ArrayList<Predicate>();
                // ID
                if (StringUtils.isNotEmpty(article.getId())) {
                    predicateList.add(cb.like(root.get("id").as(String.class), "%" + article.getId() + "%"));
                }
                // 专栏ID
                if (StringUtils.isNotEmpty(article.getColumnid())) {
                    predicateList.add(cb.like(root.get("columnid").as(String.class), "%" + article.getColumnid() + "%"));
                }
                // 用户ID
                if (StringUtils.isNotEmpty(article.getUserid())) {
                    predicateList.add(cb.like(root.get("userid").as(String.class), "%" + article.getUserid() + "%"));
                }
                // 标题
                if (StringUtils.isNotEmpty(article.getTitle())) {
                    predicateList.add(cb.like(root.get("title").as(String.class), "%" + article.getTitle() + "%"));
                }
                // 文章正文
                if (StringUtils.isNotEmpty(article.getContent())) {
                    predicateList.add(cb.like(root.get("content").as(String.class), "%" + article.getContent() + "%"));
                }
                // 文章封面
                if (StringUtils.isNotEmpty(article.getImage())) {
                    predicateList.add(cb.like(root.get("image").as(String.class), "%" + article.getImage() + "%"));
                }
                // 是否公开
                if (StringUtils.isNotEmpty(article.getIspublic())) {
                    predicateList.add(cb.like(root.get("ispublic").as(String.class), "%" + article.getIspublic() + "%"));
                }
                // 是否置顶
                if (StringUtils.isNotEmpty(article.getIstop())) {
                    predicateList.add(cb.like(root.get("istop").as(String.class), "%" + article.getIstop() + "%"));
                }
                // 审核状态
                if (StringUtils.isNotEmpty(article.getState())) {
                    predicateList.add(cb.like(root.get("state").as(String.class), "%" + article.getState() + "%"));
                }
                // 所属频道
                if (StringUtils.isNotEmpty(article.getChannelid())) {
                    predicateList.add(cb.like(root.get("channelid").as(String.class), "%" + article.getChannelid() + "%"));
                }
                // URL
                if (StringUtils.isNotEmpty(article.getUrl())) {
                    predicateList.add(cb.like(root.get("url").as(String.class), "%" + article.getUrl() + "%"));
                }
                // 类型
                if (StringUtils.isNotEmpty(article.getType())) {
                    predicateList.add(cb.like(root.get("type").as(String.class), "%" + article.getType() + "%"));
                }

                return cb.and(predicateList.toArray(new Predicate[predicateList.size()]));

            }
        };

    }

}
