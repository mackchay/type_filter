package ru.haskov;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class TypeFilter {
    private final Logger logger = LoggerFactory.getLogger(TypeFilter.class);
    private final Configuration conf;

    public TypeFilter(Configuration conf) {
        this.conf = conf;
    }

    public void doFilter() {
        List<String> stringList = new ArrayList<>();
        List<Integer> integerList = new ArrayList<>();
        List<Double> doubleList = new ArrayList<>();

        for (String file : conf.getFileInList()) {
            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
                while (br.ready()) {
                    String line = br.readLine();
                    if (isInteger(line)) {
                        integerList.add(Integer.parseInt(line));
                    } else if (isDouble(line)) {
                        doubleList.add(Double.parseDouble(line));
                    } else {
                        stringList.add(line);
                    }
                }
            } catch (FileNotFoundException e) {
                logger.warn(e.getMessage());
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        if (conf.getIsShortStatisticReq() && !conf.getIsLongStatisticReq()) {
            System.out.println("Number of strings: " + stringList.size());
            System.out.println("Number of integers: " + integerList.size());
            System.out.println("Number of floats: " + doubleList.size());
        }

        if (conf.getIsLongStatisticReq()) {
            System.out.println("Number of strings: " + stringList.size());
            System.out.println("Size of shortest string: " + stringList.stream().min(String::compareTo).orElse("").length());
            System.out.println("Size of longest string: " + stringList.stream().max(String::compareTo).orElse("").length());
            System.out.println();
            System.out.println("Number of integers: " + integerList.size());
            System.out.println("Min integer: " + integerList.stream().min(Integer::compareTo).orElse(null));
            System.out.println("Max integer: " + integerList.stream().max(Integer::compareTo).orElse(null));
            System.out.println("Integer sum: " + integerList.stream().mapToInt(Integer::intValue).sum());
            System.out.println("Integer average: " + integerList.stream().mapToInt(Integer::intValue).average().orElse(0));
            System.out.println();
            System.out.println("Number of floats: " + doubleList.size());
            System.out.println("Min float: " + doubleList.stream().min(Double::compareTo).orElse(null));
            System.out.println("Max float: " + doubleList.stream().max(Double::compareTo).orElse(null));
            System.out.println("Float sum: " + doubleList.stream().mapToDouble(Double::doubleValue).sum());
            System.out.println("Float average: " + doubleList.stream().mapToDouble(Double::doubleValue).average().orElse(0));
        }

        if (!stringList.isEmpty()) writeInFile(conf.getStringPath(), stringList);
        if (!integerList.isEmpty()) writeInFile(conf.getIntPath(), integerList.stream().map(Object::toString).toList());
        if (!doubleList.isEmpty()) writeInFile(conf.getFloatPath(), doubleList.stream().map(Object::toString).toList());
    }

    private void writeInFile(Path path, List<String> stringList) {
        try (BufferedWriter bw = new BufferedWriter(
                new OutputStreamWriter(new FileOutputStream(path.toFile(), conf.getIsAppendReq()),
                        StandardCharsets.UTF_8))) {
        for (String string : stringList) {
                bw.write(string);
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
