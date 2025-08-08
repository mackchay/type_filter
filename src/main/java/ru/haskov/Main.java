package ru.haskov;

public class Main {
    public static void main(String[] args) {
        Cmd cmd = new Cmd();
        Configuration conf = cmd.parse(args);
        TypeFilter filter = new TypeFilter(conf);
        filter.doFilter();
    }
}