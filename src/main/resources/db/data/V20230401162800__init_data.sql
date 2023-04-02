insert into role (id, name)
VALUES (nextval('role_seq'), 'USER'),
       (nextval('role_seq'), 'MODERATOR'),
       (nextval('role_seq'), 'ADMIN');


insert into customer (id, name, last_name, birthday_date, email, password)
VALUES (nextval('customer_seq'), 'user', 'user', '2001-02-01', 'user@mail.ru',
        '$2a$10$6tkKF/p7/UGJGHCodvoCr.frkKaa7Byz/TMV0U4fGLhtNfJSQx9fK'),
       (nextval('customer_seq'), 'moderator', 'moderator', '2001-04-01', 'moderator@mail.ru',
        '$2a$10$6tkKF/p7/UGJGHCodvoCr.frkKaa7Byz/TMV0U4fGLhtNfJSQx9fK'),
       (nextval('customer_seq'), 'admin', 'admin', '2001-03-01', 'admin@mail.ru',
        '$2a$10$6tkKF/p7/UGJGHCodvoCr.frkKaa7Byz/TMV0U4fGLhtNfJSQx9fK');

insert into customer_role (customer_id, role_id)
VALUES (1, 1),
       (2, 3),
       (3, 3);

