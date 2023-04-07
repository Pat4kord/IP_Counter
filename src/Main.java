import modules.Finder;
import modules.Executor;

public class Main {

    public static void main(String[] args) {

        Finder finder = new Finder();
        if (finder.createProcessingStructure()){
            Executor.readByMask(finder);
        }

    }
}