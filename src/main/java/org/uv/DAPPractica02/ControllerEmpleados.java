package org.uv.DAPPractica02;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/empleados")
public class ControllerEmpleados {
     // singleton
    @Autowired
RepositoryEmpleados repositoryEmpleado;
    
    @GetMapping()
    public List<Empleado> list() {
        return repositoryEmpleado.findAll();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Empleado> get(@PathVariable long id) {
        
       Optional<Empleado> emp = repositoryEmpleado.findById(id);
       if (!emp.isPresent()){
           return ResponseEntity.notFound().build();
       }
        return ResponseEntity.ok(emp.get());
    }
    // terminar put y delete 
    @PutMapping("/{id}")
    public ResponseEntity<?> put(@PathVariable long id, @RequestBody Empleado empleado) {
        Optional<Empleado> resEmp = repositoryEmpleado.findById(id);
        if (!resEmp.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            
        }
        Empleado empleadoExisted = resEmp.get();
        empleadoExisted.setNombre(empleado.getNombre());
        empleadoExisted.setTelefono(empleado.getTelefono());
        empleadoExisted.setDireccion(empleado.getDireccion());
        
        repositoryEmpleado.save(empleadoExisted);
        
               
        return new ResponseEntity<>(empleadoExisted, HttpStatus.OK);
    }
    
    @PostMapping
    public ResponseEntity<Empleado> post(@RequestBody Empleado entrada) {
        
        Empleado empNew= repositoryEmpleado.save(entrada);
        return ResponseEntity.ok(empNew);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Empleado> delete(@PathVariable long id) {
        Optional<Empleado> emp = repositoryEmpleado.findById(id);
        if (!emp.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        repositoryEmpleado.deleteById(id);
        
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
    public void handleError() {
    }
    
}
