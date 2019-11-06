package rs.ac.uns.ftn.ktsnwt.dto;

public class ReportInfoDTO {
    private int ticketsSold;
    private double income;

    public ReportInfoDTO(int ticketsSold, double income) {
        this.ticketsSold = ticketsSold;
        this.income = income;
    }

    public int getTicketsSold() {
        return ticketsSold;
    }

    public void setTicketsSold(int ticketsSold) {
        this.ticketsSold = ticketsSold;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }
}
