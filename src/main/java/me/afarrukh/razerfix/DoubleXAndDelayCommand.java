package me.afarrukh.razerfix;

import com.github.rvesse.airline.annotations.Command;
import com.github.rvesse.airline.annotations.Option;
import org.w3c.dom.Document;

@Command(name = "doublexdelay")
public class DoubleXAndDelayCommand extends AbstractParseAndRewriteCommand {

    @Override
    public void execute(Document document) {
        new DoubleXCommand().execute(document);
        new RemoveMouseMovementDelayCommand().execute(document);
    }
}
