package ru.progmatik.sarafan.controller;

import org.springframework.web.bind.annotation.*;
import ru.progmatik.sarafan.exceptions.NotFoundException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("message")
public class MessageController {
    private int counter = 4;

    private List<Map<String, String>> messages = new ArrayList<Map<String, String>>(){{
        add(new HashMap<String, String>(){{put("id", "1"); put("text", "first message");}});
        add(new HashMap<String, String>(){{put("id", "2"); put("text", "second message");}});
        add(new HashMap<String, String>(){{put("id", "3"); put("text", "third message");}});
    }};

    @GetMapping
    public List<Map<String, String>> list(){
        return messages;
    }

    @GetMapping("{id}")
    public Map<String, String> getOne(@PathVariable String id){
        return getMessageById(id);
    }

    private Map<String, String> getMessageById(@PathVariable String id) {
        return messages.stream()
                .filter(message-> message.get("id").equals(id))
                .findFirst()
                .orElseThrow(NotFoundException::new);
    }

    @PostMapping
    public Map<String, String> create(@RequestBody Map<String, String> message){
        message.put("id", String.valueOf(counter++));
        messages.add(message);

        return message;
    }

    @PutMapping("{id}")
    public Map<String, String> update(@PathVariable String id, @RequestBody Map<String, String> message){
        final Map<String, String> messageFromDb = getMessageById(id);

        messageFromDb.putAll(message);
        messageFromDb.put("id", id);
        return messageFromDb;
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable String id){
        final Map<String, String> message = getMessageById(id);
        messages.remove(message);
    }
}