import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, AbstractControl, ValidationErrors } from '@angular/forms';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { User } from '../../models/user.model';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  registerForm: FormGroup;
  registerError: string | null = null;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', [Validators.required, Validators.minLength(2), Validators.maxLength(50)]],
      email: ['', [Validators.required, Validators.email, Validators.pattern('^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$')]],
      password: ['', [Validators.required, Validators.minLength(6), Validators.maxLength(20)]],
      confirmPassword: ['', Validators.required]
    }, { validators: this.passwordMatchValidator });
  }

  ngOnInit(): void { }

  private passwordMatchValidator(control: AbstractControl): ValidationErrors | null {
    const password = control.get('password');
    const confirmPassword = control.get('confirmPassword');

    if (password && confirmPassword && password.value !== confirmPassword.value) {
      return { 'mismatch': true };
    }

    return null;
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      const { email, password, username } = this.registerForm.value;

      const newUser: User = {
        id: undefined,
        email: email,
        password: password,
        name: username,
        role: 'USER',
        applications: []
      };

      this.authService.register(newUser).subscribe({
        next: () => {
          this.router.navigate(['/job-list']);
        },
        error: (error) => {
          console.error('Erro ao registrar:', error);
          this.registerError = 'Erro ao registrar. Tente novamente mais tarde.';
        }
      });
    } else {
      this.registerError = 'Por favor, preencha o formulário corretamente.';
    }
  }

  redirectToLogin(): void {
    this.router.navigate(['/login']);
  }
}
