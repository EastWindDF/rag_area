package top.duofeng.test.demo.config;

import com.google.common.collect.Maps;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import top.duofeng.test.demo.base.pojo.NormalCodeName;
import top.duofeng.test.demo.base.pojo.OuterServiceInfo;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * project test-demo
 * path top.duofeng.test.demo.config.RagAreaConfig
 * author Rorschach
 * dateTime 2026/1/26 10:18
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "rag-area")
public class RagAreaConfig implements Serializable {
    private List<OuterServiceInfo> outerServices;
    private List<NormalCodeName> maskProps;
    private String defaultModel;
    private Boolean mockEnabled = Boolean.FALSE;
    public Map<String, OuterServiceInfo> getOuterServiceMap(){
        return Optional.ofNullable(outerServices)
                .map(list->list.stream().collect(Collectors.toMap(OuterServiceInfo::getCode,
                        Function.identity(),(v1,v2)->v1)))
                .orElse(Maps.newHashMap());
    }
    public Map<String, NormalCodeName> getMaskMap(){
        return Optional.ofNullable(maskProps)
                .map(list->list.stream().collect(Collectors.toMap(NormalCodeName::getCode,
                        Function.identity(),(v1,v2)->v1)))
                .orElse(Maps.newHashMap());
    }


}
