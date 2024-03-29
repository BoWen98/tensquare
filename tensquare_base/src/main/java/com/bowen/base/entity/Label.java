package com.bowen.base.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @ProjectName: tensquare
 * @Package: com.bowen.base.entity
 * @ClassName: Label
 * @Author: Bowen
 * @Description: 标签实体类
 * @Date: 2019/11/18 15:41
 * @Version: 1.0.0
 */
@Entity
@Data
@Table(name = "tb_label")
public class Label {

    @Id
    private String id;//

    private String labelname;//标签名称

    private String state;//状态

    private Long count;//使用数量

    private Long fans;//关注数

    private String recommend;//是否推荐
}
