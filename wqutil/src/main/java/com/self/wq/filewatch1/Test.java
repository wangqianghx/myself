package com.self.wq.filewatch1;

import java.io.IOException;
import java.nio.file.*;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * 描述：
 *
 * @author wangqiang at 2019/4/9 11:21
 * @version 1.0.0
 */
public class Test {

    public static void main(String[] args) {

        final Path path;
        WatchService watchService = null;
        try {
            path = Paths.get("C:\\wq_work\\testfilewatch");
            watchService = path.getFileSystem().newWatchService();
            path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.println("Report any file changed within next 1 minutes...");

        WatchKey watchKey = null;

        while (true) {
            try {
                watchKey = watchService.poll(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (watchKey != null) {
                watchKey.pollEvents().stream().forEach(event ->
                        out.println(event.context()));
            }
        }

    }


}
