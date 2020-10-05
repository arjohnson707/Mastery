package learn.dontWreckMyHouse.models;

import jdk.vm.ci.meta.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Reservation {
    private int id;
    private LocalDate startDate;
    private LocalDate endDate;
    private Guest guestId;
    private BigDecimal total;
}
