package ru.progmatik.sarafan.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.progmatik.sarafan.domain.Message;
import ru.progmatik.sarafan.domain.Views;
import ru.progmatik.sarafan.repo.MessageRepository;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("message")
public class MessageController {

    private final MessageRepository messagesRepo;

    @Autowired
    public MessageController(MessageRepository messageRepository) {
        this.messagesRepo = messageRepository;
    }

    @GetMapping
    @JsonView(Views.IdName.class)
    public List<Message> list(){
        return messagesRepo.findAll();
    }

    @GetMapping("{id}")
    public Message getOne(@PathVariable("id") Message message){
        return message;
    }

    @PostMapping
    public Message create(@RequestBody Message message){
        message.setCreateDate(LocalDateTime.now());
        messagesRepo.save(message);

        return message;
    }

    @PutMapping("{id}")
    public Message update(@PathVariable("id") Message messageFromDb,
                          @RequestBody Message message
    ){
        BeanUtils.copyProperties(message, messageFromDb, "id");
        return messagesRepo.save(messageFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Message message){
        messagesRepo.delete(message);
    }
}
