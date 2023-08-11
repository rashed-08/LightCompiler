package com.demo.service.prepare;

import com.demo.service.PrepareExecutable;
import com.demo.service.impl.TempFileImpl;
import com.demo.service.impl.WriteToFileImpl;
import com.demo.utils.FileNameGenerator;

import java.io.File;

public class CPrepareExecutable implements PrepareExecutable {
    private String[] command = new String[4];

    @Override
    public String[] prepare(String sourceCode, String directory) {
        TempFileImpl tempFile = new TempFileImpl();
        WriteToFileImpl writeFile = new WriteToFileImpl();

        if (!sourceCode.isEmpty() && sourceCode != null) {
            String name = FileNameGenerator.GENERATOR.getFileName();
            String fileName = "filename" + name + ".c";
            tempFile.createFile(directory, fileName);
            writeFile.writeSourceCode(directory, fileName, sourceCode);

            String sourceCodeFile = directory + File.separator + fileName;
            String executableFile = directory + File.separator + "filename" + name;

            command[0] = "gcc";
            command[1] = sourceCodeFile;
            command[2] = "-o";
            command[3] = executableFile;
            return command;
        }
        return null;
    }
}
