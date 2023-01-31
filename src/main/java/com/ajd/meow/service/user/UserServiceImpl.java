package com.ajd.meow.service.user;

import com.ajd.meow.entity.UserMaster;
import com.ajd.meow.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public void insertMember(UserMaster user) {
        user.setUserJoinDate(LocalDateTime.now());
        user.setUserType("MEMBER");
        userRepository.save(user);
    }

    @Override
    public void updateMember(UserMaster user) {
        Optional<UserMaster> useruser=userRepository.findByUserName(user.getUserName());
        useruser.get().setNickName(user.getNickName());
        useruser.get().setDetailAddress(user.getDetailAddress());
        useruser.get().setPhoneType(user.getPhoneType());
        useruser.get().setPhoneNumber(user.getPhoneNumber());
        useruser.get().setUserPassword(user.getUserPassword());
        useruser.get().setIntroduce(user.getIntroduce());
        userRepository.save(useruser.get());
    }

    @Override
    public void deleteMember(UserMaster user) {
        userRepository.deleteById(user.getUserNo());
    }

    @Override
    public long getTotalRowCount() {
        return userRepository.count();
    }

    @Override
    public UserMaster getUserMaster(UserMaster user) {
        //Optional<UserMaster> findUser=userRepository.findById(user.getUserNo());
        Optional<UserMaster> findUser=userRepository.findByUserId(user.getUserId());
        /*if(findUser.isPresent()){return findUser.get();}
        else {return null;}*/
        if(findUser.isPresent()){return findUser.get();}
        else {return null;}
    }
}
