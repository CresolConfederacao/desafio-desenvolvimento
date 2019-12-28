package br.com.cresol.desafio.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.manager.impl.ContratoEmprestimoManagerImpl;
import br.com.cresol.desafio.manager.impl.PessoaManagerImpl;
import br.com.cresol.desafio.service.ContratoEmprestimoService;
import br.com.cresol.desafio.service.EmprestimoService;
import br.com.cresol.desafio.service.PessoaService;
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
	public SimulacaoEmprestimo simular(SimularEmprestimoPayload payload) throws Exception {
		final PessoaService pessoaService = new PessoaService(PessoaManagerImpl.getInstance());
		final ContratoEmprestimoService contratoService = new ContratoEmprestimoService(
				ContratoEmprestimoManagerImpl.getInstance());
		return new EmprestimoService(pessoaService, contratoService).simular(payload);
	}

}
