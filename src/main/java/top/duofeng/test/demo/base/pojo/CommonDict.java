package top.duofeng.test.demo.base.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.base.pojo.CommonDict
 * author Rorschach
 * dateTime 2026/1/11 17:08
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public abstract class CommonDict <T,E> {
    protected T id;
    protected E val;
}
