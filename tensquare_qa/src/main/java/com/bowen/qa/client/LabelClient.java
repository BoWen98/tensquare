package com.bowen.qa.client;

import com.bowen.common.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("tensquare-base")
public interface LabelClient {

    @GetMapping("/label/{labelId}")
    public Result findById(@PathVariable("labelId") String id);
}
