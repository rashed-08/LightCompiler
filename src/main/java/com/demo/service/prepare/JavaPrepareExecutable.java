package com.demo.service.prepare;

import com.demo.service.PrepareExecutable;
import com.demo.service.impl.TempFileImpl;
import com.demo.service.impl.WriteToFileImpl;
import com.demo.utils.FileNameGenerator;

import java.io.File;

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
            String executableFile = "Main" + name;
            String sourceCodeFile = directory + File.separator + fileName;
            command[1] = sourceCodeFile;
            command[3] = executableFile;
            return command;
        }
        return null;
    }
}
