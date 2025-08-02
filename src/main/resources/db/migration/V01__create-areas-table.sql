CREATE TABLE areas (
    area_id uuid DEFAULT gen_random_uuid() NOT NULL,
    name varchar(100) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    deleted_at TIMESTAMPTZ,

    CONSTRAINT areas_pk PRIMARY KEY (area_id)
);