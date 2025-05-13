import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from '../../templates/menu/menu.component';
import { EmployeeService } from '../../services/employee.service';
import { HighestSalaryEmployee, YoungestEmployee } from '../../interfaces/employee.interface';
import { catchError, finalize, forkJoin, of } from 'rxjs';

@Component({
  selector: 'app-metrics',
  standalone: true,
  imports: [CommonModule, MenuComponent],
  templateUrl: './metrics.component.html',
  styleUrl: './metrics.component.css'
})
export class MetricsComponent implements OnInit {
  highestSalaryEmployee: HighestSalaryEmployee | null = null;
  youngestEmployee: YoungestEmployee | null = null;
  employeeCount: number = 0;
  
  loading = true;
  error = false;
  errorMessage = '';

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.loadMetrics();
  }

  loadMetrics(): void {
    this.loading = true;
    this.error = false;
    
    forkJoin({
      highestSalary: this.employeeService.getEmployeeWithHighestSalary().pipe(
        catchError(error => {
          /* console.error('Error fetching highest salary employee', error); */
          return of(null);
        })
      ),
      lowestAge: this.employeeService.getEmployeeWithLowestAge().pipe(
        catchError(error => {
          /* console.error('Error fetching youngest employee', error); */
          return of(null);
        })
      ),
      countLastMonth: this.employeeService.getEmployeeCountLastMonth().pipe(
        catchError(error => {
          /* console.error('Error fetching employee count', error); */
          return of({ count: 0 });
        })
      )
    }).pipe(
      finalize(() => this.loading = false)
    ).subscribe({
      next: (results) => {
        this.highestSalaryEmployee = results.highestSalary;
        this.youngestEmployee = results.lowestAge;
        this.employeeCount = results.countLastMonth?.count || 0;
      },
      error: (err) => {
        this.error = true;
        this.errorMessage = 'Error al cargar métricas. Por favor, intente nuevamente.';
        /* console.error('Error loading metrics:', err); */
      }
    });
  }
}