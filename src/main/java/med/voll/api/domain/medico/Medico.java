package med.voll.api.domain.medico;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import med.voll.api.domain.direccion.Direccion;

@Entity(name = "Medico")
@Table(name="medicos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of="id")

public class Medico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String email;
    private String telefono;
    private String documento;
    private Boolean activo;
    @Enumerated(EnumType.STRING)
    private Especialidad especialidad;
    @Embedded
    private Direccion direccion;

    public Medico(DatosRegistroMedico datosRegistroMedico) {
        this.activo=true;
        this.nombre= datosRegistroMedico.nombre();
        this.email= datosRegistroMedico.email();
        this.telefono= datosRegistroMedico.telefono();
        this.documento= datosRegistroMedico.documento();
        this.especialidad= datosRegistroMedico.especialidad();
        this.direccion= new Direccion(datosRegistroMedico.direccion());
    }

    public void actualizarDatos(DatosActualizadosMedico datosActualizadosMedico) {
        if (datosActualizadosMedico.nombre() != null){
            this.nombre= datosActualizadosMedico.nombre();
        }
       if (datosActualizadosMedico.documento() != null){
           this.documento= datosActualizadosMedico.documento();
       }
       if (datosActualizadosMedico.direccion() != null){
           this.direccion= direccion.actualizarDireccion(datosActualizadosMedico.direccion());
       }

    }

    public void desactivarMedico() {
        this.activo=false;
    }
}
