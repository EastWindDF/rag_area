package top.duofeng.test.demo.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import top.duofeng.test.demo.base.pojo.ResultDTO;
import top.duofeng.test.demo.pojo.req.FeedbackQueryReq;
import top.duofeng.test.demo.pojo.req.FeedbackReq;
import top.duofeng.test.demo.pojo.res.FeedbackResultVO;
import top.duofeng.test.demo.pojo.res.ReferenceDetailVO;
import top.duofeng.test.demo.service.OthersInfoService;

/**
 * project test-demo
 * path top.duofeng.test.demo.controller.OtherController
 * author Rorschach
 * dateTime 2026/1/28 0:38
 */
@RestController
@Tag(name = "其他接口")
@RequestMapping("/others")
public class OthersIntoController {

    private final OthersInfoService othersInfoService;

    public OthersIntoController(OthersInfoService othersInfoService) {
        this.othersInfoService = othersInfoService;
    }

    @GetMapping("/ref/detail")
    @Operation(summary = "查看引用文档")
    public ResultDTO<ReferenceDetailVO> refDetail(@RequestParam("refId") String refId,
                                                  @RequestParam("priId") String priId){
        return ResultDTO.success(othersInfoService.refDetail(refId, priId));
    }

    @PostMapping("/feedback")
    @Operation(summary = "反馈信息")
    public ResultDTO<Boolean> feedback(@RequestBody FeedbackReq param) {
        return ResultDTO.success(othersInfoService.feedback(param));
    }

    @PostMapping("/feedback/query")
    @Operation(summary = "外部接口调用查询")
    public FeedbackResultVO feedbackQuery(@RequestBody FeedbackQueryReq param) {
        return othersInfoService.feedbackQuery(param);
    }

}
