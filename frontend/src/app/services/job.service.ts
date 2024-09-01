import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobService {
  private apiUrl = 'http://localhost:3000/api/jobs';

  constructor(private http: HttpClient) { }

  getJobs(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  getJob(id: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  createJob(job: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, job);
  }

  updateJob(id: string, job: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, job);
  }

  deleteJob(id: string): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }
}
