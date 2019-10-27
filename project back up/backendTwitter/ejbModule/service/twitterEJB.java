package service;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import model.TwitterEntity;

/**
 * Session Bean implementation class twitterEJB
 */
@Stateless
@LocalBean
public class twitterEJB {

   @PersistenceContext
   private EntityManager em;
   
    public twitterEJB() {
        // TODO Auto-generated constructor stub
    }
    
    public void addTwitterMessage(TwitterEntity twitterentity)
    {
    	
    	em.persist(twitterentity);
    }
    
    

}
