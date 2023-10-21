package me.afarrukh.razerfix;

import com.github.rvesse.airline.Cli;
import com.github.rvesse.airline.builder.CliBuilder;
import me.afarrukh.razerfix.command.CollapseMouseMovementCommand;
import me.afarrukh.razerfix.command.FullCleanupCommand;
import me.afarrukh.razerfix.command.DoubleXCommand;
import me.afarrukh.razerfix.command.RemoveMouseMovementDelayCommand;

public class Main {

    public static void main(String[] args) {
        Cli<Runnable> cli = new CliBuilder<Runnable>("parser")
                .withCommand(DoubleXCommand.class)
                .withCommand(RemoveMouseMovementDelayCommand.class)
                .withCommand(FullCleanupCommand.class)
                .withCommand(CollapseMouseMovementCommand.class)
                .build();
        cli.parse(args).run();
    }
}
