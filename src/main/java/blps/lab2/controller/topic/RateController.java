package blps.lab2.controller.topic;

import blps.lab2.model.domain.topic.Topic;
import blps.lab2.model.responses.topic.TopicView;
import blps.lab2.service.rate.RateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("api/v1/rate")
public class RateController {
    private RateService rateService;

    @Autowired
    public RateController(RateService rateService) {
        this.rateService = rateService;
    }

    @PostMapping("/")
    public TopicView rateTopic(
            @RequestParam(value = "userId", defaultValue = "", required = false) String userId,
            @RequestParam(value = "topicId",  defaultValue = "", required = false) String topicId) {
        try {
            Topic topic = rateService.rateTopic(Long.parseLong(userId), Long.parseLong(topicId));
            return TopicView.fromTopic(topic);
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
