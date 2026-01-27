package top.duofeng.test.demo.pojo.dto;

import com.google.common.collect.Maps;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.util.Pair;
import top.duofeng.test.demo.base.pojo.NormalCodeName;
import top.duofeng.test.demo.pojo.res.ConvCreatedVO;

import java.io.Serializable;
import java.util.Map;

/**
 * project test-demo
 * path top.duofeng.test.demo.pojo.dto.ConvPriMappingDTO
 * author Rorschach
 * dateTime 2026/1/13 10:49
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConvPriMappingDTO implements Serializable {

    private String sessionId;
    private Map<String, Pair<NormalCodeName, String>> priMapping;

    public ConvCreatedVO toCreatedVO() {
        ConvCreatedVO vo = new ConvCreatedVO();
        Map<String, String> map = Maps.newHashMap();
        vo.setPriIdMapping(map);
        priMapping.forEach((k, v) -> map.put(v.getFirst().getCode(),
                v.getSecond()));
        vo.setSessionId(sessionId);
        return vo;
    }
}
