CREATE TABLE IF NOT EXISTS Geschenk (
    GeschenkID SERIAL PRIMARY KEY,
    ListeID INTEGER REFERENCES Geschenkliste (ListeID),
    Name VARCHAR(255) NOT NULL,
    Preis DECIMAL(10, 2) NOT NULL,
    Menge INTEGER DEFAULT 0,
    ProduktLink VARCHAR(255),
    ReserviererUserID INTEGER REFERENCES "User" (UserID)
);
