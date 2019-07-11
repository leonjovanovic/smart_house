/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uredjaji;


import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.DatabaseReader.Builder;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.record.Location;
import entitetskeklase.Obaveze;
import entitetskeklase.Pesme;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
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
public class Planer {
   
    @Resource (lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource (lookup="redAlarm")
    private static Queue queue1;
    
    @Resource (lookup="redPlaner")
    private static Queue queue2; 
    
    private List obaveze;
    
    public Planer(){
        obaveze = new ArrayList<Obaveza>();
    }
    
    public void unesiObavezu(String ob){
        Map<String, String> properties = new HashMap<String, String>();  
        properties.put("javax.persistence.jdbc.user", "root");  
        properties.put("javax.persistence.jdbc.password", "Root 987");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PametnaKucaPU", properties);
        EntityManager em = emf.createEntityManager();         
        Obaveze obaveza = new Obaveze(0, ob);
        boolean vecIma = false;
        try {      
            em.getTransaction().begin(); 
            Query q = em.createQuery ("SELECT count(x) FROM Obaveze x");
            int brojObaveza = ((Number)q.getSingleResult()).intValue();
            for(int i = 0; i<brojObaveza;i++){   //Da li vec ima obaveza
                Obaveze temp = em.find(Obaveze.class, i+1);
                if(temp.getOpisObaveza().equalsIgnoreCase(ob)){
                    vecIma = true;
                    break;
                }
            }
            if(!vecIma)     //ako nema
                em.persist(obaveza);   
            em.getTransaction().commit();  
        }
        finally {
            if (em.getTransaction().isActive())          
                em.getTransaction().rollback();  
        }
        emf.close();
    }
    
    public void unesiObavezu(String ob, String destinacija){
        Map<String, String> properties = new HashMap<String, String>();  
        properties.put("javax.persistence.jdbc.user", "root");  
        properties.put("javax.persistence.jdbc.password", "Root 987");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PametnaKucaPU", properties);
        EntityManager em = emf.createEntityManager();         
        Obaveze obaveza = new Obaveze(0, ob);
        obaveza.setDestinacija(destinacija);
        boolean vecIma = false;
        try {      
            em.getTransaction().begin(); 
            Query q = em.createQuery ("SELECT count(x) FROM Obaveze x");
            int brojObaveza = ((Number)q.getSingleResult()).intValue();
            for(int i = 0; i<brojObaveza;i++){   //Da li vec ima obaveza
                Obaveze temp = em.find(Obaveze.class, i+1);
                if(temp.getOpisObaveza().equalsIgnoreCase(ob)){
                    vecIma = true;
                    break;
                }
            }
            if(!vecIma)     //ako nema
                em.persist(obaveza);   
            em.getTransaction().commit();  
        }
        finally {
            if (em.getTransaction().isActive())          
                em.getTransaction().rollback();  
        }
        emf.close();
    }
    
    public void izlistaj(){
        Map<String, String> properties = new HashMap<String, String>();  
        properties.put("javax.persistence.jdbc.user", "root");  
        properties.put("javax.persistence.jdbc.password", "Root 987");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PametnaKucaPU", properties);
        EntityManager em = emf.createEntityManager();
        try {      
            em.getTransaction().begin();
            Query q = em.createQuery ("SELECT count(x) FROM Obaveze x");
            Number brojObaveza = (Number) q.getSingleResult ();
            for(int i = 0; i<brojObaveza.intValue();i++){
                Obaveze obaveza = em.find(Obaveze.class, i+1);
                System.out.println("Obaveza: "+obaveza.getOpisObaveza()+" Destinacija: "+obaveza.getDestinacija());
            }
            em.getTransaction().commit();  
        }
        finally {
            if (em.getTransaction().isActive())          
                em.getTransaction().rollback();  
        }
        emf.close();
    }
    
    public void menjajObavezu(Obaveza stara, Obaveza nova){
        Map<String, String> properties = new HashMap<String, String>();  
        properties.put("javax.persistence.jdbc.user", "root");  
        properties.put("javax.persistence.jdbc.password", "Root 987");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PametnaKucaPU", properties);
        EntityManager em = emf.createEntityManager();
        try {      
            em.getTransaction().begin();
            Query q = em.createQuery ("SELECT count(x) FROM Obaveze x");
            int brojObaveza = ((Number)q.getSingleResult()).intValue();
            for(int i = 0; i<brojObaveza;i++){   //Da li vec ima obaveza
                Obaveze temp = em.find(Obaveze.class, i+1);
                if(temp.getOpisObaveza().equalsIgnoreCase(stara.getObaveza())){
                    temp.setOpisObaveza(nova.getObaveza());
                    if(nova.getDestinacija()!=null)
                        temp.setDestinacija(nova.getDestinacija());
                    break;
                }
            }            
            em.getTransaction().commit();  
        }
        finally {
            if (em.getTransaction().isActive())          
                em.getTransaction().rollback();  
        }
        emf.close();
    }
    
    public void obrisiObavezu(Obaveza obaveza){
        Map<String, String> properties = new HashMap<String, String>();  
        properties.put("javax.persistence.jdbc.user", "root");  
        properties.put("javax.persistence.jdbc.password", "Root 987");
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("PametnaKucaPU", properties);
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query q = em.createQuery ("SELECT MAX(x.idObaveza) FROM Obaveze x");
            int brojObaveza = ((Number)q.getSingleResult()).intValue();
            for(int i = 0; i<brojObaveza;i++){   //Da li vec ima obaveza
                Obaveze temp = em.find(Obaveze.class, i+1);
                if(temp==null)continue;
                if(temp.getOpisObaveza().equalsIgnoreCase(obaveza.getObaveza())){
                    em.remove(temp);
                    break;
                }
            }     
            q = em.createQuery("SELECT COUNT(x) FROM Obaveze x");
            brojObaveza = ((Number)q.getSingleResult()).intValue()+1;            
            q = em.createNativeQuery("ALTER TABLE Obaveze AUTO_INCREMENT = :brojObaveza;");
            
            q.setParameter("brojObaveza", brojObaveza+1);
            em.getTransaction().commit();  
        }
        finally {
            if (em.getTransaction().isActive())          
                em.getTransaction().rollback();  
        }
        emf.close();
    }
    
