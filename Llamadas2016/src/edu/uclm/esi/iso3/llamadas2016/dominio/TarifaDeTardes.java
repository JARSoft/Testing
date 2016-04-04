package edu.uclm.esi.iso3.llamadas2016.dominio;

import java.util.Calendar;

/***
 * 
 * Establecimiento: 16 céntimos; coste por segundo: 1,5 céntimos. Las llamadas
 * no se cobran si se empiezan entre las 16h y antes de las 8h [16h, 8h).
 *
 */
public class TarifaDeTardes extends Tarifa {

	public TarifaDeTardes() {
		super(0.16, 0.015);
	}

	public double getCosteLlamada(Llamada llamada) {
		Calendar fecha = llamada.getFecha();
		if (fecha.get(Calendar.HOUR_OF_DAY) >= 16 || fecha.get(Calendar.HOUR_OF_DAY) < 8)
			return 0;
		return establecimiento + costePorSegundo * llamada.getDuracion();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName();
	}
}
