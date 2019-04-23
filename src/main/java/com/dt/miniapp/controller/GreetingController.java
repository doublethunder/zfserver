package com.dt.miniapp.controller;

import com.dt.miniapp.model.Greeting;
import com.dt.miniapp.util.JsonConvert;
import com.dt.miniapp.util.JsonpString;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping("/greeting")
    public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
        return new Greeting(counter.incrementAndGet(),
                String.format(template, name));
    }

    @RequestMapping("/jsonp")
    public String jsonp(@RequestParam(value = "callback", defaultValue = "callback") String callback, @RequestParam(value = "name", defaultValue = "World") String name) {
        Greeting greeting = new Greeting(counter.incrementAndGet(), String.format(template, name));
        return JsonpString.jsonp(JsonConvert.serializeObject(greeting), callback);
    }
}
