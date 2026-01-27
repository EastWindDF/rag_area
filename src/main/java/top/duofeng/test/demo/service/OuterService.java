package top.duofeng.test.demo.service;

import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.springframework.data.util.Pair;
import reactor.core.publisher.Flux;
import top.duofeng.test.demo.base.pojo.NormalCodeName;
import top.duofeng.test.demo.pojo.req.ChatMsgReq;
import top.duofeng.test.demo.pojo.res.ChatResponseVO;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public interface OuterService {

    /*default  Flux<ChatResponseVO> chatMessage(ChatMsgReq req , Pair<FakeModelEnum, String> pair) {
        if(DataCreateUtil.isMocked()){
            return DataCreateUtil.genTest(req).doOnNext(re->{
                re.setMaskName(pair.getFirst().getName());
                re.setPrivateId(pair.getSecond());
                re.setMaskCode(pair.getFirst().toString());
            });
        }
        return getWebClient().post()
                .uri(uriBuilder -> uriBuilder.path("/ai/gen-test").build())
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(req)
                .retrieve()
                .bodyToFlux(ChatResponseVO.class)
                .doOnNext(re->{
                    re.setMaskName(pair.getFirst().getName());
                    re.setPrivateId(pair.getSecond());
                    re.setMaskCode(pair.getFirst().toString());
                });
    }
*/
    Flux<ChatResponseVO> chatSingle(
            String systemCode,
            Pair<NormalCodeName, String> priPair,
            ChatMsgReq req);

    @SneakyThrows
    default List<Flux<ChatResponseVO>> chats(ChatMsgReq req , Map<String,Pair<NormalCodeName, String>> pairMap){
        List<Flux<ChatResponseVO>> list = Lists.newCopyOnWriteArrayList();
        List<CompletableFuture<Void>> futures = Lists.newArrayList();
        pairMap.forEach((k,v)-> futures.add(CompletableFuture.runAsync(()->list.add(this.chatSingle(k,v,req)))));
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).get();
        return list;
    }
}
