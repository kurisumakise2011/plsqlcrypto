-- SEQUENCE: public.security_docs_id_seq

-- DROP SEQUENCE public.security_docs_id_seq;

CREATE SEQUENCE IF NOT EXISTS public.security_docs_id_seq;

ALTER SEQUENCE public.security_docs_id_seq
    OWNER TO postgres;

-- Table: public.security_docs

-- DROP TABLE public.security_docs;

CREATE TABLE IF NOT EXISTS public.security_docs
(
    id bigint NOT NULL DEFAULT nextval('security_docs_id_seq'::regclass),
    title character varying(255) COLLATE pg_catalog."default",
    created_at timestamp with time zone,
    text bytea,
    CONSTRAINT security_docs_pkey PRIMARY KEY (id)
)
    WITH (
        OIDS = FALSE
    )
    TABLESPACE pg_default;

ALTER TABLE public.security_docs
    OWNER to postgres;

CREATE TABLE IF NOT EXISTS signed_documents(

);