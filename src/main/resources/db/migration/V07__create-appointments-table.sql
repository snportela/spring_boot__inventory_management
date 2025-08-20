CREATE TABLE appointments (
      appointment_id uuid default gen_random_uuid() NOT NULL,
      lab_id uuid NOT NULL,
      requester_name varchar(100) NOT NULL,
      requester_id varchar(100) NOT NULL,
      requester_type varchar(100) NOT NULL,
      requester_email varchar(100) NOT NULL,
      checkin_date TIMESTAMPTZ NOT NULL,
      checkout_date TIMESTAMPTZ NOT NULL,
      observation text NULL,
      created_at TIMESTAMPTZ DEFAULT NOW(),
      updated_at TIMESTAMPTZ DEFAULT NOW(),
      deleted_at TIMESTAMPTZ,

      CONSTRAINT appointments_pk PRIMARY KEY (appointment_id),
      CONSTRAINT appointment_lab_fk FOREIGN KEY (lab_id) REFERENCES labs
);