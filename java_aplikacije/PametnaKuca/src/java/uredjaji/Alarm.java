/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uredjaji;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Time;
import java.time.Duration;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;

/**
 *
 * @author leon
 */
public class Alarm {
    
    @Resource (lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource (lookup="redAlarm")
    private static Queue queue;
    
    private boolean perioda;
    private String zvono;
    public enum ponudjenoVreme {ZA_PET_MINUTA, ZA_SAT_VREMENA, ZA_CETIRI_SATA}; 
    
    public Alarm(){
        perioda=true;
        zvono = "Over the Horizon";
    }
    
    public void postaviAlarm1(ZonedDateTime vreme){
        try {
            ZonedDateTime now = ZonedDateTime.now();
            Duration duration = Duration.between(now, vreme);
            if(duration.isNegative()){
                System.out.println("Vreme mora biti vece od trenutnog!");
                return;
            }
            System.out.println("Ceka se "+duration.getSeconds()+" sekundi");
            
            Thread.sleep(duration.toMillis());
            System.out.println("Alarm zvoni!");
            
        } catch (InterruptedException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void postaviAlarm2(ZonedDateTime vreme, int periodaDana){
        try {
            while(perioda){
                ZonedDateTime now = ZonedDateTime.now();
                Duration duration = Duration.between(now, vreme);
                if(duration.isNegative()){
                    System.out.println("Vreme mora biti vece od trenutnog!");
                    return;
                }
                vreme = vreme.plusDays(periodaDana);
                System.out.println("Ceka se "+duration.getSeconds()+" sekundi sa periodom");
                System.out.println("Novo vreme je: "+vreme +" i cekace se "+Duration.between(now, vreme).getSeconds()+" sekundi");
                Thread.sleep(duration.toMillis());
                System.out.println("Alarm zvoni!");
            } 
        } catch (InterruptedException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zaustaviPeriodu(){
        this.perioda = false;
    }
    
    public void postaviAlarm3(ponudjenoVreme temp){
        try {
            ZonedDateTime now = ZonedDateTime.now();
            ZonedDateTime next = now;
            switch(temp){
                case ZA_PET_MINUTA:
                    next = next.plusMinutes(5);
                    break;
                case ZA_SAT_VREMENA:
                    next = next.plusHours(1);
                    break;
                case ZA_CETIRI_SATA:
                    next = next.plusHours(4);
                    break;
            }
            Duration duration = Duration.between(now, next);
            if(duration.isNegative()){
                System.out.println("Vreme mora biti vece od trenutnog!");
                return;
            }
            System.out.println("Ceka se "+duration.getSeconds()+" sekundi");
            Thread.sleep(duration.toMillis());
            System.out.println("Alarm zvoni!");
        } catch (InterruptedException ex) {
            Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void promeniZvono(String naziv){
        this.zvono = naziv;
        System.out.println("Zvono je promenjeno na "+this.zvono);
    }
    
    public static void main(String[] args) {
        JMSContext context = connectionFactory.createContext();
        JMSConsumer consumer = context.createConsumer(queue);
        Alarm alarm = new Alarm();
        
        while(true){
            Message message = consumer.receive();
            if(message instanceof ObjectMessage){
                try {
                    ObjectMessage obj = (ObjectMessage)message;
                    Zahtev zahtev = (Zahtev)obj.getObject();
                    if(zahtev.getPerioda()!=0){
                        Method method = alarm.getClass().getMethod(zahtev.getMetoda(), ZonedDateTime.class, int.class);
                        method.invoke(alarm, zahtev.getVreme(), zahtev.getPerioda());
                    }
                    else if(zahtev.getEn()!= null){
                        Method method = alarm.getClass().getMethod(zahtev.getMetoda(), Alarm.ponudjenoVreme.class);
                        method.invoke(alarm, zahtev.getEn());
                    }
                    else if(zahtev.getVreme()!=null){
                        Method method = alarm.getClass().getMethod(zahtev.getMetoda(), ZonedDateTime.class);
                        method.invoke(alarm, zahtev.getVreme());
                    }
                    else{
                        Method method = alarm.getClass().getMethod(zahtev.getMetoda(), String.class);
                        method.invoke(alarm, zahtev.getNaziv());                        
                    }
                    
                    Thread.sleep(2000);
                    
                } catch (JMSException ex) {
                    Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InterruptedException ex) {
                    Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchMethodException ex) {
                    Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (SecurityException ex) {
                    Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalAccessException ex) {
                    Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IllegalArgumentException ex) {
                    Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
                } catch (InvocationTargetException ex) {
                    Logger.getLogger(Alarm.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
}
