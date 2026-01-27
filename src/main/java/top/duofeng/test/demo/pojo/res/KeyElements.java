package top.duofeng.test.demo.pojo.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.res.KeyElements
 * author Rorschach
 * dateTime 2026/1/28 0:31
 */
@Data
@Schema(title = "关键要素")
public class KeyElements implements Serializable {

    @Schema(title = "人物数组")
    private List<String> persons;
    @Schema(title = "组织数组")
    private List<String> organizations;
    @Schema(title = "事件数组")
    private List<String> events;
    @Schema(title = "其余元素数组")
    private List<String> others;
}
