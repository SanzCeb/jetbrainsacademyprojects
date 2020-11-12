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
        int numberOfRows;
        int numberOfSeats;
        int rowNumberSeat;
        int numberSeat;
        Cinema cinema;

        System.out.println("Enter the number of rows:");
        numberOfRows = Integer.parseInt(scanner.nextLine());
        System.out.println("\nEnter the number of seats in each row:");
        numberOfSeats = Integer.parseInt(scanner.nextLine());
        cinema = new Cinema(numberOfRows, numberOfSeats);
        System.out.printf("%nCinema:%n%s%n", cinema);
        System.out.println("Enter a row number:");
        rowNumberSeat = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter a seat number in that row:");
        numberSeat = Integer.parseInt(scanner.nextLine());
        System.out.printf("%nTicket price: $%d%n", cinema.buySeat(rowNumberSeat, numberSeat));
        System.out.printf("%nCinema:%n%s%n", cinema);

    }

}
