package com.taller2.practica1.controllers;

import com.taller2.practica1.models.DAO.IClienteDao;
import com.taller2.practica1.models.Entity.Cliente;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/clientes")
public class ClienteController {

    @Autowired
    private IClienteDao clienteDao;

    @GetMapping("/listarClientes")
    public String Listar(Model model) {
        model.addAttribute("titulo", "Listado de Clientes");
        model.addAttribute("clientes", clienteDao.findAll());
        return "listarClientes";
    }

    @GetMapping("/formClientes")
    public String crear(Model model) {
        Cliente cliente = new Cliente();
        model.addAttribute("titulo", "Formulario de Cliente");
        model.addAttribute("cliente", cliente);
        return "formClientes";
    }

    @PostMapping("/formClientes")
    public String Guardar(@Valid Cliente cliente, BindingResult result, Model model) {
        System.out.println("Cliente recibido: " + cliente);

        if (result.hasErrors()) {
            System.out.println("Errores de validación: " + result.getAllErrors());
            model.addAttribute("titulo", "Formulario de Cliente");
            return "formClientes";
        }

        clienteDao.save(cliente);
        return "redirect:/clientes/listarClientes";
    }


    @GetMapping("/formClientes/{id}")
    public String Editar(@PathVariable Long id, Model model) {
        Cliente cliente = null;

        if (id > 0) {
            cliente = clienteDao.findOne(id);
        } else {
            return "redirect:/clientes/listarClientes";
        }
        model.addAttribute("cliente", cliente);
        model.addAttribute("titulo", "Editar Cliente");
        return "formClientes";
    }

    @GetMapping("/eliminar/{id}")
    public String Eliminar(@PathVariable Long id) {
        if (id > 0) {
            clienteDao.Delete(id);
        }
        return "redirect:/clientes/listarClientes";
    }

}
