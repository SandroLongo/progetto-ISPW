package it.uniroma2.progettoispw.model.domain;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class TherapyCalendar {
    private Map<LocalDate, DailyTherapy> calendar;

    public TherapyCalendar() {
        calendar = new HashMap<LocalDate, DailyTherapy>();
    }

    public Map<LocalDate, DailyTherapy> getCalendar() {
        return calendar;
    }

    public void addDailyTherapy(DailyTherapy DailyTherapy) {
        calendar.put(DailyTherapy.getDate(), DailyTherapy);
    }

    public DailyTherapy getDailyTherapy(LocalDate date) {
        return calendar.get(date);
    }

    public boolean exists(LocalDate date) {
        return calendar.containsKey(date);
    }

}
