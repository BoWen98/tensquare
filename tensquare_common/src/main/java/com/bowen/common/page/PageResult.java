package com.bowen.common.page;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @ProjectName: tensquare
 * @Package: com.bowen.common.page
 * @ClassName: PageResult
 * @Author: Bowen
 * @Description: 分页结果
 * @Date: 2019/11/18 15:28
 * @Version: 1.0.0
 */
@Data
@AllArgsConstructor
public class PageResult<T> {
    private Long total;
    private List<T> rows;
}