    public double kalkulatorRazdaljine(Point2D.Double a, Point2D.Double b){
        double distance = a.distance(b)*100; //*100 da bi dobili kilometre otprilike
        double speed = 30;  //30km/h
        return distance/speed;  //u satima
    }
    
    public double kalkulatorRazdaljine(Point2D.Double b){
        //try {
            File db = new File("/home/leon/Downloads/GeoLite2-City_20190528/GeoLite2-City.mmdb");
            //Builder builder = new Builder(db);
            //DatabaseReader dr = builder.build();
            //InetAddress addr = InetAddress.getByName("91.185.120.162");
            //CityResponse city = dr.city(addr);
            //Location myCoords = city.getLocation();            
            //Point2D a = new Point(myCoords.getLatitude().intValue(), myCoords.getLatitude().intValue());
            Point2D.Double a = new Point.Double(44.7947,20.3738);
            
            double distance = a.distance(b)*100;
            double speed = 30;
            return distance/speed;
        //} catch (IOException ex) {
        //    Logger.getLogger(Planer.class.getName()).log(Level.SEVERE, null, ex);
        //} catch (GeoIp2Exception ex) {
        //    Logger.getLogger(Planer.class.getName()).log(Level.SEVERE, null, ex);
        //}
        //return 0;
    }
    
    public ZonedDateTime aktivirajPodsetnik(Obaveza o, ZonedDateTime vreme, Point2D.Double mesto){
        long sati = (long)this.kalkulatorRazdaljine(mesto);
        //Alarm alarm = new Alarm();
        //alarm.postaviAlarm1(vreme.minusHours(sati));
        return vreme.minusHours(sati);
    }
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        JMSConsumer consumer = context.createConsumer(queue2);
        Destination destination = queue1;
        Planer planer = new Planer();
        
        while(true){    
            Message message = consumer.receive();
            if(message instanceof ObjectMessage){
                try {
                    ObjectMessage obj = (ObjectMessage)message;
                    Zahtev zahtev = (Zahtev)obj.getObject();
                    Method method;
                    
                    if(zahtev.getObaveza()!=null){
                        method = planer.getClass().getMethod(zahtev.getMetoda(), String.class, String.class);
                        method.invoke(planer, zahtev.getObaveza(), zahtev.getDestinacija());
                    } else if(zahtev.getStara()!=null){
                        method = planer.getClass().getMethod(zahtev.getMetoda(), Obaveza.class, Obaveza.class);
                        method.invoke(planer, zahtev.getStara(), zahtev.getNova());
                    } else if(zahtev.getObavezaObj()!=null){                    
                        method = planer.getClass().getMethod(zahtev.getMetoda(), Obaveza.class);
                        method.invoke(planer, zahtev.getObavezaObj());
                    }else if(zahtev.getStart()!=null){                    
                        method = planer.getClass().getMethod(zahtev.getMetoda(), Point2D.Double.class, Point2D.Double.class);
                        method.invoke(planer, zahtev.getStart(), zahtev.getEnd());
                    }else if(zahtev.getVreme()!=null){                    
                        method = planer.getClass().getMethod(zahtev.getMetoda(), Obaveza.class, ZonedDateTime.class, Point2D.Double.class);
                        Object temp = method.invoke(planer, zahtev.getObavezaObj(), zahtev.getVreme(), zahtev.getEnd());
                        ZonedDateTime sati = (ZonedDateTime)temp;
                        Zahtev zahtevAlarm = new Zahtev("postaviAlarm1", sati);
                        ObjectMessage objectMessage = context.createObjectMessage();
                        objectMessage.setObject(zahtevAlarm);
                        producer.send(destination, objectMessage);
                    }else if(zahtev.getEnd()!=null){                    
                        method = planer.getClass().getMethod(zahtev.getMetoda(), Point2D.Double.class);
                        method.invoke(planer, zahtev.getEnd());
                    }else{
                        method = planer.getClass().getMethod(zahtev.getMetoda());
                        method.invoke(planer);
                    }
                    
                    Thread.sleep(2000);
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Planer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Planer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(Planer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(Planer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Planer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Planer.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(Planer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
            
    }
}
