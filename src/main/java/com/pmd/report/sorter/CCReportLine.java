package com.pmd.report.sorter;

public class CCReportLine implements Comparable<CCReportLine> {
    private String filename;
    private String rule;
    private String line;
    private String type;
    private int cyclomaticComplexity;

    public CCReportLine() {

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String result) {
        this.line = result;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCyclomaticComplexity() {
        return cyclomaticComplexity;
    }

    public void setCyclomaticComplexity(int cyclomaticComplexity) {
        this.cyclomaticComplexity = cyclomaticComplexity;
    }

    public String toString() {
        return this.line;
    }

    @Override
    public int compareTo(CCReportLine o) {
        if (o.getCyclomaticComplexity() == this.cyclomaticComplexity)
            return 0;
        else
            return this.cyclomaticComplexity > o.getCyclomaticComplexity() ? -1 : 1;
    }
}
