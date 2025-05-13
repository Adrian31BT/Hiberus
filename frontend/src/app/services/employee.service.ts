import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Employee, HighestSalaryEmployee, YoungestEmployee, EmployeeCount, Department } from '../interfaces/employee.interface';

@Injectable({
  providedIn: 'root'
})
export class EmployeeService {
  private apiUrlEmployee = 'http://localhost:8080/api/employee';

  constructor(private http: HttpClient) { }

  getEmployeeWithHighestSalary(): Observable<HighestSalaryEmployee> {
    return this.http.get<HighestSalaryEmployee>(`${this.apiUrlEmployee}/highestSalary`);
  }

  getEmployeeWithLowestAge(): Observable<YoungestEmployee> {
    return this.http.get<YoungestEmployee>(`${this.apiUrlEmployee}/lowerAge`);
  }

  getEmployeeCountLastMonth(): Observable<EmployeeCount> {
    return this.http.get<number>(`${this.apiUrlEmployee}/countLastMonth`)
      .pipe(map(count => ({ count })));
  }

  getAllEmployees(): Observable<Employee[]> {
    return this.http.get<Employee[]>(`${this.apiUrlEmployee}/all`);
  }


  getDepartments(): Observable<Department[]> {
    // Lista hardcodeada de departamentos
    const departments: Department[] = [
      { departmentId: 1, departmentName: "Sistemas" },
      { departmentId: 2, departmentName: "Contabilidad" },
      { departmentId: 4, departmentName: "People" }
    ];
    
    // Devolver como Observable usando 'of'
    return of(departments);
  }

  createEmployee(departmentId: number, employee: any): Observable<any> {
    return this.http.post(`${this.apiUrlEmployee}/create/${departmentId}`, employee);
  }
}