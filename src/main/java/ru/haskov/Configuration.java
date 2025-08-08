package ru.haskov;

import lombok.Getter;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class Configuration {
    private Path stringPath = Path.of("strings.txt");
    private Path intPath = Path.of("integers.txt");
    private Path floatPath = Path.of("floats.txt");
    private final Boolean isShortStatisticReq;
    private final Boolean isLongStatisticReq;
    private final Boolean isAppendReq;
    private final List<String> fileInList = new ArrayList<>();

    public Configuration(String path, String prefix,
                         Boolean isShortStatisticReq, Boolean isLongStatisticReq,
                         Boolean isAppendReq,
                         String[] files) {
        stringPath = Paths.get(path, prefix + stringPath);
        intPath = Paths.get(path, prefix + intPath);
        floatPath = Paths.get(path, prefix + floatPath);
        this.isShortStatisticReq = isShortStatisticReq;
        this.isLongStatisticReq = isLongStatisticReq;
        this.isAppendReq = isAppendReq;
        fileInList.addAll(Arrays.asList(files));
    }
}
