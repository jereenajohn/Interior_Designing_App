/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 5.7.9 : Database - colormyworld
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`colormyworld` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `colormyworld`;

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `chat_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `sender_type` varchar(50) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `receiver_type` varchar(50) DEFAULT NULL,
  `message` varchar(100) DEFAULT NULL,
  `message_date` date DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `chat` */

insert  into `chat`(`chat_id`,`sender_id`,`sender_type`,`receiver_id`,`receiver_type`,`message`,`message_date`) values 
(1,1,'designer',3,'user','hai','2020-03-16'),
(2,3,'user',1,'designer','hello','2020-03-16'),
(3,1,'designer',3,'user','tell me ','2020-03-16'),
(4,5,'designer',6,'user','hai','2023-01-16'),
(5,6,'user',5,'designer','hello','2023-01-16'),
(6,2,'user',1,'designer','hai','2023-03-14');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `complaint` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `complaint_date` date DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`user_id`,`complaint`,`reply`,`complaint_date`) values 
(1,2,'vbb',' ccc','2020-03-17'),
(2,1,'xcvb',' ok','2020-03-18'),
(3,3,'hai',' ok','2023-01-16');

/*Table structure for table `designers` */

DROP TABLE IF EXISTS `designers`;

CREATE TABLE `designers` (
  `designer_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `company_name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `pincode` varchar(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`designer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `designers` */

insert  into `designers`(`designer_id`,`login_id`,`first_name`,`last_name`,`company_name`,`place`,`pincode`,`phone`,`email`) values 
(1,1,'Juval','Mary','Desire','kakkanadu','623589','2345678901','juval@gmail.com'),
(2,5,'design','desi','company','ernakulam','123456','0234567890','design@gmail.com');

/*Table structure for table `designes` */

DROP TABLE IF EXISTS `designes`;

