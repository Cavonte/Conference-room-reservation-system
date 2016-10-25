
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

INSERT INTO `students` (`roomId, `roomNumber`) VALUES
(1, 'LB 123');

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
(1);

-- --------------------------------------------------------



/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;