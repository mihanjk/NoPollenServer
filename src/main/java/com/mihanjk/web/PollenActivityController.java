package com.mihanjk.web;

import com.mihanjk.model.AllergenMoscow;
import com.mihanjk.services.DatabaseService;
import com.mihanjk.services.PollenActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

@RestController
public class PollenActivityController {
    private final PollenActivityService pollenActivityService;
    private final DatabaseService databaseService;

    @Autowired
    public PollenActivityController(PollenActivityService pollenActivityService, DatabaseService databaseService) {
        this.pollenActivityService = pollenActivityService;
        this.databaseService = databaseService;
    }

    @RequestMapping("/getMoscowForecast")
    @Scheduled(fixedRate = 600000)
    public Map<String, List<AllergenMoscow>> getForecastMoscow() throws Exception {
        System.err.println("Execute request allergotop: " + Calendar.getInstance().getTime());
        return pollenActivityService.getForecastMoscow();
    }

    @RequestMapping("/checkNNForecast")
    @Scheduled(fixedRate = 660000)
    public void createForecastTemplateNN() throws Exception {
        System.err.println("Execute request: nikaNN" + Calendar.getInstance().getTime());
        pollenActivityService.receiveRequestNN();
    }

    @RequestMapping("/sendNotificationNN")
    public void sendNotificationNN() {
        System.err.println("Send notification: " + Calendar.getInstance().getTime());
        databaseService.getDataForNotificationAndSendIt();
    }
}
