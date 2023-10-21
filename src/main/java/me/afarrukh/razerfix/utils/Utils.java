package me.afarrukh.razerfix.utils;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class Utils {
    public static List<Node> getChildNodes(Node parent) {
        List<Node> children = new ArrayList<>();
        NodeList childNodes = parent.getChildNodes();
        int length = childNodes.getLength();
        for (int i = 0; i < length; i++) {
            children.add(childNodes.item(i));
        }
        return children;
    }

    public static List<Node> nodeListToList(NodeList nodeList) {
        List<Node> items = new ArrayList<>();
        for (int i = 0; i < nodeList.getLength(); i++) {
            items.add(nodeList.item(i));
        }
        return items;
    }
}
