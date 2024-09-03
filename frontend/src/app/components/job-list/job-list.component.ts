import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { JobService } from '../../services/job.service';
import { AuthService } from '../../services/auth.service';
import { Job } from '../../models/job.model';

@Component({
  selector: 'app-job-list',
  templateUrl: './job-list.component.html',
  styleUrls: ['./job-list.component.scss']
})
export class JobListComponent implements OnInit {
  jobs: Job[] = [];

  constructor(
    private jobService: JobService,
    private router: Router,
    private authService: AuthService
  ) { }

  ngOnInit(): void {
    this.loadJobs();
  }

  viewJob(id: string): void {
    this.router.navigate(['/job-list', id]);
  }

  private loadJobs(): void {
    this.jobService.getJobs().subscribe({
      next: (jobs: Job[]) => {
        this.jobs = jobs;
      },
      error: (error) => {
        console.error('Erro ao carregar jobs:', error);
      }
    });
  }

  deleteJob(id: string): void {
    if (this.authService.isAdmin()) {
      this.jobService.deleteJob(id).subscribe({
        next: () => {
          this.jobs = this.jobs.filter(job => job.id !== id);
        },
        error: (error) => {
          console.error('Erro ao deletar job:', error);
        }
      });
    } else {
      console.error('Acesso negado: apenas administradores podem deletar jobs.');
    }
  }

  updateJob(id: string): void {
    this.router.navigate(['/job-update', id]);
  }
}
