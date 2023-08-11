package com.demo.service.prepare;

import com.demo.service.PrepareExecutable;

public class PrepareExecutableFile {
    private PrepareExecutable prepareExecutable;

    public void setPrepareExecutable(PrepareExecutable prepareExecutable) {
        this.prepareExecutable = prepareExecutable;
    }

    public String[] prepare(String sourceCode, String directory) {
        return prepareExecutable.prepare(sourceCode, directory);
    }
}
