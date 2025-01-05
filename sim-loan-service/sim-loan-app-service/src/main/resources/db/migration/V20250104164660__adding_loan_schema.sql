CREATE TABLE IF NOT EXISTS loans (
                                     id UUID NOT NULL PRIMARY KEY,
                                     loan_amount DECIMAL(18, 2) NOT NULL,
    loan_type VARCHAR(64) NOT NULL,
    loan_status VARCHAR(32) NOT NULL,
    tenure INT NOT NULL,
    loan_start_date DATE NOT NULL,
    loan_end_date DATE NOT NULL,
    repayment_date DATE NOT NULL,
    interest_rate DECIMAL(5, 2) NOT NULL,
    total_amount DECIMAL(18, 2) NOT NULL,
    monthly_installment_amount DECIMAL(18, 2) NOT NULL,
    monthly_installment DECIMAL(18, 2) NOT NULL,
    total_installment DECIMAL(18, 2) NOT NULL,
    paid_amount DECIMAL(18, 2) NOT NULL,
    outstanding_amount DECIMAL(18, 2) NOT NULL,
    user_id UUID NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
    );

CREATE INDEX idx_loans_user_id ON loans (user_id);
CREATE INDEX idx_loans_status ON loans (loan_status);
