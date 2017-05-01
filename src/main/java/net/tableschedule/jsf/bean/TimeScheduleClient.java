package net.tableschedule.jsf.bean;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.log4j.Logger;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import java.util.List;

/**
 * Created by aleksandrprendota on 18.04.17.
 */

@Data
@RequestScoped
@NoArgsConstructor
@ManagedBean(name = "schedule")
public class TimeScheduleClient {

    @ManagedProperty(value = "#{param.station}")
    private String station;

    public TimeScheduleService timeScheduleService = new TimeScheduleService();
    public List<TimeSchedule> timeSchedules;
    private static final Logger LOG = Logger.getLogger(TimeScheduleClient.class);

    @PostConstruct
    public void init() {
        if (station == null){
            LOG.info("Station is null -> input: all of station");
        } else{
            LOG.info("Station = " + station);
        }
        timeSchedules = timeScheduleService.getContent(station);
    }

    public List<TimeSchedule> getTimeSchedules(){
        return timeSchedules;
    }

    public  String getStation(){
        if (station == null) {
            return "All ways";
        } else{
            return station;
        }
    }
}