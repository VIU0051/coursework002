/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */
package javapages;

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.*;
import java.util.List;
import entities.Users;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;

/**
 *
 * @author vishn
 */
@Named(value = "userBean")
@RequestScoped
public class UserBean {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private SessionBean sessionValidationBean;
    
    private String username;
    private String password;
    private boolean editing = false;
    
    public boolean isEditing() {
        return editing;
    }
    
    public void setEditing(boolean editing) {
        this.editing = editing;
    }
    
    public void enableEdit() {
        editing = true;
        username = null;
    }
    
    @Transactional
    public String register() {
        List<Users> existingUsers = em.createQuery(
                "Select u FROM Users u WHERE u.username = :username", Users.class)
                .setParameter("username", username)
                .getResultList();
        
        if (!existingUsers.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(
            null, 
            new FacesMessage(FacesMessage.SEVERITY_ERROR, "Username already exists", null)
            );
            return null;
        }
        
        Users user = new Users();
        user.setUsername(username);
        user.setPassword(password);
        user.setUserRole("Member");
        
        em.persist(user);
        
        return "index?faces-redirect=true";
    }
    
    @Transactional
    public String updateUsername() {
        try {
            Users user = em.createQuery(
            "Select u FROM Users u WHERE u.username = :u",
            Users.class)
            .setParameter("u", sessionValidationBean.getUsername())
            .getSingleResult();
            
            user.setUsername(username);
            em.merge(user);
            
            sessionValidationBean.setUsername(username);
            
            return "userprofile?faces-redirect=true";
        } catch (Exception e) {
            return null;
        }
    }
    
    public String login() {
        try {
            Users user = em.createQuery("Select u FROM Users u WHERE u.username = :u AND u.password = :p", Users.class)
            .setParameter("u", username)
            .setParameter("p", password)
            .getSingleResult();
            
           sessionValidationBean.login(user);
         
           if (user.getUserRole().equals("Admin")) {
               return "index?faces-redirect=true";
           } else if (user.getUserRole().equals("Member")) {
               return "index?faces-redirect=true";
           }
           else 
           {
               return "index?faces-redirect=true";
           }
        } catch (Exception e) {
            return null; 
        }
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getPassword() {
        return password;
    }
    
    public void setPassword(String password) {
        this.password = password;
    }
    
}
