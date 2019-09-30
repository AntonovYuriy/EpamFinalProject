DROP USER provider;
CREATE USER provider IDENTIFIED BY 'internet';
GRANT ALL PRIVILEGES ON * . * TO provider@localhost;
FLUSH PRIVILEGES;