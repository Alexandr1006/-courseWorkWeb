package me.popov.courseworkweb1.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.popov.courseworkweb1.dto.SockDto;
import me.popov.courseworkweb1.exception.InSufficientSockQuantityException;
import me.popov.courseworkweb1.exception.InvalidSockRequestException;
import me.popov.courseworkweb1.model.Color;
import me.popov.courseworkweb1.model.Size;
import me.popov.courseworkweb1.model.Sock;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
@Service
public class SockService {

    private final ObjectMapper objectMapper;
    private final Map<Sock, Integer> socks = new HashMap<>();

    public SockService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;

    }

    public void addSock(SockDto sockDto) {
        validateRequest(sockDto);
        Sock sock = mapToSock(sockDto);
        if (socks.containsKey(sock)) {
            socks.put(sock, socks.get(sock) + sockDto.getQuantity());
        } else
            socks.put(sock, sockDto.getQuantity());
    }


    public void issueSock(SockDto sockDto) {

        decreaseSockQuantity(sockDto, true);
    }
    public void removeDefectiveSocks(SockDto sockDto) {
        decreaseSockQuantity(sockDto, false);
    }

    public void decreaseSockQuantity(SockDto sockDto, boolean isIssue){
        validateRequest(sockDto);
        Sock sock = mapToSock(sockDto);
        int sockQuentity = socks.getOrDefault(sock,0);
        if (sockQuentity >= sockDto.getQuantity()){
            socks.put(sock, sockQuentity - sockDto.getQuantity());
        } else {
            throw new InSufficientSockQuantityException("There is not socks");
        }
        if (isIssue){

        }
    }
    public int getSockQuantity(Color color, Size size, Integer cottonMin, Integer cottonMax) {
        int total = 0;
        for (Map.Entry<Sock, Integer> entry : socks.entrySet()) {
            if (color != null && !entry.getKey().getColor().equals(color)) {
                continue;
            }
            if (size != null && !entry.getKey().getSize().equals(size)) {
                continue;
            }
            if (cottonMin != null && entry.getKey().getCottonPercentage() < cottonMin) {
                continue;
            }
            if (cottonMax != null && entry.getKey().getCottonPercentage() > cottonMax) {
                continue;
            }
            total += entry.getValue();
        }
        return total;
    }
    public void validateRequest(SockDto sockDto){
        if(sockDto.getColor() == null || sockDto.getSize() == null){
            throw new InvalidSockRequestException("Все поля должны быть заполнены");
        }
        if (sockDto.getCottonPercentage() < 0 || sockDto.getCottonPercentage() > 100){
            throw new InvalidSockRequestException("Количество хлопка должно быть от 0 до 100");
        }
        if (sockDto.getQuantity()<= 0){
            throw new InvalidSockRequestException("Количество должно быть больше 0");
        }
    }
    private Sock mapToSock(SockDto sockDto){
        return new Sock(sockDto.getColor(),
                sockDto.getSize(),
                sockDto.getCottonPercentage());
    }
}
