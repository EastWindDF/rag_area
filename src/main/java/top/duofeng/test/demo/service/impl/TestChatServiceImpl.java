package top.duofeng.test.demo.service.impl;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.base.pojo.NormalCodeName;
import top.duofeng.test.demo.config.RagAreaConfig;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatChoice;
import top.duofeng.test.demo.pojo.res.ChatDelta;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.service.TestChatService;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.impl.TestChatServiceImpl
 * author Rorschach
 * dateTime 2026/1/25 0:31
 */
@Slf4j
@Service
public class TestChatServiceImpl implements TestChatService {
    private final ChatModel chatModel;
    private final List<NormalCodeName> normalCodeNames;
    public TestChatServiceImpl(ChatModel chatModel, RagAreaConfig ragAreaConfig) {
        this.chatModel = chatModel;
        this.normalCodeNames = ragAreaConfig.getMaskProps();
    }

    @Override
    public Flux<ChatResponseVO> testChat(ChatMsgReq req) {
        List<Flux<ChatResponseVO>> list = Lists.newArrayList();
        for (int i = 0; i <4; i++) {
            NormalCodeName normalCodeName = normalCodeNames.get(i);
            list.add(chatModel.stream(new Prompt(req.getMessages().get(0).getContent()))
                .map(resp -> {
            ChatResponseVO vo = new ChatResponseVO();
            vo.setCreated(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)));
            vo.setSession_id(req.getSession_id());
            String text = resp.getResult().getOutput().getText();
            ChatDelta delta = new ChatDelta();
            ChatChoice choice = new ChatChoice();
            choice.setDelta(delta);
            delta.setContent(text);
            choice.setFinish_reason(resp.getResult().getMetadata().getFinishReason());
            vo.setChoices(Lists.newArrayList(choice));
            vo.setMaskCode(normalCodeName.getCode());
            vo.setMaskName(normalCodeName.getName());
            return vo;
        }).delayElements(Duration.ofMillis(RandomUtil.randomInt(100,1000))));
        }
        return Flux.merge(list);
    }

    @Override
    public Flux<ChatResponseVO> testChat2(ChatMsgReq req) {
        List<Flux<ChatResponseVO>> list = Lists.newArrayList();
        for (int i = 0; i <4; i++) {
            list.add(chatModel.stream(new Prompt(req.getMessages().get(0).getContent()))
                    .map(resp -> {
                        ChatResponseVO vo = new ChatResponseVO();
                        vo.setCreated(LocalDateTime.now().toEpochSecond(ZoneOffset.ofHours(8)));
                        vo.setSession_id(req.getSession_id());
                        String text = resp.getResult().getOutput().getText();
                        ChatDelta delta = new ChatDelta();
                        ChatChoice choice = new ChatChoice();
                        choice.setDelta(delta);
                        delta.setContent(text);
                        choice.setFinish_reason(resp.getResult().getMetadata().getFinishReason());
                        vo.setChoices(Lists.newArrayList(choice));
                        return vo;
                    }));
        }
        return Flux.merge(list);
    }
}
