package com.banco.connect.net.clientebankcadastro.controller;

import com.banco.connect.net.clientebankcadastro.entity.Cliente;
import com.banco.connect.net.clientebankcadastro.service.ClienteService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clientes")
@AllArgsConstructor
public class ClienteController {


    private final ClienteService clienteService;

    @PostMapping("/registro")
    public ResponseEntity<Cliente> cadastrarNovoCliente(@RequestBody Cliente cliente) {
        return ResponseEntity.ok(clienteService.cadastrarCliente(cliente));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<Cliente> buscarClienteId(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findByIdCliente(id));
    }

    @GetMapping("/{email}")
    public ResponseEntity<Cliente> buscarPorEmail(@PathVariable String email) {
        return ResponseEntity.ok(clienteService.buscarByEmail(email).get());
    }


}
