CREATE TABLE Product (prodId VARCHAR(10), pname VARCHAR(20), price DECIMAL);
CREATE TABLE Depot (depId VARCHAR(10), addr VARCHAR(30), volume INTEGER);
CREATE TABLE Stock (prodId VARCHAR(10), depId VARCHAR(10), quantity INTEGER);

ALTER TABLE Product ADD CONSTRAINT pk_product PRIMARY KEY (prodId);
ALTER TABLE Depot ADD CONSTRAINT pk_depot PRIMARY KEY (depId);
ALTER TABLE Stock ADD CONSTRAINT pk_stock PRIMARY KEY (prodId, depId);

ALTER TABLE Stock ADD CONSTRAINT fk_stock_product FOREIGN KEY(prodId) REFERENCES Product(prodId);
ALTER TABLE Stock ADD CONSTRAINT fk_stock_depot FOREIGN KEY(depId) REFERENCES Depot(depId);

INSERT INTO Product VALUES ('p1', 'tape', '2.5');
INSERT INTO Product VALUES ('p2', 'tv', '250');
INSERT INTO Product VALUES ('p3', 'vcr', '80');

INSERT INTO Depot VALUES ('d1', 'New York', '9000');
INSERT INTO Depot VALUES ('d2', 'Syracuse', '6000');
INSERT INTO Depot VALUES ('d4', 'New York', '2000');

INSERT INTO Stock VALUES ('p1', 'd1', '1000');
INSERT INTO Stock VALUES ('p1', 'd2', '-100');
INSERT INTO Stock VALUES ('p1', 'd4', '1200');
INSERT INTO Stock VALUES ('p3', 'd1', '3000');
INSERT INTO Stock VALUES ('p3', 'd4', '2000');
INSERT INTO Stock VALUES ('p2', 'd4', '1500');
INSERT INTO Stock VALUES ('p2', 'd1', '-400');
INSERT INTO Stock VALUES ('p2', 'd2', '2000');
