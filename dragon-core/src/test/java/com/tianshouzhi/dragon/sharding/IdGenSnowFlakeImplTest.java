package com.tianshouzhi.dragon.sharding;

import org.junit.Test;

import com.tianshouzhi.dragon.sharding.idgen.IdGenSnowFlakeImpl;

/**
 * Created by tianshouzhi on 2017/7/7.
 */
public class IdGenSnowFlakeImplTest {
    @Test
    public void getAutoIncrementId() throws Exception {
        IdGenSnowFlakeImpl snowFlake = new IdGenSnowFlakeImpl(1);
        Long autoIncrementId = snowFlake.getAutoIncrementId();
        System.out.println(autoIncrementId);
    }
}