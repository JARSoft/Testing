package edu.uclm.esi.iso3.llamadas2016.dominio;

/***
 * 
 * Establecimiento: 16 céntimos; coste por segundo: 1,5 céntimos. Las llamadas
 * al número favorito no se cobran.
 *
 */
public class TarifaDuo extends Tarifa {
	private String numeroFavorito;

	public TarifaDuo(String numeroFavorito) {
		super(0.16, 0.015);
		this.numeroFavorito = numeroFavorito;
	}

	public double getCosteLlamada(Llamada llamada) {
		if (llamada.getDestino().equals(numeroFavorito))
			return 0;
		return establecimiento + costePorSegundo * llamada.getDuracion();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + "\t" + this.numeroFavorito;
	}
}
