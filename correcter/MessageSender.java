package correcter;

import java.io.ByteArrayOutputStream;
import java.util.Random;

public class MessageSender {
    final static Random bitRandomizer = new Random();

    public static byte[] simulateSend(byte[] userInput){
        var outputWriter = new ByteArrayOutputStream();

        for (var inputByte : userInput) {
            outputWriter.write(corruptByte(inputByte));
        }

        return outputWriter.toByteArray();
    }

    private static int corruptByte(byte currentByte) {
        return currentByte ^ 0x01 << bitRandomizer.nextInt(8);
    }
}
