import org.w3c.dom.{Document, NodeList}

import java.io.{File, FileInputStream, IOException}
import javax.xml.parsers.{DocumentBuilder, DocumentBuilderFactory, ParserConfigurationException}
import javax.xml.xpath.{XPath, XPathConstants, XPathExpression, XPathExpressionException, XPathFactory}
import scala.collection.mutable.ListBuffer


case object ReadXml {

  def readFile(xmlFile: File): Document = {
    val factory: DocumentBuilderFactory = DocumentBuilderFactory.newInstance()
    val builder: DocumentBuilder = factory.newDocumentBuilder()
    builder.parse(new FileInputStream(xmlFile))
  }

  def Configure(): XPath = {
    XPathFactory.newInstance().newXPath()
  }

  def SearchPerson(xpath: XPath, document: Document): NodeList = {
    val expr: XPathExpression = xpath.compile("//person")
    expr.evaluate(document, XPathConstants.NODESET).asInstanceOf[org.w3c.dom.NodeList]
  }

  def SearchAge(xpath: XPath, document: Document): NodeList = {
    val expr: XPathExpression = xpath.compile("//age/text()")
    expr.evaluate(document, XPathConstants.NODESET).asInstanceOf[org.w3c.dom.NodeList]
  }

  def YoungOld(xpath: XPath, nodes: NodeList): Unit = {
    var minAge = Integer.MAX_VALUE
    var maxAge = 0
    var youngestName = ""
    var oldestName = ""

    for (i <- 0 until nodes.getLength) {
      val age: Int = xpath.compile("age").evaluate(nodes.item(i), XPathConstants.NUMBER).asInstanceOf[Double].toInt
      val name: String = xpath.compile("name").evaluate(nodes.item(i))
      if (age < minAge) {
        minAge = age
        youngestName = name
      } else if (age > maxAge) {
        maxAge = age
        oldestName = name
      }
    }
    println("Le plus jeune est " + youngestName + ",age de " + minAge + " ans.")
    println("Le plus vieux est " + oldestName + ",age de " + maxAge + " ans.")
  }

  def LongShort(xpath: XPath, nodes: NodeList): Unit = {
    var maxNameLength = Integer.MIN_VALUE
    var minNameLength = Integer.MAX_VALUE
    var longestName = ""
    var shortestName = ""
    for (i <- 0 until nodes.getLength) {
      val name: String = xpath.compile("name").evaluate(nodes.item(i))
      if (name.length > maxNameLength) {
        maxNameLength = name.length
        longestName = name
      } else if (name.length < minNameLength) {
        minNameLength = name.length
        shortestName = name
      }
    }
    println("Le nom le plus long est " + longestName + " avec " + longestName.length + " caractères.")
    println("Le nom le plus court est " + shortestName + " avec " + shortestName.length + " caractères.")
  }

  def AverageAge(xpath: XPath, nodes: NodeList): Unit = {
    var sommeAge = 0.0
    for (i <- 0 until nodes.getLength) {
      sommeAge += xpath.compile("age").evaluate(nodes.item(i)).toDouble
    }
    val moyenneAge = sommeAge / nodes.getLength
    println(s"La moyenne d'âge est $moyenneAge")
  }

  def BiggestDiff(xpath: XPath, ageNodes: NodeList, document: Document): Unit = {
    // Convertir la liste d'âges en une liste d'entiers
    val ages = new ListBuffer[Int]
    for (i <- 0 until ageNodes.getLength) {
      ages += ageNodes.item(i).getNodeValue.toInt
    }
    var maxAgeDiff = 0
    var name1 = ""
    var name2 = ""
    for (i <- 1 until ages.size) {
      val ageDiff = math.abs(ages(i) - ages(i - 1))
      if (ageDiff > maxAgeDiff) {
        maxAgeDiff = ageDiff
        //        name1 = xpath.evaluate("//person[" + i + "]/name/text()", document);
        name1 = xpath.evaluate(s"//person[$i]/name/text()", document)
        name2 = xpath.evaluate(s"//person[${i + 1}]/name/text()", document)
      }
    }
    println(s"Le plus grand écart d'âge est entre $name1 et $name2 et c'est de $maxAgeDiff ans.")
  }
}

