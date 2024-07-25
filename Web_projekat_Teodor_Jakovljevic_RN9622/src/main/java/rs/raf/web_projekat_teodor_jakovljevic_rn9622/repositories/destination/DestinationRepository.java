package rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.destination;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Destination;

import java.util.List;

public interface DestinationRepository {


    public List<Destination> allDestinations();
    public List<Destination> allDestinationsPagination(Integer page, Integer pageSize);
    public Destination getDestination(Integer id);
    public Destination addDestination(Destination destination);
    public Destination editDestination(Integer id, Destination destination);
    public Destination findDestination(String name);
    public void deleteDestination(Integer id);
}
