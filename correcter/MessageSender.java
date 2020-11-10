package correcter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class MessageSender {
    public static byte[] simulateSend(byte[] userInput) throws IOException {
        var outputReader = new ByteArrayInputStream(userInput);
        var outputWriter = new ByteArrayOutputStream();
        int currentByte = outputReader.read();
        while (currentByte != -1) {
            currentByte = makeNoise(currentByte);
            outputWriter.write(currentByte);
            currentByte = outputReader.read();
        }
        outputReader.close();
        return outputWriter.toByteArray();
    }

    private static int makeNoise(int currentByte) {
        var bitSelector = 0xFE;
        var byteWithBitError = currentByte & bitSelector;
        while (byteWithBitError == currentByte) {
            bitSelector = bitSelector << 1;
            byteWithBitError &= bitSelector;
        }
        return byteWithBitError;
    }
}
