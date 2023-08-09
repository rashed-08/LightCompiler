package com.demo.service;

import java.util.ArrayList;

public interface CodeExecute {
    ArrayList<String> codeExecute(String executableFile, String language, String STDIN);
}