CREATE TABLE `designes` (
  `design_id` int(11) NOT NULL AUTO_INCREMENT,
  `designer_id` int(11) DEFAULT NULL,
  `name` varchar(50) DEFAULT NULL,
  `type` varchar(30) DEFAULT NULL,
  `photo` varchar(500) DEFAULT NULL,
  `material_id` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `price_per_piece` varchar(50) DEFAULT NULL,
  `status` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`design_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `designes` */

insert  into `designes`(`design_id`,`designer_id`,`name`,`type`,`photo`,`material_id`,`description`,`price_per_piece`,`status`) values 
(1,1,'fgh','curtain','/static/uploads/68a7ce7f-3596-4882-a087-aff3a42678e7pexels-photo-276460.jpeg',3,'zxcvoikjhb','200','accept'),
(3,1,'JKL','curtain','/static/uploads/0531e8ba-81e0-45c7-833e-99a900a45c11water-lilies-pink-water-lake-46231.jpeg',4,'zxx','400','pending'),
(4,1,'asdc','curtain','/static/uploads/bda67214-79ce-4ac7-a1cb-d70371eb5811Screenshot (9).png',2,'dc','100','pending'),
(5,2,'room design','room','/static/uploads/662d8cd2-1350-452c-bb5a-2c93124ca85a5e8da860a7c3a578a27dabbf431e43ca.png',5,'gfjhgkj','500','pending'),
(6,2,'room design','room','/static/uploads/2f79bd81-a195-466e-a02a-82036a708bc51992656.jpg',3,'hfhgfhjg','1','pending');

/*Table structure for table `feedback` */

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `feedback_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `feedback_date` date DEFAULT NULL,
  PRIMARY KEY (`feedback_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `feedback` */

insert  into `feedback`(`feedback_id`,`user_id`,`description`,`feedback_date`) values 
(1,2,'nice','2020-03-16'),
(2,3,'good','2023-01-16');

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values 
(1,'juval','juval','designer'),
(2,'madhav','madhav','user'),
(3,'kali','kali','user'),
(4,'admin','admin','admin'),
(5,'design123','design','designer'),
(6,'renuka','renuka','user');

/*Table structure for table `materials` */

DROP TABLE IF EXISTS `materials`;

CREATE TABLE `materials` (
  `material_id` int(11) NOT NULL AUTO_INCREMENT,
  `material_name` varchar(50) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `color` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `materials` */

insert  into `materials`(`material_id`,`material_name`,`description`,`color`) values 
(2,'ADF','zxcvsdfgh','red'),
(3,'CVB','wer','Green'),
(5,'met1',' this is a meterials1','yellow1');

/*Table structure for table `payment` */

DROP TABLE IF EXISTS `payment`;

CREATE TABLE `payment` (
  `payment_id` int(11) NOT NULL AUTO_INCREMENT,
  `proposal_id` int(11) DEFAULT NULL,
  `amount` varchar(50) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  `payment_date` date DEFAULT NULL,
  PRIMARY KEY (`payment_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `payment` */

insert  into `payment`(`payment_id`,`proposal_id`,`amount`,`user_id`,`payment_date`) values 
(1,1,'1200',1,'2020-03-17'),
(2,2,'350',2,'2021-05-12'),
(3,3,'500',2,'2021-05-13'),
(4,3,'500',2,'2021-05-13'),
(5,4,'2000',3,'2023-01-16'),
(6,5,'200',3,'2023-01-16');

/*Table structure for table `proposal` */

DROP TABLE IF EXISTS `proposal`;

CREATE TABLE `proposal` (
  `proposal_id` int(11) NOT NULL AUTO_INCREMENT,
  `requirement_id` int(11) DEFAULT NULL,
  `designer_id` int(11) DEFAULT NULL,
  `price` varchar(50) DEFAULT NULL,
  `proposal_date` date DEFAULT NULL,
  `proposal_status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`proposal_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `proposal` */

insert  into `proposal`(`proposal_id`,`requirement_id`,`designer_id`,`price`,`proposal_date`,`proposal_status`) values 
(1,1,1,'300','2020-03-16','confirm delivery'),
(2,2,1,'350','2020-03-16','confirm delivery'),
(3,5,1,'500','2021-05-13','confirm delivery'),
(4,6,2,'2000','2023-01-16','confirm delivery'),
(5,6,2,'200','2023-01-16','confirm delivery');

/*Table structure for table `requirements` */

DROP TABLE IF EXISTS `requirements`;

CREATE TABLE `requirements` (
  `requirement_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `design_id` int(11) DEFAULT NULL,
  `design_type` varchar(50) DEFAULT NULL,
  `quantity` varchar(50) DEFAULT NULL,
  `material_id` int(11) DEFAULT NULL,
  `requirement_date` date DEFAULT NULL,
  PRIMARY KEY (`requirement_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1;

/*Data for the table `requirements` */

insert  into `requirements`(`requirement_id`,`user_id`,`design_id`,`design_type`,`quantity`,`material_id`,`requirement_date`) values 
(1,1,1,'qww','4',3,'2020-03-16'),
(2,2,1,'xcvb','3',3,'2020-03-18'),
(3,2,0,'gh','2',0,'2021-05-13'),
(4,2,0,'df','3',0,'2021-05-13'),
(5,2,4,'ch','5',2,'2021-05-13'),
(6,3,5,'need long','200',5,'2023-01-16'),
(7,3,4,'bshwh','14',2,'2023-01-16');

/*Table structure for table `users` */

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `dob` varchar(100) DEFAULT NULL,
  `house_name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `pincode` varchar(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `users` */

insert  into `users`(`user_id`,`login_id`,`first_name`,`last_name`,`dob`,`house_name`,`place`,`pincode`,`phone`,`email`) values 
(1,2,'Madhav','S','1990-03-16','madhavalayam','Chottanikkara','690236','2589630147','madhu@gmail.com'),
(2,3,'Kalindhi','Jayashankar','1995-11-10','Devaragam','Kannur','690589','7410258963','kali@gmail.com'),
(3,6,'dfdfds','vdhsh','12-01-2000','fdsfsdfs','dfsd','682032','1234567891','ammu@gmail.com');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
