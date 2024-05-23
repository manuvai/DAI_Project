package repositories;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import models.Commande;
import models.Concerner;
import models.keys.ConcernerKey;

public class CommandeRepository extends AbstractRepository<Commande, Integer> {

	public CommandeRepository() {
		super(Commande.class);
	}
	
	public List<Commande> findAllSorted() {
	    List<Commande> commandes = findAll();
	    return commandes.stream()
	            .sorted(Comparator.comparing(Commande::getEtat)
	                    .thenComparing(Commande::getDateArrivee))
	            .collect(Collectors.toList());
	}

}
