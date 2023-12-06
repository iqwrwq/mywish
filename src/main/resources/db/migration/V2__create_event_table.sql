CREATE TABLE IF NOT EXISTS Event (
    EventID SERIAL PRIMARY KEY,
    ErstellerUserID INTEGER REFERENCES "User" (UserID),
    Pflichtname VARCHAR(255) NOT NULL,
    Datum DATE,
    Beschreibung TEXT,
    Ort VARCHAR(255),
    Adresse VARCHAR(255)
);
