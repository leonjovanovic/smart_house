/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package korisnickiuredjaj;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import uredjaji.Alarm;
import uredjaji.Obaveza;
import uredjaji.Zahtev;

/**
 *
 * @author leon
 */
public class Main {

    @Resource (lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource (lookup="redPesma")
    private static Queue queue1;
    
    @Resource (lookup="redAlarm")
    private static Queue queue2;
    
    @Resource (lookup="redPlaner")
    private static Queue queue3;
    
    
    public static void main(String[] args) {
        
        Destination destination1 = queue1;
        Destination destination2 = queue2;
        Destination destination3 = queue3;
        JMSContext context = connectionFactory.createContext();
        JMSProducer producer = context.createProducer();
        
        while(true){
            try {
                Zahtev zahtevPesma = new Zahtev("reprodukuj", "Once upon a December");
                ObjectMessage objectMessage = context.createObjectMessage();
                objectMessage.setObject(zahtevPesma);
                producer.send(destination1, objectMessage);
                Thread.sleep(5000);
                
                zahtevPesma = new Zahtev("reprodukuj", "He lives in you");
                objectMessage.setObject(zahtevPesma);
                producer.send(destination1, objectMessage);
                Thread.sleep(5000);
                
                zahtevPesma = new Zahtev("reprodukuj", "El Condor Pasa");
                objectMessage.setObject(zahtevPesma);
                producer.send(destination1, objectMessage);
                Thread.sleep(5000);
                
                zahtevPesma = new Zahtev("svePesme");
                objectMessage.setObject(zahtevPesma);
                producer.send(destination1, objectMessage);
                
                /*System.out.println(ZonedDateTime.now().plusSeconds(15));
                Zahtev zahtevAlarm = new Zahtev("postaviAlarm1", ZonedDateTime.now().plusSeconds(15));
                objectMessage = context.createObjectMessage();
                objectMessage.setObject(zahtevAlarm);
                producer.send(destination2, objectMessage);
                Thread.sleep(5000);               

                
                System.out.println(ZonedDateTime.now().plusMinutes(5));
                zahtevAlarm = new Zahtev("postaviAlarm3", Alarm.ponudjenoVreme.ZA_PET_MINUTA);
                objectMessage = context.createObjectMessage();
                objectMessage.setObject(zahtevAlarm);
                producer.send(destination2, objectMessage);    
                Thread.sleep(5000);
                
                System.out.println(ZonedDateTime.now().plusSeconds(60));
                zahtevAlarm = new Zahtev("postaviAlarm2", ZonedDateTime.now().plusSeconds(60), 1);
                objectMessage = context.createObjectMessage();
                objectMessage.setObject(zahtevAlarm);
                producer.send(destination2, objectMessage);             
                Thread.sleep(5000);       
                
                zahtevAlarm = new Zahtev("promeniZvono", "RandomSong");
                objectMessage = context.createObjectMessage();
                objectMessage.setObject(zahtevAlarm);
                producer.send(destination2, objectMessage);         
                Thread.sleep(5000);      
                
                Zahtev zahtevPlaner = new Zahtev("unesiObavezu", "idi tamo", "tamo");
                objectMessage = context.createObjectMessage();
                objectMessage.setObject(zahtevPlaner);
                producer.send(destination3, objectMessage);
                Thread.sleep(5000);
                
                zahtevPlaner = new Zahtev("unesiObavezu", "obrisi prasinu", "stan");
                objectMessage = context.createObjectMessage();
                objectMessage.setObject(zahtevPlaner);
                producer.send(destination3, objectMessage);
                Thread.sleep(5000);
                
                zahtevPlaner = new Zahtev("unesiObavezu", "kupi jagode", "pijaca");
                objectMessage = context.createObjectMessage();
                objectMessage.setObject(zahtevPlaner);
                producer.send(destination3, objectMessage);
                Thread.sleep(5000);
                
                zahtevPlaner = new Zahtev("izlistaj");
                objectMessage.setObject(zahtevPlaner);
                producer.send(destination3, objectMessage);
                Thread.sleep(5000);
                
                zahtevPlaner = new Zahtev("menjajObavezu", new Obaveza("obrisi prasinu", "stan"), new Obaveza("operi sudove", "kuhinja"));
                objectMessage.setObject(zahtevPlaner);
                producer.send(destination3, objectMessage);
                Thread.sleep(5000);
                
                zahtevPlaner = new Zahtev("obrisiObavezu", new Obaveza("idi tamo", "tamo"));
                objectMessage.setObject(zahtevPlaner);
                producer.send(destination3, objectMessage);
                Thread.sleep(5000);
                
                zahtevPlaner = new Zahtev("izlistaj");
                objectMessage.setObject(zahtevPlaner);
                producer.send(destination3, objectMessage);
                Thread.sleep(5000);      
                
                
                zahtevPlaner = new Zahtev("aktivirajPodsetnik", new Obaveza("kupi jagode", "pijaca"), ZonedDateTime.now().plusDays(2), new Point2D.Double(44,25));
                objectMessage = context.createObjectMessage();
                objectMessage.setObject(zahtevPlaner);
                producer.send(destination3, objectMessage);
                Thread.sleep(5000);  */              
               
                Thread.sleep(2400000);
            } catch (JMSException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (InterruptedException ex) {
                Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            } catch (SecurityException e) {}
        }
    }
    
}
