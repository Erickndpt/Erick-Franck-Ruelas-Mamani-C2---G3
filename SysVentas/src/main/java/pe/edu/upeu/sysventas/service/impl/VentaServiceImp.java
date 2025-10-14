package pe.edu.upeu.sysventas.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.edu.upeu.sysventas.model.Venta;
import pe.edu.upeu.sysventas.repository.ICrudGenericoRepository;
import pe.edu.upeu.sysventas.repository.VentaRepository;
import pe.edu.upeu.sysventas.service.IVentaService;
@RequiredArgsConstructor
 @Service
public class VentaServiceImp extends CrudGenericoServiceImp<Venta, Long> implements IVentaService {

    private final VentaRepository ventaRepository;

    @Override
    protected ICrudGenericoRepository<Venta, Long> getRepo() {
        return ventaRepository;
    }
}
