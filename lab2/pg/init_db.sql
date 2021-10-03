CREATE TABLE public.users (
	id int4 NOT NULL,
	"name" varchar NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)
);

INSERT INTO public.users (id, "name")
VALUES(1, 'vasya');

INSERT INTO public.users (id, "name")
VALUES(2, 'petya');

commit;