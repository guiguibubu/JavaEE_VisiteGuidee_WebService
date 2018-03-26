package fr.eseo.javaee.projet.visiteguidee;

import java.sql.SQLException;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

import fr.eseo.javaee.projet.db.objet.Reservation;
import fr.eseo.javaee.projet.db.objet.Visite;

@WebService(name = "ReservationVisiteSEI", targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/")
public interface ReservationVisiteSEI {

	@WebMethod(operationName = "trouverVisite", action = "urn:TrouverVisite")
	@RequestWrapper(className = "fr.eseo.javaee.projet.visiteguidee.jaxws.TrouverVisite", localName = "trouverVisite", targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/")
	@ResponseWrapper(className = "fr.eseo.javaee.projet.visiteguidee.jaxws.TrouverVisiteResponse", localName = "trouverVisiteResponse", targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/")
	@WebResult(name = "return")
	List<Visite> trouverVisite(@WebParam(name = "arg0") Visite maVisite) throws SQLException;

	@WebMethod(operationName = "reserverVisite", action = "urn:ReserverVisite")
	@RequestWrapper(className = "fr.eseo.javaee.projet.visiteguidee.jaxws.ReserverVisite", localName = "reserverVisite", targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/")
	@ResponseWrapper(className = "fr.eseo.javaee.projet.visiteguidee.jaxws.ReserverVisiteResponse", localName = "reserverVisiteResponse", targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/")
	@WebResult(name = "return")
	int reserverVisite(@WebParam(name = "arg0") Reservation maReservation) throws SQLException;

	@WebMethod(operationName = "payerVisite", action = "urn:PayerVisite")
	@RequestWrapper(className = "fr.eseo.javaee.projet.visiteguidee.jaxws.PayerVisite", localName = "payerVisite", targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/")
	@ResponseWrapper(className = "fr.eseo.javaee.projet.visiteguidee.jaxws.PayerVisiteResponse", localName = "payerVisiteResponse", targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/")
	@WebResult(name = "return")
	boolean payerVisite(@WebParam(name = "arg0") int monCodeReservation) throws SQLException;

	@WebMethod(operationName = "annulerVisite", action = "urn:AnnulerVisite")
	@RequestWrapper(className = "fr.eseo.javaee.projet.visiteguidee.jaxws.AnnulerVisite", localName = "annulerVisite", targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/")
	@ResponseWrapper(className = "fr.eseo.javaee.projet.visiteguidee.jaxws.AnnulerVisiteResponse", localName = "annulerVisiteResponse", targetNamespace = "http://visiteguidee.projet.javaee.eseo.fr/")
	@WebResult(name = "return")
	boolean annulerVisite(@WebParam(name = "arg0") int monCodeReservation) throws SQLException;

}