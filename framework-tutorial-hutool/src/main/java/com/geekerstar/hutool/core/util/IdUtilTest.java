package com.geekerstar.hutool.core.util;

import cn.hutool.core.lang.Console;
import cn.hutool.core.lang.ObjectId;
import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import org.junit.Test;

/**
 * @author geekerstar
 * date: 2019/11/30 16:46
 * description:
 * UUID
 * ObjectId（MongoDB）
 * Snowflake（Twitter）
 */
public class IdUtilTest {
    @Test
    public void test(){
        /**
         * UUID
         */
        //生成的UUID是带-的字符串，类似于：a5c8a5e8-df2b-4706-bea4-08d0939410e3
        String uuid = IdUtil.randomUUID();
        Console.log(uuid);

        //生成的是不带-的字符串，类似于：b17f24ff026d40949c85a24f4f375d42
        String simpleUUID = IdUtil.simpleUUID();
        Console.log(simpleUUID);

        /**
         * ObjectId
         */
        //生成类似：5b9e306a4df4f8c54a39fb0c
        String id = ObjectId.next();
        Console.log(id);

        //方法2：从Hutool-4.1.14开始提供
        String id2 = IdUtil.objectId();
        Console.log(id2);

        /**
         * Snowflake
         */
        //参数1为终端ID
        //参数2为数据中心ID
        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
        long id3 = snowflake.nextId();
        Console.log(id3);



    }
}
