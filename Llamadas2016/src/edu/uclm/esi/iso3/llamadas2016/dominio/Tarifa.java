package edu.uclm.esi.iso3.llamadas2016.dominio;

public abstract class Tarifa {
	protected double establecimiento;
	protected double costePorSegundo;

	public Tarifa(double establecimiento, double costePorSegundo) {
		super();
		this.establecimiento = establecimiento;
		this.costePorSegundo = costePorSegundo;
	}

	@Override
	public abstract String toString();

	public double getCosteLlamada(Llamada llamada) {
		return establecimiento + costePorSegundo * llamada.getDuracion();
	}
}
