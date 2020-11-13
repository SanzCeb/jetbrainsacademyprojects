package cinema;

import java.util.OptionalInt;
import java.util.Scanner;

public class Cinema {
    public final static Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        var numberOfRows = askUserForIntInput("Enter the number of rows:");
        var numberOfSeats = askUserForIntInput("Enter the number of seats in each row:");
        var cinema = new CinemaRoomManager(numberOfRows, numberOfSeats);
        int actionNumber;

        do {
            actionNumber = askUserForIntInput(cinemaRoomManagerMenu());
            switch (actionNumber) {
                case 1:
                     showSeats(cinema);
                    break;
                case 2:
                    buyTicket(cinema);
                    break;
                case 3:
                    showStatistics(cinema);
                    break;
                default:
                    break;
            }
        } while (actionNumber != 0);

    }

    private static String cinemaRoomManagerMenu() {
        return "1. Show the seats\n" +
                "2. Buy a ticket\n" +
                "3. Statistics\n" +
                "0. Exit\n";
    }

    private static void showStatistics(CinemaRoomManager cinema) {
        System.out.print(cinema.getStatistics());
    }

    private static void buyTicket(CinemaRoomManager cinema) {
        int rowNumber;
        int seatNumber;
        var ticketPriceOpt = OptionalInt.empty();

        do {
            rowNumber = askUserForIntInput("Enter a row number:");
            seatNumber = askUserForIntInput("Enter a seat number in that row:");

            if (cinema.isValidSeat(rowNumber, seatNumber)) {
                ticketPriceOpt = cinema.tryBuyTicket(rowNumber, seatNumber);

                if (ticketPriceOpt.isPresent()) {
                    System.out.printf("%nTicket price: $%d%n", ticketPriceOpt.getAsInt());
                } else {
                    System.out.print("That ticket has already been purchased!\n\n");
                }

            } else {
                System.out.print("Wrong input!\n\n");
            }

        } while (!cinema.isValidSeat(rowNumber, seatNumber) || ticketPriceOpt.isEmpty());

    }

    private static void showSeats(CinemaRoomManager cinema) {
        System.out.printf("%n%nCinema:%n%s%n", cinema);
    }

    private static void printUserSymbol() {
        System.out.print("> ");
    }

    private static int askUserForIntInput(String message) {
        System.out.println(message);
        printUserSymbol();
        var output = Integer.parseInt(SCANNER.nextLine());
        System.out.println();
        return output;
    }

}
