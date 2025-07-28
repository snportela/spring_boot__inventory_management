CREATE TABLE areas (
    area_id uuid DEFAULT gen_random_uuid() NOT NULL,
    name varchar(100) NOT NULL,
    created_at TIMESTAMP DEFAULT TIMEZONE('UTC', NOW()),
    updated_at TIMESTAMP DEFAULT TIMEZONE('UTC', NOW()),
    deleted_at TIMESTAMP,

    CONSTRAINT areas_pk PRIMARY KEY (area_id)
);