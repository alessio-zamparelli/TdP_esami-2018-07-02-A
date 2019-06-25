package it.polito.tdp.extflightdelays.db;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import it.polito.tdp.extflightdelays.model.Airport;

public class TestDAO {

	public static void main(String[] args) {

		ExtFlightDelaysDAO dao = new ExtFlightDelaysDAO();

//		System.out.println(dao.loadAllAirlines());
//		System.out.println(dao.loadAllAirports());
//		System.out.println(dao.loadAllFlights().size());
		Map<Integer, Airport> aIdMap = dao.loadAllAirports().stream().collect(Collectors.toMap(Airport::getId, a->a));
		List<Collegamento> archi = dao.getAllEdges(2000, aIdMap);
		System.out.println("Aeroporti");
		aIdMap.values().forEach(a->System.out.println(a));
		System.out.println("Archi");
		archi.forEach(a->System.out.println(a));
	}

}
