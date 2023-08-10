package com.demo.service.prepare;

import com.demo.service.PrepareExecutable;
import com.demo.service.impl.TempFileImpl;
import com.demo.service.impl.WriteToFileImpl;
import com.demo.utils.FileNameGenerator;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JavaPrepareExecutable implements PrepareExecutable {
    private String[] command = new String[4];

    @Override
    public String[] prepare(String sourceCode, String directory) {

        TempFileImpl tempFile = new TempFileImpl();
        WriteToFileImpl writeFile = new WriteToFileImpl();

        if (!sourceCode.isEmpty() && sourceCode != null) {
            String name = FileNameGenerator.GENERATOR.getFileName();
            String fileName = "Main" + name + ".java";
            tempFile.createFile(directory, fileName);
            writeFile.writeSourceCode(directory, fileName, sourceCode);
            String className = getClassName(sourceCode);
            String executableFile = className;
            String sourceCodeFile = directory + File.separator + fileName;
            command[1] = sourceCodeFile;
            command[3] = directory + File.separator + executableFile;
            return command;
        }
        return null;
    }

    public String getClassName(String sourceCode) {
        String regex = "\\s*(public|private)?\\s*class\\s+(\\w+)\\s*";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(sourceCode);
        String className = "";
        String isPublic = "";
        if (matcher.find()) {
            isPublic = matcher.group(0);
            className = matcher.group(2);
        }
        if (isPublic.contains("public")) className = "";
        return className;
    }
}
