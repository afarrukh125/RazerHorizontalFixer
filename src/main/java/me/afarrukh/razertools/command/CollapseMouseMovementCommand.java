package me.afarrukh.razertools.command;

import com.github.rvesse.airline.annotations.Command;
import me.afarrukh.razertools.utils.NodeUtils;
import org.w3c.dom.Document;

@Command(name = "collapse-mouse-movement")
public class CollapseMouseMovementCommand extends AbstractParseAndRewriteCommand {
    @Override
    public void execute(Document document) {
        var mouseMovementNodes = document.getElementsByTagName("MouseMovement");
        for (var node : NodeUtils.nodeListToList(mouseMovementNodes)) {
            var mouseMovementEventNodes = NodeUtils.getChildNodes(node);
            mouseMovementEventNodes.stream()
                    .limit(mouseMovementEventNodes.size() - 2)
                    .forEach(childNode -> childNode.getParentNode().removeChild(childNode));
        }
    }
}
