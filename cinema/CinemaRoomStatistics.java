package cinema;

class CinemaStatistics {

    private double numberOfTickets = 0;
    private int currentIncome = 0;
    private final int totalIncome;
    private final int totalSeats;

    CinemaStatistics(int totalIncome, int totalSeats) {
        this.totalIncome = totalIncome;
        this.totalSeats = totalSeats;
    }

    void purchaseTicket(int ticketIncome) {
        this.currentIncome += ticketIncome;
        numberOfTickets++;
    }

    @Override
    public String toString() {
        var statistics = new StringBuilder();

        statistics.append(String.format("Number of purchased tickets: %.0f%n", numberOfTickets));
        statistics.append(String.format("Percentage: %.2f%%%n", percentage()));
        statistics.append(String.format("Current income: $%d%n", currentIncome));
        statistics.append(String.format("Total income: $%d%n", totalIncome));

        return statistics.toString();
    }

    private double percentage() {
        return numberOfTickets * 100 / totalSeats;
    }

}
