package hello;

import com.dt.miniapp.model.UserHomepage;
import com.dt.miniapp.processor.IUserHomepageProcessor;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author chenlei
 * @date 2019-04-23
 */
@Slf4j
public class TestCompletableFuture extends BaseTest {

    @Autowired
    private List<IUserHomepageProcessor> processorList;

    private static ThreadFactoryBuilder threadFactoryBuilder = new ThreadFactoryBuilder().setNameFormat("asyntask-util-%d");


    public static final ThreadPoolExecutor EXECUTOR_SERVICE = new ThreadPoolExecutor(1,
            1,
            0L,
            TimeUnit.MILLISECONDS,
            new LinkedBlockingQueue<>(),
            threadFactoryBuilder.build());

    @Test
    public void test() {
        UserHomepage userHomepage = new UserHomepage();

        CompletableFuture<UserHomepage> cf = CompletableFuture.completedFuture(userHomepage);

        log.info("开始");

        long now = System.currentTimeMillis();

        CompletableFuture[] cfArr = processorList.stream()
                .map(process ->
                        cf.thenCompose(homepage ->
                                CompletableFuture.supplyAsync(() -> process.processor(homepage))))
                                       // .completeOnTimeout(homepage, 1000, TimeUnit.MILLISECONDS)))
                .toArray(CompletableFuture[]::new);

        CompletableFuture.allOf(cfArr).join();

        log.info("结束，耗时：{}", System.currentTimeMillis() - now);

        log.info("res={}", userHomepage.toString());
    }

}
