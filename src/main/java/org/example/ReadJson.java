package org.example;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadJson
{


    public ReadJson()
    {
    }

    public static Object document = null ;
    public static String readFile(String filePath) throws IOException
    {
        byte[] encoded = Files.readAllBytes(Paths.get(filePath));
        return new String(encoded);
    }

    public static Object AnalyseJson(String jsonContent)
    {
        document = Configuration.defaultConfiguration().jsonProvider().parse(jsonContent);
        return document ;
    }

    public static void YoungOld(Object object)
    {
        List<Integer> ages = JsonPath.read(object, "$.users[*].age");
        int minAge = ages.stream().mapToInt(Integer::intValue).min().orElse(-1);
        int maxAge = ages.stream().mapToInt(Integer::intValue).max().orElse(-1);

        JSONArray result = JsonPath.read(object, "$.users[?(@.age == " + minAge + ")].name") ;
        JSONArray result1 = JsonPath.read(object, "$.users[?(@.age == " + maxAge + ")].name");
        String youngestName = result.get(0).toString();
        String oldestName =result1.get(0).toString();
        System.out.println("Le plus jeune est " + youngestName + ",age de " + minAge + " ans.");
        System.out.println("Le plus vieux est " + oldestName + ",age de " + maxAge + " ans.");
    }

    public static void LongShort(Object object)
    {
        List<String> names = JsonPath.read(object, "$.users[*].name");
        String longestName = names.stream().max((name1, name2) -> Integer.compare(name1.length(), name2.length())).orElse("");
        String shortestName = names.stream().min((name1, name2) -> Integer.compare(name1.length(), name2.length())).orElse("");
        System.out.println("Le nom le plus long est " + longestName + " avec " + longestName.length() + " caractères).");
        System.out.println("Le nom le plus court est " + shortestName + " avec " + shortestName.length() + " caractères).");
    }

    public static void AverageAge(Object object)
    {
        List<Integer> ages = JsonPath.read(object, "$.users[*].age");
        double averageAge = ages.stream().mapToDouble(Integer::intValue).average().orElse(Double.NaN);
        System.out.println("La moyenne d'âge est " + averageAge + " ans.");
    }

    public static void BiggestDiff(Object object)
    {
        List<Integer> ages = JsonPath.read(object, "$.users[*].age");
        int maxAgeDiff = 0;
        String name1 = "";
        String name2 = "";
        for (int i = 1; i < ages.size(); i++)
        {
            int ageDiff = Math.abs(ages.get(i) - ages.get(i - 1));
            if (ageDiff > maxAgeDiff)
            {
                maxAgeDiff = ageDiff;
                name1 = JsonPath.read(document, "$.users[" + (i - 1) + "].name");
                name2 = JsonPath.read(document, "$.users[" + i + "].name");
            }
        }
        System.out.println("Le plus grand écart d'âge est entre " + name1 + " et " + name2 + " et c'est de " + maxAgeDiff + " ans .");

    }


}

