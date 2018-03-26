DELETE FROM client;
DELETE FROM visite;
DELETE FROM reservation;

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
insert into visite values(5,'nature','Caen','2018-05-06',555);
insert into visite values(6,'nature','Lille','2018-05-06',123);
insert into visite values(7,'nature','Marseille','2018-05-06',5);
insert into visite values(8,'Vin','Paris','2019-05-06',50);
insert into visite values(9,'Vin','Bordeaux','2018-07-06',54);
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










