package top.duofeng.test.demo.base.utils;

import cn.hutool.core.lang.Snowflake;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.generator.BeforeExecutionGenerator;
import org.hibernate.generator.EventType;
import org.hibernate.generator.EventTypeSets;
import org.hibernate.generator.Generator;
import org.hibernate.id.IdentifierGenerator;

import java.io.Serializable;
import java.util.EnumSet;

/**
 * project test-demo
 * path top.duofeng.test.demo.base.utils.LocalSnowFlakeGenerator
 * author Rorschach
 * dateTime 2026/1/10 21:09
 */
public class LocalSnowFlakeGenerator implements BeforeExecutionGenerator {
    private final Snowflake snowflake = new Snowflake(1, 1);
//    @Override
//    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o) {
//        return snowflake.nextIdStr();
//    }

    @Override
    public Object generate(SharedSessionContractImplementor sharedSessionContractImplementor, Object o, Object o1, EventType eventType) {
        return snowflake.nextIdStr();
    }

    @Override
    public EnumSet<EventType> getEventTypes() {
        return  EventTypeSets.INSERT_ONLY;
    }
}
