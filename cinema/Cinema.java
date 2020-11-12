package cinema;

import java.util.Scanner;

public class Cinema {
    private final static int FRONT_HALF_SEAT_TICKET_PRICE = 10;
    private final static int BACK_HALF_SEAT_TICKET_PRICE = 8;
    private final static int SMALL_ROOM_NUM_OF_SEATS = 60;

    private final int numbOfRows;
    private final int numbOfSeatsPerRow;
    private final char[][] seats;
    private final String columns;
    private final boolean isASmallRoom;
    private final int numbOfFrontHalfSeats;

    public Cinema (int numbOfRows, int numbOfSeatsPerRow) {
        this.numbOfRows = numbOfRows;
        this.numbOfSeatsPerRow = numbOfSeatsPerRow;
        this.columns = buildSeatNumbers();
        this.seats = buildSeats();
        this.isASmallRoom = getNumbOfSeats() <= SMALL_ROOM_NUM_OF_SEATS;
        this.numbOfFrontHalfSeats = numbOfSeatsPerRow * (numbOfRows / 2);
    }

    public int buySeat(int rowNumberSeat, int numberSeat) {
        int seatPrice;
        markSeat(rowNumberSeat - 1, numberSeat - 1);

        if (isASmallRoom || isFrontHalfSeat(rowNumberSeat, numberSeat)) {
            seatPrice = FRONT_HALF_SEAT_TICKET_PRICE;
        } else {
            seatPrice = BACK_HALF_SEAT_TICKET_PRICE;
        }

        return seatPrice;
    }

    @Override
    public String toString() {
        var rows = new StringBuilder();

        for (int i = 0; i < numbOfRows; i++) {
            rows.append(String.format("%d ", i + 1));
            for (int j = 0; j < numbOfSeatsPerRow; j++) {
                rows.append(String.format("%c ", seats[i][j]));
            }
            rows.append('\n');
        }

        return String.format(" %s%n%s", columns, rows);
    }

    private boolean isFrontHalfSeat(int rowNumberSeat, int numberSeat) {
        return numbOfSeatsPerRow * (rowNumberSeat - 1) +  numberSeat <= this.numbOfFrontHalfSeats;
    }

    private void markSeat(int rowNumberSeat, int numberSeat) {
        this.seats[rowNumberSeat][numberSeat] = 'B';
    }

    private String buildSeatNumbers() {
        var seatNumbersBuilder = new StringBuilder();
        for (int i = 0; i < numbOfSeatsPerRow; i++) {
            seatNumbersBuilder.append(String.format(" %d", i + 1));
        }
        return seatNumbersBuilder.toString();
    }

    private char[][] buildSeats() {
        var screenRoom = new char[numbOfRows][numbOfSeatsPerRow];
        for (int i = 0; i < this.numbOfRows; i++) {
            for (int j = 0; j < this.numbOfSeatsPerRow; j++) {
                screenRoom[i][j] = 'S';
            }
        }
        return screenRoom;
    }

    private int getNumbOfSeats() { return numbOfSeatsPerRow * numbOfRows;}

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);

        System.out.println("Enter the number of rows:");
        printUserSymbol();
        var numberOfRows = Integer.parseInt(scanner.nextLine());

        System.out.println("Enter the number of seats in each row:");
        printUserSymbol();
        var numberOfSeats = Integer.parseInt(scanner.nextLine());

        var cinema = new Cinema(numberOfRows, numberOfSeats);
        int actionNumber;
        do {

            System.out.println("1. Show the seats");
            System.out.println("2. Buy a ticket");
            System.out.println("0. Exit");
            printUserSymbol();
            actionNumber = Integer.parseInt(scanner.nextLine());
            switch (actionNumber) {
                case 1:
                     showSeats(cinema);
                    break;
                case 2:
                    buyTicket(cinema);
                    break;
                default:
                    break;
            }
        } while (actionNumber != 0);

    }

    private static void buyTicket(Cinema cinema) {
        var scanner = new Scanner(System.in);
        System.out.println("Enter a row number:");
        printUserSymbol();
        var rowNumberSeat = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter a seat number in that row:");
        printUserSymbol();
        var numberSeat = Integer.parseInt(scanner.nextLine());
        System.out.printf("%nTicket price: $%d%n%n", cinema.buySeat(rowNumberSeat, numberSeat));
    }

    private static void showSeats(Cinema cinema) {
        System.out.printf("%nCinema:%n%s%n", cinema);
    }

    private static void printUserSymbol() {
        System.out.print("> ");
    }

}
