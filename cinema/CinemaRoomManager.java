package cinema;

import java.util.OptionalInt;

public class CinemaRoomManager {

    private final static int FRONT_HALF_SEAT_TICKET_PRICE = 10;
    private final static int BACK_HALF_SEAT_TICKET_PRICE = 8;

    private final CinemaRoom room;
    private final CinemaStatistics statistics;

    public CinemaRoomManager (int numbOfRows, int numbOfSeatsPerRow) {
        room = new CinemaRoom(numbOfRows, numbOfSeatsPerRow);
        statistics = new CinemaStatistics(calculateTotalIncome(), room.getNumberOfSeats());
    }

    @Override
    public String toString() {
        return room.toString();
    }

    public OptionalInt tryBuyTicket(int rowNumber, int seatNumber) {

        var optionalSeatPrice = OptionalInt.empty();

        if (room.isSeatAvailable(rowNumber, seatNumber)) {
            room.bookSeat(rowNumber, seatNumber);
            var seatPrice = calculateTicketIncome(rowNumber, seatNumber);
            if (seatPrice > 0) {
                statistics.purchaseTicket(seatPrice);
                optionalSeatPrice = OptionalInt.of(seatPrice);
            }
        }

        return optionalSeatPrice;
    }

    public String getStatistics() {
        return statistics.toString();
    }

    private int  calculateTotalIncome() {
        int totalIncome;

        if (room.isSmall()){
            totalIncome = calculateSmallRoomTotalIncome();
        } else {
            totalIncome = calculateBigRoomTotalIncome();
        }
        return totalIncome;
    }

    private int calculateBigRoomTotalIncome() {
        return FRONT_HALF_SEAT_TICKET_PRICE * room.getNumberOfFrontSeats() +
                BACK_HALF_SEAT_TICKET_PRICE * room.getNumberOfBackSeats();
    }

    private int calculateSmallRoomTotalIncome() {
        return FRONT_HALF_SEAT_TICKET_PRICE * room.getNumberOfSeats();
    }

    private int calculateTicketIncome(int rowNumber, int seatNumber) {
        if (room.isSmall() || room.isFrontSeat(rowNumber, seatNumber)) {
            return FRONT_HALF_SEAT_TICKET_PRICE;
        } else {
            return BACK_HALF_SEAT_TICKET_PRICE;
        }
    }

    public boolean isValidSeat(int rowNumber, int seatNumber) {
        return room.isValidSeat(rowNumber, seatNumber);
    }
}
