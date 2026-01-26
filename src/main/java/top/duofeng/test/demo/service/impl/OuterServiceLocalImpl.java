package top.duofeng.test.demo.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.base.pojo.NormalCodeName;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatChoice;
import top.duofeng.test.demo.pojo.res.ChatDelta;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.service.OuterService;
import top.duofeng.test.demo.utils.DataCreateUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

import static java.lang.Integer.min;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.impl.OuterServiceLocalImpl
 * author Rorschach
 * dateTime 2026/1/26 20:15
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "local", matchIfMissing = true)
public class OuterServiceLocalImpl implements OuterService {

    private static final String TEXT = "你好！我是 Qwen，通义千问的助手。我是一个由通义实验室开发的大规模语言模型，能够帮助你回答问题、创作文字、编程、逻辑推理以及更多任务。有什么我可以帮助你的吗？";


    @Override
    public Flux<ChatResponseVO> chatSingle(String systemCode,
                                           Pair<NormalCodeName, String> priPair,
                                           ChatMsgReq req) {
        int idx = 0;
        List<ChatResponseVO> respList = Lists.newArrayList();
        while (idx < TEXT.length()) {
            int i = min(idx + RandomUtil.randomInt(1, 3), TEXT.length());
            ChatResponseVO vo = new ChatResponseVO();
            vo.setCreated(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)));
            vo.setSession_id(req.getSession_id());
            ChatDelta delta = new ChatDelta();
            ChatChoice choice = new ChatChoice();
            choice.setDelta(delta);
            delta.setContent(TEXT.substring(idx, i));
            choice.setFinish_reason(null);
            vo.setChoices(Lists.newArrayList(choice));
            vo.setPrivateId(priPair.getSecond());
            vo.setMaskCode(priPair.getFirst().getCode());
            vo.setMaskName(priPair.getFirst().getName());
            respList.add(vo);
            idx = i;
        }
        respList.get(respList.size()-1).getChoices().get(0).setFinish_reason("stop");
        return Flux.fromIterable(respList).concatWithValues(DataCreateUtil.fakeReference());

    }
}
