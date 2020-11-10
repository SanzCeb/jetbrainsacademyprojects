package correcter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try (FileInputStream inputStream = new FileInputStream("C:\\Users\\scebollero\\IdeaProjects\\Error Correcting Encoder-Decoder\\Error Correcting Encoder-Decoder\\task\\src\\correcter\\send.txt")) {
            var userInputReader = new UserInputReader(inputStream);
            var userInput = userInputReader.readUserInput();
            var sentMessage = MessageSender.simulateSend(userInput);
            try(FileOutputStream outputStream = new FileOutputStream("received.txt")) {
                outputStream.write(sentMessage);
            }
        }catch (IOException exception) {
            System.exit(-1);
        }



    }

}
