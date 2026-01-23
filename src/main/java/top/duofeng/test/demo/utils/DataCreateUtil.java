package top.duofeng.test.demo.utils;

import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import lombok.Data;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaChatOptions;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.config.MockConfiguration;
import top.duofeng.test.demo.pojo.req.ChatMessage;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatChoice;
import top.duofeng.test.demo.pojo.res.ChatCitation;
import top.duofeng.test.demo.pojo.res.ChatDelta;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

/**
 * project test-demo
 * path top.duofeng.test.demo.utils.DataCreateUtil
 * author Rorschach
 * dateTime 2026/1/23 1:23
 */
@Data
@Component
public class DataCreateUtil implements Serializable {

    private static OllamaChatModel chatModel;
    private static MockConfiguration mockConfig;
    public DataCreateUtil(OllamaChatModel chatModel,
                          MockConfiguration mockConfig) {
        DataCreateUtil.chatModel = chatModel;
        DataCreateUtil.mockConfig = mockConfig;
    }

    public static Boolean isMocked(){
        return mockConfig.getEnabled();
    }


    public static Flux<ChatResponseVO> genTest(ChatMsgReq req) {
        List<Message> messages = Lists.newArrayList();
        for (int i = 0; i < req.getMessages().size(); i++) {
            ChatMessage chatMessage = req.getMessages().get(i);
            messages.add(new UserMessage(chatMessage.getContent()));
        }
        Prompt prompt = new Prompt(messages);
        OllamaChatOptions chatOption = OllamaChatOptions
                .builder()
                .disableThinking()
                .model(mockConfig.getDefaultModel()).build();
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
                    return vo;
                });
        return map.concatWithValues(fakeReference());
    }

    private static List<String> nums = Lists.newArrayList("10086","12315","10010",
            "10001","12580","31445");

    public static ChatResponseVO fakeReference(){
        ChatResponseVO result = new ChatResponseVO();
        ChatChoice choice = new ChatChoice();
        choice.setFinish_reason("stop");
        choice.setDelta(new ChatDelta());
        result.setChoices(Lists.newArrayList(choice));
        List<ChatCitation> citations = Lists.newArrayList();
        result.setCitations(citations);
        for (int i = 0; i < 5; i++) {
            int num = Math.abs(RandomUtil.randomInt()%nums.size());
            int num2 = num+1>= nums.size()?0:num+1;
            ChatCitation citation = new ChatCitation();
            citation.setCallednumber(nums.get(num));
            citation.setId("1778");
            citation.setCallnumber(nums.get(num2));
            citation.setLabels("测试111|测试222|测试333");
            citation.setSummary("123444444");
            citation.setDuration(2333);
            citation.setSummary("测试数据");
            citation.setStart_time(LocalDateTime.of(2025,12,24,13,18));
            citations.add(citation);
        }
        return result;

    }


}
