-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 03, 2024 at 07:13 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `harmonyhaven`
--

-- --------------------------------------------------------

--
-- Table structure for table `articles`
--

CREATE TABLE `articles` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `publish_date` date NOT NULL,
  `category_id` int(11) NOT NULL,
  `image_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `articles`
--

INSERT INTO `articles` (`id`, `title`, `content`, `publish_date`, `category_id`, `image_path`) VALUES
(1, 'What is Lorem Ipsum?', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum', '2024-03-20', 1, 'http://10.2.20.43/harmonyhaven/category/science.png'),
(3, 'Where does it come from?', 'Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32.\n\nThe standard chunk of Lorem Ipsum used since the 1500s is reproduced below for those interested. Sections 1.10.32 and 1.10.33 from \"de Finibus Bonorum et Malorum\" by Cicero are also reproduced in their exact original form, accompanied by English versions from the 1914 translation by H. Rackham.', '2024-03-20', 3, 'http://10.2.20.43/harmonyhaven/category/science.png'),
(4, 'Why do we use it?', 'It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using \'Content here, content here\', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for \'lorem ipsum\' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like).', '2024-03-20', 3, 'http://10.2.20.43/harmonyhaven/category/science.png'),
(5, 'Where can I get some?', 'There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don\'t look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn\'t anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc.', '2024-03-20', 1, 'http://10.2.20.43/harmonyhaven/category/science.png'),
(6, 'Lorem Ipsum', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aliquam urna felis, mattis ut dapibus in, sagittis eget lectus. Donec posuere dignissim mi eget eleifend. Orci varius natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Cras magna leo, pulvinar scelerisque sapien eget, maximus placerat nisi. Maecenas facilisis a metus at pretium. Aliquam erat volutpat. Curabitur tempus tellus vel nunc vulputate, vitae eleifend ex aliquam. Vivamus eu ullamcorper purus. In lorem turpis, pulvinar et suscipit in, accumsan eu urna. Nam congue hendrerit turpis, sit amet cursus diam pulvinar eu.\n\nMauris eleifend at lectus a aliquet. Sed at nulla mi. Sed rutrum mauris sit amet lacinia hendrerit. Donec suscipit sodales dui, nec imperdiet erat faucibus at. Cras sodales ultrices odio eu sodales. Vivamus posuere elementum dolor. Quisque at gravida ante.\n\nEtiam posuere fermentum tortor, a pellentesque nibh aliquam ut. Phasellus eget eleifend nulla. Sed interdum, dui vitae feugiat ultricies, erat ante efficitur sapien, eget posuere lectus nisi quis metus. Nunc eget lacinia arcu. In ante magna, ultricies sed fringilla lacinia, suscipit eget est. Integer eu purus quis quam placerat viverra vitae sit amet ipsum. Mauris non mattis quam. Fusce vehicula metus libero. Curabitur laoreet a diam vel accumsan. Maecenas quis eros non neque congue ornare nec mollis nibh. Ut posuere luctus orci, sed pellentesque sem placerat ut. Aliquam pharetra semper ultrices. Quisque neque risus, venenatis in porttitor in, porttitor vitae tellus.\n\nSed rutrum volutpat mi non consequat. Aliquam lacus tellus, elementum sit amet nisi semper, rutrum rhoncus est. Sed at facilisis enim, vel interdum mauris. Nam in est nibh. Nam varius fermentum pretium. In at libero ex. Aliquam nibh libero, venenatis viverra vestibulum a, fermentum eget libero. Aliquam vitae placerat tellus.\n\nSed at ante diam. Mauris eu libero tincidunt, tempus mauris et, eleifend orci. Sed ac lorem vel felis porta aliquet sit amet scelerisque odio. Donec elementum lacus eget massa aliquet imperdiet quis sed ante. Mauris ac neque eu ex placerat fermentum. Suspendisse bibendum risus consequat sapien feugiat, malesuada vehicula lectus dignissim. Aliquam eget felis molestie, mattis enim quis, tempor erat.\n\nGenerated 5 paragraphs, 340 words, 2265 bytes of Lorem Ipsum', '2024-03-20', 1, 'http://10.2.20.43/harmonyhaven/category/science.png'),
(7, 'What is Lorem Ipsum?', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum', '2024-03-20', 1, 'http://10.2.20.43/harmonyhaven/category/science.png'),
(8, 'Career Dev', 'Career DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer DevCareer Dev', '2024-03-12', 9, 'https://images.pexels.com/photos/260024/pexels-photo-260024.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
(9, 'What is Lorem Ipsum?', 'Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry\'s standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum', '2024-03-20', 6, 'http://10.2.20.43/harmonyhaven/category/science.png'),
(16, 'Lorem Ipsum Title', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', '2024-03-25', 1, 'https://images.pexels.com/photos/260024/pexels-photo-260024.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
(17, 'Another Title', 'Another content goes here.', '2024-03-26', 1, 'https://images.pexels.com/photos/260024/pexels-photo-260024.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
(18, 'More Title', 'More content goes here.', '2024-03-27', 1, 'https://images.pexels.com/photos/260024/pexels-photo-260024.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
(19, 'Lorem Ipsum Title', 'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.', '2024-03-25', 10, 'https://images.pexels.com/photos/260024/pexels-photo-260024.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
(20, 'Another Title', 'Another content goes here.', '2024-03-26', 10, '/path/to/another/image.jpg'),
(21, 'More Title', 'More content goes here.', '2024-03-27', 10, 'https://images.pexels.com/photos/260024/pexels-photo-260024.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1');

-- --------------------------------------------------------

--
-- Table structure for table `categories`
--

CREATE TABLE `categories` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `image_path` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `categories`
--

INSERT INTO `categories` (`id`, `name`, `image_path`) VALUES
(1, 'Self-improvement', 'https://images.pexels.com/photos/414860/pexels-photo-414860.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
(3, 'Motivation', 'https://images.pexels.com/photos/3651820/pexels-photo-3651820.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
(4, 'Communication Skills', 'http://10.2.20.43/harmonyhaven/category/science.png'),
(5, 'Mental Health', 'http://10.2.20.43/harmonyhaven/category/science.png'),
(6, 'Emotional Intelligence', 'https://images.pexels.com/photos/54101/magic-cube-cube-puzzle-play-54101.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1'),
(9, 'Career Development', 'https://picsum.photos/id/870/200/300?grayscale&blur=2'),
(10, 'Self-Care and Wellness', 'https://images.pexels.com/photos/414860/pexels-photo-414860.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1');

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `surname` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `gender` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `profilePhotoPath` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `fcm_id` varchar(255) NOT NULL,
  `uuid` varchar(255) DEFAULT NULL,
  `role` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `surname`, `email`, `password`, `gender`, `profilePhotoPath`, `fcm_id`, `uuid`, `role`) VALUES
(135, 'Example User Name', 'Example User Surname', 'me.serhaterdem@gmail.com', '57e8125b1127b8e3bd1a329cbf78300fc4a8f3eb190a887c32aaaef757518a1f', 'male', '/path/to/profile/photo.jpg', '', '7c407752-ad04-46a7-855a-1b56bfa0f8ba', 'admin'),
(136, 'John', 'Doe', 'john.doe@example.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '126528d5-8e3a-457d-80c8-2f9d442135db', 'user'),
(137, 'John', 'Doe', 'john.doe@examwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '0c963a3f-a636-46c4-bf55-ca5a53e42ec2', 'user'),
(138, 'John', 'Doe', 'john.doe@ex5amwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '07744ac2-8da2-4d1a-a1d8-4f493b5962e5', 'user'),
(139, 'sdsadsad', 'Doe', 'john.doe@ex5agmwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', 'db39d428-ba02-4e9b-91a0-919d6a06ee52', 'user'),
(140, 'sdsadsad', 'Doe', 'john.doe@ex5agmwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', 'acde90bc-b852-4530-8611-2dae7aa3344e', 'user'),
(141, 'sdsadsad', 'Doe', 'john.doe@ex5agmwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '4ee84e1e-c75f-40a7-85be-df6a3747549e', 'user'),
(142, 'sdsadsad', 'Doe', 'john.doe@ex5agmwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '945ee64c-a3bd-4831-a78e-06b3e9b6dda6', 'user'),
(143, 'sdsadsad', 'Doe', 'john.doe@ex5agmwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', 'f76f9687-9ada-44cc-9ea6-b0af316aae25', 'user'),
(144, '', 'Doe', 'john.doe@ex5agmwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '5aea6808-06c2-4c6c-8f04-274ffae400ce', 'user'),
(145, '', 'Doe', 'john.doe@ex5agmwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '635ee31d-2c8f-44f1-bdef-cf7237d91872', 'user'),
(146, 's', 'Doe', 'john.doe@ex5agmwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '515e8f04-eb24-47a1-be37-cc40bc4f89e0', 'user'),
(147, 'Serhat', 'Doe', 'john.doe@ex5agmwple.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', 'd05270df-2561-4202-813f-cebd6ac33849', 'user'),
(148, 'Serhat', 'Erdem', 'me.serhaterdem@gmail.com', '57e8125b1127b8e3bd1a329cbf78300fc4a8f3eb190a887c32aaaef757518a1f', 'Male', '-', '-', 'e8cb4c61-03ad-4cf9-a8b0-56de3d112448', 'user'),
(149, 'Serhat', 'Erdem', 'me.serhaterdem@gmail.coom', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', 'e4eac152-c3e7-478e-b0a8-4a37921a39c1', 'user'),
(150, 'Serhat', 'Erdem', 'me.serhajterdem@gmail.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '5f62e61f-68f8-4080-ba4e-fe751b6ed234', 'user'),
(151, 'Serhat', 'Erdem', 'me.serhajterdem@gmail.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '91914a28-2279-4a94-87ca-5c0cdcbcd0d9', 'user'),
(152, 'Serhat', 'Erdem', 'me.serhajterdem@gmail.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', 'b7cf1c25-836b-458d-88f8-ea5f236a1855', 'user'),
(153, 'Serhat', 'Erdem', 'me.serhajterdem@gmail.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '12db3b77-b305-483d-a482-52b1208eb4b8', 'user'),
(154, 'Serhat', 'Erdem', 'me.serhajterdem@gmail.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', 'da125d01-6430-4034-8c37-2cd95ace39e4', 'user'),
(155, 'Serhat', 'Erdem', 'me.serhajterdem@gmail.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', 'f34d14b7-ba4b-4229-a6ed-34620e0609e4', 'user'),
(156, 'Serhat', 'Erdem', 'se.serhajterdem@gmail.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', '63034172-7a19-4dc2-903e-f009cb994c44', 'user'),
(157, 'Serhat', 'Erdem', 'se.serhaterdem@gmail.com', '245b22dce938051efc28cf4364928fa26e171db139755311a93703ba65fd6d07', 'Male', '-', '-', 'ac90367e-2ae0-4e79-a411-96951357cd3e', 'user');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `articles`
--
ALTER TABLE `articles`
  ADD PRIMARY KEY (`id`),
  ADD KEY `category_id` (`category_id`);

--
-- Indexes for table `categories`
--
ALTER TABLE `categories`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `idx_uuid` (`uuid`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `articles`
--
ALTER TABLE `articles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT for table `categories`
--
ALTER TABLE `categories`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=162;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `articles`
--
ALTER TABLE `articles`
  ADD CONSTRAINT `Articles_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
