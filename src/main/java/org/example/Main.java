package org.example;

import org.slf4j.*;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import javax.xml.xpath.*;
import org.w3c.dom.*;

import java.io.InputStream;

import java.util.Properties;


public class Main
{
      private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException, XPathExpressionException
    {
        // Acces aux proprietes
        Properties props = new Properties();
        InputStream is = Main.class.getClassLoader().getResourceAsStream("application.properties");
        props.load(is);
        File xmlFile = new File(props.getProperty("xml_path"));
        String JsonFile = props.getProperty("json_path");

        LOGGER.warn("************************** Partie de XML :*************************** ");

        ReadXml readXml = new ReadXml();
       // Charger le fichier XML
        Document XmlDocument = readXml.readFile(xmlFile);

        // Configurer XPath et l'initialisation de l'objet
        XPath xpath = readXml.Configure();

        // Recherche des noeuds "person"
        NodeList nodes = readXml.SearchPerson(xpath , XmlDocument);

        //Recherche des noeuds ages
        NodeList ageNodes = readXml.SearchAge(xpath , XmlDocument);

        // Recherche du plus jeune et plus vieux
        readXml.YoungOld(xpath ,nodes);

        // Trouver le nom le plus long et le plus court
        readXml.LongShort(xpath , nodes);

        // Calcul de la moyenne d'âge
        readXml.AverageAge(xpath , nodes);

        // Trouver le plus grand écart d'âge entre deux personnes successives
        readXml.BiggestDiff(xpath,ageNodes,XmlDocument);

        LOGGER.info("************************** Partie de Json :*************************** ");
        // Lire le contenu du fichier JSON
        String jsonContent = null;
        ReadJson readJson = new ReadJson();
        try {jsonContent = readJson.readFile(JsonFile);}
        catch (IOException e) {throw new RuntimeException(e);}

        // Analyser le contenu du fichier JSON avec jsonpath
        Object JsonDocument = readJson.AnalyseJson(jsonContent);

        // Trouver le plus jeune et le plus vieux

        readJson.YoungOld(JsonDocument);

        // Trouver le nom le plus long et le plus court
        readJson.LongShort(JsonDocument);

        // Calculer la moyenne d'âge
        readJson.AverageAge(JsonDocument);

        // Trouver le plus grand écart d'âge entre deux personnes successives
        readJson.BiggestDiff(JsonDocument);
    }
}