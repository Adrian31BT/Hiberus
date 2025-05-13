INSERT INTO Department (Department_name, Department_status) VALUES ('Sistemas', 'A');
INSERT INTO Department (Department_name, Department_status) VALUES ('Contabilidad', 'A');
INSERT INTO Department (Department_name, Department_status) VALUES ('RRHH', 'I');
INSERT INTO Department (Department_name, Department_status) VALUES ('People', 'A');


INSERT INTO Employee (Department_id, Employee_name, Employee_last_name, Age, Salary, Init_date, End_date, Employee_status) VALUES (1, 'Luis', 'Pérez', 22, 500.00, '2021-02-10', NULL, 'A');
INSERT INTO Employee (Department_id, Employee_name, Employee_last_name, Age, Salary, Init_date, End_date, Employee_status) VALUES (1, 'Maria', 'Gonzalez', 25, NULL, '2020-03-11', NULL, 'A');
INSERT INTO Employee (Department_id, Employee_name, Employee_last_name, Age, Salary, Init_date, End_date, Employee_status) VALUES (2, 'Pedro', 'Gómez', 30, NULL, '2020-03-11', '2024-05-20', 'I');
INSERT INTO Employee (Department_id, Employee_name, Employee_last_name, Age, Salary, Init_date, End_date, Employee_status) VALUES (2, 'José', 'López', 20, NULL, NULL, NULL, 'A'); 