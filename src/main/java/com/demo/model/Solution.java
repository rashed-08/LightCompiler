package com.demo.model;

public class Solution {

    private String solutionSourceCode;
    private String stdin;
    private String language;

    public Solution() {
        
    }

    public Solution(String solutionSourceCode, String stdin, String language) {
        this.solutionSourceCode = solutionSourceCode;
        this.stdin = stdin;
        this.language = language;
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

    public String getStdin() {
        return stdin;
    }

    public void setStdin(String stdin) {
        this.stdin = stdin;
    }

    @Override
    public String toString() {
        return "Solution [solutionName=solutionSourceCode=" + solutionSourceCode + ", stdin=" + stdin + ", language="
                + language + "]";
    }

}
