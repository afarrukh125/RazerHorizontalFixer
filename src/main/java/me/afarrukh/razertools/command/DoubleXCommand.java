package me.afarrukh.razertools.command;

import com.github.rvesse.airline.annotations.Command;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

@Command(name = "doublex")
public class DoubleXCommand extends AbstractParseAndRewriteCommand {
    @Override
    public void execute(Document document) {
        NodeList xNodes = document.getElementsByTagName("X");
        for (int i = 0; i < xNodes.getLength(); i++) {
            Node targetNode = xNodes.item(i).getChildNodes().item(0);
            int currentValue = Integer.parseInt(targetNode.getNodeValue());
            targetNode.setNodeValue(String.valueOf(currentValue * 2));
        }
    }
}
