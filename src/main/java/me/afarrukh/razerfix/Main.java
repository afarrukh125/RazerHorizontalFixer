package me.afarrukh.razerfix;

import com.github.rvesse.airline.Cli;
import com.github.rvesse.airline.builder.CliBuilder;

public class Main {

    public static void main(String[] args) {
        Cli<Runnable> cli = new CliBuilder<Runnable>("parser")
                .withCommand(ParseXMLCommand.class)
                .build();
        cli.parse(args).run();
    }
}
