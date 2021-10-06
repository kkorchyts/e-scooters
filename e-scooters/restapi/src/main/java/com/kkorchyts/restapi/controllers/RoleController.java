package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dto.dtos.RoleDto;
import com.kkorchyts.service.services.role.RoleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/roles")
public class RoleController {

    private RoleService roleService;

    @ApiOperation(value = "Return list of roles", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<RoleDto>> getAll(Pageable pageable) {
        return new ResponseEntity<>(roleService.getAll(pageable, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new role", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<RoleDto> register(@RequestBody RoleDto roleDto) {
        return new ResponseEntity<>(roleService.create(roleDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update role", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<RoleDto> update(@RequestBody RoleDto roleDto) {
        roleService.update(roleDto);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Get role information", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<RoleDto> get(@PathVariable(name = "id") Integer id) {
        RoleDto roleDto = roleService.getById(id);
        if (roleDto != null) {
            return new ResponseEntity<>(roleDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete role", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Integer id) {
        // todo validate
        roleService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }
}
