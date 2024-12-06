CREATE DATABASE  IF NOT EXISTS `jobportal` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `jobportal`;
-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: jobportal
-- ------------------------------------------------------
-- Server version	9.0.0

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `contactmessages`
--

DROP TABLE IF EXISTS `contactmessages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contactmessages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `message` text NOT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contactmessages`
--

LOCK TABLES `contactmessages` WRITE;
/*!40000 ALTER TABLE `contactmessages` DISABLE KEYS */;
INSERT INTO `contactmessages` VALUES (1,'Sanjot Kaur','sanjotkaur6284@gmail.com','hlo','2024-11-03 14:47:50'),(2,'nitish','nitish@mail.com','hlooo','2024-11-03 15:03:40'),(3,'sneh','sneh@mail.com','hlo','2024-11-03 17:27:42'),(4,'sneh','sneh@mail.com','hlo','2024-11-12 06:01:07'),(5,'shubh','shubh@mail.com','fhj','2024-11-18 06:59:51');
/*!40000 ALTER TABLE `contactmessages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_company`
--

DROP TABLE IF EXISTS `job_company`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_company` (
  `id` int NOT NULL AUTO_INCREMENT,
  `logo` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_company`
--

LOCK TABLES `job_company` WRITE;
/*!40000 ALTER TABLE `job_company` DISABLE KEYS */;
INSERT INTO `job_company` VALUES (32,NULL,'Infosys'),(33,NULL,'Infosys'),(34,NULL,'Infosys'),(35,NULL,'Infosys'),(36,NULL,'Infosys'),(37,NULL,'Infosys'),(38,NULL,'Infosys'),(39,NULL,'Infosys'),(40,NULL,'TCs'),(41,NULL,'Infosys'),(42,NULL,'TCS'),(43,NULL,'Wipro'),(44,NULL,'Wipro');
/*!40000 ALTER TABLE `job_company` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_location`
--

DROP TABLE IF EXISTS `job_location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_location` (
  `id` int NOT NULL AUTO_INCREMENT,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_location`
--

LOCK TABLES `job_location` WRITE;
/*!40000 ALTER TABLE `job_location` DISABLE KEYS */;
INSERT INTO `job_location` VALUES (31,'Mumbai','India','Maharashtra'),(32,'Mumbai','India','Maharashtra'),(33,'Banglore','India','Karnatka'),(34,'Banglore','India','Karnatka'),(35,'Mumbai','India','Maharashtra'),(36,'Mumbai','India','Maharashtra'),(37,'Mumbai','India','Maharashtra'),(38,'Mumbai','India','Maharashtra'),(39,'Mumbai','India','Maharashtra'),(40,'Mumbai','India','Maharashtra'),(41,'Amritsar','India','Punjab'),(42,'Mumbai','India','Maharashtra'),(43,'Mumbai','India','Maharashtra');
/*!40000 ALTER TABLE `job_location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_post_activity`
--

DROP TABLE IF EXISTS `job_post_activity`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_post_activity` (
  `job_post_id` int NOT NULL AUTO_INCREMENT,
  `description_of_job` text,
  `job_title` varchar(255) DEFAULT NULL,
  `job_type` varchar(255) DEFAULT NULL,
  `posted_date` datetime(6) DEFAULT NULL,
  `remote` varchar(255) DEFAULT NULL,
  `salary` varchar(255) DEFAULT NULL,
  `job_company_id` int DEFAULT NULL,
  `job_location_id` int DEFAULT NULL,
  `posted_by_id` int DEFAULT NULL,
  PRIMARY KEY (`job_post_id`),
  KEY `job_company_id` (`job_company_id`),
  KEY `job_location_id` (`job_location_id`),
  KEY `posted_by_id` (`posted_by_id`),
  CONSTRAINT `job_post_activity_ibfk_1` FOREIGN KEY (`job_company_id`) REFERENCES `job_company` (`id`),
  CONSTRAINT `job_post_activity_ibfk_2` FOREIGN KEY (`job_location_id`) REFERENCES `job_location` (`id`),
  CONSTRAINT `job_post_activity_ibfk_3` FOREIGN KEY (`posted_by_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=43 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_post_activity`
--

LOCK TABLES `job_post_activity` WRITE;
/*!40000 ALTER TABLE `job_post_activity` DISABLE KEYS */;
INSERT INTO `job_post_activity` VALUES (30,'<p>Infosys is a global leader in IT services, consulting, and business solutions. We are committed to helping our clients achieve their business objectives through innovative technology solutions. Join our diverse, global team and contribute to transforming industries and improving lives with technology.</p><p><strong>Job Overview:</strong><br>We are seeking an experienced and highly motivated Full Stack Developer to join our dynamic team at Infosys. In this role, you will work on designing and developing innovative web applications and services. The ideal candidate should be proficient in both front-end and back-end technologies and have a strong understanding of software development life cycle, architecture, and design principles.</p><p><strong>Key Responsibilities:</strong></p><ul><li>Design, develop, test, and deploy scalable web applications.</li><li>Work with front-end technologies such as HTML, CSS, JavaScript, and modern frameworks like Angular, React, or Vue.js.</li><li>Develop back-end services and APIs using technologies such as Java (Spring Boot), Node.js, Python, or .NET.</li><li>Integrate data storage solutions (Relational and NoSQL databases like MySQL, MongoDB, etc.).</li><li>Collaborate with cross-functional teams to gather requirements and deliver high-quality software solutions.</li><li>Troubleshoot, debug, and optimize web applications for maximum performance and scalability.</li><li>Participate in code reviews to maintain code quality and best practices.</li><li>Ensure the security, scalability, and efficiency of applications.</li><li>Stay updated with the latest trends and technologies in full-stack development and propose innovative solutions.</li></ul><p><strong>Required Skills &amp; Qualifications:</strong></p><ul><li>Bachelor\'s degree in Computer Science, Engineering, or a related field.</li><li>Proven experience as a Full Stack Developer with a strong portfolio of web development projects.</li><li>Proficiency in front-end technologies such as HTML5, CSS3, JavaScript, and frameworks like React, Angular, or Vue.js.</li><li>Solid experience in back-end technologies, such as Java (Spring Boot), Node.js, or Python.</li><li>Strong understanding of relational and NoSQL databases (e.g., MySQL, PostgreSQL, MongoDB).</li><li>Familiarity with cloud services like AWS, Azure, or Google Cloud.</li><li>Understanding of version control tools like Git.</li><li>Experience with RESTful API design and integration.</li><li>Knowledge of Agile methodologies and practices.</li><li>Strong problem-solving and analytical skills.</li><li>Excellent communication and collaboration skills.</li></ul><p><strong>Preferred Skills:</strong></p><ul><li>Experience with DevOps practices and CI/CD pipelines.</li><li>Knowledge of containerization technologies like Docker and Kubernetes.</li><li>Experience with mobile application development.</li><li>Familiarity with microservices architecture.</li><li>Knowledge of front-end build tools like Webpack, Babel, etc.</li></ul><p><br></p><p><strong>How to Apply:</strong><br>Interested candidates can submit their resumes and portfolios via the TCS careers portal or by sending an email to sandhutejbir.55@gmail.com.</p>','Full Stack Developer','Part-time','2024-12-06 01:24:03.655000','Office-Only','10000-20000',32,31,51),(31,'<h3><strong>About Infosys</strong></h3><p>Infosys is a global leader in next-generation digital services and consulting. We enable clients in 46 countries to navigate their digital transformation. With over 200,000 employees, Infosys offers a diverse and inclusive work environment with opportunities for growth, innovation, and leadership.</p><hr><h3><strong>Job Description</strong></h3><p>We are looking for a dynamic and experienced <strong>Human Resources (HR) Specialist</strong> to join our growing team at Infosys. As an HR Specialist, you will be responsible for overseeing HR operations, managing employee relations, ensuring compliance with labor laws, and contributing to the development of effective HR strategies.</p><hr><h3><strong>Key Responsibilities</strong></h3><ul><li><strong>Recruitment and Onboarding</strong>: Manage end-to-end recruitment processes, including talent acquisition, interviewing, selection, and onboarding of new employees.</li><li><strong>Employee Relations</strong>: Act as a point of contact for employees, addressing concerns, resolving conflicts, and promoting a positive work environment.</li><li><strong>Performance Management</strong>: Assist with performance appraisals, feedback processes, and performance improvement plans.</li><li><strong>Compensation &amp; Benefits</strong>: Ensure employees are provided with competitive compensation packages and manage benefits programs.</li><li><strong>Training &amp; Development</strong>: Identify training needs, organize learning and development programs, and support employees in enhancing their skills and capabilities.</li><li><strong>Compliance and Documentation</strong>: Ensure the company adheres to labor laws, regulations, and organizational policies. Maintain accurate employee records and ensure confidentiality.</li><li><strong>HR Strategy &amp; Planning</strong>: Collaborate with senior management to align HR practices with the company’s strategic goals and objectives.</li><li><strong>Employee Engagement</strong>: Plan and organize engagement activities and initiatives to enhance employee satisfaction, motivation, and retention.</li></ul><hr><h3><strong>Skills &amp; Qualifications</strong></h3><ul><li><strong>Education</strong>: Bachelor’s degree in Human Resources, Business Administration, or a related field. A Master’s degree is a plus.</li><li><strong>Experience</strong>: 2-5 years of experience in Human Resources, preferably in a corporate or multinational environment.</li><li><strong>Knowledge</strong>: Strong understanding of labor laws, HR best practices, and performance management techniques.</li><li><strong>Skills</strong>: Excellent communication, interpersonal, and problem-solving skills. Proficiency in HR software and MS Office Suite.</li><li><strong>Certifications</strong>: HR certifications such as SHRM-CP, PHR, or equivalent are an advantage.</li></ul><hr><h3><strong>What We Offer</strong></h3><ul><li>Competitive salary and benefits</li><li>Opportunities for career growth and professional development</li><li>A diverse and inclusive work environment</li><li>Access to cutting-edge technology and digital transformation projects</li><li>Work-life balance initiatives</li></ul><hr><h3><strong>How to Apply</strong></h3><p>If you are passionate about HR and want to make a meaningful impact at a global company, apply now through our career portal or send your resume and cover letter to sandhutejbir.55@gmail.com</p>','Human Resources','Full-time','2024-12-06 01:08:05.377000','Partial-Remote','10000-20000',33,32,51),(32,'<p>We are seeking a highly skilled <strong>Software Engineer</strong> to join our team at <b>Infosys</b>. The ideal candidate will work on developing, testing, and maintaining software applications. You will collaborate with cross-functional teams to build high-quality, scalable, and efficient solutions for our clients.</p><h4><strong>Responsibilities</strong></h4><ul><li>Design, develop, and maintain software applications.</li><li>Collaborate with product managers and designers to create user-friendly solutions.</li><li>Write clean, efficient, and maintainable code.</li><li>Troubleshoot and debug software issues.</li><li>Participate in code reviews and contribute to continuous improvement efforts.</li></ul><h4><strong>Skills &amp; Qualifications</strong></h4><ul><li>Bachelor’s degree in Computer Science or related field.</li><li>2-4 years of software development experience.</li><li>Proficiency in programming languages such as Java, Python, or C++.</li><li>Experience with database management and software development tools.</li></ul><h3></h3><hr>','Software Engineer','Freelancer','2024-12-06 01:27:47.065000','Remote-Only','10000-20000',34,33,51),(33,'<p><b>Infosys</b> is looking for an experienced <strong>Marketing Manager</strong> to lead marketing campaigns, develop strategic plans, and drive brand growth across multiple channels. The ideal candidate should have a strong background in marketing strategy, analytics, and team leadership.</p><h4><strong>Responsibilities</strong></h4><ul><li>Develop and implement marketing strategies to drive brand awareness and growth.</li><li>Analyze market trends and customer behavior to tailor marketing campaigns.</li><li>Manage and mentor a marketing team.</li><li>Collaborate with sales and product teams to ensure cohesive branding and messaging.</li><li>Manage digital marketing channels, including social media, content, and email marketing.</li></ul><h4><strong>Skills &amp; Qualifications</strong></h4><ul><li>Bachelor’s degree in Marketing, Business, or related field.</li><li>5+ years of marketing experience, preferably in a leadership role.</li><li>Strong knowledge of digital marketing platforms and tools.</li><li>Excellent communication and leadership skills.</li></ul><h3></h3><hr>','Marketing Manager','Part-time','2024-12-06 01:27:09.859000','Remote-Only','10000-30000',35,34,51),(34,'<p>As a <strong>Data Scientist</strong> at <strong>Infosys</strong>, you will analyze and interpret complex data to drive business insights. You will work closely with cross-functional teams to solve challenging problems using machine learning models, statistical analysis, and data visualization techniques.</p><h4><strong>Responsibilities</strong></h4><ul><li>Build predictive models and machine learning algorithms.</li><li>Analyze large datasets to extract meaningful insights.</li><li>Collaborate with business and technical teams to understand data needs.</li><li>Create data visualizations to communicate findings to stakeholders.</li><li>Stay updated on the latest trends and advancements in data science.</li></ul><h4><strong>Skills &amp; Qualifications</strong></h4><ul><li>Master’s degree in Data Science, Statistics, or related field.</li><li>Strong experience in programming languages such as Python, R, or SQL.</li><li>Knowledge of machine learning frameworks and tools (TensorFlow, PyTorch).</li><li>Excellent problem-solving and analytical skills.</li></ul><h3></h3><hr>','Data Scientist','Part-time','2024-12-06 01:20:33.159000','Office-Only','10000-30000',36,35,51),(35,'<p><b>Infosys</b> is looking for a <strong>Customer Support Representative</strong> to assist customers with their inquiries, resolve issues, and provide exceptional service. The ideal candidate will have excellent communication skills and a passion for helping others.</p><h4><strong>Responsibilities</strong></h4><ul><li>Handle customer inquiries via phone, email, and chat.</li><li>Resolve product or service-related issues in a timely and efficient manner.</li><li>Provide accurate information regarding product features, shipping, and returns.</li><li>Document customer interactions and feedback.</li><li>Assist with troubleshooting technical issues and guide customers through the process.</li></ul><h4><strong>Skills &amp; Qualifications</strong></h4><ul><li>High school diploma or equivalent; college degree preferred.</li><li>1-2 years of customer service experience.</li><li>Excellent communication and problem-solving skills.</li><li>Ability to work in a fast-paced environment.</li></ul>',' Customer Support Representative','Freelancer','2024-12-06 01:22:35.304000','Partial-Remote','10000-20000',37,36,51),(36,'<p>Infosys is looking for a talented <strong>Software Engineer</strong> to join our engineering team. As a <strong>Software Engineer</strong>, you will be responsible for designing, developing, testing, and maintaining software applications that support our business and technical objectives. This position requires a blend of technical knowledge, critical thinking, and problem-solving skills. You will work in a collaborative environment where continuous learning is encouraged.</p><h4><strong>Responsibilities</strong></h4><ul><li><strong>Software Development</strong>: Write clean, maintainable, and efficient code while following best practices. Develop software solutions that meet client requirements.</li><li><strong>System Design</strong>: Design and implement scalable and high-performance software systems, keeping in mind both functional and non-functional requirements.</li><li><strong>Collaboration</strong>: Work closely with product managers, quality assurance engineers, and business analysts to gather requirements and deliver the final product.</li><li><strong>Troubleshooting &amp; Debugging</strong>: Identify and resolve issues within existing software, providing necessary patches and updates as required.</li><li><strong>Code Review &amp; Best Practices</strong>: Participate in regular code reviews, provide constructive feedback, and ensure code quality standards are upheld across the team.</li><li><strong>Documentation</strong>: Maintain clear and thorough documentation for software, ensuring it is understandable for other engineers and stakeholders.</li></ul><h4><strong>Skills &amp; Qualifications</strong></h4><ul><li>Bachelor’s degree in Computer Science, Engineering, or related field.</li><li>2-4 years of experience in software development.</li><li>Proficiency in at least one of the following programming languages: Java, Python, C++, or JavaScript.</li><li>Strong understanding of data structures, algorithms, and software design patterns.</li><li>Familiarity with relational databases (e.g., MySQL, PostgreSQL) and web services (REST, SOAP).</li><li>Experience with version control tools such as Git.</li></ul>','Senior Software Engineer','Full-time','2024-12-06 01:30:30.865000','Office-Only','10000-40000',38,37,51),(37,'<p>Amazon is looking for a <strong>Customer Support Representative</strong> who will play a key role in providing exceptional service to our customers. You will handle customer inquiries, resolve issues related to orders, returns, and product information, and ensure that every customer receives the best possible experience.</p><h4><strong>Responsibilities</strong></h4><ul><li><strong>Customer Interaction</strong>: Respond to customer inquiries via phone, email, and live chat, providing accurate information and resolving issues promptly.</li><li><strong>Issue Resolution</strong>: Investigate and resolve customer complaints, processing returns and exchanges, and ensuring the best possible outcome for the customer.</li><li><strong>Product Knowledge</strong>: Develop and maintain a strong understanding of Amazon’s products and services to assist customers effectively.</li><li><strong>Problem Solving</strong>: Troubleshoot technical issues, guiding customers through the solution process.</li><li><strong>Customer Feedback</strong>: Collect customer feedback and report trends to management to improve the service provided.</li><li><strong>Documentation</strong>: Keep detailed records of customer interactions, issues, and resolutions using the CRM system.</li></ul><h4><strong>Skills &amp; Qualifications</strong></h4><ul><li>High school diploma or equivalent; associate’s or bachelor’s degree preferred.</li><li>1-2 years of experience in a customer service or support role.</li><li>Excellent communication skills and a customer-first attitude.</li><li>Strong problem-solving abilities and attention to detail.</li><li>Ability to work in a fast-paced and dynamic environment.</li></ul><hr>','Customer Support Representative','Part-time','2024-12-06 01:31:49.857000','Office-Only','10000-20000',39,38,51),(38,'<h4><strong>About TCS:</strong></h4><p>Tata Consultancy Services (TCS) is a global leader in IT services, consulting, and business solutions. Renowned for delivering innovative solutions and exceptional customer service, TCS is committed to fostering a positive and inclusive workplace.</p><hr><h4><strong>Role Overview:</strong></h4><p>As a <strong>Customer Support Representative</strong>, you will be the first point of contact for customers, ensuring that their queries and issues are resolved promptly and effectively. You will represent TCS\'s commitment to exceptional service while maintaining professionalism, empathy, and problem-solving expertise.</p><hr><h4><strong>Key Responsibilities:</strong></h4><ul><li>Respond promptly to customer inquiries via phone, email, or chat.</li><li>Provide accurate, valid, and complete information by utilizing company resources.</li><li>Resolve product or service issues by clarifying customer complaints and determining the best course of action.</li><li>Escalate unresolved issues to the appropriate internal teams, ensuring timely resolution.</li><li>Maintain a thorough understanding of company policies, procedures, and services to effectively address customer concerns.</li><li>Document customer interactions, feedback, and resolutions in the company’s CRM system.</li><li>Identify opportunities to improve the customer experience and proactively suggest solutions.</li><li>Support team goals by meeting or exceeding individual performance metrics (e.g., response time, customer satisfaction scores).</li></ul><hr><h4><strong>Qualifications:</strong></h4><ul><li><strong>Education:</strong> A Bachelor’s degree in any discipline (preferred).</li><li><strong>Experience:</strong> 1-2 years of experience in customer service or a related field is advantageous but not mandatory.</li><li><strong>Skills:</strong><ul><li>Strong communication skills (verbal and written) in English (additional languages are a plus).</li><li>Active listening and problem-solving abilities.</li><li>Proficiency in basic computer applications and customer service software.</li><li>Ability to multitask, prioritize, and manage time effectively.</li><li>High levels of patience and empathy while interacting with customers.</li></ul></li></ul><hr><h4><strong>Preferred Attributes:</strong></h4><ul><li>Familiarity with CRM tools like Salesforce, Zendesk, or ServiceNow.</li><li>Basic technical knowledge related to IT services or products.</li><li>Flexibility to work in rotational shifts, including weekends and holidays, if required.</li></ul><hr><h4><strong>Benefits:</strong></h4><ul><li>Competitive salary with performance-based incentives.</li><li>Comprehensive training and development programs.</li><li>Opportunities for career growth within TCS.</li><li>Access to health and wellness programs.</li><li>A diverse and inclusive workplace culture.</li></ul><hr>','Customer Support Representative','Part-time','2024-12-06 14:33:33.243000','Office-Only','10000-20000',40,39,41),(39,'<h3><strong>Job Summary:</strong></h3><p>We are seeking a dynamic and results-driven <strong>Human Resource Executive</strong> to join our team at TCS. The ideal candidate will play a pivotal role in managing HR functions, fostering a positive workplace culture, and driving strategic HR initiatives aligned with organizational goals.</p><hr><h3><strong>Key Responsibilities:</strong></h3><ol><li><p><strong>Talent Acquisition &amp; Recruitment:</strong></p><ul><li>Manage the end-to-end recruitment process, including sourcing, screening, interviewing, and onboarding.</li><li>Partner with hiring managers to identify staffing needs and create job descriptions.</li><li>Drive campus recruitment and employer branding activities.</li></ul></li><li><p><strong>Employee Engagement:</strong></p><ul><li>Plan and execute employee engagement programs to enhance satisfaction and retention.</li><li>Address employee grievances and provide solutions in alignment with company policies.</li></ul></li><li><p><strong>Learning &amp; Development:</strong></p><ul><li>Identify skill gaps and implement training and development programs.</li><li>Facilitate leadership development and upskilling initiatives.</li></ul></li><li><p><strong>Performance Management:</strong></p><ul><li>Oversee performance appraisal processes, including setting KPIs and reviewing employee goals.</li><li>Assist managers in providing constructive feedback to employees.</li></ul></li><li><p><strong>Compliance &amp; Policies:</strong></p><ul><li>Ensure adherence to labor laws and company policies.</li><li>Update and communicate HR policies to employees regularly.</li></ul></li><li><p><strong>HR Analytics:</strong></p><ul><li>Utilize HR data to provide insights and support decision-making.</li><li>Track key metrics such as employee turnover, absenteeism, and engagement scores.</li></ul></li><li><p><strong>Compensation &amp; Benefits:</strong></p><ul><li>Assist in salary benchmarking, payroll processing, and administering employee benefits.</li><li>Handle rewards and recognition programs to motivate employees.</li></ul></li></ol><hr><h3><strong>Qualifications &amp; Skills:</strong></h3><ul><li>Bachelor’s/Master’s degree in Human Resource Management, Business Administration, or a related field.</li><li>Proven experience in HR or related roles is preferred (for experienced positions).</li><li>Strong knowledge of HR principles, labor laws, and regulations.</li><li>Proficiency in HR software and tools (e.g., SAP, Workday).</li><li>Excellent interpersonal, communication, and organizational skills.</li><li>Ability to handle confidential information with discretion.</li><li>Problem-solving mindset with strong analytical skills.</li></ul><hr><h3><strong>Why Join TCS?</strong></h3><ul><li>A global leader in IT services and consulting with a strong commitment to employee growth and development.</li><li>Exposure to diverse and challenging HR projects in a dynamic work environment.</li><li>Competitive salary and comprehensive benefits package.</li><li>Opportunities for career advancement within a global organization.<span style=\"font-family: Impact;\">﻿</span></li></ul>','Human Resources','Full-time','2024-12-06 14:35:22.943000','Office-Only','10000-40000',41,40,41),(40,'<p>As a <strong>Data Scientist</strong> at Tata Consultancy Services, you will leverage advanced analytics and machine learning techniques to extract insights from complex datasets, enabling data-driven decision-making for our clients. You will work closely with cross-functional teams to develop, implement, and optimize data models and solutions that drive innovation and business transformation.</p><hr><h3><strong>Key Responsibilities:</strong></h3><ul><li>Collaborate with stakeholders to identify business challenges and translate them into data science solutions.</li><li>Collect, preprocess, and analyze large datasets from diverse sources.</li><li>Develop and deploy machine learning models, including supervised and unsupervised algorithms.</li><li>Conduct exploratory data analysis (EDA) to uncover patterns and trends in data.</li><li>Build predictive models to support business forecasting and strategic planning.</li><li>Implement natural language processing (NLP) techniques for unstructured data analysis.</li><li>Visualize data insights using tools like Tableau, Power BI, or Python libraries (e.g., Matplotlib, Seaborn).</li><li>Optimize algorithms for efficiency and scalability in big data environments.</li><li>Work with cloud platforms (AWS, Azure, GCP) for model deployment and management.</li><li>Document processes and present findings to both technical and non-technical stakeholders.</li></ul><hr><h3><strong>Required Skills and Qualifications:</strong></h3><ul><li>Bachelor’s or Master’s degree in Computer Science, Statistics, Data Science, or a related field.</li><li>Proven experience in Python, R, or similar programming languages.</li><li>Strong proficiency in machine learning frameworks like TensorFlow, PyTorch, or Scikit-learn.</li><li>Hands-on experience with data visualization tools such as Tableau, Power BI, or Python visualization libraries.</li><li>Proficiency in querying languages like SQL.</li><li>Knowledge of big data tools and technologies like Hadoop, Spark, or Kafka is a plus.</li><li>Familiarity with cloud platforms (AWS, Azure, or GCP) for data storage and model deployment.</li><li>Strong analytical and problem-solving skills with an ability to translate business needs into technical solutions.</li><li>Excellent communication skills to convey insights effectively to stakeholders.</li></ul><hr><h3><strong>Preferred Skills:</strong></h3><ul><li>Experience with time-series analysis, recommendation systems, or deep learning.</li><li>Knowledge of Agile/Scrum methodologies.</li><li>Certification in data science or related technologies (e.g., AWS Certified Machine Learning – Specialty).</li></ul><hr><h3><strong>Why Join TCS?</strong></h3><ul><li>Work on cutting-edge projects with global clients across industries.</li><li>Access to world-class learning and development resources.</li><li>A culture that fosters innovation, collaboration, and career growth.</li><li>Competitive salary and benefits package.</li></ul><hr>','Data Scientist','Freelancer','2024-12-06 14:37:08.583000','Remote-Only','10000-30000',42,41,41),(41,'<h3><strong>Job Description:</strong></h3><p>Wipro is seeking a talented and innovative <strong>Data Scientist</strong> to join our team. The ideal candidate will leverage advanced analytics, machine learning, and statistical techniques to extract insights, solve complex business challenges, and drive data-driven decision-making. This role involves collaboration across cross-functional teams to deliver impactful solutions aligned with Wipro’s business objectives.</p><hr><h3><strong>Key Responsibilities:</strong></h3><ol><li><p><strong>Data Analysis &amp; Modeling:</strong></p><ul><li>Collect, process, and analyze large datasets to identify trends and patterns.</li><li>Develop predictive and prescriptive models using machine learning algorithms.</li></ul></li><li><p><strong>Machine Learning &amp; AI Implementation:</strong></p><ul><li>Build, train, and deploy machine learning models to address business needs.</li><li>Optimize model performance through iterative improvements and validation.</li></ul></li><li><p><strong>Business Insights &amp; Strategy:</strong></p><ul><li>Translate complex data insights into actionable strategies for stakeholders.</li><li>Partner with business units to solve domain-specific challenges using data-driven approaches.</li></ul></li><li><p><strong>Data Management &amp; Engineering:</strong></p><ul><li>Work closely with data engineers to ensure efficient data pipelines and storage solutions.</li><li>Ensure data quality, integrity, and security for all analytical processes.</li></ul></li><li><p><strong>Visualization &amp; Reporting:</strong></p><ul><li>Create interactive dashboards and reports to communicate findings effectively.</li><li>Present technical insights in a clear and concise manner to non-technical audiences.</li></ul></li></ol><hr><h3><strong>Required Skills &amp; Qualifications:</strong></h3><ul><li><p><strong>Educational Background:</strong></p><ul><li>Bachelor’s or Master’s degree in Data Science, Computer Science, Statistics, Mathematics, or a related field.</li><li>Ph.D. is a plus.</li></ul></li><li><p><strong>Technical Skills:</strong></p><ul><li>Proficiency in programming languages such as Python, R, or Java.</li><li>Expertise in SQL and experience with big data technologies (e.g., Hadoop, Spark).</li><li>Hands-on experience with machine learning frameworks (e.g., TensorFlow, PyTorch, Scikit-learn).</li><li>Familiarity with data visualization tools (e.g., Tableau, Power BI, Matplotlib).</li></ul></li><li><p><strong>Domain Knowledge:</strong></p><ul><li>Strong statistical analysis and mathematical modeling skills.</li><li>Experience in natural language processing (NLP), computer vision, or other specialized fields is an advantage.</li></ul></li><li><p><strong>Soft Skills:</strong></p><ul><li>Excellent problem-solving and critical-thinking abilities.</li><li>Strong communication and presentation skills.</li><li>Ability to work collaboratively in cross-functional teams.</li></ul></li></ul><hr><h3><strong>Preferred Experience:</strong></h3><ul><li>2–5 years of experience in a data science role, preferably in consulting or IT services.</li><li>Experience with cloud platforms like AWS, Azure, or Google Cloud.</li></ul><hr><h3><strong>Why Join Wipro?</strong></h3><p><p></p></p><ul><li>Opportunity to work with cutting-edge technologies and a global team of experts.</li><li>Comprehensive learning and development programs to enhance skills.</li><li>Collaborative and inclusive workplace culture.</li><li>Competitive compensation and benefits package.</li></ul>','Data Scientist','Freelancer','2024-12-06 14:53:41.118000','Partial-Remote','10000-30000',43,42,52),(42,'<p>Wipro is a leading global information technology, consulting, and business process services company. With a commitment to innovation and sustainability, we partner with clients to deliver superior digital solutions. As a Senior Software Engineer, you will play a crucial role in shaping the future of technology-driven businesses.</p><p><strong>Key Responsibilities:</strong></p><ol><li><strong>Software Development &amp; Design:</strong><ul><li>Architect, design, and develop scalable, high-quality software solutions.</li><li>Contribute to all phases of the software development lifecycle, including planning, coding, testing, and deployment.</li></ul></li><li><strong>Technical Leadership:</strong><ul><li>Provide guidance and mentorship to junior developers.</li><li>Conduct code reviews to ensure adherence to best practices and coding standards.</li></ul></li><li><strong>Innovation &amp; Problem-Solving:</strong><ul><li>Analyze complex business requirements and propose innovative technical solutions.</li><li>Identify and resolve performance bottlenecks, system failures, and integration challenges.</li></ul></li><li><strong>Collaboration:</strong><ul><li>Work closely with cross-functional teams, including product managers, QA, and UX/UI designers.</li><li>Collaborate with clients to understand project requirements and deliver customized solutions.</li></ul></li><li><strong>Quality Assurance &amp; Documentation:</strong><ul><li>Ensure robust and reliable systems by implementing effective testing strategies.</li><li>Document designs, processes, and solutions to maintain a knowledge base.</li></ul></li></ol><p><strong>Qualifications:</strong></p><ul><li><strong>Educational Background:</strong> Bachelor’s or Master’s degree in Computer Science, Engineering, or a related field.</li><li><strong>Experience:</strong><ul><li>5+ years of experience in software development.</li><li>Expertise in programming languages such as Java, Python, C#, or similar.</li><li>Proficiency with modern frameworks, libraries, and tools (e.g., Spring Boot, Angular, React, Kubernetes).</li></ul></li><li><strong>Skills:</strong><ul><li>Strong understanding of software architecture and design patterns.</li><li>Experience with cloud platforms like AWS, Azure, or GCP.</li><li>Familiarity with CI/CD pipelines, DevOps practices, and agile methodologies.</li><li>Excellent analytical and problem-solving skills.</li></ul></li><li><strong>Soft Skills:</strong><ul><li>Strong communication and teamwork abilities.</li><li>Leadership and project management capabilities.</li></ul></li></ul><p><strong>Why Join Wipro?</strong></p><ul><li>Work with a diverse, global team on cutting-edge technologies.</li><li>Competitive salary and benefits package.</li><li>Opportunities for professional growth and upskilling through Wipro’s learning platforms.</li><li>Inclusive and innovative work environment fostering creativity and collaboration.</li></ul><p><strong>How to Apply:</strong><br>Submit your application through <a rel=\"noopener\" href=\"#\">Wipro’s Careers Page</a> or contact [HR Email/Phone Number].</p><p>Wipro is an equal opportunity employer. We celebrate diversity and are committed to creating an inclusive environment for all employees.</p><hr>','Senior Software Engineer','Full-time','2024-12-06 14:53:25.018000','Office-Only','10000-40000',44,43,52);
/*!40000 ALTER TABLE `job_post_activity` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_seeker_apply`
--

DROP TABLE IF EXISTS `job_seeker_apply`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_seeker_apply` (
  `id` int NOT NULL AUTO_INCREMENT,
  `apply_date` datetime(6) DEFAULT NULL,
  `cover_letter` varchar(255) DEFAULT NULL,
  `job` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`job`),
  KEY `job_seeker_apply_ibfk_1` (`job`),
  CONSTRAINT `job_seeker_apply_ibfk_1` FOREIGN KEY (`job`) REFERENCES `job_post_activity` (`job_post_id`) ON DELETE CASCADE,
  CONSTRAINT `job_seeker_apply_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `job_seeker_profile` (`user_account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=24 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_seeker_apply`
--

LOCK TABLES `job_seeker_apply` WRITE;
/*!40000 ALTER TABLE `job_seeker_apply` DISABLE KEYS */;
INSERT INTO `job_seeker_apply` VALUES (22,'2024-12-06 14:41:42.201000',NULL,33,40),(23,'2024-12-06 14:42:10.614000',NULL,35,40);
/*!40000 ALTER TABLE `job_seeker_apply` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_seeker_profile`
--

DROP TABLE IF EXISTS `job_seeker_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_seeker_profile` (
  `user_account_id` int NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `employment_type` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `profile_photo` varchar(255) DEFAULT NULL,
  `resume` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  `work_authorization` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_account_id`),
  CONSTRAINT `job_seeker_profile_ibfk_1` FOREIGN KEY (`user_account_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_seeker_profile`
--

LOCK TABLES `job_seeker_profile` WRITE;
/*!40000 ALTER TABLE `job_seeker_profile` DISABLE KEYS */;
INSERT INTO `job_seeker_profile` VALUES (40,'Amritsar','India','Full-Time','Lvitpal ','Singh','lvitpal.jpg','Student Athlete Resume.pdf','Punjab','PIO (Person of Indian Origin)'),(42,'Amritsar','India','Full-Time','Aashiriya ','Mahajan ','aashiriya.jpg','Copy of First_Year_Resume.docx.pdf','Punjab','Indian Citizen'),(43,'Amritsar','India','Freelance','Nadarjot','Singh','nadarjot.jpg','CriminalJusticeResumedocx.pdf','Punjab','OCI (Overseas Citizen of India)'),(45,'Amritsar','India','Freelance','Karanpal','Singh','Karanpal.jpg','','Punjab','PIO (Person of Indian Origin)'),(46,'Amritsar','India','Part-Time','Tegnoor','Kaur','Noor.jpeg','','Punjab','Work Visa'),(47,'Amritsar','India','Full-Time','Joban','Singh','joban.jpg','','Punjab','OCI (Overseas Citizen of India)'),(48,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(50,'Amritsar','India','Full-Time','Sanjot','Kaur','background.jpeg','','Punjab','Indian Citizen'),(53,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(55,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(58,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(59,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(62,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(63,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(65,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(66,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(67,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(68,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(71,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `job_seeker_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `job_seeker_save`
--

DROP TABLE IF EXISTS `job_seeker_save`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `job_seeker_save` (
  `id` int NOT NULL AUTO_INCREMENT,
  `job` int DEFAULT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `user_id` (`user_id`,`id`),
  KEY `job_seeker_save_ibfk_1` (`job`),
  CONSTRAINT `job_seeker_save_ibfk_1` FOREIGN KEY (`job`) REFERENCES `job_post_activity` (`job_post_id`) ON DELETE CASCADE,
  CONSTRAINT `job_seeker_save_ibfk_2` FOREIGN KEY (`user_id`) REFERENCES `job_seeker_profile` (`user_account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `job_seeker_save`
--

LOCK TABLES `job_seeker_save` WRITE;
/*!40000 ALTER TABLE `job_seeker_save` DISABLE KEYS */;
INSERT INTO `job_seeker_save` VALUES (7,31,40),(8,37,40);
/*!40000 ALTER TABLE `job_seeker_save` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `newsletter_subscribers`
--

DROP TABLE IF EXISTS `newsletter_subscribers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `newsletter_subscribers` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `newsletter_subscribers`
--

LOCK TABLES `newsletter_subscribers` WRITE;
/*!40000 ALTER TABLE `newsletter_subscribers` DISABLE KEYS */;
/*!40000 ALTER TABLE `newsletter_subscribers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recruiter_profile`
--

DROP TABLE IF EXISTS `recruiter_profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `recruiter_profile` (
  `user_account_id` int NOT NULL,
  `city` varchar(255) DEFAULT NULL,
  `company` varchar(255) DEFAULT NULL,
  `country` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `profile_photo` varchar(255) DEFAULT NULL,
  `state` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_account_id`),
  CONSTRAINT `recruiter_profile_ibfk_1` FOREIGN KEY (`user_account_id`) REFERENCES `users` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recruiter_profile`
--

LOCK TABLES `recruiter_profile` WRITE;
/*!40000 ALTER TABLE `recruiter_profile` DISABLE KEYS */;
INSERT INTO `recruiter_profile` VALUES (41,'Amritsar','TCS','India','Azuni','Kaur','azuni.jpg','Punjab'),(44,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(51,'Amritsar','Infosys','India','Tejbir','Singh','ab04.c122948ac7bf15368325.jpg','Punjab'),(52,'Mumbai','Wipro','India','Rubinder','Kaur','rubinder.jpg','Maharashtra'),(54,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(56,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(57,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(60,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(61,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(64,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(69,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(70,NULL,NULL,NULL,NULL,NULL,NULL,NULL),(72,NULL,NULL,NULL,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `recruiter_profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `skills`
--

DROP TABLE IF EXISTS `skills`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `skills` (
  `id` int NOT NULL AUTO_INCREMENT,
  `experience_level` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `years_of_experience` varchar(255) DEFAULT NULL,
  `job_seeker_profile` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `job_seeker_profile` (`job_seeker_profile`),
  CONSTRAINT `skills_ibfk_1` FOREIGN KEY (`job_seeker_profile`) REFERENCES `job_seeker_profile` (`user_account_id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `skills`
--

LOCK TABLES `skills` WRITE;
/*!40000 ALTER TABLE `skills` DISABLE KEYS */;
INSERT INTO `skills` VALUES (12,'Intermediate','Java','2',50),(13,'Beginner','Python','1',50),(14,'Beginner','Photoshop','1',47),(15,'Advanced','Ms Word','2',47),(16,'Beginner','Python','1',46),(17,'Intermediate','C++','2',46),(18,'Beginner','Python','1',45),(19,'Intermediate','public  speaking','3',45),(20,'Beginner','Java','1',43),(21,'Intermediate','Microsoft Office','3',43),(22,'Beginner','Python','1',42),(23,'Advanced','AI /ML','3',42),(24,'Beginner','Data Analytic','2',42),(25,'Beginner','Python','1',40),(26,'Intermediate','Html','2',40),(27,'Intermediate','Css','3',40);
/*!40000 ALTER TABLE `skills` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `user_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `is_active` bit(1) DEFAULT NULL,
  `password` varchar(255) NOT NULL,
  `registration_date` datetime(6) DEFAULT NULL,
  `user_type_id` int DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email` (`email`),
  KEY `user_type_id` (`user_type_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`user_type_id`) REFERENCES `users_type` (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=73 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (40,'lvilohka@gmail.com',_binary '','$2a$10$M7Ln9iy99ZLgkofC1QfQzOaKMV9hZxMp4Jy5RsQGv2lmw0PO/GCr.','2024-12-05 01:06:00.647000',2),(41,'inuza6902@gmail.com',_binary '','$2a$10$arxNsJSblLcmuhBHZmzdIOdLPqxcnaiV6DcS9XDjcWX/s.w.fasI.','2024-12-05 01:08:00.190000',1),(42,'aashiriyavasu@gmail.com',_binary '','$2a$10$70nF6C0vl8Sz/nacslTazexply1Kodt.WDfVOQICLQoospmae8p7m','2024-12-05 01:09:33.250000',2),(43,'walianadarjotsingh1313@gmail.com',_binary '','$2a$10$MilX8c.SAbuIiDWNEnUcGOnAHMnCdjenWIxa5PyCw38QbAtz1Chca','2024-12-05 01:10:56.893000',2),(44,'contacttejbir@gmail.com',_binary '','$2a$10$O4IvzKxjYZpV8nnbLX2Gj.WsemtpRDGSp9cAwXsWyaggAXAppygUO','2024-12-05 01:13:09.136000',1),(45,'kpsdhillon108@gmail.com',_binary '','$2a$10$HMmuDVTyUHj.4WhyDmLmcuY4NZhrMo7LVCkfNnf/SojrOblSQNJn.','2024-12-05 01:15:27.786000',2),(46,'tegnoorkaur3572@gmail.com',_binary '','$2a$10$eVpFR8D.ck/faKlLwUtDx.fn081HsBsQnp1Rem5hoXjwA/SsuE7h2','2024-12-05 01:16:37.902000',2),(47,'jobansingh4691@gmail.com',_binary '','$2a$10$GibObZfZJVo6BgF6reL/8.JwEYPAdaEsXDuTQ2d8A5XOMhJvV03.i','2024-12-05 01:17:58.231000',2),(48,'sk19985374@gmail.com',_binary '','$2a$10$6F3R/i1./AQTFGU8J8wq.e9ACxtg.xBtsVY32odz3jvbP83xZEaPq','2024-12-05 01:19:03.074000',2),(50,'sanjotkaur6284@gmail.com',_binary '','$2a$10$DYL4m7mQAp8cxcUW.HzgVOU8xsbTJZt6l/e/bEsqrnNiDGkLYe7gK','2024-12-05 01:24:34.257000',2),(51,'sandhutejbir.55@gmail.com',_binary '','$2a$10$t8GcqsX1UzKH7btrwVZkA.qNR7etV/rI6Zjr80RVozfFMku/jU7M2','2024-12-05 13:09:55.548000',1),(52,'rubinder088@gmail.com',_binary '','$2a$10$7dTSkw6ZNYG/2PlIZt.oZOePEIXWT/5cRrjRnX4LM/baqLqyGOpPG','2024-12-06 13:27:52.639000',1),(53,'nitinnanda1290@gmail.com',_binary '','$2a$10$oABJ/sO4PH8bcue4z4mw4.L79CKannjuF9VOk61MfXbRUReos/sou','2024-12-06 13:29:09.126000',2),(54,'navpreetsinghcse@gmail.com',_binary '','$2a$10$Wua/Fg.BeJaEbyG0B3Ygye.4fn1N2uqW0wQskK1Gv812BC5ek.Uhq','2024-12-06 13:30:53.204000',1),(55,'randhawakomal940@gmail.com',_binary '','$2a$10$L.cIuOgwa.wl2Zpj18s0re9CeXyqrkQc1FuyD9JsnScserUuvZ2Ei','2024-12-06 13:31:53.630000',2),(56,'sainimuskan731@gmail.com',_binary '','$2a$10$cfrVFU7Upk8TVnB5r41Pvu39uf2NLxIebfXf6MqZJwMd/iXHHC5P2','2024-12-06 13:32:42.006000',1),(57,'manorma2523@gmail.com',_binary '','$2a$10$VscKxnD2JUAy8ae9r4.1ze5222GpXkRYumqS0dX59RAx87hD62DvO','2024-12-06 13:33:45.120000',1),(58,'bhatiashaina20@gmail.com',_binary '','$2a$10$YuvLYJXyTCC3hx8JdEHR0.atFMwhI/HzmJ2Rk0gZAUtuwuZl3cqKG','2024-12-06 13:34:32.618000',2),(59,'gillrubal221@gmail.com',_binary '','$2a$10$t.tRIxZldJhYtmVU3DV0.e80g0kY8Ryz3lMDioTOckt.NI96uI8ka','2024-12-06 13:35:19.459000',2),(60,'mahajanhridya007@gmail.com',_binary '','$2a$10$jWHFS/Phy7gcAznbsxqwY.cW8lJaByuSOWQMeNOCSgUNk5/iHryti','2024-12-06 13:36:33.128000',1),(61,'ekamkaur790@gmail.com',_binary '','$2a$10$UC8NFe8kiqymbdtg474GqukteCwv139ORXrqs30fjw779faSRS7mW','2024-12-06 13:37:53.093000',1),(62,'avneetkauruppal@gmail.com',_binary '','$2a$10$mw5Pw1ggKWjj3aYrAY.d2uSc18KPe/MUgCviiGhqVW59c2QpggY.a','2024-12-06 13:38:30.691000',2),(63,'tishatandon5@gmail.com',_binary '','$2a$10$8ifxdxkGQw7lzaUIzBh0f.bcgBFwXPVxhO3SlpqkJyRxArZyFzFVq','2024-12-06 13:39:13.965000',2),(64,'urbangabru777@gmail.com',_binary '','$2a$10$m0fV616/HxC2ACjP07xqMu9tl1t9uyMgEjPJorDqoHvr6tTO7xFHe','2024-12-06 13:44:02.194000',1),(65,'urbangabru77@gmail.com',_binary '','$2a$10$PT6KgiU1TtFhrkL2WJkfWOsW2.LGb0.mYMeOiXD.dvxPWEPvyITuy','2024-12-06 13:46:51.368000',2),(66,'nitish.mahajanhalf8@gmail.com',_binary '','$2a$10$QdStjiOhVuv.ROyF67IzJO2/Uf8bflbSjewXmRGJXNr5i5sFZLrvS','2024-12-06 13:47:39.880000',2),(67,'mehtakaran8872@gmail.com',_binary '','$2a$10$KwS1wPAqvRE85C6hPt7xw.DukVf0.8dyAmfO0eTUiYsYf.dJdWXwa','2024-12-06 13:48:14.730000',2),(68,'anureetk.2003@gmail.com',_binary '','$2a$10$lpiFguuvCIYlvUioJGQjjOx.xbTG638oFCInpbVxAzJgm1t48TKNq','2024-12-06 13:48:59.140000',2),(69,'shashisaroj1997@gmail.com',_binary '','$2a$10$Mp.be067SH3ZxpT7/fzRC.r6soeAnfUc6spjp2Nuxhgajdh5G70fG','2024-12-06 13:51:18.934000',1),(70,'sanjotkaurcse934@gmail.com',_binary '','$2a$10$ogSi1WwvCSrjBU1fKW7eheajSNWjB6qKJbPQUSRzlW.HinAH.9Akm','2024-12-06 13:52:16.675000',1),(71,'sudhambehl938@gmail.com',_binary '','$2a$10$r44Oyk7BF5mAiF9BN.TXIukMfyIzMe08lDZY36oD9Xx4Ef32pkT2G','2024-12-06 15:43:22.548000',2),(72,'sandhutejbir.556@gmail.com',_binary '','$2a$10$GjT2kIt1vWzoxXSpQghJl..Q6LwZDy/lGY9U1TdS6iwVFM0yMllK6','2024-12-06 15:47:57.616000',1);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_type`
--

DROP TABLE IF EXISTS `users_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_type` (
  `user_type_id` int NOT NULL AUTO_INCREMENT,
  `user_type_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_type_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_type`
--

LOCK TABLES `users_type` WRITE;
/*!40000 ALTER TABLE `users_type` DISABLE KEYS */;
INSERT INTO `users_type` VALUES (1,'Recruiter'),(2,'Job Seeker');
/*!40000 ALTER TABLE `users_type` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-06 16:26:59
