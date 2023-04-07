package modules;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executor {

    public static void readByMask(Finder finder){
        Set<String> filesForProcessing = finder.getFiles();
        for (String currentFilePath : filesForProcessing) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            executorService.submit(new ReaderWriter(finder, currentFilePath));
            try {
                executorService.shutdown();
                executorService.awaitTermination(1, TimeUnit.DAYS);
            }catch (InterruptedException exp){
                exp.printStackTrace();
            }
        }
    }
    
}
