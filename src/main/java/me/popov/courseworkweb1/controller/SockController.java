package me.popov.courseworkweb1.controller;

import me.popov.courseworkweb1.dto.SockDto;
import me.popov.courseworkweb1.exception.InSufficientSockQuantityException;
import me.popov.courseworkweb1.model.Color;
import me.popov.courseworkweb1.model.Size;
import me.popov.courseworkweb1.service.SockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sock")
public class SockController {
    private final SockService sockService;

    public SockController(SockService sockService) {
        this.sockService = sockService;
    }

    @ExceptionHandler(InSufficientSockQuantityException.class)
    public ResponseEntity<String> handleInvalidException(InSufficientSockQuantityException invalidSockRequestException){
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }

    @PostMapping
    public void addSock(@RequestBody SockDto sockDto) {
        sockService.addSock(sockDto);
    }

    @PutMapping
    public void issueSocks(@RequestBody SockDto sockDto) {
        sockService.issueSock(sockDto);
    }

    @GetMapping
    public int getSocksCount(@RequestParam(required = false, name = "color") Color color,
                             @RequestParam(required = false, name = "size") Size size,
                             @RequestParam(required = false, name = "cottonMin") Integer cottonMin,
                             @RequestParam(required = false, name = "cottonMax") Integer cottonMax) {
        return sockService.getSockQuantity(color, size, cottonMin, cottonMax);
    }

    @DeleteMapping
    public void removeDefectiveSocks(@RequestBody SockDto sockDto){
        sockService.removeDefectiveSocks(sockDto);
    }

}
