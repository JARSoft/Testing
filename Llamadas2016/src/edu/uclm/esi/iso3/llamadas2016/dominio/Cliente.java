package edu.uclm.esi.iso3.llamadas2016.dominio;

import java.util.GregorianCalendar;
import java.util.Vector;

public class Cliente {
	private int id;
	private String nombre;
	private String apellido1, apellido2;
	private String dni;
	private String telefono;
	private Vector<TarifaCliente> tarifas;

	public Cliente(int id, String nombre, String apellido1, String apellido2, String dni, String telefono) {
		this.id = id;
		this.nombre = nombre;
		this.apellido1 = apellido1;
		this.apellido2 = apellido2;
		this.dni = dni;
		this.telefono = telefono;
		this.tarifas = new Vector<>();
	}

	public void setTarifa(Tarifa tarifa, GregorianCalendar fechaDeComienzo, GregorianCalendar fechaDeFin) {
		TarifaCliente tc = new TarifaCliente(tarifa, fechaDeComienzo, fechaDeFin);
		this.tarifas.add(tc);
	}

	public String getTelefono() {
		return telefono;
	}

	public String getDatosPersonales() {
		return apellido1 + " " + apellido2 + ", " + nombre;
	}

	public String getDni() {
		return dni;
	}

	public Tarifa getTarifaAplicable(Llamada llamada) {
		Tarifa tarifa = null;
		for (int i = 0; i < tarifas.size(); i++) {
			TarifaCliente t = tarifas.get(i);
			if (t.isInPeriod(llamada.getFecha()))
				tarifa = t.getTarifa();
		}
		if (tarifa != null)
			return tarifa;
		return null;
	}

}
