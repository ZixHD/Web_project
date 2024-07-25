package rs.raf.web_projekat_teodor_jakovljevic_rn9622.services;


import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Destination;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.destination.DestinationRepository;

import javax.inject.Inject;
import java.util.List;


public class DestinationService {
    @Inject
    private DestinationRepository destinationRepository;


    public List<Destination> allDestinations(){ return this.destinationRepository.allDestinations(); }
    public List<Destination> allDestinationPagination(Integer page, Integer pageSize){ return this.destinationRepository.allDestinationsPagination(page, pageSize); }
    public Destination getDestination(Integer id){ return this.destinationRepository.getDestination(id);}
    public Destination addDestination(Destination destination){ return this.destinationRepository.addDestination(destination); }
    public Destination editDestination(Integer id, Destination destination){ return this.destinationRepository.editDestination(id, destination); }
    public void deleteDestination(Integer id){ this.destinationRepository.deleteDestination(id); }
    public Destination findDestination(String name){ return this.destinationRepository.findDestination(name); }
}
