package edu.uclm.esi.iso3.llamadas2016.dominio;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Factura {

	private Cliente cliente;

	public Factura(Cliente cliente) {
		this.cliente = cliente;
	}

	public void save() throws IOException {
		FileWriter fw = new FileWriter(
				Constantes.directorioRaiz + Constantes.facturas + cliente.getTelefono() + ".txt");
		fw.write("Cliente: " + cliente.getDatosPersonales() + "\n");
		fw.write("DNI: " + cliente.getDni() + "\n");
		fw.write("Teléfono: " + cliente.getTelefono() + "\n\n");

		fw.write("Detalle de llamadas:\n");
		fw.write("Destino\t\tFecha\t\tHora\tDuración (segs.)\t\tImporte\tTarifa\n");
		fw.close();
	}

	public static double loadImporteDeFactura(String telefono) throws IOException {
		FileReader fr = new FileReader(Constantes.directorioRaiz + Constantes.facturas + telefono + ".txt");
		BufferedReader br = new BufferedReader(fr);
		String ultima = null, linea;
		while ((linea = br.readLine()) != null) {
			ultima = linea;
		}
		fr.close();
		ultima = ultima.substring(ultima.indexOf(":") + 1);
		ultima = ultima.substring(0, ultima.length() - 1);
		ultima = ultima.trim();
		double result = Double.parseDouble(ultima);
		return result;
	}

}
