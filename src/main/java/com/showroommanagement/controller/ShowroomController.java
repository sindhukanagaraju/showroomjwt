package com.showroommanagement.controller;

import com.showroommanagement.dto.ResponseDTO;
import com.showroommanagement.entity.Showroom;
import com.showroommanagement.service.ShowroomService;
import com.showroommanagement.util.Constant;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class ShowroomController {
    private final ShowroomService showroomService;

    public ShowroomController(final ShowroomService showroomService) {
        this.showroomService = showroomService;
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PostMapping("/showroom")
    public ResponseDTO createShowroom(@RequestBody final Showroom showroom) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.CREATE, this.showroomService.createShowroom(showroom));
    }

    @GetMapping("/showroom/{id}")
    public ResponseDTO retrieveShowroomById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.showroomService.retrieveShowroomById(id));
    }

    @GetMapping("/showroom/retrieve")
    public ResponseDTO retrieveShowroom() {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.RETRIEVE, this.showroomService.retrieveShowroom());
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PatchMapping("/showroom/{id}")
    public ResponseDTO patchById(@PathVariable Integer id, @RequestBody Showroom showroom) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.showroomService.patchById(showroom, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @PutMapping("/showroom/{id}")
    public ResponseDTO updateShowroomById(@PathVariable final Integer id, @RequestBody final Showroom showroom) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.UPDATE, this.showroomService.updateShowroomById(showroom, id));
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @DeleteMapping("/showroom/{id}")
    public ResponseDTO removeShowroomById(@PathVariable final Integer id) {
        return new ResponseDTO(HttpStatus.OK.value(), Constant.REMOVE, this.showroomService.removeShowroomById(id));
    }
}
