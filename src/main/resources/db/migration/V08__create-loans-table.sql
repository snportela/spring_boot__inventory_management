CREATE TABLE loans (
   loan_id uuid default gen_random_uuid() NOT NULL,
   student_name varchar(100) NOT NULL,
   student_id varchar(15) NOT NULL,
   loan_date TIMESTAMPTZ NOT NULL,
   loan_status varchar(100) NOT NULL,
   observation text NULL,
   created_at TIMESTAMPTZ DEFAULT NOW(),
   updated_at TIMESTAMPTZ DEFAULT NOW(),
   deleted_at TIMESTAMPTZ,

   CONSTRAINT loan_pk PRIMARY KEY (loan_id)
);