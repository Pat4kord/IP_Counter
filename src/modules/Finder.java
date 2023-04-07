package modules;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Finder {

    private String TARGET_PATH = "";
    private String RESULT_PATH = "";
    private final String PATH_TO_FILES = "src/files/target/";
    private Set<String> files;

    public String getTARGET_PATH() {
        return TARGET_PATH;
    }
    public String getRESULT_PATH() {
        return RESULT_PATH;
    }
    public String getPATH_TO_FILES() {
        return PATH_TO_FILES;
    }

    public Set<String> getFiles() {
        return files;
    }

    public Finder(){
    }

    public boolean searchFiles(){
        this.files = Stream.of(new File(PATH_TO_FILES).listFiles())
                .filter(file -> !file.isDirectory())
                .map(File::getName).collect(Collectors.toSet());

        return files.size() > 0;
    }

    private boolean createDirectory(String filePath){
        File directory = new File(filePath);

        if(directory.isDirectory()){
            if(!directory.canRead()){
                directory.setReadable(true);
            }

            if (!directory.canWrite()){
                directory.setWritable(true);
            }

            return true;
        }else {
            if(directory.mkdir()){
                return true;
            }
        }

        return false;
    }

    public boolean createProcessingStructure(){
        if (!this.searchFiles()){
            return false;
        }

        TARGET_PATH = "src/files/target/" + DateTimeFormatter.ofPattern("yyyyMMddHHmm").format(LocalDateTime.now());
        RESULT_PATH = "src/files/result/" + DateTimeFormatter.ofPattern("yyyyMMddHHmm").format(LocalDateTime.now());

        if (createDirectory(TARGET_PATH) && createDirectory(RESULT_PATH)){
            return true;
        }

        return false;
    }


}
