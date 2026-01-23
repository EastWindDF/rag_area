package top.duofeng.test.demo.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FakeModelEnum {
    ALPHA(1, "模型A", "modelA"),
    BRAVO(2, "模型B", "modelB"),
    CHARLIE(3, "模型C", "modelC"),
    DELTA(4, "模型D", "modelD");
    private Integer sort;
    private String name,code;

    public static FakeModelEnum getByCode(String code) {
        for (FakeModelEnum value : values()) {
            if(value.getName().equals(code)){
                return value;
            }
        }
        return null;
    }

    public static FakeModelEnum getBySort(Integer sort){
        for (FakeModelEnum value : values()) {
            if(value.getSort().intValue() == sort){
                return value;
            }
        }
        return null;
    }

    public static FakeModelEnum getByName(String name){
        for (FakeModelEnum value : values()) {
            if(value.getName().equals(name)){
                return value;
            }
        }
        return null;
    }
}
