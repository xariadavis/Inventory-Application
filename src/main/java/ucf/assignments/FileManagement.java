/*
 *  UCF COP3330 Summer 2021 Assignment 5 Solution
 *  Copyright 2021 Xaria Davis
 */

package ucf.assignments;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;


import java.util.ArrayList;
import java.util.List;

public class FileManagement {
    String title;
    Inventory inventory = new Inventory(title);

    public void listToHTML(String outputPath, ArrayList<Item> theList, String title) {

        File output = new File(outputPath);
        PrintStream outputFile = null;
        try {
            outputFile = new PrintStream(output);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        if (outputFile == null) {
            outputFile.println("<html><h1>LIST IS EMPTY</h1></html>");
        }

        outputFile.println("""
                <head><style>table {
                \tborder-collapse: collapse;
                }

                table th {
                \ttext-align: left;
                \tbackground-color: purple;
                \tcolor: #FFF;
                \tpadding: 4px 30px 4px 8px;
                }

                table td {
                \tborder: 1px solid purple;
                \tpadding: 4px 8px;
                }

                table tr:nth-child(odd) td{
                \tbackground-color: #B1A2CA;
                }</style></head>""");
        outputFile.format("<h1>%s</h1>", title);
        outputFile.println("<table>");
        outputFile.println("<tr>");
        outputFile.println("<th>Values($)</th> <th>Serial Number</th> <th>Name</th>");

        outputFile.println("</tr>");

        for (Item item : theList) {
            String currValue = item.getValue();
            outputFile.println("<tr>");
            outputFile.println("<td>" + currValue +  "</td>");
            outputFile.println("<td>" + item.getSerialNumber() + "</td>");
            outputFile.println("<td>" + item.getName() + "</td>");
            outputFile.println("</tr>");
        }

        outputFile.println("</table>");
        outputFile.println("</html>");
    }

    public void HTMLtoList(String output, ArrayList<Item> theList) {
        File input = new File(output);
        Document doc = null;
        try {
            doc = Jsoup.parse(input, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element table = doc.select("table").get(0);
        Elements rows = table.select("tr");

        for (int i = 1; i < rows.size(); i++) {
            Element row = rows.get(i);
            Elements cols = row.select("td");
            Item item = new Item(cols.toString().replace("<td>", "").replaceAll("\n", ""), "</td>");
            theList.add(item);
        }
    }

    public void listToTXT(String output, ArrayList<Item> theList) {

        List<String> test = new ArrayList<>();

        for (Item item : theList) {
            test.add(item.toString());
        }

        String collect = String.join("\n", test);

        FileWriter writer;
        try {
            writer = new FileWriter(output);
            writer.write(collect);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void TXTtoList(String inputPath, ArrayList<Item> theList) {

        BufferedReader br = null;

        try {
            String input;
            br = new BufferedReader(new FileReader(inputPath));

            while ((input = br.readLine()) != null) {
                rawArray(input, theList);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void rawArray (String data, ArrayList<Item> theList) {
        if (data != null) {
            String[] splitData = data.split("\\s*\n\\s*");
            for (String splitInfo : splitData) {
                if (!(splitInfo == null) || !(splitInfo.length() == 0)) {
                    Item item = new Item(data, "\\t");
                    theList.add(item);
                }
            }
        }
    }
}
