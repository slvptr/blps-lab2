package blps.lab2.controller;

import blps.lab2.model.domain.Topic;
import blps.lab2.model.domain.TopicCategory;
import blps.lab2.model.requests.CreateTopicRequest;
import blps.lab2.model.responses.TopicView;
import blps.lab2.service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@RestController
@RequestMapping("api/v1/topic")
public class TopicController {
    private TopicService topicService;

    @Autowired
    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/{id}")
    public Topic getTopic(@PathVariable String id) {
        try {
            Optional<Topic> topic = topicService.findById(Long.parseLong(id));
            return topic.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public Long deleteTopic(@PathVariable String id) {
        try {
            Optional<Long> topicId = topicService.delete(Long.parseLong(id));
            return topicId.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping(value = "/{id}", consumes = "application/json")
    public Topic updateTopic(@PathVariable String id, @RequestBody CreateTopicRequest req) {
        try {
            Topic topic = topicService.update(Long.parseLong(id), req);
            if (topic == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND);
            return topic;
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/", consumes = "application/json")
    public TopicView createTopic(@RequestBody CreateTopicRequest req ) {
        try {
            Date currentDate = new Date();
            Topic topic = topicService.save(
                    new Topic(req.getTitle(),
                            req.getDescription(),
                            req.getContent(),
                            TopicCategory.valueOf(req.getCategory().toUpperCase()),
                            currentDate,
                            currentDate
                    ));
            return TopicView.fromTopic(topic);
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "There is no such topic category");
        }
    }
}