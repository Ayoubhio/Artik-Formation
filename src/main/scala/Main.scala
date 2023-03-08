import org.w3c.dom.{Document, NodeList}

import java.io.File
import javax.xml.xpath.XPath

object Main
{
  def main(args: Array[String]): Unit =
  {
    val xmlFile = new File("X:\\Interne Artik\\04 - Formations internes\\01 - Stagiaire - Nouvel arrivé\\Exercices\\liste_noms_age.xml")
    println("************************** Partie de XML :*************************** ")
    val readXml = ReadXml

    // Charger le fichier XML// Charger le fichier XML
    val XmlDocument = readXml.readFile(xmlFile)

    // Configurer XPath et l'initialisation de l'objet
    val xpath = readXml.Configure()

    // Recherche des noeuds "person"
    val nodes = readXml.SearchPerson(xpath, XmlDocument)

    //Recherche des noeuds ages
    val ageNodes = readXml.SearchAge(xpath, XmlDocument)

    // Recherche du plus jeune et plus vieux
    readXml.YoungOld(xpath, nodes)

    // Trouver le nom le plus long et le plus court
    readXml.LongShort(xpath, nodes)

    // Calcul de la moyenne d'âge
    readXml.AverageAge(xpath, nodes)

    // Trouver le plus grand écart d'âge entre deux personnes successives
    readXml.BiggestDiff(xpath, ageNodes, XmlDocument)

  }
}