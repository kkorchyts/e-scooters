package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dto.dtos.TariffDto;
import com.kkorchyts.service.services.tariff.TariffService;
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
@RequestMapping(value = "/tariffs")
public class TariffController {

    private TariffService tariffService;

    @ApiOperation(value = "Return list of tariffs", authorizations = {@Authorization(value = "jwtToken")})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<TariffDto>> getAll(Pageable pageable) {
        return new ResponseEntity<>(tariffService.getAll(pageable, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new tariff", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<TariffDto> register(@RequestBody TariffDto tariffDto) {
        return new ResponseEntity<>(tariffService.create(tariffDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update tariff", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<TariffDto> update(@RequestBody TariffDto tariffDto) {
        tariffService.update(tariffDto);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Get tariff information", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<TariffDto> get(@PathVariable(name = "id") Integer id) {
        TariffDto tariffDto = tariffService.getById(id);
        if (tariffDto != null) {
            return new ResponseEntity<>(tariffDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete tariff", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Integer id) {
        // todo validate
        tariffService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Autowired
    public void setTariffService(TariffService tariffService) {
        this.tariffService = tariffService;
    }
}
