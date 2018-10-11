/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.File;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import model.Person;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;




/**
 *
 * @author DM3-2-17
 */
public class GestionListaEnMemoria {


    
    //PRUEBA DE COMIT 2 para ver si va
    public static ObservableList<Person> cargarDatos(File f) throws ParserConfigurationException, SAXException {
        //sartzen ditu taulan datuak 

        ObservableList<Person> listia = FXCollections.observableArrayList();
        File file = new File("fitxeroa.xml");
        String izenajok=null, abizenajok=null,emailajok=null,postuajok=null;
        int urteajok=0;
        try {
            
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            NodeList nList = doc.getElementsByTagName("jokalaria");
            
            for (int i=0; i<nList.getLength();i++) 
            {
                Element eElement = (Element) nList.item(i); //coge cada jokalari
                NodeList jokalariNodoSemeak = eElement.getChildNodes();//cada jokalari
                for (int j = 0; j<jokalariNodoSemeak.getLength(); j++) 
                {
                    Node semea = jokalariNodoSemeak.item(j);
                    if (semea.getNodeName() == "izena") {
                        izenajok = ((Element)semea.getChildNodes()).getTextContent();
                    }
                    else if (semea.getNodeName() == "abizena") {
                        abizenajok = ((Element)semea.getChildNodes()).getTextContent();
                    }
                    else if (semea.getNodeName() == "emaila") {
                        emailajok = ((Element)semea.getChildNodes()).getTextContent();
                    }
                    else if (semea.getNodeName() == "postua") {
                        postuajok = ((Element)semea.getChildNodes()).getTextContent();
                    }
                    else if (semea.getNodeName() == "urtea") {
                        urteajok = Integer.parseInt(((Element)semea.getChildNodes()).getTextContent());
                    }
                    
                }
                Person per = new Person (izenajok, abizenajok,emailajok,postuajok,urteajok);
                listia.add(per);
            }
           
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
      return listia;
    }  

   

    public static void Gorde(ObservableList<Person> lista) {
       
        try {
            // Create file 
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.newDocument();
            
            Element eRaiz = doc.createElement("jokalariak");
            doc.appendChild(eRaiz);
            
            for(Person per:lista)
            {
                Element ePerson = doc.createElement("jokalaria");
                eRaiz.appendChild(ePerson);
                
                Element efirstName = doc.createElement("izena");
                efirstName.appendChild(doc.createTextNode(per.getFirstName()));
                ePerson.appendChild(efirstName);
                
                Element eabizena = doc.createElement("abizena");
                eabizena.appendChild(doc.createTextNode(per.getLastName()));
                ePerson.appendChild(eabizena);
                
                Element email = doc.createElement("emaila");
                email.appendChild(doc.createTextNode(per.getEmail()));
                ePerson.appendChild(email);
                
                Element epostua = doc.createElement("postua");
                epostua.appendChild(doc.createTextNode(per.getPostua()));
                ePerson.appendChild(epostua);
                
                Element eurtea = doc.createElement("urtea");
                eurtea.appendChild(doc.createTextNode(String.valueOf(per.getUrtea())));
                ePerson.appendChild(eurtea);
            }
           TransformerFactory transformerFactory = TransformerFactory.newInstance();
           Transformer transformer = transformerFactory.newTransformer();
           DOMSource source = new DOMSource(doc);
           StreamResult result = new StreamResult(new File("fitxeroa.xml"));
           transformer.transform(source, result);
        } catch (Exception e) 
        {//Catch exception if any
            e.printStackTrace();
        }
    }

}
