package modules;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ReaderWriter implements Runnable{

    private Finder finder;
    private String currentFileName;
    private String currentFilePath;
    private String targetFilePath;
    private String resultFilePath;
    private final Pattern IP_ADDRES_MASK = Pattern.compile("((25[0-5]|(2[0-4]|1\\d|[1-9]|)\\d)\\.?\\b){4}");

    public ReaderWriter(Finder finder, String currentFileName) {
        this.finder = finder;
        this.currentFileName = currentFileName;
        this.currentFilePath = finder.getPATH_TO_FILES() + "/" + currentFileName;
        this.targetFilePath = finder.getTARGET_PATH() + "/" + currentFileName;
        this.resultFilePath = finder.getRESULT_PATH() + "/" + currentFileName;
    }

    public void run() {

        HashMap<String, Integer> findedValues = new HashMap<>();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(currentFilePath));
            while (reader.ready()) {
                Matcher matcher = IP_ADDRES_MASK.matcher(reader.readLine());
                if (matcher.find()) {
                    String findeValue = matcher.group();
                    if (findedValues.containsKey(findeValue)) {
                        int currentCount = findedValues.get(findeValue);
                        findedValues.put(findeValue, ++currentCount);
                    }else {
                        findedValues.put(findeValue, 1);
                    }
                }
            }

            reader.close();
        }catch (FileNotFoundException exp){
            exp.printStackTrace();
        }catch (IOException exp){
            exp.printStackTrace();
        }

        if(findedValues.size() > 0){
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(resultFilePath));
                for (Map.Entry<String, Integer> entry:findedValues.entrySet()) {
                    writer.write("IP_ADDRES: " + entry.getKey() + " count: " + entry.getValue() + "\n");
                }

                writer.close();

                Files.move(Paths.get(currentFilePath), Paths.get(targetFilePath));

            }catch (IOException exp){
                exp.printStackTrace();
            }
        }

    }
}
