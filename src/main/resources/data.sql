insert into USERS (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, IS_ACTIVE)
values ('Laura', 'Oquendo', 'laura.oquendo', '{bcrypt}$2a$10$U8UCVtJVtahvbBqzvEiQnuYkjbPbKMh0nAk5UfKl6c1SsCSlu5Jcu', true);
insert into USERS (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, IS_ACTIVE)
values ('Shea', 'McFater', 'shea.mcfater', '{bcrypt}$2a$10$1PLBB9PoNNIS3DCAX5rRX.jBKULwkfik8NWWF2ZrRCnGMk1zgK.W6', true);
insert into USERS (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, IS_ACTIVE)
values ('Miquela', 'Trembley', 'miquela.trembley', '{bcrypt}$2a$10$2XyRIUzokwLdme4pJbNGr.DoPMrJUYeOzpheujN0IrMbl9TpVRooC', true);
insert into USERS (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, IS_ACTIVE)
values ('Roddy', 'Patman', 'roddy.patman', '{bcrypt}$2a$10$t/thTC/OvAJzZLq7n/weIuWVpLI1lYwZwiuttk/I1WHWdSWOaKQTi', true);
insert into USERS (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, IS_ACTIVE)
values ('Betteann', 'Staten', 'betteann.staten', '{bcrypt}$2a$10$fAGXmGOitlgf2ec7fxOfOeKtpRlDP3tcqT7qTkel91l2qYhEw1z3K', true);
insert into USERS (FIRST_NAME, LAST_NAME, USERNAME, PASSWORD, IS_ACTIVE)
values ('Ricardo', 'Patino', 'ricardo.patino', '{bcrypt}$2a$10$TgJORjmmAxr3f3jITjPxwuDj8A7rIk4dYJLbC0QK3.b5jpR/I9D6O', true);

insert into authorities (username, authority) values('laura.oquendo', 'ROLE_USER');
insert into authorities (username, authority) values('shea.mcfater', 'ROLE_USER');
insert into authorities (username, authority) values('miquela.trembley', 'ROLE_USER');
insert into authorities (username, authority) values('roddy.patman', 'ROLE_USER');
insert into authorities (username, authority) values('betteann.staten', 'ROLE_USER');
insert into authorities (username, authority) values('ricardo.patino', 'ROLE_USER');
insert into authorities (username, authority) values('ricardo.patino', 'ROLE_ADMIN');

insert into TRAINEE (BIRTHDATE, ADDRESS, USER_ID) values ('2001-05-15', '6612 Rockefeller Lane', 1);
insert into TRAINEE (BIRTHDATE, ADDRESS, USER_ID) values ('1993-08-24', '234 American Point', 2);
insert into TRAINEE (BIRTHDATE, ADDRESS, USER_ID) values ('1990-11-25', '0899 Stone Corner Circle', 3);

insert into TRAINING_TYPE (TRAINING_TYPE_NAME) values ('Fitness');
insert into TRAINING_TYPE (TRAINING_TYPE_NAME) values ('Yoga');
insert into TRAINING_TYPE (TRAINING_TYPE_NAME) values ('Zumba');
insert into TRAINING_TYPE (TRAINING_TYPE_NAME) values ('Stretching');
insert into TRAINING_TYPE (TRAINING_TYPE_NAME) values ('Resistance');

insert into TRAINER (SPECIALIZATION_ID, USER_ID) values (2, 4);
insert into TRAINER (SPECIALIZATION_ID, USER_ID) values (4, 5);
insert into TRAINER (SPECIALIZATION_ID, USER_ID) values (5, 6);

insert into TRAINEE2TRAINER (TRAINEE_ID, TRAINER_ID) values (1, 1);
insert into TRAINEE2TRAINER (TRAINEE_ID, TRAINER_ID) values (1, 2);
insert into TRAINEE2TRAINER (TRAINEE_ID, TRAINER_ID) values (2, 1);

insert into TRAINING (TRAINEE_ID, TRAINER_ID, TRAINING_NAME, TRAINING_TYPE_ID, TRAINING_DATE, TRAINING_DURATION) values (1, 1, 'Hard', 4, '2023-09-10', 94);
insert into TRAINING (TRAINEE_ID, TRAINER_ID, TRAINING_NAME, TRAINING_TYPE_ID, TRAINING_DATE, TRAINING_DURATION) values (1, 2, 'Easy', 1, '2023-02-08', 60);

