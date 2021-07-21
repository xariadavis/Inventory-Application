package ucf.assignments;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileManagement {
    public void listToHTML(String outputPath, ArrayList<Item> theList) {

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

        outputFile.println("<head><style>" +
                "table {\n" +
                "\tborder-collapse: collapse;\n" +
                "}\n" +
                "\n" +
                "table th {\n" +
                "\ttext-align: left;\n" +
                "\tbackground-color: purple;\n" +
                "\tcolor: #FFF;\n" +
                "\tpadding: 4px 30px 4px 8px;\n" +
                "}\n" +
                "\n" +
                "table td {\n" +
                "\tborder: 1px solid purple;\n" +
                "\tpadding: 4px 8px;\n" +
                "}\n" +
                "\n" +
                "table tr:nth-child(odd) td{\n" +
                "\tbackground-color: pink;\n" +
                "}</style></head>");
        outputFile.println("<h1>Inventory Table</h1>");
        outputFile.println("<table>");
        outputFile.println("<tr>");
        outputFile.println("<th>Values($)</th> <th>Serial Number</th> <th>Name</th>");

        outputFile.println("</tr>");

        for (int i = 0; i < theList.size(); i++) {
            double currValue = theList.get(i).getValue();
            outputFile.println("<tr>");
            outputFile.println("<td>" + currValue + "</td>");
            outputFile.println("<td>" + theList.get(i).getSerialNumber() + "</td>");
            outputFile.println("<td>" + theList.get(i).getName() + "</td>");
            outputFile.println("</tr>");
        }

        outputFile.println("</table>");
        outputFile.println("</html>");
    }
}
