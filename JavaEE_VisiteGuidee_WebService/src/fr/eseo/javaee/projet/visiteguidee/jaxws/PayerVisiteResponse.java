
package fr.eseo.javaee.projet.visiteguidee.jaxws;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 * This class was generated by Apache CXF 3.2.2
 * Mon Mar 26 02:51:57 CEST 2018
 * Generated source version: 3.2.2
 */

@XmlRootElement(name = "payerVisiteResponse", namespace = "http://visiteguidee.projet.javaee.eseo.fr/")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "payerVisiteResponse", namespace = "http://visiteguidee.projet.javaee.eseo.fr/")

public class PayerVisiteResponse {

    @XmlElement(name = "return")
    private boolean _return;

    public boolean getReturn() {
        return this._return;
    }

    public void setReturn(boolean new_return)  {
        this._return = new_return;
    }

}

