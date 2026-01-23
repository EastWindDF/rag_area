package top.duofeng.test.demo.pojo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.apache.commons.compress.utils.Lists;
import org.springframework.util.CollectionUtils;
import top.duofeng.test.demo.base.pojo.CommonDict;

import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.res.TaskTreeDict
 * author Rorschach
 * dateTime 2026/1/11 17:09
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class CommonTreeDict extends CommonDict<String,String> {
    private boolean leaf;
    private List<CommonTreeDict> children = Lists.newArrayList();

    public CommonTreeDict(String id, String name,
                          boolean leaf,
                          List<CommonTreeDict> children) {
        super(id,name);
        this.leaf = leaf;
        if(!CollectionUtils.isEmpty(children)) {
            this.children = children;
        }
    }
}
