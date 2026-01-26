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
import top.duofeng.test.demo.config.RagAreaConfig;
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
    private static RagAreaConfig ragAreaConfig;
    public DataCreateUtil(RagAreaConfig ragAreaConfig) {
        DataCreateUtil.ragAreaConfig = ragAreaConfig;
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
