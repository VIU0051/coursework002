package javapages;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSF/JSFManagedBean.java to edit this template
 */

import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.*;
import jakarta.inject.Inject;
import java.util.List;
import entities.Reviews;

/**
 *
 * @author vishn
 */
@Named(value = "reviewBean")
@RequestScoped
public class ReviewBean {
    private Reviews newReview = new Reviews();
    @PersistenceContext
    private EntityManager reviewTable;
    
    @Inject
    private SessionBean sessionValidationBean;
    
    
    public List<Reviews> getReviews() {
        if("Admin".equals(sessionValidationBean.getUserRole())) {
            return reviewTable.createQuery("SELECT r FROM Reviews r", Reviews.class)
                    .getResultList();
        }
        
        return reviewTable.createQuery(
                "SELECT r FROM Reviews r WHERE r.userId.userId = :id",
                Reviews.class)
                .setParameter("id", sessionValidationBean.getUserID())
                .getResultList();
    }


    
   
}
