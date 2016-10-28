
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `343db`
--

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE IF NOT EXISTS `rooms` (
  `roomId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `roomNumber` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `roomsize` int(10) unsigned NOT NULL,
  UNIQUE KEY `rooms_roomNumber_unique` (`roomNumber`),
  PRIMARY KEY (`roomId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=135 ;

--
-- Dumping data for table `rooms`
--

INSERT INTO `students` (`roomId, `roomNumber`, `description`, `roomsize`) VALUES
(1, 'LB-351', 'This room is located in the Webster Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6), 
(2, 'LB-353', 'This room is located in the Webster Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6), 
(3, 'LB-359', 'This room is located in the Webster Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),
(4, 'LB-447', 'This room is located in the Webster Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),
(5, 'LB-449', 'This room is located in the Webster Library.', 6),   
(6, 'LB-479', 'This room is located in the Webster Library.', 6), 
(7, 'LB-481', 'This room is located in the Webster Library.', 6), 
(8, 'LB-518', 'This room is located in the Webster Library. It has 1 table, two 46" LCD screens with an HDMI port, an USB camera and multiple power outlets.', 6), 
(9, 'LB-522', 'This room is located in the Webster Library. It has 1 table, two 46" LCD screens with an HDMI port, an USB camera and multiple power outlets.', 6), 
(10, 'LB-547', 'This room is located in the Webster Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6), 
(11, 'LB-583', 'This room is located in the Webster Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6), 
(12, 'VL 301-10', 'This room is located in the Vanier Library.', 6),
(13, 'VL 301-11', 'This room is located in the Vanier Library.', 6), 
(14, 'VL 301-22', 'This room is located in the Vanier Library.', 6), 
(15, 'VL 301-23', 'This room is located in the Vanier Library.', 6), 
(16, 'VL 301-24', 'This room is located in the Vanier Library.', 6), 
(17, 'VL 301-12', 'This room is located in the Vanier Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6), 
(18, 'VL 301-13', 'This room is located in the Vanier Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),
(19, 'VL 301-20', 'This room is located in the Vanier Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),
(20, 'VL 301-21', 'This room is located in the Vanier Library. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets. However, the screen is currently experiencing technical issues. If you wish to use it, please consider reserving another room.', 6),  
(21, 'B.101', 'This room is located in Grey Nuns.', 6), 
(22, 'B.102', 'This room is located in Grey Nuns.', 6), 
(23, 'B.106', 'This room is located in Grey Nuns.', 6),
(24, 'B.120', 'This room is located in Grey Nuns.', 6),   
(25, 'B.104', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),
(26, 'B.108', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),
(27, 'B.109', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),    
(28, 'B.110', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),
(29, 'B.111', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6), 
(30, 'B.121', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6), 
(31, 'B.122', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),  
(32, 'B.123', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),  
(33, 'B.124', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),  
(34, 'B.125', 'This room is located in Grey Nuns. It has 1 table, a 46" LCD screen with an HDMI port, an USB camera and multiple power outlets.', 6),  
(35, 'H961-1', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2), 
(36, 'H961-2', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2), 
(37, 'H961-3', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2), 
(38, 'H961-4', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2), 
(39, 'H961-6', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2), 
(40, 'H961-7', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(41, 'H961-11', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(42, 'H961-13', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(43, 'H961-15', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(44, 'H961-17', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(45, 'H961-19', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2), 
(46, 'H961-21', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(47, 'H961-23', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(48, 'H961-25', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2), 
(49, 'H961-26', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(50, 'H961-27', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(51, 'H961-28', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2), 
(52, 'H961-29', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),
(53, 'H961-31', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2), 
(54, 'H961-33', 'This room is located in the Henry F. Hall Building (H-Building). It is mainly used for Captsone projects.', 2),          
(54, 'LB-520', 'This room is located in the Webster Library. It has 1 table, two 46" LCD screens with an HDMI port, an USB camera and multiple power outlets.', 6);
-- --------------------------------------------------------

--
-- Table structure for table `students`
--

CREATE TABLE IF NOT EXISTS `students` (
  `studentId` int(10) unsigned NOT NULL AUTO_INCREMENT,	
  `username` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `name` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `password` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  UNIQUE KEY `users_username_unique` (`username`),
  PRIMARY KEY ('studentId')
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=135;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`studentId`, `username`, `name`, `password`) VALUES
(1, 'v_emili', 'Emili Vasseva', 'Ca8/08c9onmg77QVxPXQh.w/OLRqO9e'),
(2, 'bob', 'Sponge Bob', 'pass'),
(3, 'wazzup', 'Bishes', 'JW8a8/iaCSg92WboP8foeO12Ym6t7PW');

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE IF NOT EXISTS `reservations` (
  `reservationId` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `roomId` int(10) unsigned NOT NULL,
  `studentId` int(10) unsigned NOT NULL,
  `weekDay` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `startTime` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `endTime` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `position` int(10) unsigned NOT NULL,
  PRIMARY KEY (`reservationId`),
  KEY `studentid_foreign` (`studentId`),
  KEY `roomid_foreign` (`roomId`)
) ENGINE=InnoDB  DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci AUTO_INCREMENT=135 ;

--
-- Dumping data for table `rooms`
--

INSERT INTO `students` (`reservationId`, `roomId`, `studentId`, `startTime`, `endTime`, `position`) VALUES
();

-- --------------------------------------------------------



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;