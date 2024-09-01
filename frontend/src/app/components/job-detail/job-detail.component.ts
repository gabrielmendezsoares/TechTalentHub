import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JobService } from '../../services/job.service';

@Component({
  selector: 'app-job-detail',
  templateUrl: './job-detail.component.html',
  //  styleUrls: ['./job-detail.component.css']
})
export class JobDetailComponent implements OnInit {
  job: any;

  constructor(private route: ActivatedRoute, private jobService: JobService) { }

  ngOnInit(): void {
    const jobId = this.route.snapshot.paramMap.get('id');
    if (jobId) {
      this.jobService.getJob(jobId).subscribe(job => {
        this.job = job;
      });
    } else {
      console.error('Job ID is null');
    }
  }
}