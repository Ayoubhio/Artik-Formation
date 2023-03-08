package org.example;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReadXml
{
    public ReadXml()
    {
    }
    public static Document readFile(File xmlFile) throws IOException, SAXException, ParserConfigurationException
    {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(new InputSource(new FileInputStream(xmlFile)));
        return document ;
    }
    public static XPath Configure() throws IOException, SAXException, ParserConfigurationException
    {
        XPath xpath = XPathFactory.newInstance().newXPath();
        return xpath ;
    }

    public static NodeList SearchPerson(XPath xpath, Document document) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("//person");
        NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        return nodes ;
    }

    public static NodeList SearchAge(XPath xpath, Document document) throws XPathExpressionException {
        XPathExpression expr = xpath.compile("//age/text()");
        NodeList nodes = (NodeList) expr.evaluate(document, XPathConstants.NODESET);
        return nodes ;
    }
    public static void YoungOld(XPath xpath , NodeList nodes) throws XPathExpressionException {
        int minAge = Integer.MAX_VALUE;
        int maxAge = 0;
        String youngestName = "";
        String oldestName ="";

        for (int i = 0; i < nodes.getLength(); i++)
        {
            int age = Integer.parseInt(xpath.compile("age").evaluate(nodes.item(i)));
            if (age < minAge)
            {
                minAge = age;
                youngestName = xpath.compile("name").evaluate(nodes.item(i));
            }
            else if (age > maxAge)
            {
                maxAge = age ;
                oldestName = xpath.compile("name").evaluate(nodes.item(i));
            }
        }
        System.out.println("Le plus jeune est " + youngestName + ",age de " + minAge + " ans.");
        System.out.println("Le plus vieux est " + oldestName + ",age de " + maxAge + " ans.");
    }

    public static void LongShort(XPath xpath , NodeList nodes) throws XPathExpressionException {
        int maxNameLength = Integer.MIN_VALUE;
        int minNameLength = Integer.MAX_VALUE;
        String longestName  = "";
        String shortestName = "";
        for (int i = 0; i < nodes.getLength(); i++)
        {
            String name = xpath.compile("name").evaluate(nodes.item(i));
            if (name.length() > maxNameLength)
            {
                maxNameLength = name.length();
                longestName = name;
            }
            else if (name.length() < minNameLength)
            {
                minNameLength = name.length();
                shortestName= name;
            }
        }
        System.out.println("Le nom le plus long est " + longestName + " avec " + longestName.length() + " caractères.");
        System.out.println("Le nom le plus court est " + shortestName + " avec " + shortestName.length() + " caractères.");
    }

    public static void AverageAge(XPath xpath , NodeList nodes) throws XPathExpressionException {
        double sommeAge = 0;
        for (int i = 0; i < nodes.getLength(); i++)
        {
            sommeAge += Double.parseDouble(xpath.compile("age").evaluate(nodes.item(i)));
        }
        double moyenneAge = sommeAge / nodes.getLength();
        System.out.println("La moyenne d'âge est " + moyenneAge);
    }

    public static void BiggestDiff(XPath xpath , NodeList ageNodes,Document document) throws XPathExpressionException {
       // Convertir la liste d'âges en une liste d'entiers
        List<Integer> ages = new ArrayList<>();
        for (int i = 0; i < ageNodes.getLength(); i++)
        {
            ages.add(Integer.parseInt(ageNodes.item(i).getNodeValue()));
        }
        int maxAgeDiff = 0;
        String name1 = "";
        String name2 = "";
        for (int i = 1; i < ages.size(); i++) {
            int ageDiff = Math.abs(ages.get(i) - ages.get(i - 1));
            if (ageDiff > maxAgeDiff)
            {
                maxAgeDiff = ageDiff;
                name1 = xpath.evaluate("//person[" + i + "]/name/text()", document);
                name2 = xpath.evaluate("//person[" + (i+1) + "]/name/text()", document);
            }
        }
        System.out.println("Le plus grand écart d'âge est entre " + name1 + " et " + name2 + " et c'est de " + maxAgeDiff + " ans.");
    }
}
