package edu.uclm.esi.iso3.llamadas2016.dominio;

import java.util.Vector;

/***
 * 
 * Establecimiento: 25 céntimos; coste por segundo: 1,5 céntimos. Las llamadas a
 * los números favoritos no se cobran. Puede haber hasta 5 números favoritos.
 *
 */
public class TarifaGrupo extends Tarifa {
	private Vector<String> numerosFavoritos;

	public TarifaGrupo() {
		super(0.25, 0.015);
		this.numerosFavoritos = new Vector<>();
	}

	public void addFavorito(String numero) throws Exception {
		if (this.numerosFavoritos.size() > 5)
			throw new Exception("Ya hay 5 números favoritos");
		this.numerosFavoritos.add(numero);
	}

	public double getCosteLlamada(Llamada llamada) {
		for (String numeroFavorito : this.numerosFavoritos) {
			if (numeroFavorito.equals(llamada.getDestino()))
				return 0;
		}
		return establecimiento + costePorSegundo * llamada.getDuracion();
	}

	@Override
	public String toString() {
		String r = this.getClass().getSimpleName();
		for (String numero : numerosFavoritos) {
			r += "\t" + numero;
		}
		return r;
	}
}
