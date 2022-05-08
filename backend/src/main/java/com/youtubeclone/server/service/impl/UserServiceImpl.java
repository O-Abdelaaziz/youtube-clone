package com.youtubeclone.server.service.impl;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.youtubeclone.server.model.User;
import com.youtubeclone.server.model.Video;
import com.youtubeclone.server.payload.request.UserInfoRequest;
import com.youtubeclone.server.repository.UserRepository;
import com.youtubeclone.server.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.Set;


@Transactional
@Service
public class UserServiceImpl implements IUserService {

    @Value("${auth0.userInfoEndpoint}")
    private String userInfoEndpoint;

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public String registerUser(String tokenValue) {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(userInfoEndpoint))
                .setHeader("Authorization", String.format("Bearer %s", tokenValue))
                .build();

        HttpClient httpClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();


        try {
            HttpResponse<String> responseString = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            String body = responseString.body();

            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            UserInfoRequest userInfoDTO = objectMapper.readValue(body, UserInfoRequest.class);

            Optional<User> userBySubject = userRepository.findBySub(userInfoDTO.getSub());

            if (userBySubject.isPresent()) {
                return userBySubject.get().getId();
            } else {

                User user = new User();
                user.setFirstName(userInfoDTO.getGivenName());
                user.setLastName(userInfoDTO.getFamilyName());
                user.setFullName(userInfoDTO.getName());
                user.setEmailAddress(userInfoDTO.getEmail());
                user.setSub(userInfoDTO.getSub());

                User savedUser = userRepository.save(user);
                return savedUser.getId();
            }

        } catch (Exception exception) {
            throw new RuntimeException("Exception occurred while registering user", exception);
        }
    }

    @Override
    public User getCurrentUser() {
        String sub = ((Jwt) (SecurityContextHolder.getContext().getAuthentication().getPrincipal())).getClaim("sub");
        return userRepository.findBySub(sub)
                .orElseThrow(
                        () -> new IllegalArgumentException("Cannot find user with sub" + sub)
                );
    }

    @Override
    public void addToLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    @Override
    public boolean ifLikedVideo(String videoId) {
        return getCurrentUser()
                .getLikedVideos()
                .stream()
                .anyMatch(likedVideo -> likedVideo.equals(videoId));
    }

    @Override
    public boolean ifDisLikedVideo(String videoId) {
        return getCurrentUser()
                .getDisLikedVideos()
                .stream()
                .anyMatch(disLikedVideo -> disLikedVideo.equals(videoId));
    }

    @Override
    public void removeFromLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    @Override
    public void removeFromDisLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromDisLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    @Override
    public void addToDisLikedVideos(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addToDisLikedVideos(videoId);
        userRepository.save(currentUser);
    }

    @Override
    public void addVideoToHistory(String videoId) {
        User currentUser = getCurrentUser();
        currentUser.addVideoToHistory(videoId);
        userRepository.save(currentUser);
    }

    @Override
    public void subscribeUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.addToSubscribedUsers(userId);

        User user = getUserById(userId);
        user.addSubscribers(currentUser.getId());
        userRepository.save(currentUser);
        userRepository.save(user);
    }

    @Override
    public void unSubscribeUser(String userId) {
        User currentUser = getCurrentUser();
        currentUser.removeFromSubscribeTo(userId);

        User user = getUserById(userId);
        user.removeSubscribers(currentUser.getId());
        userRepository.save(currentUser);
        userRepository.save(user);
    }

    @Override
    public Set<String> userHistory(String userId) {
        User user = getUserById(userId);
        return user.getVideoHistory();
    }

    private User getUserById(String userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("Connot find user with id " + userId));
    }
}
