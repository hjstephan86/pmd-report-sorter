package com.pmd.report.sorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

public class App {

    public static void main(String[] args) {
        ArrayList<CCReportLine> ccReportLines = new ArrayList<CCReportLine>();
        String pmdReportFilename = "/home/stephan/Git/consorsbank-parser/cc-report.txt";
        if (args.length == 1 && args[0].endsWith(".txt")) {
            pmdReportFilename = args[0];
        }
        parsePmdReportFile(ccReportLines, pmdReportFilename);
        Collections.sort(ccReportLines);
        String pmdReportSortedFilename = pmdReportFilename.replace(".txt", "-sorted.txt");
        generatePmdReportSortedFile(ccReportLines, pmdReportSortedFilename);
    }

    private static void parsePmdReportFile(ArrayList<CCReportLine> ccReportLines, String pmdReportFilename) {
        File pmdReportFile = new File(pmdReportFilename);
        if (pmdReportFile.exists()) {
            try (BufferedReader br = new BufferedReader(new FileReader(pmdReportFile))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] splittedLine = line.split(":");
                    CCReportLine ccReportLine = new CCReportLine();
                    ccReportLine.setFilename(splittedLine[0]);
                    ccReportLine.setRule(splittedLine[2].replace("\t", ""));
                    ccReportLine.setLine(line.replaceAll("\t", " "));
                    String result = splittedLine[3].replace("\t", "");
                    String[] resultSplit = result.split(" ");
                    ccReportLine.setType(resultSplit[1]);
                    parseCyclomaticComplexity(ccReportLine, resultSplit);
                    ccReportLines.add(ccReportLine);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void parseCyclomaticComplexity(CCReportLine ccReportLine, String[] resultSplit) {
        int cyclomaticComplexity = 0;
        if (ccReportLine.getType().equals("class")) {
            cyclomaticComplexity = Integer.parseInt(resultSplit[resultSplit.length - 3]);
        } else {
            // Treat types like "method" and "constructor"
            for (int i = 0; i < resultSplit[resultSplit.length - 1].length() - 1; i++) {
                try {
                    cyclomaticComplexity = Integer.parseInt(resultSplit[resultSplit.length - 1].substring(0, 1 + i));
                } catch (Exception e) {
                    break;
                }
            }
        }
        ccReportLine.setCyclomaticComplexity(cyclomaticComplexity);
    }

    private static void generatePmdReportSortedFile(ArrayList<CCReportLine> ccReportLines,
            String pmdReportSortedFilename) {
        StringBuilder stringBuilder = new StringBuilder();
        for (CCReportLine ccReportLine : ccReportLines) {
            stringBuilder.append(ccReportLine.toString() + "\n");
        }
        try {
            FileWriter writer = new FileWriter(pmdReportSortedFilename);
            writer.write(stringBuilder.toString());
            writer.close();
            System.out.println(
                    "Successfully generated sorted report to " + pmdReportSortedFilename + ".");
        } catch (IOException e) {
            System.out.println(
                    "An error occurred while writing to " + pmdReportSortedFilename + ".");
            e.printStackTrace();
        }
    }
}
