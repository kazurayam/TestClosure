import java.nio.file.Files
import java.nio.file.Path
import java.nio.file.Paths

import com.kazurayam.unittest.TestOutputOrganizer

Path testOutput = Paths.get("test-output")
TestOutputOrganizer.cleanDirectoryRecursively(testOutput)
Files.createDirectories(testOutput)
