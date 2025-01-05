CREATE TABLE IF NOT EXISTS requests (
                                        id BIGSERIAL PRIMARY KEY,
                                        request_type VARCHAR(32) NOT NULL,
    status VARCHAR(32) NOT NULL,
    user_id UUID NOT NULL,
    email VARCHAR(255),
    additional_info TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    );

CREATE INDEX idx_requests_user_id ON requests (user_id);
CREATE INDEX idx_requests_status ON requests (status);
CREATE INDEX idx_requests_email ON requests (email);
