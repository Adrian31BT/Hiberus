import { Routes } from '@angular/router';
import { WellcomeComponent } from './pages/wellcome/wellcome.component';         
import { MetricsComponent } from './pages/metrics/metrics.component';
import { EmployeeComponent } from './pages/employee/employee.component';

export const routes: Routes = [
    { path: '', component: WellcomeComponent },
    { path: 'welcome', component: WellcomeComponent },
    { path: 'metrics', component: MetricsComponent },
    { path: 'employee', component: EmployeeComponent },
    { path: '**', redirectTo: '', pathMatch: 'full' },
];
