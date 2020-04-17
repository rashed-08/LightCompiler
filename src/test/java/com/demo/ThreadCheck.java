package com.demo;

import java.io.IOException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ThreadCheck {

    @Test
    public void threadKill() throws IOException, InterruptedException {
        ProcessBuilder builder = new ProcessBuilder("chmod","+x","/home/romantic-coder/","hello");
        Process process = builder.start();
        while (true) {
            long start = System.currentTimeMillis();
            System.out.println("The start time: " + start);
            Thread.sleep(6001);
            if ((System.currentTimeMillis() - start) >= 6000) {
                System.out.println("The end time: " + System.currentTimeMillis());
                process.destroy();
                Thread.interrupted();
                break;
            }
        }
    }
}
