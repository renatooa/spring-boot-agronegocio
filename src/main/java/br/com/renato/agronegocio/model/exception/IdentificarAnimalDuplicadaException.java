package br.com.renato.agronegocio.model.exception;

public class IdentificarAnimalDuplicadaException extends FazendaException {

	private static final long serialVersionUID = 1L;

	public IdentificarAnimalDuplicadaException() {
		super("Ja existe um animal com esta identificacao");
	}
}