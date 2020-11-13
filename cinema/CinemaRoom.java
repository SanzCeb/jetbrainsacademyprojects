package cinema;

public class CinemaRoom {

    private final static int SMALL_ROOM_NUM_OF_SEATS = 60;
    private final int numbOfRows;
    private final int numbOfSeatsPerRow;
    private final int numbOfFrontSeats;
    private final char[][] seats;
    private final String columns;
    private final boolean isASmallRoom;

    public CinemaRoom(int numbOfRows, int numbOfSeatsPerRow) {
        this.numbOfRows = numbOfRows;
        this.numbOfSeatsPerRow = numbOfSeatsPerRow;
        this.seats = buildSeats();
        this.columns = buildSeatNumbers();
        this.isASmallRoom = getNumberOfSeats() <= SMALL_ROOM_NUM_OF_SEATS;
        this.numbOfFrontSeats = numbOfSeatsPerRow * (numbOfRows / 2);
    }

    boolean isSmall() { return isASmallRoom;}

    boolean isFrontSeat(int rowNumberSeat, int numberSeat) {
        return numbOfSeatsPerRow * (rowNumberSeat - 1) +  numberSeat <= this.numbOfFrontSeats;
    }

    boolean isSeatAvailable(int rowNumber, int seatNumber) {
        try {
            return this.seats[rowNumber - 1][seatNumber - 1] == 'S';
        } catch (IndexOutOfBoundsException ex) {
            return false;
        }
    }

    void bookSeat(int rowNumberSeat, int numberSeat) {
        this.seats[rowNumberSeat - 1][numberSeat - 1] = 'B';
    }

    int getNumberOfSeats() { return numbOfSeatsPerRow * numbOfRows;}

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

    private char[][] buildSeats() {
        var screenRoom = new char[numbOfRows][numbOfSeatsPerRow];
        for (int i = 0; i < this.numbOfRows; i++) {
            for (int j = 0; j < this.numbOfSeatsPerRow; j++) {
                screenRoom[i][j] = 'S';
            }
        }
        return screenRoom;
    }

    private String buildSeatNumbers() {
        var seatNumbersBuilder = new StringBuilder();
        for (int i = 0; i < numbOfSeatsPerRow; i++) {
            seatNumbersBuilder.append(String.format(" %d", i + 1));
        }
        return seatNumbersBuilder.toString();
    }


    public int getNumberOfFrontSeats() {
        return this.numbOfFrontSeats;
    }

    public int getNumberOfBackSeats() {
        return getNumberOfSeats() - getNumberOfFrontSeats();
    }

    public boolean isValidSeat(int rowNumber, int seatNumber) {
        return isValidRowNumber(rowNumber) && isValidSeatNumber(seatNumber);
    }

    private boolean isValidSeatNumber(int seatNumber) {
        return seatNumber >= 1 && seatNumber <= numbOfSeatsPerRow;
    }

    private boolean isValidRowNumber(int rowNumber) {
        return rowNumber >= 1 && rowNumber <= numbOfRows;
    }
}
