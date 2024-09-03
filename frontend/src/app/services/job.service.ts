import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobService {
  private apiUrl = 'http://localhost:8080/jobs';
  private username = 'user';
  private password = 'password';

  constructor(private http: HttpClient) { }

  private getAuthHeaders(): HttpHeaders {
    const auth = btoa(`${this.username}:${this.password}`);

    return new HttpHeaders({
      'Authorization': `Basic ${auth}`
    });
  }

  getJobs(): Observable<any[]> {
    const headers = this.getAuthHeaders();

    return this.http.get<any[]>(this.apiUrl, { headers });
  }

  getJobById(id: string): Observable<any> {
    const headers = this.getAuthHeaders();

    return this.http.get<any>(`${this.apiUrl}/${id}`, { headers });
  }

  createJob(job: any): Observable<any> {
    const headers = this.getAuthHeaders();

    return this.http.post<any>(this.apiUrl, job, { headers });
  }

  updateJob(id: string, job: any): Observable<any> {
    const headers = this.getAuthHeaders();

    return this.http.put<any>(`${this.apiUrl}/${id}`, job, { headers });
  }

  deleteJob(id: string): Observable<any> {
    const headers = this.getAuthHeaders();

    return this.http.delete<any>(`${this.apiUrl}/${id}`, { headers });
  }
}
