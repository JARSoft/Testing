package edu.uclm.esi.iso3.llamadas2016.dominio;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class TarifaCliente {
	private GregorianCalendar fechaDeComienzo;
	private Tarifa tarifa;
	private GregorianCalendar fechaDeFin;

	public TarifaCliente(Tarifa tarifa, GregorianCalendar fechaDeComienzo, GregorianCalendar fechaDeFin) {
		this.tarifa = tarifa;
		this.fechaDeComienzo = fechaDeComienzo;
		this.fechaDeComienzo.set(Calendar.HOUR_OF_DAY, 0);
		this.fechaDeComienzo.set(Calendar.MINUTE, 0);
		this.fechaDeComienzo.set(Calendar.SECOND, 0);
		this.fechaDeFin = fechaDeFin;
		if (this.fechaDeFin != null) {
			this.fechaDeFin.set(Calendar.HOUR_OF_DAY, 23);
			this.fechaDeFin.set(Calendar.MINUTE, 59);
			this.fechaDeFin.set(Calendar.SECOND, 59);
		}
	}

	@Override
	public String toString() {
		return fechaDeComienzo.get(Calendar.DAY_OF_MONTH) + "/" + (1 + fechaDeComienzo.get(Calendar.MONTH)) + "/"
				+ fechaDeComienzo.get(Calendar.YEAR) + "\t"
				+ (fechaDeFin != null ? fechaDeFin.get(Calendar.DAY_OF_MONTH) + "/"
						+ (1 + fechaDeFin.get(Calendar.MONTH)) + "/" + fechaDeFin.get(Calendar.YEAR) : "ON")
				+ "\t" + tarifa.toString() + "\n";
	}

	public double getCoste(Llamada llamada) {
		return tarifa.getCosteLlamada(llamada);
	}

	public GregorianCalendar getFechaDeComienzo() {
		return fechaDeComienzo;
	}

	public GregorianCalendar getFechaDeFin() {
		return fechaDeFin;
	}

	public Tarifa getTarifa() {
		return tarifa;
	}

	public boolean isInPeriod(GregorianCalendar fecha) {
		if (fechaDeFin != null) {
			if ((fechaDeComienzo.before(fecha) || fechaDeComienzo.equals(fecha)) && fechaDeFin.after(fecha))
				return true;
		}
		if (fechaDeComienzo.before(fecha) || fechaDeComienzo.equals(fecha))
			return true;
		return false;
	}
}
