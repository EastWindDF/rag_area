package top.duofeng.test.demo.base.dao;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 持久层通用父类
 * @param <T> 持久对象类型
 * @param <E> 持久对象主键类型
 */
public interface CommonDao<T,E> extends JpaSpecificationExecutor<T>, JpaRepository<T, E> {
}
