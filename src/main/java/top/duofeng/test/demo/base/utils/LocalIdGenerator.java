package top.duofeng.test.demo.base.utils;

import cn.hutool.core.lang.Snowflake;

/**
 * project test-demo
 * path top.duofeng.test.demo.base.utils.LocalIdGenerator
 * author Rorschach
 * dateTime 2026/1/13 10:51
 */
public class LocalIdGenerator {
    private static final Snowflake SNOW_FLAK = new Snowflake(0,0);

    public LocalIdGenerator() {
        throw new IllegalArgumentException();
    }


    public static String getStrId(){
        return SNOW_FLAK.nextIdStr();
    }

    public static Long getLonId(){
        return SNOW_FLAK.nextId();
    }
 }
