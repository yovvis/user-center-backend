package com.yovv.usercenter.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yovv.usercenter.common.BaseResponse;
import com.yovv.usercenter.common.ErrorCode;
import com.yovv.usercenter.common.ResultUtils;
import com.yovv.usercenter.constant.UserConstant;
import com.yovv.usercenter.exception.BusinessException;
import com.yovv.usercenter.model.domain.User;
import com.yovv.usercenter.model.domain.register.UserLoginRequest;
import com.yovv.usercenter.model.domain.register.UserRegisterRequest;
import com.yovv.usercenter.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yovvis
 * @Description 用户接口
 * @date 2023/5/21 15:32
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/current")
    public BaseResponse<User> getCurrentUser(HttpServletRequest request) {
        User currentUser = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATUS);
        if (currentUser == null) {
            throw new BusinessException(ErrorCode.NO_LOGIN, "请登录！");
        }
        long userId = currentUser.getId();
        // TODO 用户校验
        User user = userService.getById(userId);
        User safetyUser = userService.getSafetyUser(user);
        return ResultUtils.success(safetyUser);
    }

    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        if (userRegisterRequest == null) {
            return null;
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        String planetCode = userRegisterRequest.getPlanetCode();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword, planetCode)) {
            return null;
        }
        long result = userService.userRegister(userAccount, userPassword, checkPassword, planetCode);
        return ResultUtils.success(result);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request) {
        if (userLoginRequest == null) {
            return null;
        }
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword)) {
            return null;
        }
        User user = userService.userLogin(userAccount, userPassword, request);
        return ResultUtils.success(user);
    }

    @GetMapping("/search")
    public BaseResponse<List<User>> searchUserList(String username, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "请求体为空");
        }
        QueryWrapper<User> queryUser = new QueryWrapper<>();
        if (StringUtils.isNotBlank(username)) {
            queryUser.like("username", username);
        }
        List<User> userList = userService.list(queryUser);
        List<User> result = userList.stream().map(user -> userService.getSafetyUser(user)).collect(Collectors.toList());
        return ResultUtils.success(result);
    }

    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody long id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            return null;
        }
        QueryWrapper<User> queryUser = new QueryWrapper<>();
        if (id <= 0) {
            return null;
        }
        boolean result = userService.removeById(id);
        return ResultUtils.success(result);
    }

    /**
     * 是否是管理员权限
     *
     * @param request
     * @return boolean
     **/
    private boolean isAdmin(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(UserConstant.USER_LOGIN_STATUS);
        if (user == null || user.getUserRole() != 1) {
            return false;
        }
        return true;
    }

    /**
     * 用户注销
     *
     * @param request
     * @return Integer
     **/
    @PostMapping("/logout")
    private BaseResponse<Integer> userLogOut(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        Integer result = userService.userLogOut(request);
        return ResultUtils.success(result);
    }
}
