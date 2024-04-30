package com.example.demo.Services;

import com.example.demo.DAO.UserRepository;
import com.example.demo.Entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    public ResponseEntity<Object> createUser( UserEntity userEntity){
    try{
        UserEntity userEntityTest = userRepository.findByEmail(userEntity.getEmail());
        if(userEntityTest !=null){
            return new ResponseEntity<>("User already exists in database", HttpStatus.CONFLICT);

        }userRepository.save(userEntity);
        return new ResponseEntity<>("Successfully added user",HttpStatus.CREATED);
    }catch (Exception e){
        System.out.println(e.toString());
        return new ResponseEntity<>("An unexpected error has occurred, please check logs for more info",HttpStatus.BAD_REQUEST);
    }

    }

    public ResponseEntity<Object> findById(long id) {
        try{
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if(userEntity.isPresent())
                return new ResponseEntity<>(userEntity,HttpStatus.OK);
            return new ResponseEntity<>("User with user id "+id+" not found",HttpStatus.NOT_FOUND);


        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>("Unexpected error has occurred, check logs",HttpStatus.BAD_REQUEST);
        }
    }
}
