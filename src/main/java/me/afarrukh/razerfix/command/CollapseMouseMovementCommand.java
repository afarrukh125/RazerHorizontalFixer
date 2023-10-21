package me.afarrukh.razerfix.command;

import com.github.rvesse.airline.annotations.Command;
import me.afarrukh.razerfix.utils.Utils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.List;

@Command(name = "collapse-mouse-movement")
public class CollapseMouseMovementCommand extends AbstractParseAndRewriteCommand {
    @Override
    public void execute(Document document) {
        NodeList mouseMovementNodes = document.getElementsByTagName("MouseMovement");
        for (Node node : Utils.nodeListToList(mouseMovementNodes)) {
            List<Node> mouseMovementEventNodes = Utils.getChildNodes(node);
            mouseMovementEventNodes.stream()
                    .limit(mouseMovementEventNodes.size() - 2)
                    .forEach(childNode -> childNode.getParentNode().removeChild(childNode));
        }
    }
}
