package top.duofeng.test.demo.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.service.TestChatService;

/**
 * project test-demo
 * path top.duofeng.test.demo.controller.TestController
 * author Rorschach
 * dateTime 2026/1/25 0:38
 */

@RestController
@Tag(name = "测试接口")
@RequestMapping("/combine")
public class TestController {
private final TestChatService testChatService;

    public TestController(TestChatService testChatService) {
        this.testChatService = testChatService;
    }

    @PostMapping(value = "/test",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponseVO> test(@RequestBody ChatMsgReq req) {
        return testChatService.testChat(req);
    }

    @PostMapping(value = "/test2",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ChatResponseVO> test2(@RequestBody ChatMsgReq req) {
        return testChatService.testChat2(req);
    }
}
