package edu.uclm.esi.iso3.llamadas2016.dominio;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Llamada {
	private String origen, destino;
	private int duracion;
	private GregorianCalendar fecha;

	public Llamada(String origen, String destino, int duracion, int year, int mes, int dia, int hora, int minuto,
			int segundo) {
		this.origen = origen;
		this.destino = destino;
		this.duracion = duracion;
		this.fecha = new GregorianCalendar();
		this.fecha.set(year, mes - 1, dia, hora, minuto, segundo);
	}

	public int getDuracion() {
		return duracion;
	}

	public GregorianCalendar getFecha() {
		return fecha;
	}

	public String getDestino() {
		return this.destino;
	}

	public String toString() {
		return origen + "\t" + destino + "\t" + duracion + "\t" + fecha.get(Calendar.YEAR) + "\t"
				+ (fecha.get(Calendar.MONTH) + 1) + "\t" + fecha.get(Calendar.DAY_OF_MONTH) + "\t"
				+ fecha.get(Calendar.HOUR_OF_DAY) + "\t" + fecha.get(Calendar.MINUTE) + "\t"
				+ fecha.get(Calendar.SECOND);
	}

	public void save() throws IOException {
		File f = new File(Constantes.directorioRaiz + Constantes.llamadasRecibidas);
		int n = f.list().length + 1;
		FileOutputStream fos = new FileOutputStream(
				Constantes.directorioRaiz + Constantes.llamadasRecibidas + n + ".txt");
		fos.write(this.toString().getBytes());
		fos.close();
	}

	public void facturar(Cliente cliente) throws IOException {
		Tarifa tarifa = cliente.getTarifaAplicable(this);
		FileWriter fw = new FileWriter(Constantes.directorioRaiz + Constantes.facturas + origen + ".txt", true);
		fw.write(destino + "\t" + fecha.get(Calendar.DAY_OF_MONTH) + "/" + (fecha.get(Calendar.MONTH) + 1) + "/"
				+ fecha.get(Calendar.YEAR) + "\t" + fecha.get(Calendar.HOUR_OF_DAY) + ":" + fecha.get(Calendar.MINUTE)
				+ ":" + fecha.get(Calendar.SECOND) + "\t\t" + duracion + "\t\t\t" + tarifa.getCosteLlamada(this) + "\t"
				+ tarifa.getClass().getSimpleName() + "\n");
		fw.close();
	}
}
