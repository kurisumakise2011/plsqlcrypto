-- SEQUENCE: public.signed_documents_id_seq

-- DROP SEQUENCE public.signed_documents_id_seq;

CREATE SEQUENCE IF NOT EXISTS public.signed_documents_id_seq;

ALTER SEQUENCE public.signed_documents_id_seq
    OWNER TO postgres;

-- Table: public.signed_documents

-- DROP TABLE public.signed_documents;

CREATE TABLE IF NOT EXISTS public.signed_documents
(
    id bigint NOT NULL DEFAULT nextval('signed_documents_id_seq'::regclass),
    public_key character varying COLLATE pg_catalog."default",
    pqa character varying COLLATE pg_catalog."default",
    data bytea
    CONSTRAINT signed_documents_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.signed_documents
    OWNER to postgres;