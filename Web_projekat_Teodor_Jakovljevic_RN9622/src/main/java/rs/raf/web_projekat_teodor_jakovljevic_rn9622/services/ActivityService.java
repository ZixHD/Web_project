package rs.raf.web_projekat_teodor_jakovljevic_rn9622.services;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Activity;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.activity.ActivityRepository;

import javax.inject.Inject;
import java.util.List;

public class ActivityService {

    @Inject
    private ActivityRepository activityRepository;
    public Activity findActivity(Integer id){ return this.activityRepository.findActivity(id); }
    public List<Activity> allActivities(){ return this.activityRepository.allActivities(); }
    public List<Activity> getActivity(Integer id){ return this.activityRepository.getActivity(id); }
    public Activity addActivity(Activity activity){ return this.activityRepository.addActivity(activity); }
    public void deleteActivity(Integer id) { this.activityRepository.deleteActivity(id); }
}
