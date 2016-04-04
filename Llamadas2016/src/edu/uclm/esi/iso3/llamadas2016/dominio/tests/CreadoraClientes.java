package edu.uclm.esi.iso3.llamadas2016.dominio.tests;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.Vector;

import edu.uclm.esi.iso3.llamadas2016.dominio.Constantes;
import edu.uclm.esi.iso3.llamadas2016.dominio.Tarifa;
import edu.uclm.esi.iso3.llamadas2016.dominio.TarifaCliente;
import edu.uclm.esi.iso3.llamadas2016.dominio.TarifaDeTardes;
import edu.uclm.esi.iso3.llamadas2016.dominio.TarifaDuo;
import edu.uclm.esi.iso3.llamadas2016.dominio.TarifaFinDeSemana;
import edu.uclm.esi.iso3.llamadas2016.dominio.TarifaGrupo;

/****
 * ATENCIÓN: esta clase no hay que probarla. Se utilizó para crear la lista de
 * clientes y las tarifas de cada uno.
 */

public class CreadoraClientes {
	public static void main(String[] args) throws Exception {
		final int NUMERO_DE_CLIENTES = 3000;
		String nombresFileName = "nombres.txt";
		String apellidosFileName = "apellidos.txt";
		Vector<String> nombres = read(nombresFileName);
		Vector<String> apellidos = read(apellidosFileName);
		FileOutputStream fosClientes = new FileOutputStream(Constantes.directorioRaiz + "/clientes.txt");

		int cont = 1;
		Random dado = new Random();
		for (int i = 0; i < nombres.size() * apellidos.size(); i++) {
			int posNombre = dado.nextInt(nombres.size());
			int posApellido1 = dado.nextInt(apellidos.size());
			int posApellido2 = dado.nextInt(apellidos.size());
			int iDNI = 5000000 + dado.nextInt(5000000);
			int iTelefono = 600000000 + i;

			String nombre = nombres.get(posNombre);
			String apellido1 = apellidos.get(posApellido1);
			String apellido2 = apellidos.get(posApellido2);
			String dni = "" + iDNI;
			String telefono = "" + iTelefono;

			String lineaCliente = cont + "\t" + nombre + "\t" + apellido1 + "\t" + apellido2 + "\t" + dni + "\t"
					+ telefono + "\n";
			fosClientes.write(lineaCliente.getBytes());

			addTarifas(fosClientes, dado);
			cont++;
			if (cont > NUMERO_DE_CLIENTES)
				break;
		}
		fosClientes.close();
	}

	private static void addTarifas(FileOutputStream fosClientes, Random dado) throws Exception, IOException {
		float n = dado.nextFloat();
		int numTarifas;
		if (n < 0.40)
			numTarifas = 1;
		else if (n < 0.70)
			numTarifas = 2;
		else if (n < 0.90)
			numTarifas = 3;
		else
			numTarifas = 4;
		GregorianCalendar fechaComienzo = fechaAlAzar();
		Tarifa tarifa = tarifaAlAzar(dado);
		TarifaCliente tc;
		if (numTarifas == 1)
			tc = new TarifaCliente(tarifa, fechaComienzo, null);
		else
			tc = new TarifaCliente(tarifa, fechaComienzo, fechaAlAzarAfter(fechaComienzo));
		String lineaTarifa = "\t" + tc.toString();
		fosClientes.write(lineaTarifa.getBytes());

		for (int i = 1; i < numTarifas; i++) {
			fechaComienzo = new GregorianCalendar();
			fechaComienzo.setTimeInMillis(tc.getFechaDeFin().getTimeInMillis() + 3600 * 24 * 1000);
			n = dado.nextFloat();
			if (n < 0.25)
				tarifa = new TarifaDeTardes();
			else if (n < 0.50)
				tarifa = new TarifaDuo(telefonoAlAzar(dado));
			else if (n < 0.75)
				tarifa = new TarifaFinDeSemana();
			else {
				tarifa = new TarifaGrupo();
				TarifaGrupo tg = (TarifaGrupo) tarifa;
				int max = dado.nextInt(5);
				for (int k = 0; k < max; k++) {
					tg.addFavorito(telefonoAlAzar(dado));
				}
			}
			if (i < numTarifas - 1)
				tc = new TarifaCliente(tarifa, fechaComienzo, fechaAlAzarAfter(fechaComienzo));
			else
				tc = new TarifaCliente(tarifa, fechaComienzo, null);
			lineaTarifa = "\t" + tc.toString();
			fosClientes.write(lineaTarifa.getBytes());
		}
	}

	private static Tarifa tarifaAlAzar(Random dado) throws Exception {
		float n;
		Tarifa tarifa;
		n = dado.nextFloat();
		if (n < 0.25)
			tarifa = new TarifaDeTardes();
		else if (n < 0.50)
			tarifa = new TarifaDuo(telefonoAlAzar(dado));
		else if (n < 0.75)
			tarifa = new TarifaFinDeSemana();
		else {
			tarifa = new TarifaGrupo();
			TarifaGrupo tg = (TarifaGrupo) tarifa;
			int max = dado.nextInt(5);
			for (int k = 0; k < max; k++) {
				tg.addFavorito(telefonoAlAzar(dado));
			}
		}
		return tarifa;
	}

	private static String telefonoAlAzar(Random dado) {
		String r = "6";
		for (int i = 0; i < 8; i++)
			r += dado.nextInt(10);
		return r;
	}

	private static String tarifaAlAzar() {
		Random dado = new Random();
		int n = dado.nextInt(4);
		if (n == 0)
			return "TarifaDeTardes";
		if (n == 0)
			return "TarifaDuo";
		if (n == 0)
			return "TarifaFinDeSemana";
		return "TarifaGrupo";
	}

	private static Vector<String> read(String fileName) throws IOException {
		Vector<String> result = new Vector<>();
		FileInputStream fis = new FileInputStream(fileName);
		DataInputStream dis = new DataInputStream(fis);
		BufferedReader br = new BufferedReader(new InputStreamReader(dis));
		String linea;
		while ((linea = br.readLine()) != null) {
			result.add(linea);
		}
		fis.close();
		return result;
	}

	private static GregorianCalendar fechaAlAzarAfter(GregorianCalendar startingDate) {
		long milis = startingDate.getTimeInMillis();
		Random dado = new Random();
		long delta = (long) (dado.nextFloat() * 31536000000l);
		milis += delta;
		GregorianCalendar result = new GregorianCalendar();
		result.setTimeInMillis(milis);
		return result;
	}

	private static GregorianCalendar fechaAlAzar() {
		GregorianCalendar result = new GregorianCalendar();
		int year = randBetween(2012, 2015);
		result.set(GregorianCalendar.YEAR, year);
		int dayOfYear = randBetween(1, result.getActualMaximum(GregorianCalendar.DAY_OF_YEAR));
		result.set(GregorianCalendar.DAY_OF_YEAR, dayOfYear);
		return result;
	}

	private static int randBetween(int start, int end) {
		return start + (int) Math.round(Math.random() * (end - start));
	}
}
