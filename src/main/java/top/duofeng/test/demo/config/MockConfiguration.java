package top.duofeng.test.demo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.io.Serializable;

/**
 * project test-demo
 * path top.duofeng.test.demo.config.MockConfiguration
 * author Rorschach
 * dateTime 2026/1/23 1:19
 */
@Data
public class MockConfiguration implements Serializable {

    private Boolean enabled;
    private String defaultModel;
}
