DROP TABLE IF EXISTS public.laptop_images;
DROP TABLE IF EXISTS public.order_to_laptop_join;
DROP TABLE IF EXISTS public.laptops;
DROP TABLE IF EXISTS public.brands;

DROP TABLE IF EXISTS public.orders;
DROP TABLE IF EXISTS public.user_roles CASCADE;
DROP TABLE IF EXISTS public.users;


DROP SEQUENCE IF EXISTS public.brands_seq;
DROP SEQUENCE IF EXISTS public.image_seq;
DROP SEQUENCE IF EXISTS public.item_seq;
DROP SEQUENCE IF EXISTS public.user_seq;
DROP SEQUENCE IF EXISTS public.order_seq;
DROP SEQUENCE IF EXISTS public.order_to_laptop_join_id_seq;

CREATE SEQUENCE public.order_to_laptop_join_id_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE public.order_to_laptop_join_id_seq
  OWNER TO "user";


CREATE SEQUENCE public.order_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE public.order_seq
  OWNER TO "user";

CREATE SEQUENCE public.brands_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE public.brands_seq
  OWNER TO "user";

CREATE SEQUENCE public.image_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE public.image_seq
  OWNER TO "user";

CREATE SEQUENCE public.item_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 11
CACHE 1;
ALTER TABLE public.item_seq
  OWNER TO "user";


CREATE SEQUENCE public.user_seq
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;
ALTER TABLE public.user_seq
  OWNER TO "user";


CREATE TABLE public.brands
(
  id          INTEGER           NOT NULL DEFAULT nextval('brands_seq' :: REGCLASS),
  short_name  CHARACTER VARYING NOT NULL,
  full_name   CHARACTER VARYING,
  description CHARACTER VARYING,
  CONSTRAINT pk_brands_id PRIMARY KEY (id),
  CONSTRAINT uc_brand_short_name UNIQUE (short_name)
)
WITH (
OIDS = FALSE
);
ALTER TABLE public.brands
  OWNER TO "user";


CREATE TABLE public.laptops
(
  id            INTEGER           NOT NULL DEFAULT nextval('item_seq' :: REGCLASS),
  model         CHARACTER VARYING,
  description   CHARACTER VARYING NOT NULL,
  price         INTEGER           NOT NULL DEFAULT 10,
  ram           INTEGER,
  cpu_frequency DOUBLE PRECISION,
  brand_id      INTEGER           NOT NULL,
  stock         INTEGER  NOT NULL DEFAULT 0,
  CONSTRAINT pk_laptops PRIMARY KEY (id),
  CONSTRAINT fk_brand_id FOREIGN KEY (brand_id)
  REFERENCES public.brands (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uc_laptop_model UNIQUE (model)
)
WITH (
OIDS = FALSE
);
ALTER TABLE public.laptops
  OWNER TO "user";
CREATE INDEX fki_brand_id
  ON public.laptops
  USING BTREE
  (brand_id);


CREATE TABLE public.laptop_images
(
  id        INTEGER           NOT NULL DEFAULT nextval('image_seq' :: REGCLASS),
  path character varying NOT NULL DEFAULT 'resources/img/laptop_photos/default.png'::character varying,
  laptop_id INTEGER           NOT NULL,
  CONSTRAINT pk_images PRIMARY KEY (id),
  CONSTRAINT fk_laptop_id FOREIGN KEY (laptop_id)
  REFERENCES public.laptops (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uc_image_path UNIQUE (laptop_id, path)
)
WITH (
OIDS = FALSE
);
ALTER TABLE public.laptop_images
  OWNER TO "user";
CREATE INDEX fki_laptop_id
  ON public.laptop_images
  USING BTREE
  (laptop_id);


CREATE TABLE public.users
(
  id      INTEGER           NOT NULL DEFAULT nextval('user_seq' :: REGCLASS),
  name    CHARACTER VARYING NOT NULL,
  password character varying NOT NULL,
  CONSTRAINT pk_user_id PRIMARY KEY (id),
  CONSTRAINT uc_user_name UNIQUE (name)
)
WITH (
OIDS = FALSE
);
ALTER TABLE public.users
  OWNER TO "user";
CREATE INDEX fki_user_name
  ON public.users
  USING BTREE
  (name);


CREATE TABLE public.user_roles
(
  id integer NOT NULL,
  name character varying NOT NULL,
  CONSTRAINT user_roles_pkey PRIMARY KEY (id),
  CONSTRAINT fk_user_id FOREIGN KEY (id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT uc_user_role_name UNIQUE (name, id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.user_roles
  OWNER TO "user";


CREATE TABLE public.orders
(
  id integer NOT NULL DEFAULT nextval('order_seq'::regclass),
  address character varying NOT NULL,
  account_id integer NOT NULL,
  received timestamp WITHOUT time zone NOT NULL DEFAULT now(),
  paid boolean NOT NULL DEFAULT false,
  CONSTRAINT pk_order_id PRIMARY KEY (id),
  CONSTRAINT fk_account_id FOREIGN KEY (account_id)
  REFERENCES public.users (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.orders
  OWNER TO "user";


CREATE TABLE public.order_to_laptop_join
(
  id integer NOT NULL DEFAULT nextval('order_to_laptop_join_id_seq'::regclass),
  order_id integer NOT NULL,
  laptop_id integer NOT NULL,
  quantity integer NOT NULL,
  CONSTRAINT pk_order_laptop_join PRIMARY KEY (id),
  CONSTRAINT fk_laptop_id_ FOREIGN KEY (laptop_id)
  REFERENCES public.laptops (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk_order_ FOREIGN KEY (order_id)
  REFERENCES public.orders (id) MATCH SIMPLE
  ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT unq_order_details UNIQUE (order_id, laptop_id)
)
WITH (
OIDS=FALSE
);
ALTER TABLE public.order_to_laptop_join
  OWNER TO "user";
CREATE INDEX fki_laptop_id_
  ON public.order_to_laptop_join
  USING btree
  (laptop_id);
CREATE INDEX fki_order_
  ON public.order_to_laptop_join
  USING btree
  (order_id);