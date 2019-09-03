
DROP TABLE IF EXISTS `tbl_Employee`;

CREATE TABLE `tbl_Employee` (`Id` int(3) NOT NULL AUTO_INCREMENT, `FirstName` varchar(30) NOT NULL, `MiddleInitial` varchar(1) DEFAULT NULL, `LastName` varchar(30) NOT NULL, `DateOfBirth` date DEFAULT NULL, `DateOfEmployment` date NOT NULL, `Status` tinyint(1) NOT NULL DEFAULT '0', `IsAdmin` tinyint(1) NOT NULL DEFAULT '0', PRIMARY KEY (`Id`), KEY `Id` (`Id`)) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `tbl_Employee` WRITE;

INSERT INTO `tbl_Employee` (`FirstName`, `MiddleInitial`, `LastName`, `DateOfBirth`, `DateOfEmployment`, `Status`, `IsAdmin`) VALUES ('Jesus Enrique',NULL,'Diaz Burgos','1985-09-23','2019-09-01',1,1), ('Mario','A','Perez Martinez','1978-02-11','2017-01-22',0,0), ('Cesar','M','Baez Partida','1990-06-05','2018-12-10',1,0), ('Karla','V','Vazquez Trinidad','1976-11-21','2018-02-18',1,0), ('Matilda',NULL,'Perez Perez','1992-04-30','2016-09-29',1,1), ('Agatha Helena',NULL,'Diaz Talavera','1989-06-02','2019-05-20',1,0);

UNLOCK TABLES;
