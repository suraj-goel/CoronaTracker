package request;

import data.User;

import java.io.Serializable;

public class SignupRequest extends Request implements Serializable
{
    private String password;
    private User user;
    public SignupRequest(String password, User user)
    {
        this.password=password;
        this.user=user;
    }
    public String getPassword()
    {
        return password;
    }
    public User getUser() throws CloneNotSupportedException
    {
        return (User) this.user.clone();
    }

    @Override
    public String getRequestCode(){
        return "signup";
    }
}
