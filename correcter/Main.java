package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;
import java.util.function.Function;

public class Main {
    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        var mode = scanner.nextLine();

        try{
            switch (mode) {
                case "encode":
                    sendStream("send.txt", "encoded.txt", MessageEncoderHamming::encode);
                    break;
                case "send":
                    sendStream("encoded.txt", "received.txt", MessageSender::simulateSend);
                    break;
                case "decode":
                    sendStream("received.txt", "decoded.txt", MessageDecoderHamming::decode);
                    break;
                default:
            }
        } catch (IOException exception) {
            System.out.println(exception.toString());
            System.exit(-1);
        }
        
    }

    private static void sendStream (String from, String to, Function<byte[], byte[]> transform) throws IOException {
        var inputStream = new FileInputStream(from);
        var outputStream = new FileOutputStream(to);
        var inputBytes = inputStream.readAllBytes();

        inputStream.close();
        outputStream.write(transform.apply(inputBytes));
        outputStream.close();

    }
}
