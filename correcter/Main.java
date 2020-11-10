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
                    encode(new FileInputStream("send.txt"));
                    break;
                case "send":
                    send(new FileInputStream("encoded.txt"));
                    break;
                case "decode":
                    decode(new FileInputStream("received.txt"));
                    break;
                default:
            }
        }catch (IOException exception) {
            System.out.println(exception.toString());
            System.exit(-1);
        }
        
    }

    private static void decode(FileInputStream inputStream) throws IOException {
        try (var outputStream = new FileOutputStream("decoded.txt")){
            sendStream(inputStream, outputStream, MessageDecoder::decode);
        }
    }

    private static void send(FileInputStream inputStream) throws IOException {
        try (var outputStream = new FileOutputStream("received.txt")){
            sendStream(inputStream, outputStream, MessageSender::simulateSend);
        }
    }

    private static void encode(FileInputStream inputStream) throws IOException {
        try (var outputStream = new FileOutputStream("encoded.txt")){
            sendStream(inputStream, outputStream, MessageEncoder::encode);
        }
    }

    private static void sendStream (FileInputStream from, FileOutputStream to, Function<byte[], byte[]> transform) throws IOException {
        var inputReader = new UserInputReader(from);
        var userInput = inputReader.readUserInput();
        var output = transform.apply(userInput);
        to.write(output);
        to.close();
    }
}
