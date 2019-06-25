package it.polito.tdp.extflightdelays.model;

import java.util.List;
import java.util.Set;

import it.polito.tdp.extflightdelays.db.Collegamento;

public class TestModel {

	public static void main(String[] args) {

		Model model = new Model();
		model.creaGrafo(1000);
		System.out.println("Aeroporti");
		model.getAllAirports().forEach(a -> System.out.println(a));
		Airport aeroportoDiPartenza = null;
		for (Airport a : model.getAllAirports()) {

			List<Collegamento> vicini = model.getAeroportiVicini(a);
			if (vicini.size() > 0) {
				System.out.println("Aeroporti vicini a " + a + "\n");
				vicini.forEach(aa -> System.out.println(aa.getA2() + " - " + aa.getPeso()));
				if(vicini.size()>5)
					aeroportoDiPartenza = a;
			}
		}
		
		if(aeroportoDiPartenza==null) {
			System.out.println("errore nel test dell'itinerario");
			return;
		}
		
		Set<Airport> itinerario = model.calcolaItinerario(aeroportoDiPartenza, 50000);
		itinerario.forEach(a->System.out.println(a));

	}

}
