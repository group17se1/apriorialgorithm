DROP TABLE IF EXISTS settings;
DROP TABLE IF EXISTS transactions;
DROP TABLE IF EXISTS associations;

CREATE TABLE IF NOT EXISTS settings (
    settings_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    support_level FLOAT NOT NULL,
    confidence_level FLOAT NOT NULL
);

CREATE TABLE IF NOT EXISTS transactions (
    transaction_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    transaction_store VARCHAR(40),
    transaction_start_date VARCHAR(20),
    transaction_end_date VARCHAR(20),
    transaction_set VARCHAR(500)
);

CREATE TABLE IF NOT EXISTS associations (
    association_ID INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    support_level FLOAT,
    confidence_level FLOAT,
    consequent VARCHAR(500),
    antecedent VARCHAR(500),
    transaction_ID INT
);