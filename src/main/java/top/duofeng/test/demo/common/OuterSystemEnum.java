package top.duofeng.test.demo.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.util.StringUtils;

@Getter
@AllArgsConstructor
public enum OuterSystemEnum {

    SYS_FIRST("1ST", "系统一"),
    SYS_SECOND("2ND", "系统二"),
    SYS_THIRD("3RD", "系统三"),
    SYS_FOURTH("4TH", "系统四");
    private String code,name;

    public static OuterSystemEnum getByCode(String sysCode) {
        if(StringUtils.hasText(sysCode)){
            for (OuterSystemEnum value : values()) {
                if(sysCode.equals(value.getCode())){
                    return value;
                }
            }
        }
        return null;
    }
}
