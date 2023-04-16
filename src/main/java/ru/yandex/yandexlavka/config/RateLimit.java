package ru.yandex.yandexlavka.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Component
@Order(1)
public class RateLimit implements Filter {

    private final HashMap<String, HashMap<String, Integer>> hashMap = new HashMap<>();
    private final List<String> getMapping = List.of("/couriers/assignments", "/couriers", "/couriers/",
            "/couriers/meta-info/", "/orders", "/orders/");
    private final List<String> postMapping = List.of("/couriers", "/orders", "/orders/complete", "/orders/assign");

    private final Integer rateLimit = 10;

    private final Long time = 1000L; //1c

    {
        HashMap<String, Integer> GetMethodHashMap = new HashMap<>();
        for (String key : getMapping) {
            GetMethodHashMap.put(key, rateLimit);
        }
        HashMap<String, Integer> PostMethodHashMap = new HashMap<>();
        for (String key : postMapping) {
            PostMethodHashMap.put(key, rateLimit);
        }
        hashMap.put("GET", GetMethodHashMap);
        hashMap.put("POST", PostMethodHashMap);

        Timer timer = new Timer("RateLimiter");
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                for (String key : hashMap.keySet()) {
                    HashMap<String, Integer> currentHashMap = hashMap.get(key);
                    currentHashMap.replaceAll((m, v) -> rateLimit);
                }
            }
        }, time, time);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HashMap<String, Integer> currentHashMap = hashMap.get(request.getMethod());
        String uri = request.getRequestURI().replaceAll("[0-9]", "");
        Integer remainsLimit = currentHashMap.get(uri);
        if (remainsLimit <= 0) {
            HttpServletResponse response = (HttpServletResponse) resp;
            response.setStatus(429);
            return;
        }
        currentHashMap.put(uri, remainsLimit - 1);
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
