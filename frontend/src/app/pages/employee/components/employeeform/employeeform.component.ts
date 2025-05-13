import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { Department } from '../../../../interfaces/employee.interface';
import { EmployeeService } from '../../../../services/employee.service';

@Component({
  selector: 'app-employeeform',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './employeeform.component.html',
  styleUrl: './employeeform.component.css'
})
export class EmployeeformComponent implements OnInit {
  @Input() departments: Department[] = [];
  @Output() employeeAdded = new EventEmitter<boolean>();

  employeeForm!: FormGroup;
  loading = false;
  error = false;
  errorMessage = '';

  constructor(
    private fb: FormBuilder,
    private employeeService: EmployeeService
  ) { }

  ngOnInit() {
    this.initForm();
    const modalElement = document.getElementById('addEmployeeModal');
    if (modalElement) {
      modalElement.addEventListener('hidden.bs.modal', () => {
        this.resetForm();
      });

      modalElement.addEventListener('show.bs.modal', () => {
        this.resetForm();
      });
    }
  }



  initForm() {
    this.employeeForm = this.fb.group({
      employeeName: ['', [Validators.required, Validators.minLength(2)]],
      employeeLastName: ['', [Validators.required, Validators.minLength(2)]],
      age: ['', [
        Validators.required,
        Validators.min(18),
        Validators.max(80),
        Validators.pattern('^[0-9]*$'),
        this.positiveNumberValidator
      ]],
      salary: ['', [
        Validators.pattern('^[0-9]+(\\.[0-9]{1,2})?$'),
        this.positiveNumberValidator
      ]],
      initDate: ['', [Validators.required, this.dateValidator]],
      endDate: [''],
      employeeStatus: ['A', Validators.required],
      departmentId: ['', Validators.required]
    });
  }

  positiveNumberValidator(control: AbstractControl): ValidationErrors | null {
    if (!control.value) {
      return null;
    }
    const value = parseFloat(control.value);
    return value < 0 ? { 'negativeNumber': true } : null;
  }

  dateValidator(control: any) {
    if (!control.value) {
      return null;
    }

    const datePattern = /^\d{4}-\d{2}-\d{2}$/;
    if (!datePattern.test(control.value)) {
      return { invalidDate: true };
    }

    const date = new Date(control.value);
    if (isNaN(date.getTime())) {
      return { invalidDate: true };
    }

    return null;
  }

  onSubmit() {
    if (this.employeeForm.invalid) {
      this.markFormGroupTouched(this.employeeForm);
      return;
    }

    this.loading = true;
    const formData = { ...this.employeeForm.value };
    const departmentId = formData.departmentId;
    delete formData.departmentId;

    this.employeeService.createEmployee(departmentId, formData).subscribe({
      next: () => {
        this.loading = false;
        this.employeeAdded.emit(true);
      },
      error: (err) => {
        this.loading = false;
        this.error = true;
        this.errorMessage = 'Error al crear el empleado';
      }
    });
  }

  markFormGroupTouched(formGroup: FormGroup) {
    Object.values(formGroup.controls).forEach(control => {
      control.markAsTouched();

      if (control instanceof FormGroup) {
        this.markFormGroupTouched(control);
      }
    });
  }

  resetForm() {
    this.employeeForm.reset({
      employeeName: '',
      employeeLastName: '',
      age: '',
      salary: '',
      initDate: '',
      endDate: '',
      employeeStatus: 'A',
      departmentId: ''
    });
    this.error = false;
    this.errorMessage = '';
    this.loading = false;
  }

  ngOnDestroy() {
    this.resetForm();
  }

  get nameInvalid() {
    return this.employeeForm.get('employeeName')?.invalid && this.employeeForm.get('employeeName')?.touched;
  }

  get lastNameInvalid() {
    return this.employeeForm.get('employeeLastName')?.invalid && this.employeeForm.get('employeeLastName')?.touched;
  }

  get ageInvalid() {
    return this.employeeForm.get('age')?.invalid && this.employeeForm.get('age')?.touched;
  }

  get salaryInvalid() {
    return this.employeeForm.get('salary')?.invalid && this.employeeForm.get('salary')?.touched;
  }

  get initDateInvalid() {
    return this.employeeForm.get('initDate')?.invalid && this.employeeForm.get('initDate')?.touched;
  }

  get departmentInvalid() {
    return this.employeeForm.get('departmentId')?.invalid && this.employeeForm.get('departmentId')?.touched;
  }
}