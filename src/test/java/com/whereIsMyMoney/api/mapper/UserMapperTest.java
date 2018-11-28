package com.whereIsMyMoney.api.mapper;

import com.whereIsMyMoney.api.model.UserDto;
import com.whereIsMyMoney.domain.User;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class UserMapperTest {

    private static final String NAME = "userName";
    private static final Long ID = 1L;
    UserMapper userMapper = UserMapper.INSTANCE;


    @Test
    public void userDtoToUser() {
        UserDto userDto = new UserDto();
        userDto.setId(ID);
        userDto.setName(NAME);

        User user = userMapper.userDtoToUser(userDto);

        assertEquals(NAME, user.getName());
        assertEquals(ID, user.getId());
    }

    @Test
    public void userToUserDto() {
        User user = new User();
        user.setId(ID);
        user.setName(NAME);

        UserDto userDto = userMapper.userToUserDto(user);

        assertEquals(NAME, userDto.getName());
        assertEquals(ID, userDto.getId());
    }
}