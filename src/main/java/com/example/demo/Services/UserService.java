package com.example.demo.Services;

import com.example.demo.DAO.UserRepository;
import com.example.demo.Entities.UserEntity;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
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

        }userEntity.setStatus("Active");
        userRepository.save(userEntity);
        return new ResponseEntity<>("Successfully added user",HttpStatus.CREATED);
    }catch (Exception e){
        System.out.println(e.toString());
        return new ResponseEntity<>("An unexpected error has occurred, please check logs for more info",HttpStatus.BAD_REQUEST);
    }

    }

    public ResponseEntity<Object> findById(long id) {
        try{
            Optional<UserEntity> userEntity = userRepository.findById(id);
            if(userEntity.isPresent()) {
                if(userEntity.get().getStatus().equals("Active"))
                return new ResponseEntity<>(userEntity, HttpStatus.OK);
                else
                    return new ResponseEntity<>("User exists but is inactive",HttpStatus.OK);
            }
            return new ResponseEntity<>("User with user id "+id+" not found",HttpStatus.NOT_FOUND);


        }catch (Exception e){
            System.out.println(e.toString());
            return new ResponseEntity<>("Unexpected error has occurred, check logs",HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<Object> updateUser(UserEntity userEntity) {
        try{

            UserEntity userToUpdate = userRepository.findByEmail(userEntity.getEmail());
            if(userToUpdate == null)
                return new ResponseEntity<>("User with email "+userEntity.getEmail()+" not found",HttpStatus.NOT_FOUND);
            if(userEntity.getAge()!=0)
                userToUpdate.setAge(userEntity.getAge());
            if(userEntity.getFirstName()!=null)
                userToUpdate.setFirstName(userEntity.getFirstName());
            if(userEntity.getLastName()!=null)
                userToUpdate.setLastName(userEntity.getLastName());
            if(userEntity.getPhoneNumber()!=null)
                userToUpdate.setPhoneNumber(userEntity.getPhoneNumber());
            userRepository.save(userToUpdate);
            return new ResponseEntity<>(userToUpdate,HttpStatus.OK);
        }catch (Exception e){
            System.out.print(e.toString());
            return new ResponseEntity<>("An unexpected error has occurred, please check the logs for more info",HttpStatus.BAD_REQUEST);

        }
    }

    public ResponseEntity<Object> delete(String email) {
        try{
            UserEntity userEntity = userRepository.findByEmail(email);
            if(userEntity==null)
                return new ResponseEntity<>("Not found",HttpStatus.NOT_FOUND);
            if(StringUtils.isBlank(userEntity.getStatus()) || userEntity.getStatus().equals("Active")) {
                userEntity.setStatus("Inactive");
                userRepository.save(userEntity);
                return new ResponseEntity<>("User successfully deactivated",HttpStatus.OK);

            }
                return new ResponseEntity<>("User is already inactive",HttpStatus.OK);


        }catch (Exception e){
            System.out.print(e.toString());
            return new ResponseEntity<>("Unexpected error",HttpStatus.BAD_REQUEST);
        }
    }
}
