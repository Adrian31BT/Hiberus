DROP TABLE IF EXISTS Employee;
DROP TABLE IF EXISTS Department;

CREATE TABLE Department (
    Department_id INT AUTO_INCREMENT PRIMARY KEY,
    Department_name VARCHAR(100) NOT NULL,
    Department_status VARCHAR(1) NOT NULL
);

CREATE TABLE Employee (
    Employee_id INT AUTO_INCREMENT PRIMARY KEY,
    Department_id INT,
    Employee_name VARCHAR(100) NOT NULL,
    Employee_last_name VARCHAR(100) NOT NULL,
    Age INT,
    Salary DECIMAL(10, 2),
    Init_date DATE,
    End_date DATE,
    Employee_status VARCHAR(1) NOT NULL,
    FOREIGN KEY (Department_id) REFERENCES Department(Department_id)
);