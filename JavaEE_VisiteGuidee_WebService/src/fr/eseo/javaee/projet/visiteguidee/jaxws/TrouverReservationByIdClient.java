
package fr.eseo.javaee.projet.visiteguidee.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.2.2
 * Sat Mar 31 16:18:07 CEST 2018
 * Generated source version: 3.2.2
 */

@XmlRootElement(name = "trouverReservationByIdClient", namespace = "http://visiteguidee.projet.javaee.eseo.fr/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trouverReservationByIdClient", namespace = "http://visiteguidee.projet.javaee.eseo.fr/")

public class TrouverReservationByIdClient {

    @XmlElement(name = "arg0")
    private int arg0;

    public int getArg0() {
        return this.arg0;
    }

    public void setArg0(int newArg0)  {
        this.arg0 = newArg0;
    }

}

