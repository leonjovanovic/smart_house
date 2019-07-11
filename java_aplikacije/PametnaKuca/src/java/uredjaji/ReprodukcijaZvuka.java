/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uredjaji;

import entitetskeklase.Pesme;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.awt.Desktop;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URI;
import java.net.URISyntaxException;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author leon
 */
public class ReprodukcijaZvuka {
    
    @Resource (lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource (lookup="redPesma")
    private static Queue queue;
    
    
    public ReprodukcijaZvuka(){
    }
    
    public void reprodukuj(String pesma, int id){
        try {
            Map<String, String> properties = new HashMap<String, String>();  
            properties.put("javax.persistence.jdbc.user", "root");  
            properties.put("javax.persistence.jdbc.password", "Root 987");
            EntityManagerFactory emf = Persistence.createEntityManagerFactory("PametnaKucaPU", properties);
            EntityManager em = emf.createEntityManager();         
            Pesme pesme = new Pesme(id,pesma);
            boolean vecIma = false;
            try {      
                em.getTransaction().begin(); 
                Query q = em.createQuery ("SELECT count(x) FROM Pesme x");
                int brojPesama = ((Number)q.getSingleResult()).intValue();
                for(int i = 0; i<brojPesama;i++){   //Da li vec ima pesma
                    Pesme temp = em.find(Pesme.class, i+1);
                    if(temp.getNaziv().equalsIgnoreCase(pesma)){
                        vecIma = true;
                        break;
                    }
                }
                if(!vecIma)     //ako nema
                    em.persist(pesme);   
                em.getTransaction().commit();  
            }
            finally {
                if (em.getTransaction().isActive())          
                    em.getTransaction().rollback();  
            }
            emf.close();
            
            YouTubeApi temp = new YouTubeApi();
            String part = temp.findVideoID(pesma);
            String link = "https://www.youtube.com/watch?v="+part;
            if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
                Desktop.getDesktop().browse(new URI(link));
            }
            System.out.println("Pusta se pesma "+pesma+" pomocu internet pretrazivaca");
            
        } catch (GeneralSecurityException ex) {
            Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public List svePesme(int id){
        List lista = new ArrayList<String>();
        System.out.println("Korisnik sa id "+id+" pustao je sledece pesme: ");
        
        Map<String, String> properties = new HashMap<String, String>();  
        properties.put("javax.persistence.jdbc.user", "root");  
        properties.put("javax.persistence.jdbc.password", "Root 987");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PametnaKucaPU", properties);
        EntityManager em = emf.createEntityManager();
        try {      
            em.getTransaction().begin();
            Query q = em.createQuery ("SELECT count(x) FROM Pesme x");
            Number brojPesama = (Number) q.getSingleResult ();
            for(int i = 0; i<brojPesama.intValue();i++){
                Pesme pesme = em.find(Pesme.class, i+1);
                lista.add(pesme.getNaziv());
                System.out.println(pesme.getNaziv());
            }
            em.getTransaction().commit();  
        }
        finally {
            if (em.getTransaction().isActive())          
                em.getTransaction().rollback();  
        }
        emf.close();
        return lista;
    }
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        ReprodukcijaZvuka zvuk = new ReprodukcijaZvuka();
        
        while(true){
            Message message = consumer.receive();
            if(message instanceof ObjectMessage){
                try {
                    ObjectMessage obj = (ObjectMessage)message;
                    Zahtev zahtev = (Zahtev)obj.getObject();
                    
                    if(zahtev.getPesma()!=null){//pusta pesmu
                        Method method = zvuk.getClass().getMethod(zahtev.getMetoda(), String.class, int.class);
                        method.invoke(zvuk, zahtev.getPesma(), 0);
                    }
                    else{                       //ispisuje sve pesme
                        Method method = zvuk.getClass().getMethod(zahtev.getMetoda(), int.class);
                        method.invoke(zvuk, 0);                        
                    }
                    Thread.sleep(2000);
                    
                } catch (JMSException ex) {
                    Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
                }catch (NoSuchMethodException ex) {
                    Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(ReprodukcijaZvuka.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
                System.out.println("Primljena prazna poruka");
        }
    }
}
