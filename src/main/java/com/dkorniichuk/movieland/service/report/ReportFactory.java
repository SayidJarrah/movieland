package com.dkorniichuk.movieland.service.report;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class ReportFactory {

    final static Map<String, Supplier<Report>> map = new HashMap<>();

    static {
        map.put("allMovies", AllMoviesReport::new);
        map.put("addedDuringPeriod", AddedDuringPeriodReport::new);
        map.put("topActiveUsers", TopActiveUsersReport::new);
    }

    public static Report getReport(String reportType){
        return map.get(reportType).get();
    }

}
