package cinema;

import java.util.Scanner;

public class Cinema {
    private final int numbOfRows;
    private final int numbOfSeatsPerRow;
    private final static int FRONT_HALF_SEAT_TICKET_PRICE = 10;
    private final static int BACK_HALF_SEAT_TICKET_PRICE = 8;
    private final static int SMALL_ROOM_NUM_OF_SEATS = 60;

    public Cinema (int numbOfRows, int numbOfSeatsPerRow) {
        this.numbOfRows = numbOfRows;
        this.numbOfSeatsPerRow = numbOfSeatsPerRow;
    }

    private int getNumbOfSeats() { return numbOfSeatsPerRow * numbOfRows;}

    public int profitFromSoldTickets() {
        int result;
        int numberOfSeats = getNumbOfSeats();
        if (numberOfSeats <= SMALL_ROOM_NUM_OF_SEATS) {
            result = FRONT_HALF_SEAT_TICKET_PRICE * numberOfSeats;
        } else {
            int firstHalfProfit = FRONT_HALF_SEAT_TICKET_PRICE * numbOfSeatsPerRow * (numbOfRows / 2);
            int secondHalfProfit = BACK_HALF_SEAT_TICKET_PRICE * numbOfSeatsPerRow * ((numbOfRows + 1) / 2);
            result = firstHalfProfit + secondHalfProfit;
        }
        return result;
    }

    public static void main(String[] args) {
        var scanner = new Scanner(System.in);
        int numberOfRows;
        int numberOfSeats;
        Cinema cinema;

        System.out.println("Enter the number of rows:");
        numberOfRows = Integer.parseInt(scanner.nextLine());
        System.out.println("Enter the number of seats in each rows:");
        numberOfSeats = Integer.parseInt(scanner.nextLine());
        cinema = new Cinema(numberOfRows, numberOfSeats);
        System.out.println("Total income:");
        System.out.printf("$%d%n",cinema.profitFromSoldTickets());

    }

}
