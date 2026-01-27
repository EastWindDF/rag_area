package top.duofeng.test.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.base.pojo.ResultDTO;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.req.add.ChatStarRatingAdd;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;
import top.duofeng.test.demo.pojo.res.ConvCreatedVO;
import top.duofeng.test.demo.pojo.res.HistoryChatVO;
import top.duofeng.test.demo.service.CombineAIService;
import top.duofeng.test.demo.service.ConversationService;

@RestController
@Tag(name = "会话相关接口")
@RequestMapping("/conv")
public class ConversationController {

    private final ConversationService convService;
    private final CombineAIService combineAIService;

    public ConversationController(ConversationService convService,
                                  CombineAIService combineAIService) {
        this.convService = convService;
        this.combineAIService = combineAIService;
    }

    @GetMapping("/his")
    @Operation(summary = "历史记录")
    public ResultDTO<HistoryChatVO> his(
            @RequestParam("sessionId") String sessionId,
            @RequestParam(value = "priId",required = false) String priId,
            @RequestHeader(value = "userId", defaultValue = "mamba") String userId) {
        return ResultDTO.success(convService.his(sessionId, priId, userId));
    }

    @PostMapping("/create")
    @Operation(summary = "创建对话")
    public ResultDTO<ConvCreatedVO> create(@RequestBody @Valid ChatMsgReq req,
                                           @RequestHeader(value = "userId",defaultValue = "mamba") String userId) {
        return ResultDTO.success(convService.createConv(req, userId).toCreatedVO());
    }

    @PostMapping(value = "/chat",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "对话开始")
    public Flux<ChatResponseVO> chat(@RequestBody ChatMsgReq req,
                                     @RequestHeader(value = "userId", defaultValue = "mamba") String userId) {
        return combineAIService.combineChat(req,userId);
    }

    @PostMapping(value = "/chat/pri",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "私聊")
    public Flux<ChatResponseVO> chatPri(@RequestBody ChatMsgReq req,
                                     @RequestHeader(value = "userId", defaultValue = "mamba") String userId) {
        return combineAIService.chatSingle(req,userId, true);
    }

    @PostMapping(value = "/chat/single",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @Operation(summary = "单个聊天")
    public Flux<ChatResponseVO> chatSingle(@RequestBody ChatMsgReq req,
                                        @RequestHeader(value = "userId", defaultValue = "mamba") String userId) {
        return combineAIService.chatSingle(req,userId, false);
    }

    @GetMapping("/like")
    @Operation(summary = "点赞")
    @Parameters({
            @Parameter(name = "priId", description = "根据会话ID计算来的唯一ID"),
            @Parameter(name = "userId", description = "用户ID")
    })
    public ResultDTO<Boolean> like(
            @RequestParam(value = "priId") String priId,
            @RequestHeader(value = "userId", defaultValue = "mamba") String userId) {
        return ResultDTO.success(convService.like(priId, userId));
    }

    @GetMapping("/unlike")
    @Operation(summary = "取消点赞")
    @Parameters({
            @Parameter(name = "priId", description = "根据会话ID计算来的唯一ID"),
            @Parameter(name = "userId", description = "用户ID")
    })
    public ResultDTO<Boolean> unlike(
            @RequestParam(value = "priId") String priId,
            @RequestHeader(value = "userId", defaultValue = "mamba") String userId) {
        return ResultDTO.success(convService.unlike(priId, userId));
    }


    @PostMapping("/rating")
    @Operation(summary = "星评", hidden = true)
    public ResultDTO<Boolean> rating(
            @RequestBody ChatStarRatingAdd add,
            @RequestHeader(value = "userId", defaultValue = "mamba") String userId) {
        return ResultDTO.success(convService.rating(add, userId));
    }


    @GetMapping("/del")
    @Operation(summary = "删除会话")
    public ResultDTO<Boolean> del(@RequestParam("sessionId") String sessionId,
                                  @RequestHeader(value = "userId", defaultValue = "mamba") String userId){
        return ResultDTO.success(convService.del(sessionId, userId));
    }


    @GetMapping("/rename")
    @Operation(summary = "修改会话名称")
    public ResultDTO<Boolean> rename(@RequestParam("sessionId") String sessionId,
                                  @RequestParam("title") String title,
                                  @RequestHeader(value = "userId", defaultValue = "mamba") String userId){
        return ResultDTO.success(convService.rename(sessionId, title, userId));
    }
}
