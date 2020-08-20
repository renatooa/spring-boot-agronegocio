package br.com.renato.agronegocio.controller.advice;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import br.com.renato.agronegocio.model.dto.MensagemDto;
import br.com.renato.agronegocio.model.exception.FazendaException;
import br.com.renato.agronegocio.model.exception.NaoEncontradoException;

@RestControllerAdvice
public class ExceptionHandler {

	@Autowired
	private MessageSource messageSource;

	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	@org.springframework.web.bind.annotation.ExceptionHandler
	public void handle(NaoEncontradoException naoEncontradoException) {
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler
	public MensagemDto handle(MethodArgumentNotValidException exception) {

		List<String> listaErros = extrairListaErros(exception.getBindingResult().getFieldErrors());

		MensagemDto mensagemDto = criarMensagemDto(listaErros);

		return mensagemDto;
	}

	@ResponseStatus(code = HttpStatus.BAD_REQUEST)
	@org.springframework.web.bind.annotation.ExceptionHandler
	public MensagemDto handle(FazendaException exception) {

		MensagemDto mensagemDto = criarMensagemDto(HttpStatus.BAD_REQUEST);

		mensagemDto.setDescricao(exception.getMessage());

		return mensagemDto;
	}

	@ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
	@org.springframework.web.bind.annotation.ExceptionHandler
	public MensagemDto handle(Throwable throwable) {

		String descricao = criarDescricao(throwable);

		return new MensagemDto(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), false, descricao);
	}

	private List<String> extrairListaErros(List<FieldError> fieldErrors) {

		List<String> listaErros = new ArrayList<String>();

		fieldErrors.forEach(erro -> {
			String mensagem = messageSource.getMessage(erro, LocaleContextHolder.getLocale());

			listaErros.add(MessageFormat.format("{0} - {1}", erro.getField(), mensagem));

		});

		return listaErros;
	}

	private MensagemDto criarMensagemDto(List<String> listaErros) {

		MensagemDto mensagemDto = criarMensagemDto(HttpStatus.BAD_REQUEST);
		mensagemDto.setDescricao(listaErros.stream().collect(Collectors.joining(", ")));

		return mensagemDto;
	}

	private MensagemDto criarMensagemDto(HttpStatus httpStatus) {
		MensagemDto mensagemDto = new MensagemDto(httpStatus.getReasonPhrase(), httpStatus.is2xxSuccessful());
		return mensagemDto;
	}

	private String criarDescricao(Throwable throwable) {

		String mensagemErro = throwable.getMessage();

		String descricao = throwable.getMessage() == null || throwable.getMessage().isEmpty()
				? throwable.getClass().getName()
				: mensagemErro;
		return descricao;
	}
}