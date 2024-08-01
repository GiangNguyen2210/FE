package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.CustomerRegisterRequest;
import com.backendVn.SWP.dtos.request.CustomerUpdateInforRequest;
import com.backendVn.SWP.dtos.request.UserCreationRequest;
import com.backendVn.SWP.dtos.request.UserUpdateRequest;
import com.backendVn.SWP.dtos.response.TransactionResponse;
import com.backendVn.SWP.dtos.response.UserResponse;
import com.backendVn.SWP.entities.User;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public User toUser(UserCreationRequest userCreationRequest) {
        if ( userCreationRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userName( userCreationRequest.getUserName() );
        user.password( userCreationRequest.getPassword() );
        user.email( userCreationRequest.getEmail() );
        user.address( userCreationRequest.getAddress() );
        user.title( userCreationRequest.getTitle() );
        user.cusName( userCreationRequest.getCusName() );
        user.phoneNum( userCreationRequest.getPhoneNum() );

        return user.build();
    }

    @Override
    public void updateUser(User user, UserUpdateRequest userUpdateRequest) {
        if ( userUpdateRequest == null ) {
            return;
        }

        user.setUserName( userUpdateRequest.getUserName() );
        user.setPassword( userUpdateRequest.getPassword() );
        user.setEmail( userUpdateRequest.getEmail() );
        user.setAddress( userUpdateRequest.getAddress() );
        user.setTitle( userUpdateRequest.getTitle() );
        user.setCusName( userUpdateRequest.getCusName() );
        user.setPhoneNum( userUpdateRequest.getPhoneNum() );
    }

    @Override
    public UserResponse toUserResponse(User user) {
        if ( user == null ) {
            return null;
        }

        UserResponse.UserResponseBuilder userResponse = UserResponse.builder();

        userResponse.id( user.getId() );
        userResponse.userName( user.getUserName() );
        userResponse.password( user.getPassword() );
        userResponse.email( user.getEmail() );
        userResponse.address( user.getAddress() );
        userResponse.title( user.getTitle() );
        userResponse.cusName( user.getCusName() );
        userResponse.phoneNum( user.getPhoneNum() );

        return userResponse.build();
    }

    @Override
    public void updateUserPassword(User user, String password) {
        if ( password == null ) {
            return;
        }

        user.setPassword( password );
    }

    @Override
    public User toUser(CustomerRegisterRequest customerRegisterRequest) {
        if ( customerRegisterRequest == null ) {
            return null;
        }

        User.UserBuilder user = User.builder();

        user.userName( customerRegisterRequest.getUserName() );
        user.password( customerRegisterRequest.getPassword() );
        user.email( customerRegisterRequest.getEmail() );
        user.title( customerRegisterRequest.getTitle() );

        return user.build();
    }

    @Override
    public void updateTransactionResponseFromUser(User user, TransactionResponse transactionResponse) {
        if ( user == null ) {
            return;
        }

        transactionResponse.setId( user.getId() );
        transactionResponse.setUserName( user.getUserName() );
        transactionResponse.setEmail( user.getEmail() );
        transactionResponse.setCusName( user.getCusName() );
        transactionResponse.setPhoneNum( user.getPhoneNum() );
    }

    @Override
    public void updateUser(User user, CustomerUpdateInforRequest customerRegisterRequest) {
        if ( customerRegisterRequest == null ) {
            return;
        }

        user.setEmail( customerRegisterRequest.getEmail() );
        user.setAddress( customerRegisterRequest.getAddress() );
        user.setCusName( customerRegisterRequest.getCusName() );
        user.setPhoneNum( customerRegisterRequest.getPhoneNum() );
    }
}
