package br.com.cresol.desafio.resource;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.cresol.desafio.dto.Payload;
import br.com.cresol.desafio.entity.TbContrato;
import br.com.cresol.desafio.service.EmprestimoService;

/**
 * @author evandro
 *
 */

@Path("/emprestimo")
public class EmprestimoResource {
	
	@POST
	@Path("/simular")
	public Response simular(Payload payload) {
		TbContrato contrato = new EmprestimoService().simular(payload);
		ObjectMapper mapper = new ObjectMapper();
		String response = null;
		try {
			response = mapper.writeValueAsString(contrato);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return Response.serverError().build();
		}
		return Response.ok(response).build();
	}

}
