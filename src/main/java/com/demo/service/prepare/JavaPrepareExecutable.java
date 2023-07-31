package com.demo.service.prepare;

import com.demo.service.PrepareExecutable;
import com.demo.service.impl.TempFileImpl;
import com.demo.service.impl.WriteToFileImpl;

public class JavaPrepareExecutable implements PrepareExecutable {
    private String[] command = new String[4];
    public JavaPrepareExecutable() {
    }

    @Override
    public String[] prepare(String sourceCode, String directory) {

        TempFileImpl tempFile = new TempFileImpl();
        WriteToFileImpl writeFile = new WriteToFileImpl();

        if (!sourceCode.isEmpty() && sourceCode != null) {
            String fileName = "Main.java";
            tempFile.createFile(directory, fileName);
            writeFile.writeSourceCode(directory, fileName, sourceCode);
            String executableFile = "Main";
            String sourceCodeFile = directory + fileName;
            command[1] = sourceCodeFile;
            command[3] = executableFile;
            return command;
        }
        return null;
    }
}
