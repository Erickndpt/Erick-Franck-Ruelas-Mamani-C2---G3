package pe.edu.upeu.sysventas.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.model.VentCarrito;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.repository.VentCarritoRepository;
import pe.edu.upeu.sysventas.service.IVentCarritoService;
@RequiredArgsConstructor
@Service
public class VentCarritoServiceImp extends CrudGenericoServiceImp<VentCarrito, Long> implements IVentCarritoService {

    private final VentCarritoRepository carritoRepository;

    @Override
    protected ICrudGenericoRepository<VentCarrito, Long> getRepo() {
        return carritoRepository;
    }
}
