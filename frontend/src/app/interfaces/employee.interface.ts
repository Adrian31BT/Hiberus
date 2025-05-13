export interface Department {
  departmentId: number;
  departmentName: string;
}

export interface Employee {
  employeeId: number;
  employeeName: string;
  employeeLastName: string;
  age: number;
  salary: number | null;
  initDate: string | null;
  endDate: string | null;
  employeeStatus: string;
  department: Department;
}

export interface HighestSalaryEmployee {
  employeeName: string;
  employeeLastName: string;
  salary: number;
}

export interface YoungestEmployee {
  employeeName: string;
  employeeLastName: string;
  age: number;
}

export interface EmployeeCount {
  count: number;
}