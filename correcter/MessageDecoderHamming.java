package correcter;

import java.io.ByteArrayOutputStream;

public class MessageDecoderHamming {

    public static byte[] decode(byte[] userInput){
        var outputWriter = new ByteArrayOutputStream();
        byte [] buffer = {0, 4};

        for (var encodedByte : userInput) {
            var decodedByte = decodeByte(encodedByte);
            writeDecodedByte(decodedByte, outputWriter, buffer);
        }

        return outputWriter.toByteArray();
    }

    private static void writeDecodedByte(byte decodedByte, ByteArrayOutputStream outputWriter, byte[] buffer) {
        if (buffer[1] == 0) {
            outputWriter.write(buffer[0] | decodedByte);
            initBuffer(buffer);
        } else {
            buffer[0] |= decodedByte << buffer[1];
            buffer[1] = 0;
        }
    }

    private static void initBuffer(byte[] buffer) {
        buffer[0] = 0;
        buffer[1] = 4;
    }


    private static byte decodeByte(int encodedByte) {
        byte output = 0;
        var mask = calculateErrorMask(encodedByte);

        output |= getBit(encodedByte, 2) << 3;
        output |= getBit(encodedByte, 4) << 2;
        output |= getBit(encodedByte, 5) << 1;
        output |= getBit(encodedByte, 6);

        return applyErrorMask(output, mask);
    }

    private static byte applyErrorMask(byte output, int mask) {
        var numShifts = mask == 0x20 ? 2 : 1;
        return (byte) (output ^ mask >> numShifts);
    }

    private static int calculateErrorMask(int encodedByte) {
        var b0 = getBit(encodedByte, 0);
        var b1 = getBit(encodedByte, 1);
        var b3 = getBit(encodedByte, 3);
        var p0 = calculateP0(encodedByte);
        var p1 = calculateP1(encodedByte);
        var p3 = calculateP3(encodedByte);
        var mask = 0;

        if (p0 != b0 && p1 != b1 && p3 != b3) {
            mask = 0x02;
        } else if (p1 != b1 && p3 != b3) {
            mask = 0x04;
        } else if (p0 != b0 && p3 != b3) {
            mask = 0x08;
        } else if (p0 != b0 && p1 != b1){
            mask = 0x20;
        }

        return  mask;
    }

    private static int calculateP3(int nibble) {
        return getBit(nibble, 4) ^ getBit(nibble, 5) ^ getBit(nibble, 6);
    }

    private static int calculateP1(int nibble) {
        return getBit(nibble, 2) ^ getBit(nibble, 5) ^ getBit(nibble, 6);
    }

    private static int calculateP0(int nibble) {
        return getBit(nibble, 2) ^ getBit(nibble, 4) ^ getBit(nibble,6);
    }

    private static int getBit(int bitArray, int bitPos) {
        return bitArray >> (7 - bitPos) & 0x01;
    }

}
