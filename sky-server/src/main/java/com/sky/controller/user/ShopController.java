package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

@RestController("userShopController")
@RequestMapping("/user/shop")
@Api(tags="店铺相关接口")
@Slf4j
public class ShopController {
    public static final String KEY = "SHOP_STATUS";
    @Autowired
    private RedisTemplate redisTemplate;
    /**
     * 获取营业状态
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("获取营业状态")
//    public Result<Integer> getStatus(){
//        Integer status = (Integer)redisTemplate.opsForValue().get(KEY);
//        log.info("获取营业状态:{}",status == 1 ? "营业中" : "打烊中");
//        return Result.success(status);
//    }
    public Result<Integer> getStatus(){
        Integer status = (Integer) redisTemplate.opsForValue().get(KEY);

        // 添加空值检查
        if (status == null) {
            log.info("获取营业状态: Redis中未找到状态值，默认为打烊中");
            status = 1; // 默认设置为营业状态
        } else {
            log.info("获取营业状态:{}", status == 1 ? "营业中" : "打烊中");
        }

        return Result.success(status);
    }

}
