import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JobService } from '../../services/job.service';
import { Job } from '../../models/job.model';

@Component({
  selector: 'app-job-detail',
  templateUrl: './job-detail.component.html',
  styleUrls: ['./job-detail.component.scss']
})
export class JobDetailComponent implements OnInit {
  jobForm: FormGroup;
  job: Job | null = null;

  constructor(
    private route: ActivatedRoute,
    private fb: FormBuilder,
    private jobService: JobService
  ) {
    this.jobForm = this.fb.group({
      title: [{ value: '', disabled: true }, Validators.required],
      description: [{ value: '', disabled: true }, Validators.required],
      location: [{ value: '', disabled: true }, Validators.required],
      company: [{ value: '', disabled: true }, Validators.required]
    });
  }

  ngOnInit(): void {
    this.loadJob();
  }

  private loadJob(): void {
    const jobId = this.route.snapshot.paramMap.get('id');
    if (jobId) {
      this.jobService.getJobById(jobId).subscribe({
        next: (job: Job) => {
          this.job = job;
          this.jobForm.patchValue({
            title: job.title,
            description: job.description,
            location: job.location,
            company: job.company
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

  applyForJob(): void {
    if (this.job && this.job.company) {
      const subject = encodeURIComponent('Application for Job Position');
      const body = encodeURIComponent(`Dear ${this.job.company},\n\nI am interested in applying for the position of ${this.job.title}.\n\nBest regards,\n[Your Name]`);

      window.location.href = `mailto:${this.job.company}?subject=${subject}&body=${body}`;
    } else {
      console.error('Company email is not available');
    }
  }
}