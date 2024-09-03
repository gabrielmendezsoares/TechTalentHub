import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { JobService } from '../../services/job.service';
import { AuthService } from '../../services/auth.service'; // Supondo que você tenha um serviço de autenticação
import { Job } from '../../models/job.model';

@Component({
  selector: 'app-job-create',
  templateUrl: './job-create.component.html',
  styleUrls: ['./job-create.component.scss']
})
export class JobCreateComponent implements OnInit {
  jobForm: FormGroup;
  errorMessage: string | null = null; // Variável para armazenar a mensagem de erro

  constructor(
    private fb: FormBuilder,
    private jobService: JobService,
    private router: Router,
    private authService: AuthService // Injetando o serviço de autenticação
  ) {
    this.jobForm = this.fb.group({
      title: ['', [Validators.required, Validators.minLength(2)]],
      description: ['', [Validators.required, Validators.minLength(10)]],
      location: ['', [Validators.required, Validators.minLength(2)]],
      company: ['', [Validators.required, Validators.minLength(2)]]
    });
  }

  ngOnInit(): void { }

  onSubmit(): void {
    this.errorMessage = null; // Resetando a mensagem de erro

    if (!this.authService.isAdmin()) { // Verificando se o usuário é um administrador
      this.errorMessage = 'Acesso negado. Apenas administradores podem criar vagas.';
      return;
    }

    if (this.jobForm.valid) {
      const { title, description, location, company } = this.jobForm.value;

      const newJob: Job = {
        id: undefined,
        title,
        description,
        location,
        company
      };

      this.jobService.createJob(newJob).subscribe({
        next: () => {
          this.router.navigate(['/job-list']);
        },
        error: (error) => {
          console.error('Erro ao criar vaga:', error);
        }
      });
    }
  }
}
