package com.banco.connect.net.clientebankcadastro.controller;

import com.banco.connect.net.clientebankcadastro.entity.Cliente;
import com.banco.connect.net.clientebankcadastro.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {


    private final ClienteService clienteService;

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Cliente> buscarClienteId(@PathVariable Long id){
        return ResponseEntity.ok(clienteService.findByIdCliente(id));
    }
    @GetMapping("/procurar")
    public ResponseEntity<Cliente> buscarPorEmail(@RequestParam String email){
        return ResponseEntity.ok(clienteService.buscarByEmail(email).get());
    }


}
