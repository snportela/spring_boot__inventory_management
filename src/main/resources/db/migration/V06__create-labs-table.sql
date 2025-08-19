CREATE TABLE labs (
      lab_id uuid default gen_random_uuid() NOT NULL,
      name varchar(100) NOT NULL,
      created_at TIMESTAMPTZ DEFAULT NOW(),
      updated_at TIMESTAMPTZ DEFAULT NOW(),
      deleted_at TIMESTAMPTZ,

      CONSTRAINT labs_pk PRIMARY KEY (lab_id)
);