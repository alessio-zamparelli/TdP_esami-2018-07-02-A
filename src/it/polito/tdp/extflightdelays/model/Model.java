package it.polito.tdp.extflightdelays.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.extflightdelays.db.Collegamento;
import it.polito.tdp.extflightdelays.db.ExtFlightDelaysDAO;

public class Model {

	private ExtFlightDelaysDAO dao;
	private Graph<Airport, DefaultWeightedEdge> grafo;
	private Map<Integer, Airport> airportIdMap;

	// Per la F ricorsiva
	private Set<Airport> bestItinerario;
	private double bestMiglia;

	public Model() {
		this.dao = new ExtFlightDelaysDAO();
	}

	public boolean creaGrafo(int migliaMedieMax) {
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		this.airportIdMap = dao.loadAllAirports().stream().collect(Collectors.toMap(Airport::getId, a -> a));
		Graphs.addAllVertices(this.grafo, this.airportIdMap.values());
//		this.dao.getAllEdges(migliaMedieMax);
		List<Collegamento> collegamenti = this.dao.getAllEdges(migliaMedieMax, airportIdMap);
		for (Collegamento c : collegamenti) {
			DefaultWeightedEdge e = Graphs.addEdge(this.grafo, c.getA1(), c.getA2(), c.getPeso());
//			if (e == null)
//				System.out.println("Arco non inserito");
//			else
//				System.out.println(e);
		}
//		System.out.format("Ho inserito %d vertici e %d archi", this.grafo.vertexSet().size(), this.grafo.edgeSet().size());
		if (this.grafo == null)
			return false;
		if (this.grafo.edgeSet().size() == 0 && this.grafo.vertexSet().size() == 0)
			return false;
		return true;
	}

	public List<Airport> getAllAirports() {
		return new ArrayList<>(this.airportIdMap.values());
	}

	public List<Collegamento> getAeroportiVicini(Airport a1) {
		List<Airport> vicini = Graphs.neighborListOf(this.grafo, a1);
		List<Collegamento> res = new LinkedList<>();
//		System.out.format("Ci sono %d aeroporti vicino a %s\n", vicini.size(), a1);
		for (Airport a2 : vicini) {
//			System.out.println(a2);
			double peso = this.grafo.getEdgeWeight(this.grafo.getEdge(a1, a2));
			res.add(new Collegamento(a1, a2, peso));
		}
		Collections.sort(res, Comparator.comparing(Collegamento::getPeso).reversed());
		return res;

	}

	public Set<Airport> calcolaItinerario(Airport aeroportoPartenza, double maxMiglia){
//		System.out.println("\n\n\n\nInizio ricorsione");
		this.bestItinerario = new HashSet<>();
		this.bestMiglia = -1;
		Set<Airport> itinerario = new HashSet<Airport>();
		itinerario.add(aeroportoPartenza);
		magic(new HashSet<>(), aeroportoPartenza, 0, maxMiglia, itinerario);
//		System.out.println("FINE RICORSIONE\n");
		return bestItinerario;
		
	}

	public void magic(Set<String> cittaParziale, Airport aeroportoAttuale, double migliaAttuali, double migliaMax, Set<Airport> itinerario) {
		if(migliaAttuali>=migliaMax)
			return;
		if(migliaAttuali>bestMiglia) {
			bestMiglia = migliaAttuali;
			bestItinerario = new HashSet<Airport>(itinerario);
//			System.out.println("Nuovo migliore -> " + bestMiglia);
//			System.out.println(bestItinerario);
		}
		if(bestMiglia>=0.997*migliaMax)
			// approccio greedy
			return;
		Set<Airport> aeroportiPossibili = Graphs.neighborSetOf(this.grafo, aeroportoAttuale);
		Set<String> newCittaParziale = new HashSet<>(cittaParziale);
		Set<Airport> newItinerario = new HashSet<>(itinerario);
		for(Airport a : aeroportiPossibili) {
			if(!cittaParziale.contains(a.getCity()) && !itinerario.contains(a)) {
				newCittaParziale.add(a.getCity());
				newItinerario.add(a);
				double newMigliaAttuali = this.grafo.getEdgeWeight(this.grafo.getEdge(aeroportoAttuale, a)) + migliaAttuali;
				magic(newCittaParziale, a, newMigliaAttuali, migliaMax, newItinerario);
				newCittaParziale.remove(a.getCity());
				newItinerario.remove(a);
			}
		}
		
	}

}
