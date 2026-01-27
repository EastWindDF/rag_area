package top.duofeng.test.demo.base.anno;

import org.hibernate.annotations.IdGeneratorType;
import top.duofeng.test.demo.base.utils.LocalSnowFlakeGenerator;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME; /**
 * project test-demo
 * path top.duofeng.test.demo.base.anno.CustomGenerator
 * author Rorschach
 * dateTime 2026/1/10 21:39
 */
@IdGeneratorType(LocalSnowFlakeGenerator.class)
@Retention(RUNTIME) 
@Target({METHOD,FIELD})
public @interface CustomIdGenerator {
}
