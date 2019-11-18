package com.bowen.common.result;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @ProjectName: tensquare
 * @Package: com.bowen.common.result
 * @ClassName: Result
 * @Author: Bowen
 * @Description: 返回结果
 * @Date: 2019/11/18 14:53
 * @Version: 1.0.0
 */
@Data
@NoArgsConstructor
public class Result<T> {

    private boolean flag;//是否成功
    private Integer code;// 返回码
    private String message;//返回信息
    private Object data;// 返回数据

    public Result(boolean flag, Integer code, String message, Object data) {
        this.flag = flag;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Result(boolean flag, Integer code, String message) {
        this.flag = flag;
        this.code = code;
        this.message = message;
    }
}
