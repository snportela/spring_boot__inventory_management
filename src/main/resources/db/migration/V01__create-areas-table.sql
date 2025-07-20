CREATE TABLE areas (
    area_id uuid DEFAULT gen_random_uuid() NOT NULL,
    name varchar(100) NOT NULL,
    created_at timestamp DEFAULT NOW(),
    updated_at timestamp DEFAULT NOW(),
    deleted_at timestamp NULL,

    CONSTRAINT areas_pk PRIMARY KEY (area_id)
);