package com.company.app;

import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.*;
import java.io.*;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by xk on 08.04.17.
 */
public class XmlDomObj {
    private Document document;
    private NodeList nodeList;

    private void trimWhitespace(Node node) {
        NodeList children = node.getChildNodes();
        for (int i = 0; i < children.getLength(); ++i) {
            Node child = children.item(i);
            if (child.getNodeType() == Node.TEXT_NODE) {
                child.setTextContent(child.getTextContent().trim());
            }
            trimWhitespace(child);
        }
    }

    XmlDomObj(String path) {
        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            document = documentBuilder.parse(path);
            nodeList = document.getDocumentElement().getChildNodes();
            trimWhitespace(nodeList.item(0).getParentNode());


        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    /*
    * What is actually going on is trying to append an element to document.
    * @tags is two- or one-valued array of Strings, where
    * @tag[0] is the name of element which is appended,
    * @tag[1] is the name of parent node, and
    * @tag[1] is separated pair of parent's attribute name and its value.
    * @attrs are varargs which contain separated value of appending element attributes.
    * */

    private String[] spl (String s) {
        return s.split("=");
    }

    public void addNode(String[] tags, String ... attrs) {
        Element dataTag = document.getDocumentElement();

        ArrayList<String> attrsNames = new ArrayList<String>();
        ArrayList<String> attrsValues = new ArrayList<String>();
        for (String a : attrs) {
            String[] s = spl(a);
            attrsNames.add(s[0]);
            attrsValues.add(s[1]);
        }

        Element parent = dataTag;
        String elemName = tags[0];
        if (tags.length == 3) {
            elemName = tags[0];
            String[] parentsNameNValue = spl(tags[2]);

            NodeList nl = document.getElementsByTagName(parentsNameNValue[1]);
            for (int i = 0; i < nl.getLength(); i++) {
                Element e = (Element) nl.item(i);
                if (e.hasAttribute(parentsNameNValue[1])) {
                    parent = e;
                }
            }
        }
        Element child = document.createElement(elemName);
        for (int i = 0; i < attrsNames.size(); i++) {
            child.setAttribute(attrsNames.get(i), attrsValues.get(i));
        }

        parent.appendChild(child);
        nodeList = document.getDocumentElement().getChildNodes();
    }


    /*
    * NEVER try to understand what's going in there
    **/

    public ArrayList<Double> getDistr() {
        ArrayList<Double> res = new ArrayList<Double>(0);
        NodeList nl = document.getElementsByTagName("d");
        for (int i = 0; i < nl.getLength(); i++) {
            Node e = nl.item(i).getFirstChild();
            res.add(Double.parseDouble(e.getTextContent()));
        }
        return res;
    }

    public ArrayList<String[]> getCombs() {
        ArrayList<String[]> res = new ArrayList<>(0);
        NodeList nl = document.getElementsByTagName("Comb");
        for (int i = 0; i < nl.getLength(); i++) {
            if (nl.item(i).getNodeType() != Node.TEXT_NODE) {
                NodeList nested = nl.item(i).getChildNodes();
                String array[] = new String[4];
                for (int j = 0, k = 0; j < nested.getLength(); j++) {
                    if (nested.item(j).getNodeType() != Node.TEXT_NODE) {
                        Node e = nested.item(j).getFirstChild();
                        array[k] = e.getTextContent();
                        k++;
                    }
                }
                Element e = (Element)nl.item(i);
                array[3] = e.getAttribute("Value");
                res.add(array);
            }
        }
        return res;
    }
}