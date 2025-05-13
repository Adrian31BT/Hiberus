import { Component } from '@angular/core';
import { Router } from '@angular/router';
@Component({
  selector: 'app-wellcome',
  imports: [],
  templateUrl: './wellcome.component.html',
  styleUrl: './wellcome.component.css'
})
export class WellcomeComponent {
  constructor(private router: Router) {}

  navegarAHome() {
    this.router.navigate(['/metrics']);
  }
}
