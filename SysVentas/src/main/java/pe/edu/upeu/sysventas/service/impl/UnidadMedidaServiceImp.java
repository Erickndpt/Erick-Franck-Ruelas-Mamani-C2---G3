package pe.edu.upeu.sysventas.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.model.UnidadMedida;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.repository.UnidadMedidaRepository;
import pe.edu.upeu.sysventas.service.IUnidadMedidaService;

@RequiredArgsConstructor
@Service
public class UnidadMedidaServiceImp extends CrudGenericoServiceImp<UnidadMedida, Long> implements IUnidadMedidaService {
    private final UnidadMedidaRepository unidadMedidaRepository;
    @Override
    protected ICrudGenericoRepository<UnidadMedida, Long> getRepo() {
        return unidadMedidaRepository;
    }
}
