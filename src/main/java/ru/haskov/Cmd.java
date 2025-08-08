package ru.haskov;

import org.apache.commons.cli.*;

public class Cmd {
    private final CommandLineParser parser = new DefaultParser();
    private final Options options;

    public Cmd() {
        options = new Options();
        options.addOption("h", "help", false, "print this message");
        options.addOption("p", "prefix", true, "prefix");
        options.addOption("o", "output", true, "output path");
        options.addOption("s", "short statistics", false, "short statistics");
        options.addOption("f", "full statistics", false, "full statistics");
        options.addOption("a", "append", false, "append in existing files");
    }

    public Configuration parse(String[] args) {
        try {
            CommandLine line = parser.parse(options, args);
            if (line.hasOption("h")) {
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("cmd", options);
                System.exit(0);
            }

            String prefix = line.getOptionValue("p", "");
            String outputPath = line.getOptionValue("o", "");
            boolean shortStatistics = line.hasOption("s");
            boolean fullStatistics = line.hasOption("f");
            boolean append = line.hasOption("a");

            String[] remains = line.getArgs();
            return new Configuration(
                    outputPath,
                    prefix,
                    shortStatistics,
                    fullStatistics,
                    append,
                    remains
            );
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
