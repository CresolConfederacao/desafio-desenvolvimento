package br.com.cresol.desafio.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import br.com.cresol.desafio.dto.SimulacaoEmprestimo;
import br.com.cresol.desafio.dto.SimularEmprestimoPayload;
import br.com.cresol.desafio.service.EmprestimoService;
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
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response  simular(SimularEmprestimoPayload payload) throws Exception {
		SimulacaoEmprestimo simulacaoEmprestimo = new EmprestimoService().simular(payload);
		
		if(simulacaoEmprestimo == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();			
		}
		return Response.ok(simulacaoEmprestimo).build();
		
	}
	
	@PUT
	@Path("/consultar")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response  consultar(SimularEmprestimoPayload payload) throws Exception {
		List<SimulacaoEmprestimo> lsSimulacaoEmprestimo = new EmprestimoService().consultar(payload);
		
		if(lsSimulacaoEmprestimo != null
				&& lsSimulacaoEmprestimo.size() == 0) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();			
		}
		return Response.ok(lsSimulacaoEmprestimo).build();
		
	}
		
	@DELETE
	@Path("/remover")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public Response  remover(SimulacaoEmprestimo simularEmprestimo) throws Exception {
		Long idRemovido = new EmprestimoService().remover(simularEmprestimo.getId());
		
		if(idRemovido == null) {
			return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();			
		}
		return Response.ok(idRemovido).build();
		
	}

}
