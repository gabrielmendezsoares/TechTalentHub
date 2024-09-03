import { Component, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  loginError: string | null = null;

  constructor(
    private authService: AuthService,
    private router: Router,
    private fb: FormBuilder
  ) { }

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.loginForm.valid) {
      this.authService.login(this.loginForm.value).subscribe({
        next: (success: boolean) => {
          if (success) {
            this.router.navigate(['/job-list']);
          } else {
            this.loginError = 'Nome de usuário ou senha incorretos.';
          }
        },
        error: () => {
          this.loginError = 'Ocorreu um erro ao tentar fazer login. Por favor, tente novamente.';
        }
      });
    }
  }

  redirectToRegister(): void {
    this.router.navigate(['/register']);
  }
}