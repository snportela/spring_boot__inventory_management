CREATE TABLE loan_items (
    loan_id uuid NOT NULL,
    resource_id uuid NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    deleted_at TIMESTAMPTZ,

    CONSTRAINT loan_fk FOREIGN KEY (loan_id) REFERENCES loans,
    CONSTRAINT resource_fk FOREIGN KEY (resource_id) REFERENCES resources,
    CONSTRAINT loan_items_pk PRIMARY KEY (loan_id, resource_id)
);