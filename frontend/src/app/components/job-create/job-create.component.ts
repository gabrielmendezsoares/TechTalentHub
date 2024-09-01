import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JobService } from '../../services/job.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-job-create',
  templateUrl: './job-create.component.html',
  //  styleUrls: ['./job-create.component.css']
})
export class JobCreateComponent {
  jobForm: FormGroup;

  constructor(private fb: FormBuilder, private jobService: JobService, private router: Router) {
    this.jobForm = this.fb.group({
      title: ['', Validators.required],
      description: ['', Validators.required]
    });
  }

  onSubmit(): void {
    if (this.jobForm.valid) {
      this.jobService.createJob(this.jobForm.value).subscribe(() => {
        this.router.navigate(['/jobs']);
      });
    }
  }
}