package it.polito.tdp.extflightdelays.db;

import it.polito.tdp.extflightdelays.model.Airport;

public class Collegamento {
	private Airport a1;
	private Airport a2;
	private double peso;

	public Collegamento(Airport a1, Airport a2, double peso2) {
		this.a1 = a1;
		this.a2 = a2;
		this.peso = peso2;
	}

	public Collegamento(Airport a1, Airport a2) {
		this.a1 = a1;
		this.a2 = a2;
	}

	public Airport getA1() {
		return a1;
	}

	public Airport getA2() {
		return a2;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Arco [a1=");
		builder.append(a1);
		builder.append(", a2=");
		builder.append(a2);
		builder.append(", peso=");
		builder.append(peso);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a1 == null) ? 0 : a1.hashCode());
		result = prime * result + ((a2 == null) ? 0 : a2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Collegamento other = (Collegamento) obj;
		if (a1 == null) {
			if (other.a1 != null)
				return false;
		} else if (!a1.equals(other.a1))
			return false;
		if (a2 == null) {
			if (other.a2 != null)
				return false;
		} else if (!a2.equals(other.a2))
			return false;
		return true;
	}

}
