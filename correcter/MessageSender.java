package correcter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Random;

public class MessageSender {
    final static Random bitRandomizer = new Random();
    public static byte[] simulateSend(byte[] userInput){
        var outputReader = new ByteArrayInputStream(userInput);
        var outputWriter = new ByteArrayOutputStream();
        int currentByte = outputReader.read();
        while (currentByte != -1) {
            currentByte = changeOneRandomBit(currentByte);
            outputWriter.write(currentByte);
            currentByte = outputReader.read();
        }
        return outputWriter.toByteArray();
    }

    private static int changeOneRandomBit(int currentByte) {
        return currentByte ^ 0x01 << bitRandomizer.nextInt(8);
    }
}
