import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import lombok.SneakyThrows;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.h2.tools.Server;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * project test-demo
 * path PACKAGE_NAME.TestMain
 * author Rorschach
 * dateTime 2026/1/13 23:03
 */
public class TestMain {
    static Map<Integer,String> CHAR_MAP = new HashMap<>(){{
        put(0,"A");
        put(2,"B");
        put(1,"D");
        put(3,"C");
    }};
    @SneakyThrows
    public static void main(String[] args) {
        List<Flux<String>> list = Lists.newArrayList();
        for (int i = 0; i < 4; i++) {
            String s = CHAR_MAP.get(i);

            list.add(Flux.just(s.concat("1"),s.concat("2"),s.concat("3"))
                    .delayElements(Duration.ofMillis(RandomUtil.randomInt(100,1000))));
        }

        Flux.merge(4,Flux.merge(list))
                .subscribe(System.out::println);


        Flux<Integer> streamA = Flux.just(1, 2, 3);
        Flux<Integer> streamB = Flux.just(4, 5, 6);
        Flux<Integer> streamC = Flux.just(7, 8, 9);
        Flux<Integer> streamD = Flux.just(10, 11, 12);

        Flux<Integer> mergedFlux = Flux.merge(
                streamA,
                streamB,
                streamC,
                streamD
        );
        mergedFlux.subscribe(System.out::println);

        TimeUnit.SECONDS.sleep(30);


    }
}
