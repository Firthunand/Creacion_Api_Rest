package med.voll.api.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.direccion.DatosDireccion;
import med.voll.api.medico.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/medicos")
public class MedicoController {
@Autowired
    private MedicoRepository medicoRepository;
    @PostMapping
    public void registrarMedico(@RequestBody @Valid DatosRegistroMedico datosRegistroMedico){
        medicoRepository.save(new Medico(datosRegistroMedico));

    }
    @GetMapping
    public Page<DatosListadoMedico> listadoMedicos(@PageableDefault(size=10,sort="nombre") Pageable paginacion){
        //return medicoRepository.findAll(paginacion).map(DatosListadoMedico::new);
        return medicoRepository.findByActivoTrue(paginacion).map(DatosListadoMedico::new);
    }
    @PutMapping
    @Transactional
    public ResponseEntity actualizarMedico(@RequestBody @Valid DatosActualizadosMedico datosActualizadosMedico){
        Medico medico= medicoRepository.getReferenceById(datosActualizadosMedico.id());
        medico.actualizarDatos(datosActualizadosMedico);
        return ResponseEntity.ok(new DatosRespuestaMedico(medico.getId(), medico.getNombre(), medico.getEmail(),
                                medico.getTelefono(), medico.getEspecialidad().toString(),
                                new DatosDireccion(medico.getDireccion().getCalle(), medico.getDireccion().getDistrito(),
                                medico.getDireccion().getCiudad(), medico.getDireccion().getNumero(), medico.getDireccion().getComplemento())));

    }
    @DeleteMapping("/{id}")
    @Transactional
    //CON ESTE SE PODRIA DECIR QUE ES UN DELETE LOGICO AL DESACTIVAR MEDICO NO LO TRAERA CON LA LISTA
    public ResponseEntity eliminarMedico(@PathVariable Long id){
        Medico medico= medicoRepository.getReferenceById(id);
        medico.desactivarMedico();
        return ResponseEntity.noContent().build();

    }
    //CON ESTE DELETE ELIMINAMOS DIRECTO DE LA BASE DE DATOS
    /*public void eliminarMedico(@PathVariable Long id){
        Medico medico= medicoRepository.getReferenceById(id);
        medicoRepository.delete(medico);
        }*/
}
