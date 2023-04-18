package blps.lab2.service;

import blps.lab2.dao.TopicRepository;
import blps.lab2.dao.UserRepository;
import blps.lab2.model.domain.Topic;
import blps.lab2.model.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LikeService {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Autowired
    public LikeService(TopicRepository topicRepository,
                       UserRepository userRepository) {
        this.topicRepository = topicRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Topic likeTopic(long userId, long topicId){
        Optional<Topic> topicOptional = topicRepository.findById(topicId);
        if (topicOptional.isEmpty()) return null;

        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) return null;

        Topic topic = topicOptional.get();
        User user = userOptional.get();
        if (user.getRemainingLikes() > 0) {
            user.setRemainingLikes(user.getRemainingLikes() - 1);
            userRepository.save(user);
        }
        topic.setRate(topic.getRate() + 1);

        return topicRepository.save(topic);
    }
}
