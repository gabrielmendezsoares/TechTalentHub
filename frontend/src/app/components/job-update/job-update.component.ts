import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JobService } from '../../services/job.service';
import { AuthService } from '../../services/auth.service';
import { Job } from '../../models/job.model';

@Component({
  selector: 'app-job-update',
  templateUrl: './job-update.component.html',
  styleUrls: ['./job-update.component.scss']
})
export class JobUpdateComponent implements OnInit {
  jobForm: FormGroup;
  jobId: string | null = null;
  company: string | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private fb: FormBuilder,
    private jobService: JobService,
    private authService: AuthService
  ) {
    this.jobForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required],
      location: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadJob();
  }

  private loadJob(): void {
    this.jobId = this.route.snapshot.paramMap.get('id');
    if (this.jobId) {
      this.jobService.getJobById(this.jobId).subscribe({
        next: (job: Job) => {
          this.company = job.company;
          this.jobForm.patchValue({
            title: job.title,
            description: job.description,
            location: job.location
          });
        },
        error: (error) => {
          console.error('Erro ao carregar job:', error);
        }
      });
    } else {
      console.error('Job ID is null');
    }
  }

  onSubmit(): void {
    if (this.authService.isAdmin()) {
      if (this.jobForm.valid && this.jobId) {
        const updatedJob = {
          ...this.jobForm.value,
          company: this.company
        };
        this.jobService.updateJob(this.jobId, updatedJob).subscribe({
          next: () => {
            this.router.navigate(['/job-list']);
          },
          error: (error) => {
            console.error('Erro ao atualizar job:', error);
          }
        });
      }
    } else {
      console.error('Acesso negado: apenas administradores podem atualizar jobs.');
    }
  }
}