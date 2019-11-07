package br.com.heyjanac.desafio.service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.heyjanac.desafio.commons.Commons;
import br.com.heyjanac.desafio.exception.RegrasExcpetion;
import br.com.heyjanac.desafio.exception.ResourceNotFoundException;
import br.com.heyjanac.desafio.model.Cliente;
import br.com.heyjanac.desafio.repository.IClienteRepository;

@Service
public class ClienteService {

	private static final Logger log = LoggerFactory.getLogger(ClienteService.class);

	@Autowired
	private IClienteRepository clienteRepository;

	public List<Cliente> listarTodos() {
		log.info("Buscando todos os clientes.");
		return clienteRepository.findAll();
	}

	public Cliente buscarPorId(Long id) throws ResourceNotFoundException {
		log.info("Buscando cliente por ID {}: ", id);
		Optional<Cliente> cliente = clienteRepository.findById(id);
		if (cliente.isPresent())
			return cliente.get();

		throw new ResourceNotFoundException(String.format("Cliente nao encontrado pelo id {%s} ", id));
	}

	public Cliente buscarPorCpf(String cpf) {
		log.info("Buscando cliente por CPF {}", cpf);
		return clienteRepository.findFirstByCpf(cpf);
	}

	@Transactional
	public Cliente salvar(Cliente cliente) throws ResourceNotFoundException {

		log.info("Salvando cliente: {}", cliente);
		Cliente retorno = null;

		try {

			this.isCpfValido(cliente.getCpf());
			this.isEmailValido(cliente.getEmail());

			retorno = this.clienteRepository.save(cliente);

		} catch (Exception e) {
			throw new ResourceNotFoundException("Erro ao cadastrar cliente >> Divergencia[" + e.getMessage() + "].");
		}

		return retorno;
	}

	@Transactional
	public Cliente atualizar(Long id, Cliente cliente) throws ResourceNotFoundException {

		log.info("Atualizando cliente: {}", cliente);
		Cliente retorno = null;

		try {

			Optional<Cliente> optClienteAtualizar = clienteRepository.findById(id);

			if (optClienteAtualizar.isPresent()) {

				if (cliente.getCpf() != null)
					if (isCpfValido(cliente.getCpf())) {
						optClienteAtualizar.get().setCpf(cliente.getCpf());
					}

				if (cliente.getNome() != null)
					optClienteAtualizar.get().setNome(cliente.getNome());

				if (cliente.getEmail() != null) {
					if (isEmailValido(cliente.getEmail())) {
						optClienteAtualizar.get().setEmail(cliente.getEmail());
					}
				}

				retorno = this.clienteRepository.save(optClienteAtualizar.get());
			}

		} catch (Exception e) {
			throw new ResourceNotFoundException(String
					.format("Erro ao atualizar cliente pelo id {%s} >> Divergencia [" + e.getMessage() + "].", id));
		}

		return retorno;
	}

	public void deletar(Long id) {
		log.info("Deletando cliente: {}", id);
		Optional<Cliente> optClienteAtualizar = clienteRepository.findById(id);

		if (optClienteAtualizar.isPresent()) {
			this.clienteRepository.deleteAll();
		} else {
			log.info("Cliente: {} inexistente", id);
		}
	}

	public boolean isEmailValido(String enderecoEmail) throws RegrasExcpetion {
		boolean retorno = true;
		try {
			InternetAddress email = new InternetAddress(enderecoEmail);
			email.validate();
		} catch (AddressException e) {
			retorno = false;
			throw new RegrasExcpetion(" EMAIL [" + enderecoEmail + "] informado invalido");
		}
		return retorno;
	}

	public Boolean isCpfValido(String cpf) throws RegrasExcpetion {
		Boolean retorno = false;

		try {

			log.warn("cpf: " + cpf);

			validarExistenciaTamanhoCpf(cpf);
			validarFormatoCpf(cpf);
			retorno = Commons.isCpf(cpf);

			if (!retorno) {
				log.warn("Erro [isCpfValido] retorno: " + retorno);
				throw new RegrasExcpetion();
			}

		} catch (Exception e) {
			throw new RegrasExcpetion("ERRO VALIDACAO CPF [" + cpf + "].");
		}
		return retorno;
	}

	public Boolean validarExistenciaTamanhoCpf(String cpf) throws RegrasExcpetion {
		Boolean retorno = true;
		if (cpf == null || cpf.length() != 11) {
			log.warn("Erro [validarExistenciaTamanhoCpf] : " + cpf);
			retorno = false;
			throw new RegrasExcpetion();
		}
		return retorno;
	}

	public Boolean validarFormatoCpf(String cpf) throws RegrasExcpetion {

		Boolean retorno = false;
		Boolean retorno2 = false;

		String regex = "[0-9]{3}\\.?[0-9]{3}\\.?[0-9]{3}\\-?[0-9]{2}";

		Pattern pat = Pattern.compile(regex);

		Matcher mat = pat.matcher(cpf);
		retorno2 = mat.matches();
		log.warn("retorno2 = mat.matches(): " + retorno2);

		retorno = !mat.matches();
		log.warn("retorno = !mat.matches(): " + retorno);

		if (retorno) {
			log.warn("Erro [validarFormatoCpf] retorno: " + retorno);
			throw new RegrasExcpetion();
		}

		return retorno;
	}

}
