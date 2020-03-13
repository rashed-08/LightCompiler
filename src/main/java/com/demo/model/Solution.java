package com.demo.model;

public class Solution {

    private String solutionName;
    private String solutionSourceCode;
    private String language;

    public Solution() {
        // TODO Auto-generated constructor stub
    }

    public Solution(String solutionName, String solutionSourceCode, String language) {
        this.solutionName = solutionName;
        this.solutionSourceCode = solutionSourceCode;
        this.language = language;
    }

    public String getSolutionName() {
        return solutionName;
    }

    public void setSolutionName(String solutionName) {
        this.solutionName = solutionName;
    }

    public String getSolutionSourceCode() {
        return solutionSourceCode;
    }

    public void setSolutionSourceCode(String solutionSourceCode) {
        this.solutionSourceCode = solutionSourceCode;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Solution [solutionName=" + solutionName + ", solutionSourceCode=" + solutionSourceCode + ", language="
                + language + "]";
    }

}
