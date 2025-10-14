package pe.edu.upeu.sysventas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.model.Cliente;
import pe.edu.upeu.sysventas.repository.ClienteRepository;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.service.IClienteService;

@Service
@RequiredArgsConstructor
public class ClienteServiceImp extends CrudGenericoServiceImp<Cliente,String> implements IClienteService {
    private final ClienteRepository clienteRepository;

    @Override
    protected ICrudGenericoRepository<Cliente, String> getRepo() {
        return clienteRepository;
    }
}
