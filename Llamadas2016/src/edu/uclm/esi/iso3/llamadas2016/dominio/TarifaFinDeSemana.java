package edu.uclm.esi.iso3.llamadas2016.dominio;

import java.util.Calendar;

/***
 * 
 * Establecimiento: 12 céntimos; coste por segundo: 1 céntimo. Las llamadas no
 * se cobran si se empiezan entre las 16h del viernes y antes de las 8h del
 * lunes [16h del V, 8h del lunes).
 *
 */
public class TarifaFinDeSemana extends Tarifa {

	public TarifaFinDeSemana() {
		super(0.12, 0.01);
	}

	public double getCosteLlamada(Llamada llamada) {
		Calendar fecha = llamada.getFecha();
		if (fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || fecha.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY
				|| fecha.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY && fecha.get(Calendar.HOUR_OF_DAY) >= 16
				|| fecha.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY && fecha.get(Calendar.HOUR_OF_DAY) < 8)
			return 0;
		return establecimiento + costePorSegundo * llamada.getDuracion();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
