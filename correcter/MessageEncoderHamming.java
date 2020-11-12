package correcter;

import java.io.ByteArrayOutputStream;

public class MessageEncoderHamming {

    public static byte[] encode(byte[] userInput) {
        var outputWriter = new ByteArrayOutputStream();
        for (var rawByte : userInput) {
            writeEncodedByte(rawByte, outputWriter);
        }
        return outputWriter.toByteArray();
    }

    private static void writeEncodedByte(byte currentByte, ByteArrayOutputStream outputWriter) {
        outputWriter.write(encodeHamming(currentByte >> 4 & 0x0F));
        outputWriter.write(encodeHamming(currentByte & 0x0F));
    }

    private static int encodeHamming(int nibble) {
        var parity1 = calculatePO(nibble);
        var parity2 = calculateP1(nibble);
        var parity4 = calculateP4(nibble);
        var hammingCode = 0;

        hammingCode |= parity1 << 7;
        hammingCode |= parity2 << 6;
        hammingCode |= getBit(nibble, 0) << 5;
        hammingCode |= parity4 << 4;
        hammingCode |= getBit(nibble, 1) << 3;
        hammingCode |= getBit(nibble, 2) << 2;
        hammingCode |= getBit(nibble, 3) << 1;
        hammingCode |= 0;

        return  hammingCode;
    }

    private static int calculateP4(int nibble) {
        return getBit(nibble, 1) ^ getBit(nibble, 2) ^ getBit(nibble, 3);
    }

    private static int calculateP1(int nibble) {
        return getBit(nibble, 0) ^ getBit(nibble, 2) ^ getBit(nibble, 3);
    }

    private static int calculatePO(int nibble) {
        return getBit(nibble, 0) ^ getBit(nibble, 1) ^ getBit(nibble,3);
    }

    private static int getBit(int nibble, int bitPos) {
        return nibble >> (3 - bitPos) & 0x01;
    }

}
