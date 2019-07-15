package com.example.springbootjwttest.dto;

import java.util.List;

import com.example.springbootjwttest.entity.Role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDataDto {
	 @ApiModelProperty(position = 0)
	  private String username;
	  @ApiModelProperty(position = 1)
	  private String email;
	  @ApiModelProperty(position = 2)
	  private String password;
	  @ApiModelProperty(position = 3)
	  private List<Role> roles;
}
