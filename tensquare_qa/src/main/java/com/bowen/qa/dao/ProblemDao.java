package com.bowen.qa.dao;

import com.bowen.qa.entity.Problem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;



/**
 * 数据访问接口
 *
 * @author Administrator
 */
public interface ProblemDao extends JpaRepository<Problem, String>, JpaSpecificationExecutor<Problem> {

    //mybatis
    //@Select("select * from tb_problem,tb_pl WHERE id = problemid AND labelid =?")
    @Query(value = "select * from tb_problem,tb_pl WHERE id = problemid AND labelid = ? ORDER BY replytime DESC", nativeQuery = true)
    public Page<Problem> newList(String labelid, Pageable pageable);

    @Query(value = "select * from tb_problem,tb_pl WHERE id = problemid AND labelid = ? ORDER BY reply DESC", nativeQuery = true)
    public Page<Problem> hotList(String labelid, Pageable pageable);

    @Query(value = "select * from tb_problem,tb_pl WHERE id = problemid AND labelid = ? AND reply= 0 ORDER BY createtime DESC", nativeQuery = true)
    public Page<Problem> waitList(String labelid, Pageable pageable);
}
