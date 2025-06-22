package com.example.demo;

import org.springframework.web.bind.annotation.RestController;
import jakarta.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api")
public class HomeController {
    
    @GetMapping("/ip")
    public Map<String, String> getClientIp(HttpServletRequest request) {
        String clientIp = extractClientIp(request);
        Map<String, String> response = new HashMap<>();
        response.put("clientIp", clientIp);
        return response;
    }

    private String extractClientIp(HttpServletRequest request) {
        System.out.println("Request received from: " + request.getRemoteAddr());

        String ip = request.getHeader("X-Forwarded-For");
        if(ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            // X-Forwarded-For can contain multiple IPs, the first one is the client IP
            return ip.split(",")[0].trim();
        }
        ip = request.getHeader("X-Real-IP");
        if(ip != null && !ip.isEmpty() && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();
    }
}
