package correcter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class MessageEncoder {
    public static byte[] encode(byte[] userInput){
        var outputReader = new ByteArrayInputStream(userInput);
        var outputWriter = new ByteArrayOutputStream();
        byte buffer = 0;
        while (outputReader.available() > 0) {
            var currentByte = (byte)outputReader.read();
            buffer = writeEncodedByte(currentByte, outputWriter, buffer);
        }
        if (buffer != 0) {
            var output = (byte) (buffer & 0xFC);
            output |= encodeBit(parityBit(output));
            outputWriter.write(output);
        }
        return outputWriter.toByteArray();
    }

    private static byte writeEncodedByte(byte byteToEncode, ByteArrayOutputStream outputStream, byte buffer) {
        var output = (byte) (buffer & 0xFC);
        int offset = buffer & 0x03;

        for (int i = 0; i < 8; i++) {
            var bit = getBit(byteToEncode, i) ;
            output |= placeEncodedBit(encodeBit(bit), offset);
            offset++;
            if (offset == 3) {
                output |= encodeBit(parityBit(output));
                outputStream.write(output);
                offset = 0;
                output = 0;
            }
        }

        return (byte) (output | offset);
    }

    private static int parityBit(byte output) {
        return getBit(output,0) ^ getBit(output, 2) ^ getBit(output, 4);
    }

    private static int placeEncodedBit(int bit, int offset) {
        return bit << (6 - (offset * 2));
    }

    private static int encodeBit(int bit) {
        return bit | bit << 1;
    }

    private static int getBit(int bitArray, int bitPos) {
        return bitArray >> (7 - bitPos) & 0x01;
    }
}
