package com.minigee.app.model;

import com.minigee.app.base.BaseModel;

/**
 * Created by Zhou on 2015-10-19.
 */
public class User extends BaseModel{

    static private User user=null;

    static public void setUser(User user){
        User.user = user;
    }

    static public User getInstance(){
        if (User.user == null){
            User.user = new User();
        }
        return User.user;
    }



    private String id;
    private String name;
    private String password;
    private String avatar;
    private Role role;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private boolean isLogin = false;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Boolean getLogin () {
        return this.isLogin;
    }

    public void setLogin(boolean isLogin) {
        this.isLogin = isLogin;
    }


}
