package blps.lab2.controller;

import blps.lab2.model.domain.Topic;
import blps.lab2.model.domain.TopicCategory;
import blps.lab2.model.requests.CreateTopicRequest;
import blps.lab2.model.responses.TopicView;
import blps.lab2.service.LikeService;
import blps.lab2.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("api/v1/like")
public class LikeController {
    private LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    // нужно как-то получить id пользователя
    @PutMapping(value = "/{id}", consumes = "application/json")
    public Topic likeTopic(@PathVariable String id) {
        try {
            Topic topic = likeService.likeTopic(userId, Long.parseLong(id));
            if (topic == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return topic;
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }


}
