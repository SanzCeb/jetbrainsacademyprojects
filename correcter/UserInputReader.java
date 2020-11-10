package correcter;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

public class UserInputReader {
    private final FileInputStream inputStream;

    UserInputReader(FileInputStream inputStream) {
        this.inputStream = inputStream;
    }

    public byte[] readUserInput() throws IOException {
        var userInputRead = new ByteArrayOutputStream();
        int currentByte = inputStream.read();
        while (currentByte != -1) {
            userInputRead.write(currentByte);
            currentByte = inputStream.read();
        }
        return userInputRead.toByteArray();
    }
}
