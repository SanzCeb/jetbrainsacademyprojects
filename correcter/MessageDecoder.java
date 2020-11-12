package correcter;

import java.io.ByteArrayOutputStream;

public class MessageDecoder {

    public static byte[] decode(byte[] userInput){
        var outputWriter = new ByteArrayOutputStream();
        byte [] buffer = {0, 5};

        for (var userInputByte : userInput) {
            var decodedByte = decodeByte(userInputByte);
            writeDecodedByte(decodedByte, outputWriter, buffer);
        }

        if (buffer[1] < 0) {
            outputWriter.write(buffer[0]);
        }

        return outputWriter.toByteArray();
    }

    private static void writeDecodedByte(byte decodedByte, ByteArrayOutputStream outputWriter, byte[] buffer) {
        var offset = buffer[1];
        if (offset < 0) {
            bufferRemainingBits(buffer, decodedByte);
            outputWriter.write(buffer[0]);
            initBuffer(buffer, decodedByte);
        } else {
            bufferDecodedByte(buffer, decodedByte);
        }
    }

    private static void bufferDecodedByte(byte[] buffer, byte decodedByte) {
        buffer[0] |= decodedByte << buffer[1];
        buffer[1] -= 3;
    }

    private static void initBuffer(byte[] buffer, byte decodedByte) {
        buffer[0] = (byte) (decodedByte << (8 + buffer[1]));
        buffer[1] = (byte) (5 + buffer[1]);
    }


    private static void bufferRemainingBits(byte[] buffer, byte decodedByte) {
        buffer[0] |= decodedByte >> Math.abs(buffer[1]);
    }

    private static byte decodeByte(int encodedByte) {
        byte output = 0;
        for (int i = 0; i < 6; i += 2) {
            var encodedBits = getEncodedBit(encodedByte, i);
            output <<= 1;
            output |= bitCorrect(encodedBits) ? decodeBit(encodedBits) : decodeCorrectBit(encodedByte, i);
        }
        return output;
    }

    private static boolean bitCorrect(int bits) {
        return bits == 0 || bits == 3;
    }

    private static int getEncodedBit(int encodedByte, int index) {
        return 0x03 & encodedByte >> (6 - index);
    }

    private static int decodeCorrectBit(int encodedByte, int errorPos) {
        int bit = encodedByte & 1;
        for (int i = 0; i < 6 ; i += 2) {
            if (i != errorPos) {
                bit ^= decodeBit(getEncodedBit(encodedByte, i));
            }
        }
        return bit;
    }

    private static int decodeBit(int encodedBit) {
        return encodedBit & 1;
    }

}
