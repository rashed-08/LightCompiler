package com.demo.service.impl;

import com.demo.model.Solution;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ControllerServiceImplTest {

    @Mock
    private TempFileImpl tempFile;
    @InjectMocks
    private CodeCompileImpl codeCompile;

    @Mock
    private CodeCompileImpl serviceCodeCompiler;

    @InjectMocks
    private ControllerServiceImpl controllerService;

    @Test
    @DisplayName("Compile C Successfully")
    void compileCSuccessfulTest() {
        Solution solution = prepareCSolution();
        when(tempFile.getDirectory()).thenReturn(Paths.get(System.getProperty("user.home")).toString() + "/tmp");
        assertEquals(codeCompile.compileCode(solution), 0);
        verify(tempFile).getDirectory();
    }

    @Test
    @DisplayName("Compile C Not Successfully")
    void compileCNotSuccessfulTest() {
        Solution solution = prepareWrongCSolution();
        when(tempFile.getDirectory()).thenReturn(Paths.get(System.getProperty("user.home")).toString() + "/tmp");
        assertEquals(codeCompile.compileCode(solution), 1);
    }

    @Test
    @DisplayName("Compile CPP Successfully")
    void compileCPPSuccessfulTest() {
        Solution solution = prepareCPPSolution();
        when(tempFile.getDirectory()).thenReturn(Paths.get(System.getProperty("user.home")).toString() + "/tmp");
        assertEquals(codeCompile.compileCode(solution), 0);
        verify(tempFile).getDirectory();
    }

    @Test
    @DisplayName("Compile CPP Not Successfully")
    void compileCPPNotSuccessfulTest() {
        Solution solution = prepareWrongCPPSolution();
        when(tempFile.getDirectory()).thenReturn(Paths.get(System.getProperty("user.home")).toString() + "/tmp");
        assertEquals(codeCompile.compileCode(solution), 1);
    }

    @Test
    @DisplayName("Compile Java Successfully")
    void compileJavaSuccessfulTest() {
        Solution solution = prepareJavaSolution();
        when(tempFile.getDirectory()).thenReturn(Paths.get(System.getProperty("user.home")).toString() + "/tmp");
        assertEquals(codeCompile.compileCode(solution), 0);
        verify(tempFile).getDirectory();
    }

    @Test
    @DisplayName("Controller compile service")
    void controllerCompileService() {
        Solution solution = prepareCSolution();
        when(serviceCodeCompiler.compileCode(solution)).thenReturn(0);
        assertEquals(0, controllerService.compile(solution));
        verify(serviceCodeCompiler).compileCode(solution);
    }

    private Solution prepareCSolution() {
        return new Solution(
                "#include<stdio.h>\nint main()\n{\n  printf(\"Hello\");\n  return 0;\n}",
                "",
                "c"
        );
    }

    private Solution prepareWrongCSolution() {
        return new Solution(
                "#include<stdio.h>\nint main()\n{\n  printf(\"Hello\")\n  return 0;\n}",
                "",
                "c"
        );
    }

    private Solution prepareCPPSolution() {
        return new Solution(
                "#include <iostream>\r\nusing namespace std;\r\n \r\n// Main() function: where the execution of\r\n// program begins\r\nint main()\r\n{\r\n    // Prints hello world\r\n    cout << \"Hello World\";\r\n \r\n    return 0;\r\n}",
                "",
                "cpp"
        );
    }

    private Solution prepareWrongCPPSolution() {
        return new Solution(
                "#include <iostream>\r\nusing namespace std\r\n \r\n// Main() function: where the execution of\r\n// program begins\r\nint main()\r\n{\r\n    // Prints hello world\r\n    cout << \"Hello World\";\r\n \r\n    return 0;\r\n}",
                "",
                "cpp"
        );
    }

    private Solution prepareJavaSolution() {
        return new Solution(
                "class HelloWorld {\r\n    public static void main(String[] args) {\r\nSystem.out.println(\"Hello, World!\"); \r\n    }\r\n}",
                "",
                "java"
        );
    }
}