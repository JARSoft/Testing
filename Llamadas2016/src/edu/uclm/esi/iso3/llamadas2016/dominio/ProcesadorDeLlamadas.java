package edu.uclm.esi.iso3.llamadas2016.dominio;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;

public class ProcesadorDeLlamadas {

	public void facturar() throws Exception {
		Vector<Cliente> clientes = loadClientes();
		for (Cliente cliente : clientes) {
			Factura factura = new Factura(cliente);
			factura.save();
		}

		String[] llamadas = this.leerLlamadas();
		if (llamadas != null && llamadas.length > 0) {
			for (String ficheroLlamada : llamadas) {
				FileInputStream fis = new FileInputStream(
						Constantes.directorioRaiz + Constantes.llamadasRecibidas + ficheroLlamada);
				byte[] b = new byte[fis.available()];
				fis.read(b);
				fis.close();
				String info = new String(b);
				String[] tokens = info.split("\t");
				String origen = tokens[0];
				String destino = tokens[1];
				int duracion = Integer.parseInt(tokens[2]);
				int year = Integer.parseInt(tokens[3]);
				int month = Integer.parseInt(tokens[4]);
				int day = Integer.parseInt(tokens[5]);
				int hour = Integer.parseInt(tokens[6]);
				int minute = Integer.parseInt(tokens[7]);
				int second = Integer.parseInt(tokens[8]);
				Llamada llamada = new Llamada(origen, destino, duracion, year, month, day, hour, minute, second);
				Cliente cliente = findClienteByNumero(clientes, origen);
				llamada.facturar(cliente);
			}
		}

		for (Cliente cliente : clientes) {
			FileReader fr = new FileReader(
					Constantes.directorioRaiz + Constantes.facturas + cliente.getTelefono() + ".txt");
			BufferedReader br = new BufferedReader(fr);
			for (int i = 0; i < 6; i++)
				br.readLine();
			String linea;
			double total = 0;
			while ((linea = br.readLine()) != null) {
				String[] tokens = linea.split("\t");
				double importe = Double.parseDouble(tokens[7]);
				total += importe;
			}
			fr.close();

			FileWriter fw = new FileWriter(
					Constantes.directorioRaiz + Constantes.facturas + cliente.getTelefono() + ".txt", true);
			fw.write("\n\tConsumos: " + total + " €\n");
			fw.close();
		}
	}

	private Cliente findClienteByNumero(Vector<Cliente> clientes, String origen) {
		long timeIni = System.nanoTime(), tiempo;
		for (Cliente c : clientes) {
			if (c.getTelefono().equals(origen)) {
				tiempo = System.nanoTime() - timeIni;
				System.out.println(tiempo);
				return c;
			}
		}
		tiempo = System.currentTimeMillis() - timeIni;
		System.out.println(tiempo);
		return null;
	}

	private String[] leerLlamadas() {
		File f = new File(Constantes.directorioRaiz + Constantes.llamadasRecibidas);
		String[] result = f.list();
		return result;
	}

	public Vector<Cliente> loadClientes() throws Exception {
		Vector<Cliente> result = new Vector<>();
		FileInputStream fis = new FileInputStream(Constantes.directorioRaiz + "/clientes.txt");
		DataInputStream dis = new DataInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(dis));
		String linea;
		Cliente cliente = null;
		while ((linea = br.readLine()) != null) {
			if (!linea.startsWith("\t")) {
				String[] tokens = linea.split("\t");
				int id = Integer.parseInt(tokens[0]);
				String nombre = tokens[1];
				String apellido1 = tokens[2];
				String apellido2 = tokens[3];
				String dni = tokens[4];
				String telefono = tokens[5];
				cliente = new Cliente(id, nombre, apellido1, apellido2, dni, telefono);
				result.add(cliente);
			} else {
				linea = linea.trim();
				String[] tokens = linea.split("\t");
				String[] ymd = tokens[0].split("/");
				GregorianCalendar fechaDeComienzo = new GregorianCalendar();
				fechaDeComienzo.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ymd[0]));
				fechaDeComienzo.set(Calendar.MONTH, Integer.parseInt(ymd[1]) - 1);
				fechaDeComienzo.set(Calendar.YEAR, Integer.parseInt(ymd[2]));

				if (tokens[1].equals("ON")) {
					Tarifa tarifa = loadTarifa(tokens);
					cliente.setTarifa(tarifa, fechaDeComienzo, null);
				} else {
					ymd = tokens[1].split("/");
					GregorianCalendar fechaDeFin = new GregorianCalendar();
					fechaDeFin.set(Calendar.DAY_OF_MONTH, Integer.parseInt(ymd[0]));
					fechaDeFin.set(Calendar.MONTH, Integer.parseInt(ymd[1]) - 1);
					fechaDeFin.set(Calendar.YEAR, Integer.parseInt(ymd[2]));
					Tarifa tarifa = loadTarifa(tokens);
					cliente.setTarifa(tarifa, fechaDeComienzo, fechaDeFin);
				}

			}
		}
		fis.close();
		return result;
	}

	private Tarifa loadTarifa(String[] tokens) throws Exception {
		String nombreTarifa = tokens[2];
		if (nombreTarifa.equals("TarifaFinDeSemana"))
			return new TarifaFinDeSemana();
		if (nombreTarifa.equals("TarifaDeTardes"))
			return new TarifaDeTardes();
		if (nombreTarifa.equals("TarifaDuo"))
			return new TarifaDuo(tokens[3]);
		if (nombreTarifa.equals("TarifaGrupo")) {
			TarifaGrupo tg = new TarifaGrupo();
			for (int i = 4; i < tokens.length; i++)
				tg.addFavorito(tokens[i]);
			return tg;
		}
		return null;
	}
}
