package top.itning.hexoadmin.util;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import java.io.*;
import java.util.concurrent.*;

/**
 * @author wangn
 */
public class ExecCommand {
    private static ThreadFactory threadFactory = new ThreadFactoryBuilder().setNameFormat("ExecCommand-pool-%d").build();
    private static ExecutorService executorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory);

    public static void execute() throws IOException, InterruptedException {
        String cmd = "ping www.baidu.com";
        final Process process = Runtime.getRuntime().exec(cmd);
        printMessage(process.getInputStream(), " inputStream");
        printMessage(process.getErrorStream(), " errorStream");
        int value = process.waitFor();
        System.out.println("Process finished with exit code -->" + value);
    }

    private static void printMessage(final InputStream input, String tag) {
        executorService.submit(() -> {
            try {
                Reader reader = new InputStreamReader(input, "gbk");
                BufferedReader bf = new BufferedReader(reader);
                String line;

                while ((line = bf.readLine()) != null) {
                    System.out.println(Thread.currentThread().getName() + tag + " : " + line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
