package top.duofeng.test.demo.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.base.pojo.NormalCodeName;
import top.duofeng.test.demo.config.RagAreaConfig;
import top.duofeng.test.demo.pojo.req.ChatMessage;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatChoice;
import top.duofeng.test.demo.pojo.res.ChatDelta;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.service.OuterService;
import top.duofeng.test.demo.utils.DataCreateUtil;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.service.impl.OuterServiceDevImpl
 * author Rorschach
 * dateTime 2026/1/26 20:00
 */
@Service
@Slf4j
@ConditionalOnProperty(prefix = "spring.profiles", name = "active", havingValue = "dev")
public class OuterServiceDevImpl implements OuterService {

    private final OllamaChatModel chatModel;
    private final RagAreaConfig ragAreaConfig;

    public OuterServiceDevImpl(OllamaChatModel chatModel, RagAreaConfig ragAreaConfig) {
        this.chatModel = chatModel;
        this.ragAreaConfig = ragAreaConfig;
    }

    @Override
    public Flux<ChatResponseVO> chatSingle(String systemCode,
                                           Pair<NormalCodeName, String> priPair, ChatMsgReq req) {
        List<Message> messages = Lists.newArrayList();
        for (int i = 0; i < req.getMessages().size(); i++) {
            ChatMessage chatMessage = req.getMessages().get(i);
            messages.add(new UserMessage(chatMessage.getContent()));
        }
        Prompt prompt = new Prompt(messages);
        OllamaChatOptions chatOption = OllamaChatOptions
                .builder()
                .disableThinking()
                .model(ragAreaConfig.getDefaultModel()).build();
        prompt = prompt.mutate().chatOptions(chatOption).build();
        Flux<ChatResponseVO> map = chatModel.stream(prompt)
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
                    vo.setPrivateId(priPair.getSecond());
                    vo.setMaskCode(priPair.getFirst().getCode());
                    vo.setMaskName(priPair.getFirst().getName());
                    return vo;
                });
        return map.concatWithValues(DataCreateUtil.fakeReference());
    }
}
