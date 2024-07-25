package rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.activity;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Activity;

import java.util.List;

public interface ActivityRepository {
    public List<Activity> allActivities();
    public List<Activity> getActivity(Integer id);
    public Activity findActivity(Integer id);
    public Activity addActivity(Activity activity);
    public void deleteActivity(Integer id);


}
