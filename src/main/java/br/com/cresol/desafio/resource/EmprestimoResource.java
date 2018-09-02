package br.com.cresol.desafio.resource;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response.Status;

import br.com.cresol.desafio.dto.Cliente;
import br.com.cresol.desafio.dto.ContratoEmprestimo;
import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.service.ClienteService;
import br.com.cresol.desafio.service.EmprestimoService;
import br.com.cresol.desafio.utils.Validator;
import io.swagger.annotations.Api;

/**
 * @author evandro
 *
 */
@Api
@Path("/emprestimo")
public class EmprestimoResource {
	
	@POST
	@Path("/simular")
	public SimulacaoEmprestimo simular(SimularEmprestimoPayload payload) {
		boolean isCPFValido = Validator.validaCPF(payload.getCpf());
		boolean isEmailValido = Validator.validaEmail(payload.getEmail());
		boolean isNomeValido = payload.getNome() != null && !payload.getNome().isEmpty(); 
		if (payload.getQuantidadeDeParcelas() < 1 || payload.getQuantidadeDeParcelas() > 24
				|| payload.getValorDoContrato() <= 0 || !isCPFValido || !isEmailValido || !isNomeValido) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		
		new ClienteService().adicionaCliente(payload.getCpf(), payload.getNome(), payload.getEmail());
		return new EmprestimoService().simular(payload);
	}
	
	@GET
	@Path("buscarSimulacoes/{cpf}/{email}")
	public List<SimulacaoEmprestimo> buscarSimulacoes(@PathParam("cpf") String cpf, @PathParam("email") String email) {
		boolean isCPFValido = Validator.validaCPF(cpf);
		boolean isEmailValido = Validator.validaEmail(email);
		if (!isCPFValido || !isEmailValido) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		Cliente cliente = new ClienteService().buscaCliente(cpf, email);
		if (cliente == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}
		return new EmprestimoService().buscarSimulacoes(cliente);
	}
	
	@DELETE
	@Path("removerSimulacao/{numeroDoContrato}")
	public void removerSimulacao(@PathParam("numeroDoContrato") String numeroDoContrato) {
		boolean isNumeroDoContratoValido = numeroDoContrato.length() == 14;
		if (!isNumeroDoContratoValido) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		try {
			new EmprestimoService().removerSimulacao(numeroDoContrato);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}
	
	@GET
	@Path("contratar/{numeroDoContrato}")
	public ContratoEmprestimo contratar(@PathParam("numeroDoContrato") String numeroDoContrato) {
		boolean isNumeroDoContratoValido = numeroDoContrato.length() == 14;
		if (!isNumeroDoContratoValido) {
			throw new WebApplicationException(Status.BAD_REQUEST);
		}
		try {
			return new EmprestimoService().contratar(numeroDoContrato);
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(Status.NOT_FOUND);
		}
	}

}
