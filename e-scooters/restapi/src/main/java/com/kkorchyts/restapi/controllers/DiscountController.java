package com.kkorchyts.restapi.controllers;

import com.kkorchyts.dto.dtos.DiscountDto;
import com.kkorchyts.service.services.discount.DiscountService;
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
@RequestMapping(value = "/discounts")
public class DiscountController {

    private DiscountService discountService;

    @ApiOperation(value = "Return list of discounts", authorizations = {@Authorization(value = "jwtToken")})
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<Page<DiscountDto>> getAll(Pageable pageable) {
        return new ResponseEntity<>(discountService.getAll(pageable, null), HttpStatus.OK);
    }

    @ApiOperation(value = "Create new discount", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PostMapping(value = "/")
    @ResponseBody
    public ResponseEntity<DiscountDto> register(@RequestBody DiscountDto discountDto) {
        return new ResponseEntity<>(discountService.create(discountDto), HttpStatus.CREATED);
    }

    @ApiOperation(value = "Update discount", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @PutMapping(value = "/")
    @ResponseBody
    public ResponseEntity<DiscountDto> update(@RequestBody DiscountDto discountDto) {
        discountService.update(discountDto);
        return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    }

    @ApiOperation(value = "Get discount information", authorizations = {@Authorization(value = "jwtToken")})
    @GetMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<DiscountDto> get(@PathVariable(name = "id") Integer id) {
        DiscountDto discountDto = discountService.getById(id);
        if (discountDto != null) {
            return new ResponseEntity<>(discountDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @ApiOperation(value = "Delete discount", authorizations = {@Authorization(value = "jwtToken")})
    @Secured({"ROLE_ADMIN"})
    @DeleteMapping(value = "/{id}")
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable(name = "id") Integer id) {
        // todo validate
        discountService.delete(id);
        return new ResponseEntity<>(null, HttpStatus.OK);
    }

    @Autowired
    public void setDiscountService(DiscountService discountService) {
        this.discountService = discountService;
    }
}