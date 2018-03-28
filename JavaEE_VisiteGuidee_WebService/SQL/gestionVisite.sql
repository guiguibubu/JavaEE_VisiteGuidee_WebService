-- phpMyAdmin SQL Dump
-- version 4.6.4
-- https://www.phpmyadmin.net/
--
-- Client :  127.0.0.1
-- Généré le :  Ven 16 Mars 2018 à 17:46
-- Version du serveur :  5.7.14
-- Version de PHP :  5.6.25

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `gestionVisite`
--

-- Création de la base.
CREATE DATABASE IF NOT EXISTS gestionVisite;

-- Création de l'utilisateur de la base.
CREATE USER IF NOT EXISTS 'test'@'localhost' IDENTIFIED BY 'test';

-- Déclaration des privilège pour l'utilisateur sur la base.
GRANT ALL ON gestionVisite.* TO 'test'@'localhost';

-- Utilisation de la base GestionVisites
USE gestionVisite;


-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE IF NOT EXISTS `client` (
  `idClient` int(11) NOT NULL,
  `nom` varchar(35) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `prenom` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `dateNaissance` date DEFAULT NULL,
  `adresse` varchar(70) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `codePostal` int(5) DEFAULT NULL,
  `pays` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `numTelephone` VARCHAR(10) DEFAULT NULL,
  `mail` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déclencheurs `client`
--
DROP TRIGGER IF EXISTS `trig_email_check`;
DELIMITER $$
CREATE TRIGGER `trig_email_check` BEFORE INSERT ON `client` FOR EACH ROW BEGIN 
IF (NEW.mail REGEXP '^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,4}$' ) = 0 THEN 
  SIGNAL SQLSTATE '12345'
     SET MESSAGE_TEXT = 'Format email incorrect';
END IF; 
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE IF NOT EXISTS `reservation` (
  `idReservation` int(11) NOT NULL,
  `idVisite` int(11) NOT NULL,
  `idClient` int(11) NOT NULL,
  `nombrePlaces` int(11) NOT NULL DEFAULT '1',
  `booleanPaiementEffectue` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `visite`
--

CREATE TABLE IF NOT EXISTS `visite` (
  `idVisite` int(11) NOT NULL,
  `typeVisite` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ville` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `dateVisite` datetime NOT NULL,
  `prixVisite` DECIMAL(10,2) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Index pour les tables exportées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`idClient`),
  ADD UNIQUE KEY `Unicite_Personne` (`nom`,`prenom`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`idReservation`),
  ADD KEY `idVisite` (`idVisite`),
  ADD KEY `idClient` (`idClient`);

--
-- Index pour la table `visite`
--
ALTER TABLE `visite`
  ADD PRIMARY KEY (`idVisite`);

--
-- AUTO_INCREMENT pour les tables exportées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `idClient` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;
--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `idReservation` int(11) NOT NULL AUTO_INCREMENT;
--
-- AUTO_INCREMENT pour la table `visite`
--
ALTER TABLE `visite`
  MODIFY `idVisite` int(11) NOT NULL AUTO_INCREMENT;
--
-- Contraintes pour les tables exportées
--

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`idVisite`) REFERENCES `visite` (`idVisite`) ON DELETE CASCADE,
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`idClient`) REFERENCES `client` (`idClient`) ON DELETE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;











/* Populate client Table */


insert into client values(1,'Pabst','Benoit','1994-11-25','11 rue Carnot',49100,'France','0607080910','bp@gmail.com');
insert into client values(2,'Pabst','Clement','1994-11-25','11 rue Carnot',49100,'France','0607080910','cp@gmail.com');
insert into client values(3,'Pabst','Guy','1994-11-25','11 rue Carnot',49100,'France','0607080910','gp@gmail.com');
insert into client values(4,'Skoro','Jakub','1994-11-25','11 rue Carnot',49100,'France','0607080910','js@gmail.com');
insert into client values(5,'Qu','Marc','1994-11-25','11 rue Carnot',49100,'France','0607080910','mq@gmail.com');
insert into client values(6,'Buchle','Guillaume','1994-11-25','11 rue Carnot',49100,'France','0607080910','gb@gmail.com');




/* Populate visite Table */


insert into visite values(1,'culture','Paris','2018-05-06',5);
insert into visite values(2,'culture','Paris','2018-08-06',15);
insert into visite values(3,'culture','Paris','2018-05-11',25);
insert into visite values(4,'nature','Boulogne','2018-05-06',5);
insert into visite values(5,'nature','Caen','2018-05-06 18:00:00',555);
insert into visite values(6,'nature','Lille','2018-05-06 15:00:00',123);
insert into visite values(7,'nature','Marseille','2018-05-06 08:00:00',5);
insert into visite values(8,'Vin','Paris','2019-05-06 10:00:00',50);
insert into visite values(9,'Vin','Bordeaux','2018-07-06 12:00:00',54);
insert into visite values(10,'Vin','Bordeaux','2018-08-12',45);
insert into visite values(11,'Vin','Bordeaux','2018-09-06',25);
insert into visite values(12,'culture','Paris','2018-05-06',5);
insert into visite values(13,'culture','Colombes','2018-05-06',88);
insert into visite values(14,'culture','Nantes','2018-05-06',65);
insert into visite values(15,'culture','Berlin','2018-05-06',565);




/* Populate reservation Table */


insert into reservation values(1,5,1,1,0);
insert into reservation values(2,4,2,9,0);
insert into reservation values(3,3,2,1,0);
insert into reservation values(4,2,3,5,1);
insert into reservation values(5,4,3,8,1);
insert into reservation values(6,2,3,6,1);
insert into reservation values(7,3,4,19,0);
insert into reservation values(8,4,5,3,1);
insert into reservation values(9,5,5,5,1);
insert into reservation values(10,5,6,8,0);
insert into reservation values(11,1,6,7,1);
insert into reservation values(12,2,4,1,0);