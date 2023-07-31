package com.demo.service.prepare;

import com.demo.service.PrepareExecutable;
import com.demo.service.impl.TempFileImpl;
import com.demo.service.impl.WriteToFileImpl;

public class PrepareExecutableFile {
    private PrepareExecutable prepareExecutable;
    private TempFileImpl tempFile;
    private WriteToFileImpl writeFile;

    public void setPrepareExecutable(PrepareExecutable prepareExecutable) {
        this.prepareExecutable = prepareExecutable;
    }

    public String[] prepare(String sourceCode, String directory) {
        return prepareExecutable.prepare(sourceCode, directory);
    }
}
