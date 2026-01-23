package top.duofeng.test.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.service.CombineAIService;
import top.duofeng.test.demo.service.OuterService;

import java.util.List;

@RestController
@Tag(name = "ai融合接口")
@RequestMapping("/combine")
public class CombineAIController {

    private final CombineAIService combineAIService;

    public CombineAIController(CombineAIService combineAIService) {
        this.combineAIService = combineAIService;
    }

    @RequestMapping(value = "/chat",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponseVO> chat(@RequestBody ChatMsgReq req) {
        return combineAIService.combineChat(req);
    }
}
