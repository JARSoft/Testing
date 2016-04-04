package edu.uclm.esi.iso3.llamadas2016.dominio.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import edu.uclm.esi.iso3.llamadas2016.dominio.Cliente;
import edu.uclm.esi.iso3.llamadas2016.dominio.Constantes;
import edu.uclm.esi.iso3.llamadas2016.dominio.Factura;
import edu.uclm.esi.iso3.llamadas2016.dominio.Llamada;
import edu.uclm.esi.iso3.llamadas2016.dominio.ProcesadorDeLlamadas;

public class TestProcesadorDeLlamadas {
	private Cliente lorena; // Id:1; FdS del 14/11/13 al 8/4/14; Tardes del
							// 9/4/14 en adelante
	private Cliente dolores; // Id: 2; Tardes desde el 1/7/2014
	private Cliente felipe; // Id: 11; TarifaGrupo desde el 8/5/2015. Llama
							// gratis a: 690569239 626942319 623637297
	private Cliente cristina; // Id: 7; desde el 23/6/14 al 8/12/14, duo al
								// 674029568; del 9/12/14 en adelante, duo al
								// 666955169
	private Cliente mariaAngeles; // Id: 3000;
	/**
	 * Tarifas de mariaAngeles: 5/10/2014 8/4/2015 TarifaDeTardes 9/4/2015
	 * 11/5/2015 TarifaDuo 648058963 12/5/2015 14/11/2015 TarifaDeTardes
	 * 15/11/2015 ON TarifaGrupo 641676470 672520495 679605561 604283181
	 */

	private ProcesadorDeLlamadas procesador;

	@Before
	public void crearContexto() throws IOException {
		limpiar(Constantes.directorioRaiz + Constantes.llamadasRecibidas);
		limpiar(Constantes.directorioRaiz + Constantes.llamadasProcesadas);
		limpiar(Constantes.directorioRaiz + Constantes.facturas);
		this.procesador = new ProcesadorDeLlamadas();

		this.lorena = new Cliente(1, "LORENA", "MOYA", "NAVARRO", "5366950", "600000000");

		Llamada lorenaLlamadaTarifaFdSEnMartes = new Llamada(this.lorena.getTelefono(), "626681482", 10, 2013, 11, 19,
				15, 0, 0);
		lorenaLlamadaTarifaFdSEnMartes.save();
		Llamada lorenaLlamadaTarifaFdSEnSabado = new Llamada(this.lorena.getTelefono(), "626681482", 10, 2013, 11, 23,
				15, 0, 0);
		lorenaLlamadaTarifaFdSEnSabado.save();
		Llamada lorenaLlamadaTardesPorLaMañana = new Llamada(this.lorena.getTelefono(), "600000001", 10, 2016, 2, 20, 9,
				0, 0);
		lorenaLlamadaTardesPorLaMañana.save();
		Llamada lorenaLlamadaTardesPorLaTarde = new Llamada(this.lorena.getTelefono(), "600000001", 10, 2016, 2, 20, 21,
				0, 0);
		lorenaLlamadaTardesPorLaTarde.save();

		this.dolores = new Cliente(2, "MARIA DOLORES", "GONZALEZ", "MORA", "8060469", "600000001");
		Llamada doloresLlamadaPorLaManana = new Llamada(this.dolores.getTelefono(), "666666666", 600, 2014, 7, 1, 10, 0,
				0);
		doloresLlamadaPorLaManana.save();
		Llamada doloresLlamadaPorLaTarde = new Llamada(this.dolores.getTelefono(), "666666666", 600, 2016, 1, 1, 22, 0,
				0);
		doloresLlamadaPorLaTarde.save();

		this.felipe = new Cliente(11, "FELIPE", "CASTRO", "CASTRO", "7343082", "600000010");
		Llamada felipeLlamadaAGrupo = new Llamada(this.felipe.getTelefono(), "626942319", 300, 2015, 5, 8, 12, 10, 0);
		felipeLlamadaAGrupo.save();
		Llamada felipeLlamadaFueraDeGrupo = new Llamada(this.felipe.getTelefono(), "612345678", 300, 2015, 5, 8, 12, 10,
				0);
		felipeLlamadaFueraDeGrupo.save();

		this.cristina = new Cliente(7, "MARIA CRISTINA", "ORTIZ", "PRIETO", "6007892", "600000006");
		Llamada cristinaLlamadaPeriodo1ASuDuo = new Llamada(cristina.getTelefono(), "674029568", 300, 2014, 7, 23, 11,
				0, 0);
		cristinaLlamadaPeriodo1ASuDuo.save();
		Llamada cristinaLlamadaPeriodo1FueraDeDuo = new Llamada(cristina.getTelefono(), "674029511", 300, 2014, 7, 23,
				11, 15, 0);
		cristinaLlamadaPeriodo1FueraDeDuo.save();
		Llamada cristinaLlamadaPeriodo2ASuDuo = new Llamada(cristina.getTelefono(), "666955169", 300, 2015, 7, 23, 11,
				0, 0);
		cristinaLlamadaPeriodo2ASuDuo.save();
		Llamada cristinaLlamadaPeriodo2FueraDeDuo = new Llamada(cristina.getTelefono(), "674029511", 300, 2015, 7, 23,
				11, 30, 0);
		cristinaLlamadaPeriodo2FueraDeDuo.save();

		this.mariaAngeles = new Cliente(3000, "MARIA ANGELES", "LEON", "IBAÑEZ", "9819847", "600002999");
		Llamada maTardesTarde = new Llamada(mariaAngeles.getTelefono(), "612312312", 60, 2014, 12, 5, 23, 50, 0);
		maTardesTarde.save();
		Llamada maTardesManana = new Llamada(mariaAngeles.getTelefono(), "612312312", 60, 2014, 12, 5, 15, 0, 0);
		maTardesManana.save();
		Llamada maDuo = new Llamada(mariaAngeles.getTelefono(), "648058963", 60, 2015, 5, 11, 15, 0, 0);
		maDuo.save();
		Llamada maNoDuo = new Llamada(mariaAngeles.getTelefono(), "648058963", 60, 2015, 5, 12, 15, 0, 0);
		maNoDuo.save();
	}

