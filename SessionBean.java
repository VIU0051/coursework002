/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package javapages;

import jakarta.inject.Named;
import jakarta.enterprise.context.SessionScoped;
import java.io.Serializable;
import entities.Users;
import jakarta.faces.context.FacesContext;
import java.io.IOException;
import jakarta.inject.Inject;

/**
 *
 * @author vishn
 */
@Named(value = "sessionBean")
@SessionScoped
public class SessionBean implements Serializable {

    private boolean loggedIn = false;
    private String username;
    private String user_role;
    private String password;
    private int userId;
    
    @Inject
    private ArtistBean artistBean;

    public boolean isLoggedIn() {
        return loggedIn;
    }
    
    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public String getUserRole()
    {
        return user_role;
    }
    
    public int getUserID()
    {
        return userId;
    }
    
    
    public void login(Users user) {
        this.username = user.getUsername();
        this.user_role = user.getUserRole();
        this.loggedIn = true;
    }
    
    public void logout() {
        loggedIn = false;
        username = null;
        user_role = null;
    }
    
    public String loggingOut() {
        FacesContext.getCurrentInstance()
            .getExternalContext()
            .invalidateSession();
        
        return "/index.xhtml?faces-redirect=true";
    }
    
    public void checkAdmin() throws IOException {
        if(!loggedIn || !"Admin".equals(user_role)) {
            FacesContext.getCurrentInstance()
                    .getExternalContext()
                    .redirect("/index.xhtml");
        }
    }
}
