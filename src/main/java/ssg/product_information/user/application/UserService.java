package ssg.product_information.user.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssg.product_information.exception.user.NoSuchUserException;
import ssg.product_information.user.application.dto.request.UserCreateRequestDto;
import ssg.product_information.user.domain.User;
import ssg.product_information.user.domain.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Long create(UserCreateRequestDto requestDto) {
        User user = new User(requestDto.getName(), requestDto.getType());
        User savedUser = userRepository.save(user);
        return savedUser.getId();
    }

    @Transactional
    public void withdrawal(Long id) {
        User user = findById(id);
        user.withdrawal();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                             .orElseThrow(NoSuchUserException::new);
    }
}
