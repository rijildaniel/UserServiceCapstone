package com.scaler.userservicecapstone.services;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scaler.userservicecapstone.dtos.SendEmailDto;
import com.scaler.userservicecapstone.exceptions.UserLoginException;
import com.scaler.userservicecapstone.models.Token;
import com.scaler.userservicecapstone.repositories.TokenRepository;
import com.scaler.userservicecapstone.models.User;
import com.scaler.userservicecapstone.repositories.RoleRepository;
import com.scaler.userservicecapstone.repositories.UserRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final TokenRepository tokenRepository;

    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder,
                           RoleRepository roleRepository, KafkaTemplate<String, String> kafkaTemplate,
                           TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.kafkaTemplate = kafkaTemplate;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public User createUser(String email, String password, List<String> roles) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRoles(roleRepository.findByNameIn(roles));

        SendEmailDto sendEmailDto = new SendEmailDto();
        sendEmailDto.setFrom("rijildaniel@gmail.com");
        sendEmailDto.setTo(email);
        sendEmailDto.setSubject("User Registration");
        sendEmailDto.setBody("Hello, Welcome to our site");

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            kafkaTemplate.send("sendUserEmail", objectMapper.writeValueAsString(sendEmailDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

        return userRepository.save(user);
    }

    @Override
    public Token login(String email, String password) throws UserLoginException {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            throw new UserLoginException("Invalid email address");
        }

        User user = userOptional.get();
        if(!bCryptPasswordEncoder.matches(password, user.getPassword())) {
            throw new UserLoginException("Invalid password");
        }

        Token token = new Token();
        token.setUser(user);
        token.setValue(RandomStringUtils.secureStrong().nextAlphanumeric(128));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 1);
        token.setExpiryAt(calendar.getTime());

        return tokenRepository.save(token);

    }

    @Override
    public Boolean validateToken(String token) {
        Token tokenModel = tokenRepository.findByValue(token).orElse(null);
        return tokenModel != null && !tokenModel.getExpiryAt().before(new Date());
    }


}
