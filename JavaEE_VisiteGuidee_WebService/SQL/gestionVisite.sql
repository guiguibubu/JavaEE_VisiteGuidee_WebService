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
CREATE DATABASE gestionVisite;

-- Création de l'utilisateur de la base.
CREATE USER 'test'@'localhost' IDENTIFIED BY 'test';

-- Déclaration des privilège pour l'utilisateur sur la base.
GRANT ALL ON gestionVisite.* TO 'test'@'localhost';

-- Utilisation de la base GestionVisites
USE gestionVisite;


-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `idClient` int(11) NOT NULL,
  `nom` varchar(35) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `prenom` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `dateNaissance` date DEFAULT NULL,
  `adresse` varchar(70) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `codePostal` int(5) DEFAULT NULL,
  `pays` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL,
  `numTelephone` int(10) DEFAULT NULL,
  `mail` varchar(50) CHARACTER SET utf8 COLLATE utf8_bin DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Déclencheurs `client`
--
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

CREATE TABLE `reservation` (
  `idReservation` int(11) NOT NULL,
  `idVisite` int(11) NOT NULL,
  `idClient` int(11) NOT NULL,
  `nombrePlaces` int(11) NOT NULL DEFAULT '1',
  `booleenPaiementEffectue` tinyint(1) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Structure de la table `visite`
--

CREATE TABLE `visite` (
  `idVisite` int(11) NOT NULL,
  `typeVisite` varchar(15) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `ville` varchar(30) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL,
  `dateVisite` datetime NOT NULL,
  `prixVisite` int(11) NOT NULL DEFAULT '0'
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
