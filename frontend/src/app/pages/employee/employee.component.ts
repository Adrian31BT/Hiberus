import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MenuComponent } from '../../templates/menu/menu.component';
import { EmployeeService } from '../../services/employee.service';
import { Employee, Department } from '../../interfaces/employee.interface';
import { EmployeeformComponent } from './components/employeeform/employeeform.component';
import { Modal } from 'bootstrap';

@Component({
  selector: 'app-employee',
  standalone: true,
  imports: [CommonModule, MenuComponent, EmployeeformComponent],
  templateUrl: './employee.component.html',
  styleUrl: './employee.component.css'
})
export class EmployeeComponent implements OnInit {
  employees: Employee[] = [];
  departments: Department[] = [];
  loading = true;
  error = false;
  errorMessage = '';
  showEmployeeForm = false;
  private modal: any;

  constructor(private employeeService: EmployeeService) {}

  ngOnInit(): void {
    this.loadEmployees();
    this.loadDepartments();
  }

  loadEmployees(): void {
    this.loading = true;
    this.error = false;
    
    this.employeeService.getAllEmployees().subscribe({
      next: (data) => {
        this.employees = data;
        this.loading = false;
      },
      error: (err) => {
        this.error = true;
        this.errorMessage = 'Error al cargar los empleados. Por favor, intente nuevamente.';
        this.loading = false;
        /* console.error('Error loading employees:', err); */
      }
    });
  }

  loadDepartments(): void {
    this.employeeService.getDepartments().subscribe({
      next: (data) => {
        this.departments = data;
      },
      error: (err) => {
        /* console.error('Error loading departments:', err); */
      }
    });
  }

  openAddEmployeeModal(): void {
    this.showEmployeeForm = true;
    setTimeout(() => {
      this.modal = new Modal(document.getElementById('addEmployeeModal')!);
      this.modal.show();
    }, 0);
  }

  onEmployeeAdded(success: boolean): void {
    if (success) {
      this.modal.hide();
      this.loadEmployees();
      // Opcional: mostrar un mensaje de éxito
    }
  }

  getStatusClass(status: string): string {
    return status === 'A' ? 'badge bg-success' : 'badge bg-danger';
  }

  getStatusText(status: string): string {
    return status === 'A' ? 'Activo' : 'Inactivo';
  }

  getFormattedDate(date: string | null): string {
    if (!date) return 'N/A';
    return new Date(date).toLocaleDateString();
  }
}