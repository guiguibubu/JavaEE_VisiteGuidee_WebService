
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

@XmlRootElement(name = "trouverClientResponse", namespace = "http://visiteguidee.projet.javaee.eseo.fr/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "trouverClientResponse", namespace = "http://visiteguidee.projet.javaee.eseo.fr/")

public class TrouverClientResponse {

    @XmlElement(name = "return")
    private fr.eseo.javaee.projet.db.objet.Client _return;

    public fr.eseo.javaee.projet.db.objet.Client getReturn() {
        return this._return;
    }

    public void setReturn(fr.eseo.javaee.projet.db.objet.Client new_return)  {
        this._return = new_return;
    }

}

