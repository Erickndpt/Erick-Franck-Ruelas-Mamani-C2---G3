package pe.edu.upeu.sysventas.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pe.edu.upeu.sysventas.enums.TipoDocumento;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "upeu_cliente")
public class Cliente {
    @Id
    @Column(name = "dniruc", nullable = false, length = 12)
    private String dniruc;
    @Column(name = "nombres", nullable = false, length = 160)
    private String nombres;
    @Column(name = "apellidos", nullable = false, length = 160)
    private String apellidos;
    @Column(name = "direccion", nullable = false, length = 160)
    private String direccion;
    @Column(name = "telefono", nullable = false, length = 10)
    private String telefono;
    @Column(name = "celular", nullable = false, length = 10)
    private String celular;
    @Column(name = "email", nullable = false, length = 100)
    private String email;
    @Column(name = "estado", nullable = false, length = 10)
    private String estado;
    @Column(name = "id_cliente", nullable = false, length = 10)
    private long idcliente;
    @NotNull(message = "El nombre no puede estar vacío")
    @Size(min = 2, max = 120, message = "El nombre debe tener entre 2 y 120 caracteres")
    @Column(name = "rep_legal", length = 160)
    private String repLegal;
    @Column(name = "tipo_documento", nullable = false, length = 12)
    @Enumerated(EnumType.STRING)
    private TipoDocumento tipoDocumento;
}
