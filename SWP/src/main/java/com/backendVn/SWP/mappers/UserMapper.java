package com.backendVn.SWP.mappers;

import com.backendVn.SWP.dtos.request.CustomerRegisterRequest;
import com.backendVn.SWP.dtos.request.CustomerUpdateInforRequest;
import com.backendVn.SWP.dtos.request.UserCreationRequest;
import com.backendVn.SWP.dtos.request.UserUpdateRequest;
import com.backendVn.SWP.dtos.response.TransactionResponse;
import com.backendVn.SWP.dtos.response.UserResponse;
import com.backendVn.SWP.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest userCreationRequest);
    void updateUser(@MappingTarget User user,UserUpdateRequest userUpdateRequest);
    UserResponse toUserResponse(User user);
    void updateUserPassword(@MappingTarget User user,String password);
    User toUser(CustomerRegisterRequest customerRegisterRequest);
    void updateTransactionResponseFromUser(User user, @MappingTarget TransactionResponse transactionResponse);
    void updateUser(@MappingTarget User user, CustomerUpdateInforRequest customerRegisterRequest);

}
