package br.com.cresol.desafio.resource;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimulacaoFalha;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.manager.impl.ContratoEmprestimoManagerImpl;
import br.com.cresol.desafio.manager.impl.PessoaManagerImpl;
import br.com.cresol.desafio.service.ContratoEmprestimoService;
import br.com.cresol.desafio.service.EmprestimoService;
import br.com.cresol.desafio.service.PessoaService;
import br.com.cresol.desafio.util.ValidacaoException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * @author evandro
 *
 */
@Api
@Path("/emprestimo")
public class EmprestimoResource {

	private static final Logger logger = Logger.getLogger(EmprestimoResource.class.getName());

	@POST
	@Path("/simular")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Resposta da requisição", response = SimulacaoEmprestimo.class),
			@ApiResponse(code = 400, message = "Problemas com parâmetros da requisição", response = SimulacaoFalha.class),
			@ApiResponse(code = 500, message = "Falha na requisição", response = SimulacaoFalha.class) })
	public Response simular(SimularEmprestimoPayload payload) {
		try {
			final PessoaService pessoaService = new PessoaService(PessoaManagerImpl.getInstance());
			final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(
					ContratoEmprestimoManagerImpl.getInstance());
			final SimulacaoEmprestimo simulacaoEmprestimo = new EmprestimoService(pessoaService, contratoService)
					.simular(payload);
			return Response.ok(simulacaoEmprestimo).build();

		} catch (Exception e) {
			final Status statusHttp;
			final String logMessage;
			if (e instanceof ValidacaoException) {
				statusHttp = Status.BAD_REQUEST;
				logMessage = "Problema com os parâmetros da requisição.";
			} else {
				statusHttp = Status.INTERNAL_SERVER_ERROR;
				logMessage = "Erro no processamento da requisição.";
			}
			final SimulacaoFalha falha = new SimulacaoFalha();
			falha.setDescricaoFalha(e.getMessage());
			logger.log(Level.SEVERE, logMessage, e);
			return Response.status(statusHttp).entity(falha).build();
		}
	}

}
