CREATE TABLE cm_user
(
	id INTEGER NOT NULL PRIMARY KEY AUTO_INCREMENT,
	registration_id VARCHAR(255) NOT NULL,
	ip_address VARCHAR(20) NOT NULL
);

CREATE TABLE cm_message 
(
	id INTEGER NOT NULL PRIMARY KEY,
	ip_address_from VARCHAR(20) NOT NULL,
	message TEXT NOT NULL
);
