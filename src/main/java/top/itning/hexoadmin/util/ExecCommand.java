package top.itning.hexoadmin.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import top.itning.hexoadmin.config.CommandWebSocket;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.*;

/**
 * @author wangn
 */
@Component
public class ExecCommand {
    private static final Logger logger = LoggerFactory.getLogger(ExecCommand.class);
    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("ExecCommand-pool-%d").build();
    private static ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory);
    private final CommandWebSocket commandWebSocket;

    @Autowired
    public ExecCommand(CommandWebSocket commandWebSocket) {
        this.commandWebSocket = commandWebSocket;
    }

    public void execute(String command, String workingDir) throws IOException, InterruptedException {
        final Process process = Runtime.getRuntime().exec(command, null, new File(workingDir));
        printMessage(process.getInputStream(), " inputStream");
        printMessage(process.getErrorStream(), " errorStream");
    }

    private Future<?> printMessage(final InputStream input, String tag) {
        return executorService.submit(() -> {
            try {
                Reader reader = new InputStreamReader(input, StandardCharsets.UTF_8);
                BufferedReader bf = new BufferedReader(reader);
                String line;

                while ((line = bf.readLine()) != null) {
                    commandWebSocket.sendMessage(tag + " : " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void shutdownNow() {
        executorService.shutdownNow();
        if (executorService.isShutdown()) {
            executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory);
        } else {
            logger.error("关闭失败");
        }

    }
}