	@Test
	public void testFacturarLorena() {
		try {
			this.procesador.facturar();
			double importeSimulado = 0.12 + 0.01 * 10 + 0.16 + 0.015 * 10;
			double importeLorena = Factura.loadImporteDeFactura(lorena.getTelefono());
			System.out.println("Lorena : " + importeLorena + " " + importeSimulado);
			assertTrue(importeLorena == importeSimulado);

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFacturarMariaDolores() {
		try {
			this.procesador.facturar();
			double importeSimulado = 0.16 + 600 * 0.015;
			double importeMariaDolores = Factura.loadImporteDeFactura(dolores.getTelefono());
			System.out.println("MariaDolores : " + importeMariaDolores + " " + importeSimulado);
			assertTrue(importeMariaDolores == importeSimulado);

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFacturarFelipe() {
		try {
			this.procesador.facturar();
			double importeSimulado = 0.25 + 300 * 0.015;
			double importeVeronica = Factura.loadImporteDeFactura(felipe.getTelefono());
			System.out.println("Veronica : " + importeVeronica + " " + importeSimulado);
			assertTrue(importeVeronica == importeSimulado);

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	@Test
	public void testFacturarCristina() {
		try {
			this.procesador.facturar();
			double importeSimulado = (0.16 + 300 * 0.015) * 2;
			double importeCristina = Factura.loadImporteDeFactura(cristina.getTelefono());
			System.out.println("Cristina: " + importeCristina + " " + importeSimulado);
			assertTrue(importeCristina == importeSimulado);

		} catch (Exception e) {
			fail(e.toString());
		}
	}

	public void testMariaAngeles() {
		try {
			this.procesador.facturar();
			double importeSimulado = (60 * 60 * 0.015) + (0.16) + (60 * 60 * 0.15) + (0.16);
			double importeMariaAngeles = Factura.loadImporteDeFactura(mariaAngeles.getTelefono());
			System.out.println("Mariangelestarifa: " + importeMariaAngeles + "importesimulado: " + importeSimulado);
			assertTrue(importeMariaAngeles == importeSimulado);
		} catch (Exception e) {
			fail(e.toString());
		}

	}

	private void limpiar(String directorio) {
		File f = new File(directorio);
		String[] ficheros = f.list();
		if (ficheros == null)
			return;
		for (int i = ficheros.length - 1; i >= 0; i--) {
			File file = new File(directorio + ficheros[i]);
			file.delete();
		}
	}
}
