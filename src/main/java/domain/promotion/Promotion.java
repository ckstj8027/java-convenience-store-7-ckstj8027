package domain.promotion;

import java.time.LocalDateTime;

public class Promotion {

    private String name;
    private int buy;
    private int get;
    private LocalDateTime start_date;
    private LocalDateTime end_date;

    public Promotion() {
    }

    public Promotion(String name, int buy, int get, LocalDateTime start_date, LocalDateTime end_date) {
        this.name = name;
        this.buy = buy;
        this.get = get;
        this.start_date = start_date;
        this.end_date = end_date;
    }

    public String getName() {
        return name;
    }

    public int getBuy() {
        return buy;
    }

    public int getGet() {
        return get;
    }

    public LocalDateTime getStart_date() {
        return start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }
}
