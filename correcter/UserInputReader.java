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
        if (inputStream.available() > 0) {
            inputStream.transferTo(userInputRead);
        }
        inputStream.close();
        return userInputRead.toByteArray();
    }
}
