package lambdasinaction.chap3;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class ExecuteAround {

    private static final String FILE_PATH = "/Users/leo/IdeaProjects/java8/Java8InAction" +
            "/src/main/resources/lambdasinaction/chap3/data.txt";

    private static final BufferedReaderProcessor my_processor = ExecuteAround::processBufferedReader;

    public static void main(String... args) throws IOException {

        // method we want to refactor to make more flexible
        String result = processFileLimited();
        System.out.println(result);
        printSepLine();

        String oneLine = processFile((BufferedReaderProcessor) BufferedReader::readLine);
        System.out.println(oneLine);
        printSepLine();

        String twoLines = processFile((IOFunction<? super BufferedReader, ? extends String>) (BufferedReader b) -> b.readLine() + b.readLine());
        System.out.println(twoLines);
        printSepLine();

        String threeLines = processFile(my_processor);
        System.out.println(threeLines);
        printSepLine();

    }

    private static String processFileLimited() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            return br.readLine();
        }
    }


    private static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            return p.process(br);
        }

    }

    private static String processFile(IOFunction<? super BufferedReader, ? extends String> ioFunction) throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            return ioFunction.apply(br);
        }
    }

    public interface BufferedReaderProcessor {
        String process(BufferedReader b) throws IOException;
    }

    public interface IOFunction<T, R> {
        R apply(T t) throws IOException;
    }

    private static void printSepLine() {
        Stream.generate(() -> "-").limit(60).forEach(System.out::print);
        System.out.println();
    }

    private static String processBufferedReader(BufferedReader br) throws IOException {
        return br.readLine() + br.readLine() + br.readLine();
    }
}
