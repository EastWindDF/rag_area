package top.duofeng.test.demo.pojo.res;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatRespWithLikedVO extends ChatResponseVO implements Serializable {

    private Boolean liked;
}
