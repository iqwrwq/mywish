CREATE TABLE IF NOT EXISTS Geschenkliste (
    ListeID SERIAL PRIMARY KEY,
    EventID INTEGER REFERENCES Event (EventID),
    ErstellerUserID INTEGER REFERENCES "User" (UserID),
    Name VARCHAR(255) NOT NULL,
    BerechtigungUserID INTEGER REFERENCES "User" (UserID)
);
