CREATE TABLE IF NOT EXISTS Einladung (
    EinladungID SERIAL PRIMARY KEY,
    EventID INTEGER REFERENCES Event (EventID),
    EingeladenerUserID INTEGER REFERENCES "User" (UserID),
    EinladungsStatus VARCHAR(50) NOT NULL
);
