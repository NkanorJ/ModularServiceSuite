CREATE TABLE IF NOT EXISTS users (

    id UUID NOT NULL PRIMARY KEY,
    first_name VARCHAR(64) NOT NULL,
    last_name VARCHAR(64) NOT NULL,
    date_of_birth DATE,
    gender VARCHAR(10),
    password VARCHAR(255),
    email VARCHAR(56),
    mobile VARCHAR(16),
    secret VARCHAR(255),
    public_id UUID,
    deleted BOOLEAN DEFAULT FALSE,
    role VARCHAR(10) DEFAULT 'USER',
    CONSTRAINT unique_email UNIQUE(email),
    CONSTRAINT unique_mobile UNIQUE(mobile)
    );

CREATE INDEX idx_users_email ON users (email);
CREATE INDEX idx_users_mobile ON users (mobile);
