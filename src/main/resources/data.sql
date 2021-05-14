DROP TABLE IF EXISTS user;

CREATE TABLE user (
    id INT AUTO_INCREMENT PRIMARY KEY,
    login VARCHAR(50) NOT NULL,
    role VARCHAR(50) NOT NULL
);

INSERT INTO user (id, login, role) VALUES
(-1,'arek','ADMIN'),
(-2,'zioms','CLIENT'),
(-3,'noob','CLIENT');